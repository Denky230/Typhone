package com.stucom.grupo4.typhone.model;

public class User {

    private String name;
    private String image;
    private int highScore;

    public User(String name, String image, int highScore) {
        this.name = name;
        this.image = image;
        this.highScore = highScore;
    }
    public User(String name, String image) {
        this(name, image, 0);
    }

    public String getName() {
        return name;
    }
    public String getImage() {
        return image;
    }
    public int getHighScore() { return highScore; }
}