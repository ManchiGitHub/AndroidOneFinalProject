package com.alex_nechaev.androidonefinalproject;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class PlayerDetails implements Serializable, Comparable {
    private int score;
    private String name;

    public PlayerDetails(int score, String name) {
        this.score = score;
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return "Name: "+name+", Score: "+score+".";
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Object o) {
        PlayerDetails other = (PlayerDetails)o;
        return Integer.compare(other.score,this.score);
    }
}
