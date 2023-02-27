package com.example.MvRepTile3.app;


import com.example.MvRepTile3.LogInVm;
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

//        !---------- LogIn API ----------!

    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @PostMapping(path = "/apiLogIn", consumes = "application/json", produces = "application/json")
    public boolean logIn(@RequestBody LogInVm newEntry) {
        System.out.println("login API called");

        LogInVm newLogInVm = new LogInVm(newEntry.username, newEntry.password);

        List<Users> allUserEntries = repo.findAll();


        for (int i = 0; i < allUserEntries.stream().count(); i++) {
            Users a = allUserEntries.get(i);

            System.out.println("i = " + i);
            System.out.println("database.username = " + a.username);
            System.out.println("database.password = ]" + a.password+"[");

            System.out.println("inputted username = " + newLogInVm.username);
            System.out.println("inputted pass = " + newLogInVm.password);



            if (a != null) {

                System.out.println(a.username.equals(newLogInVm.username));
                System.out.println(a.password.equals(newLogInVm.password));

                if ((a.username.equals(newLogInVm.username)) && (a.password.equals(newLogInVm.password))) {
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
