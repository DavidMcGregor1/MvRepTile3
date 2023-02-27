package com.example.MvRepTile3.app;

public class NewUserVm {


    public NewUserVm(String theNewUsername, String theNewPassword) {
        newUsername = theNewUsername;
        newPassword = theNewPassword;
    }

    public NewUserVm() {

    }

    public NewUserVm(int theNewUserId) {
        newUsersId = theNewUserId;
    }

    public long newUsersId;
    public String newUsername;
    public String newPassword;

}
