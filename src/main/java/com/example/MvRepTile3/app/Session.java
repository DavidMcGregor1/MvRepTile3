package com.example.MvRepTile3.app;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "Sessions")
@Entity
public class Session {

    private int id;
    public String sessionName;
    public int exerciseId;
    public float weight;
    public int reps;

    //need to change the database column from sets to reps

    // if a user logs a set with the same weight, reps and exercise, change to two sets


    public Session() {

    }

    public Session(String SessionName, int exerciseId, float weight, int reps) {
        this.sessionName = sessionName;
        this.exerciseId = exerciseId;
        this.weight = weight;
        this.reps = reps;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "sessionName", nullable = false)
    public String getSessionName(){return sessionName;}
    public void setSessionName(String sessionName) {this.sessionName = sessionName;}

    @Column(name = "exerciseId", nullable = false)
    public int getExerciseId(){return exerciseId;}
    public void setExerciseId(int exerciseId) {this.exerciseId = exerciseId;}

    @Column(name = "weight", nullable = true)
    public float getWeight(){return weight;}
    public void setWeight(float weight) {this.weight = weight;}

    @Column(name = "reps", nullable = true)
    public int getReps(){return reps;}
    public void setReps(int reps) {this.reps = reps;}






}
