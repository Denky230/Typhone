package com.stucom.grupo4.typhone.control;

import com.stucom.grupo4.typhone.model.modifiers.BlinkingWords;
import com.stucom.grupo4.typhone.model.modifiers.MirroredWords;
import com.stucom.grupo4.typhone.model.modifiers.WordModifier;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GameController {

    // Word timer
    public static int LETTER_TIME_MILLISECONDS = 350;

    // Modifiers
    private WordModifier[] wordModifiers;           // game modifiers
    private Set<WordModifier> activeWordModifiers;  // currently active

    private GameController() {
        this.activeWordModifiers = new HashSet<>();
        initWordModifiers();
    }
    private static GameController instance;
    public static GameController getInstance() {
        if (instance == null)
            instance = new GameController();
        return instance;
    }

    private void initWordModifiers() {
        this.wordModifiers = new WordModifier[] {
                new MirroredWords(),
                new BlinkingWords()
        };
    }

    public WordModifier[] getWordModifiers() {
        return this.wordModifiers;
    }

    public Set<WordModifier> getActiveWordModifiers() {
        return this.activeWordModifiers;
    }
    public void addModifier(WordModifier wordModifier) {
        activeWordModifiers.add(wordModifier);
    }
    public void clearModifiers() {
        this.activeWordModifiers.clear();
    }
}