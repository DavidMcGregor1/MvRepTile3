package com.example.MvRepTile3.app;

public class RegisterUserVm {


    public RegisterUserVm(String theRegisterUsername, String theRegisterPassword, String theRoleName) {
        registerUsername = theRegisterUsername;
        registerPassword = theRegisterPassword;
        RoleName = theRoleName;


    }

    public RegisterUserVm() {

    }

    public RegisterUserVm(int theId) {
        id = theId;
    }
    public long id;
    public String registerUsername;
    public String registerPassword;
    public String RoleName;







}
