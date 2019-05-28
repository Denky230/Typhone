package com.stucom.grupo4.typhone.model;

import android.graphics.Color;

public class Letter {

    private char character;
    private int color;
    private float x;

    Letter(char character) {
        this.character = character;
        this.color = Color.BLACK;
        this.x = 0;
    }

    public char getCharacter() {
        return this.character;
    }

    public int getColor() {
        return this.color;
    }
    public void setColor(int color) {
        this.color = color;
    }

    public void setX(float x) {
        this.x = x;
    }
    public float getX() {
        return this.x;
    }
}
