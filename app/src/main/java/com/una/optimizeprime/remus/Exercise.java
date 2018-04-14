package com.una.optimizeprime.remus;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Exercise implements Parcelable {

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
        stars = in.readFloat();
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
        dest.writeFloat(stars);
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