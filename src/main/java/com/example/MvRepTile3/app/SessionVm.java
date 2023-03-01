package com.example.MvRepTile3.app;

public class SessionVm {


    public SessionVm(String theSessionName, long theExerciseId, float theWeight, int theReps) {
        sessionName = theSessionName;
        exerciseId = theExerciseId;
        weight = theWeight;
        reps = theReps;
    }

    public SessionVm() {

    }

    public SessionVm(int theSessionId) {
        sessionId = theSessionId;
    }

    public long sessionId;
    public String sessionName;
    public long exerciseId;
    public float weight;
    public int reps;


}
