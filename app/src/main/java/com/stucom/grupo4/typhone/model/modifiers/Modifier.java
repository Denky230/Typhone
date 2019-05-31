package com.stucom.grupo4.typhone.model.modifiers;

public abstract class Modifier {

    private int iconResID;

    public Modifier(int iconResID) {
        this.iconResID = iconResID;
    }

    public int getIconResID() {
        return this.iconResID;
    }
}