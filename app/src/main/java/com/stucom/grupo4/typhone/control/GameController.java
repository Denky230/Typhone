package com.stucom.grupo4.typhone.control;

import com.stucom.grupo4.typhone.R;
import com.stucom.grupo4.typhone.model.modifiers.BlinkingWords;
import com.stucom.grupo4.typhone.model.modifiers.MirroredWords;
import com.stucom.grupo4.typhone.model.modifiers.Modifier;
import com.stucom.grupo4.typhone.model.modifiers.WordModifier;

import java.util.HashSet;
import java.util.Set;

public class GameController {

    // Modifiers
    private WordModifier[] modifiers;           // game modifiers
    private Set<Modifier> activeModifiers;      // currently active

    private GameController() {
        this.activeModifiers = new HashSet<>();
        initWordModifiers();
    }
    private static GameController instance;
    public static GameController getInstance() {
        if (instance == null)
            instance = new GameController();
        return instance;
    }

    private void initWordModifiers() {
        this.modifiers = new WordModifier[] {
                new MirroredWords(R.drawable.mirrored_words),
                new BlinkingWords(R.drawable.blinking_words)
        };
    }

    public Modifier[] getModifiers() {
        return this.modifiers;
    }

    public Set<Modifier> getActiveModifiers() {
        return this.activeModifiers;
    }
    public void addModifier(Modifier modifier) {
        activeModifiers.add(modifier);
    }
    public void clearModifiers() {
        this.activeModifiers.clear();
    }
}