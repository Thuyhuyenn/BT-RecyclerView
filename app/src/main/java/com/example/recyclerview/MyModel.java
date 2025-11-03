package com.example.recyclerview;

public class MyModel {
    private String name;
    private int age;
    private String description;
    private boolean isFavorite;
    private String status; // "online", "offline", "away"

    // Constructor
    public MyModel(String name, int age, String description, String status) {
        this.name = name;
        this.age = age;
        this.description = description;
        this.status = status;
        this.isFavorite = false;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getDescription() {
        return description;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}