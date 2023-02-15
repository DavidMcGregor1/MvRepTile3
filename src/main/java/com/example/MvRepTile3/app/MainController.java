package com.example.MvRepTile3.app;


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
        public MainController() {

        }



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










    }



