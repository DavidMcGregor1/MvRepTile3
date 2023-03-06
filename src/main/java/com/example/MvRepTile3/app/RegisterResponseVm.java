package com.example.MvRepTile3.app;

public class RegisterResponseVm {

    public RegisterResponseVm(String authToken, String roleName, boolean succeeded) {
        authToken = authToken;
        roleName = roleName;
        succeeded = succeeded;


    }

    public RegisterResponseVm() {

    }

    public RegisterResponseVm(int theId) {
        id = theId;
    }
    public long id;
    public String authToken;
    public String RoleName;
    public Boolean succeeded;


}
