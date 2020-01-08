package com.example.mygame;

import java.util.ArrayList;

class Winner {
    protected static ArrayList<Winner> winners = new ArrayList<>(10);
    private String name;
    private int score;
    private String latitude, longitude;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) { this.score = score; }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) { this.latitude = latitude; }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) { this.longitude = longitude; }

}


