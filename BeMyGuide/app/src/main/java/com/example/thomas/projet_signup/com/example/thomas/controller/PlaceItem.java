package com.example.thomas.projet_signup.com.example.thomas.controller;

public class PlaceItem {
    private int id;
    private String title;
    private String address;
    private String distance;

    public PlaceItem(int id, String title, String adress, String distance) {
        this.id = id;
        this.title = title;
        this.address = adress;
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String pseudo) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String adress) { this.address = adress; }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}