package com.example.MvRepTile3.app;


import com.example.MvRepTile3.LogInVm;
import org.apache.catalina.User;
import org.hibernate.tool.schema.SourceType;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.SecureRandom;
import java.util.*;



@Controller
    public class MainController {
    public MainController(UsersRepository r, ExercisesRepository p) {
        repo = r;
        repo2 = p;
    }

    private UsersRepository repo;
    private ExercisesRepository repo2;


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

    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @GetMapping("/getUsers")
    public String getUsers() {

        List<Users> allDbEntries = repo.findAll();

        String result = "bob ";

        for (int i = 0; i < allDbEntries.stream().count(); i++) {
            Users a = allDbEntries.get(i);
            if (a != null) {
                result = result + a.getEmail();
            }
        }


        System.out.println("returned hello");
        return result;
    }


//    !---------- Add Exercise API ----------!

    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @PostMapping(path = "/apiAddExercise", consumes = "application/json", produces = "application/json")
    public ExercisesVm addExercise(@RequestBody ExercisesVm submittedExercise) {
        System.out.println("starting");

        Exercises newDataBaseExercise = new Exercises();
        newDataBaseExercise.setExerciseName(submittedExercise.exerciseName);

        repo2.save(newDataBaseExercise);

        return submittedExercise;
    }


    //    !---------- Add User API ----------!
    private static final byte[] SALT = {
            (byte) 0x1, (byte) 0x2, (byte) 0x3, (byte) 0x4,(byte) 0x5, (byte) 0x6, (byte) 0x7, (byte) 0x8,
    };
    private static String base64Encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }
    private String encrypt(String property) throws GeneralSecurityException, UnsupportedEncodingException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        String bob = "bob";

        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(bob.toCharArray()));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
        return base64Encode(pbeCipher.doFinal(property.getBytes("UTF-8")));
    }

    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @PostMapping(path = "/apiAddUser", consumes = "application/json", produces = "application/json")
    public NewUserVm addUser(@RequestBody NewUserVm submittedUser) throws GeneralSecurityException, UnsupportedEncodingException {
        System.out.println("starting");
        String encryptedPassword = encrypt(submittedUser.newPassword);

        Users newUser = new Users();
        newUser.setUsername(submittedUser.newUsername);
        newUser.setPassword(encryptedPassword);

        System.out.println(">" + encryptedPassword);

//        SecureRandom random = new SecureRandom();
//        byte[] salt = new byte[16];
//        random.nextBytes(salt);
//        KeySpec spec = new PBEKeySpec("password".toCharArray(), salt, 65536, 128);
//        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
//        byte[] hash = f.generateSecret(spec).getEncoded();
//        Base64.Encoder enc = Base64.getEncoder();
//        System.out.printf("salt: %s%n", enc.encodeToString(salt));
//        System.out.printf("hash: %s%n", enc.encodeToString(hash));






        System.out.println("submitted user: ]"+submittedUser.newUsername+"[");
        System.out.println("submitted password: ]"+submittedUser.newPassword+"[");

        repo.save(newUser);

        return submittedUser;
    }



//        !---------- LogIn API ----------!

    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @PostMapping(path = "/apiLogIn", consumes = "application/json", produces = "application/json")
    public boolean logIn(@RequestBody LogInVm newEntry) throws GeneralSecurityException, UnsupportedEncodingException {
        System.out.println("login API called");

        LogInVm newLogInVm = new LogInVm(newEntry.username, newEntry.password);

        List<Users> allUserEntries = repo.findAll();

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
