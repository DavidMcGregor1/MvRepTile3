package com.example.MvRepTile3.app;

public class ExercisesVm {

    public ExercisesVm(String theExerciseName) {
        exerciseName = theExerciseName;

    }

    public ExercisesVm() {

    }

    public ExercisesVm(int theId) {
        id = theId;
    }
    public long id;
    public String exerciseName;
}
