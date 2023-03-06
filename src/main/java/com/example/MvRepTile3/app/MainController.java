package com.example.MvRepTile3.app;


import com.example.MvRepTile3.LogInVm;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.util.*;



@Controller
    public class MainController {
    public MainController(UsersRepository u, ExercisesRepository e, AuthorisationRepository au) {
        repositoryUsers = u;
        repositoryExercises = e;
        repositoryAuthorisation = au;
    }

    private UsersRepository repositoryUsers;
    private ExercisesRepository repositoryExercises;
    private AuthorisationRepository repositoryAuthorisation;


    @GetMapping("/newWorkout")
    public String greeting() {
        System.out.println("arrived at new workout page");
        return "newWorkout";
    }


    @GetMapping("/login")
    public String login() {
        System.out.println("arrived at login page");
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        System.out.println("arrived at home page");
        return "home";
    }

    @GetMapping("/viewWorkouts")
    public String viewWorkouts() {
        System.out.println("arrived at home page");
        return "viewWorkouts";
    }


    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @GetMapping("/uiTest")
    public String passwords(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {

        System.out.println("returned hello");
        return "passwords";
    }




    //    !---------- Get Users API    >   Reading From DataBase   ----------!
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @GetMapping("/getUsers")
    public String getUsers() {

        List<Users> allDbEntries = repositoryUsers.findAll();

        String result = "here >";

        for (int i = 0; i < allDbEntries.stream().count(); i++) {
            Users a = allDbEntries.get(i);
            if (a != null) {
                result = result + a.getEmail();
            }
        }

        System.out.println("returned hello");
        return result;
    }




//    !---------- Add Exercise API    >   Posting To DataBase   ----------!

    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @PostMapping(path = "/apiAddExercise", consumes = "application/json", produces = "application/json")
    public ExercisesVm addExercise(@RequestBody ExercisesVm submittedExercise) {
        System.out.println("starting");

        Exercises newDataBaseExercise = new Exercises();
        newDataBaseExercise.setExerciseName(submittedExercise.exerciseName);

        repositoryExercises.save(newDataBaseExercise);

        return submittedExercise;
    }




    //    !---------- Hashing Algorithm ----------!
    private static final byte[] SALT = {
            (byte) 0x1, (byte) 0x2, (byte) 0x3, (byte) 0x4,(byte) 0x5, (byte) 0x6, (byte) 0x7, (byte) 0x8,
    };
    private static String base64Encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }
    private String encrypt(String stringToEncrypt) throws GeneralSecurityException, UnsupportedEncodingException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        String theSecret = "bob";

        System.out.println("String to encrypt: >> " + stringToEncrypt);

        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(theSecret.toCharArray()));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
        return base64Encode(pbeCipher.doFinal(stringToEncrypt.getBytes("UTF-8")));
    }




    //    !---------- Compare Passed In User To User In The DataBase Method ----------!
    private Users getUserForUsernameAndPasswordCheck(String username, String password) throws GeneralSecurityException, UnsupportedEncodingException {
        LogInVm newLogInVm = new LogInVm(username, password);

        List<Users> allUserEntries = repositoryUsers.findAll();

        String passwordToCheck = encrypt(password);

        Users result = null;

        for (int i = 0; i < allUserEntries.stream().count(); i++) {
            Users a = allUserEntries.get(i);


            if (a != null) {

                System.out.println(a.username.equals(newLogInVm.username));
                System.out.println(a.password.equals(passwordToCheck));

                if ((a.username.equals(newLogInVm.username)) && (a.password.equals(passwordToCheck))) {
                    System.out.println("Successfully logged in");
                    newLogInVm.succeeded = true;
                    result = a;

                    break;

                } else {
                    System.out.println("NO MATCH");
                    newLogInVm.succeeded = false;

                }

            }

        }

        return result;

    }



    //    !---------- Register API    >   Authorisation   ----------!
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @PostMapping(path = "/register", consumes = "application/json", produces = "application/json")
    public RegisterResponseVm register(@RequestBody RegisterUserVm submittedUser) throws GeneralSecurityException, UnsupportedEncodingException {
        System.out.println("register API called");

        System.out.println("submitted user name >> " + submittedUser.registerUsername);
        System.out.println("submitted user password >> " + submittedUser.registerPassword);
        System.out.println("submitted user role name >> " + submittedUser.RoleName);

        RegisterResponseVm newRegisterResponse = new RegisterResponseVm();

        String authToken1 = "authToken1";
        String roleNameA = "Admin";

       Users result = getUserForUsernameAndPasswordCheck(submittedUser.registerUsername, submittedUser.registerPassword);


        newRegisterResponse.RoleName = roleNameA;
        newRegisterResponse.authToken = authToken1;
        newRegisterResponse.id = 1;
        newRegisterResponse.succeeded =  true;




        if (newRegisterResponse.succeeded = true) {


            Authorisation newAuth = new Authorisation();

            newAuth.setAuthToken(newRegisterResponse.authToken);

            newAuth.setRoleName(newRegisterResponse.RoleName);
            newAuth.setUserId(result.getId());


            repositoryAuthorisation.save(newAuth);

            System.out.println("Saved to repo");

        }

        System.out.println("After if statement");

        return newRegisterResponse;
    }




//    !---------- Add User API   >   Posting To DataBase    ----------!

    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @PostMapping(path = "/apiAddUser", consumes = "application/json", produces = "application/json")
    public UsersVm addUser(@RequestBody UsersVm submittedUser) throws GeneralSecurityException, UnsupportedEncodingException {
        System.out.println("starting");
        String encryptedPassword = encrypt(submittedUser.password);

        Users newUser = new Users();
        newUser.setUsername(submittedUser.username);
        newUser.setPassword(encryptedPassword);

        System.out.println("encrypted password >" + " " + encryptedPassword);


        System.out.println("submitted user: ]"+submittedUser.username+"[");
        System.out.println("submitted password: ]"+submittedUser.password +"[");

        repositoryUsers.save(newUser);

        return submittedUser;
    }




    //        !---------- Delete Exercise API   >    Deleting From DataBase   ----------!

    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @DeleteMapping(path = "/apiDeleteExercise", consumes = "application/json", produces = "application/json")
    public boolean deleteExercise(@RequestBody ExercisesVm exerciseIdToDelete) throws GeneralSecurityException, UnsupportedEncodingException {
        System.out.println("starting");

         repositoryExercises.deleteById(exerciseIdToDelete.id);
            return true;
    }




//    !---------- Edit User API   >   Edit Entry In DataBase  ----------!

    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @PutMapping(path = "/apiEditUser", consumes = "application/json", produces = "application/json")
    public boolean editUser(@RequestBody UsersVm submittedUser) throws GeneralSecurityException, UnsupportedEncodingException {
        System.out.println("starting");

      Optional<Users> userToUpdate = repositoryUsers.findById(submittedUser.userId);

        System.out.println("id:" + submittedUser.userId);

      if(userToUpdate.isPresent()) {

          System.out.println("the object has been found from the database");

          if (submittedUser.username != null) {
              userToUpdate.get().username = submittedUser.username;
          }
          if (submittedUser.password != null) {
              userToUpdate.get().password = submittedUser.password;
          }

          repositoryUsers.save(userToUpdate.get());
      } else {
          System.out.println("The object has not been found from the database");
      }

        return true;
    }




    //        !---------- Frontend LogIn API ----------!
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @PostMapping(path = "/apiLogIn", consumes = "application/json", produces = "application/json")
    public boolean logIn(@RequestBody LogInVm newEntry) throws GeneralSecurityException, UnsupportedEncodingException {
        System.out.println("login API called");

        LogInVm newLogInVm = new LogInVm(newEntry.username, newEntry.password);

        List<Users> allUserEntries = repositoryUsers.findAll();

        String passwordToCheck = encrypt(newEntry.password);

        for (int i = 0; i < allUserEntries.stream().count(); i++) {
            Users a = allUserEntries.get(i);

            System.out.println("i = " + i);
            System.out.println("database.username = " + a.username);
            System.out.println("database.password = ]" + a.password+"[");

            System.out.println("inputted username = " + newLogInVm.username);
            System.out.println("inputted pass = " + newLogInVm.password);


            if (a != null) {

                System.out.println(a.username.equals(newLogInVm.username));
                System.out.println(a.password.equals(passwordToCheck));

                if ((a.username.equals(newLogInVm.username)) && (a.password.equals(passwordToCheck))) {
                    System.out.println("Successfully logged in");
                    newLogInVm.succeeded = true;
                    break;

                } else {
                    System.out.println("NO MATCH");
                    newLogInVm.succeeded = false;
                }

            }

        }
        System.out.println(java.time.LocalDateTime.now() + " " +  newLogInVm.username + " " + newLogInVm.password + " " + newLogInVm.succeeded);

        return newLogInVm.succeeded;

    }


}
