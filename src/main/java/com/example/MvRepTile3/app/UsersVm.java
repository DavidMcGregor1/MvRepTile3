package com.example.MvRepTile3.app;

public class UsersVm {


    public UsersVm(String theNewUsername, String theNewPassword) {
        username = theNewUsername;
        password = theNewPassword;
    }


    public UsersVm() {

    }

    public UsersVm(int theNewUserId) {
        userId = theNewUserId;
    }

    public long userId;
    public String username;
    public String password;

}
