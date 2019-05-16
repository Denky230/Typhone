package com.stucom.grupo4.typhone.model;

public class User {

    private String name;
    private String image;

    public User(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }
    public String getImage() {
        return image;
    }
}