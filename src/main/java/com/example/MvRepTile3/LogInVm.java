package com.example.MvRepTile3;

public class LogInVm {

    public LogInVm(String theUsername, String thePassword) {
        username = theUsername;
        password = thePassword;

    }

    public LogInVm(int theId) {
        id = theId;
    }

    public String test = "test";
    public long id;
    public String username;
    public String password;
    public boolean succeeded;

}
