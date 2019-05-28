package com.stucom.grupo4.typhone.control;

import com.stucom.grupo4.typhone.model.modifiers.WordModifier;

import java.util.HashSet;
import java.util.Set;

public class GameController {

    // Word timer
    public static int LETTER_TIME_MILLISECONDS = 350;

    // Modifiers currently active
    private Set<WordModifier> activeWordModifiers;

    private GameController() {
        this.activeWordModifiers = new HashSet<>();
    }
    private static GameController instance;
    public static GameController getInstance() {
        if (instance == null)
            instance = new GameController();
        return instance;
    }

    public Set<WordModifier> getActiveWordModifiers() {
        return this.activeWordModifiers;
    }
    public void addModifier(WordModifier wordModifier) {
        activeWordModifiers.add(wordModifier);
    }
}