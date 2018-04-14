package com.una.optimizeprime.remus;

import java.util.ArrayList;

public class Exercise {

    private String clef;
    private String created_by;
    private String key;
    private String name;
    private ArrayList<String> notes;
    private float stars;


    public Exercise() {
    }

    public Exercise(String clef, String created_by, String key, String name, ArrayList<String> notes, float stars) {
        this.clef = clef;
        this.created_by = created_by;
        this.key = key;
        this.name = name;
        this.notes = notes;
        this.stars = stars;
    }

    public String getClef() {
        return clef;
    }

    public String getCreated_by() {
        return created_by;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getNotes() {
        return notes;
    }

    public float getStars() {
        return stars;
    }
}
