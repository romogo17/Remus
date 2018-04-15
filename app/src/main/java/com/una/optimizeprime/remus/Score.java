package com.una.optimizeprime.remus;

import android.os.Parcel;
import android.os.Parcelable;

public class Score implements Parcelable {

    private long milliseconds;
    private float score;
    private float stars;
    private String uid;

    public Score() {
    }

    public Score(long milliseconds, float score, float stars, String uid) {
        this.milliseconds = milliseconds;
        this.score = score;
        this.stars = stars;
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Score{" +
                "milliseconds=" + milliseconds +
                ", score=" + score +
                ", stars=" + stars +
                ", uid='" + uid + '\'' +
                '}';
    }

    public long getMilliseconds() {
        return milliseconds;
    }

    public float getScore() {
        return score;
    }

    public float getStars() {
        return stars;
    }

    public String getUid() {
        return uid;
    }

    public void setMilliseconds(long milliseconds) {
        this.milliseconds = milliseconds;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    protected Score(Parcel in) {
        milliseconds = in.readLong();
        score = in.readFloat();
        stars = in.readFloat();
        uid = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(milliseconds);
        dest.writeFloat(score);
        dest.writeFloat(stars);
        dest.writeString(uid);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Score> CREATOR = new Parcelable.Creator<Score>() {
        @Override
        public Score createFromParcel(Parcel in) {
            return new Score(in);
        }

        @Override
        public Score[] newArray(int size) {
            return new Score[size];
        }
    };
}