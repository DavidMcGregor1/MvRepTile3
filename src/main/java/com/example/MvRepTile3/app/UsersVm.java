package com.example.MvRepTile3.app;

public class UsersVm {


    public UsersVm(String theNewUsername, String theNewPassword, String theNewEmail) {
        username = theNewUsername;
        password = theNewPassword;
        email = theNewEmail;
    }


    public UsersVm() {

    }

    public UsersVm(int theNewUserId) {
        userId = theNewUserId;
    }

    public int userId;
    public String username;
    public String password;
    public String email;

}
