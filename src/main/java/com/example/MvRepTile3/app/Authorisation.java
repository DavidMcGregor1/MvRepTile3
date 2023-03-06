package com.example.MvRepTile3.app;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Table(name = "Authorisation")
@Entity
public class Authorisation {

    private int id;
    public String authToken;
    public String roleName;
    private int UserId;


    public Authorisation() {

    }

    public Authorisation(String AuthToken, String RoleName, int UserId) {
        this.authToken = AuthToken;
        this.roleName = RoleName;
        this.UserId = UserId;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "AuthToken", nullable = false)
    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String AuthToken) {
        this.authToken = AuthToken;
    }

    @Column(name = "RoleName", nullable = false)
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String RoleName) {
        this.roleName = RoleName;
    }

    @Column(name = "UserId", nullable = false)
    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }
}

