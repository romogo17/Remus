package com.una.optimizeprime.remus;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Exercise implements Parcelable {

    private String clef;
    private String created_by;
    private String key;
    private String name;
    private ArrayList<String> notes;
    private ArrayList<Score> scores;
    private String exerciseId = null;

    public Exercise() {
    }

    public Exercise(String clef, String created_by, String key, String name, ArrayList<String> notes, ArrayList<Score> scores) {
        this.clef = clef;
        this.created_by = created_by;
        this.key = key;
        this.name = name;
        this.notes = notes;
        this.scores = scores;
        this.exerciseId = null;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "clef='" + clef + '\'' +
                ", created_by='" + created_by + '\'' +
                ", key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", notes=" + notes +
                ", scores=" + scores +
                '}';
    }

    public float getStars(){
        if(scores == null) return 0;
        float sum = 0;
        for(Score sc : scores){
            sum += sc.getStars();
        }
        return sum/scores.size();
    }

    public void addScore(Score score){
        if(scores == null){
            scores = new ArrayList<>();
        }
        scores.add(score);
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

    public String getId() {
        return exerciseId;
    }

    public ArrayList<Score> getScores() {
        return scores;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public void setClef(String clef) {
        this.clef = clef;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNotes(ArrayList<String> notes) {
        this.notes = notes;
    }

    public void setScores(ArrayList<Score> scores) {
        this.scores = scores;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }



    protected Exercise(Parcel in) {
        clef = in.readString();
        created_by = in.readString();
        key = in.readString();
        name = in.readString();
        if (in.readByte() == 0x01) {
            notes = new ArrayList<String>();
            in.readList(notes, String.class.getClassLoader());
        } else {
            notes = null;
        }
        if (in.readByte() == 0x01) {
            scores = new ArrayList<Score>();
            in.readList(scores, Score.class.getClassLoader());
        } else {
            scores = null;
        }
        exerciseId = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(clef);
        dest.writeString(created_by);
        dest.writeString(key);
        dest.writeString(name);
        if (notes == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(notes);
        }
        if (scores == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(scores);
        }
        dest.writeString(exerciseId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Exercise> CREATOR = new Parcelable.Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };
}