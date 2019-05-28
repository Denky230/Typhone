package com.stucom.grupo4.typhone.control;

import com.stucom.grupo4.typhone.model.modifiers.WordModifier;

import java.util.HashSet;
import java.util.Set;

public class GameController {

    // Word timer
    public static int LETTER_TIME_MILLISECONDS = 350;

    // Event states enum
    public enum EventState {
        EVENT_DOWNTIME(5),
        EVENT_ANNOUNCEMENT(5),
        EVENT_ACTIVE(5),
        MODIFIER_ACTIVE(5);

        int seconds;    // state duration
        EventState(int seconds) { this.seconds = seconds; }
    }
    private EventState eventState;

    // Modifiers currently active
    private Set<WordModifier> activeWordModifiers;

    private GameController() {
        this.eventState = EventState.EVENT_DOWNTIME;
        this.activeWordModifiers = new HashSet<>();
    }
    private static GameController instance;
    public static GameController getInstance() {
        if (instance == null)
            instance = new GameController();
        return instance;
    }

    public void nextEventState() {
        int currStateOrdinal = this.eventState.ordinal();
        int nextStateOrdinal = ++currStateOrdinal == EventState.values().length ? 0 : currStateOrdinal;
        setEventState(EventState.values()[nextStateOrdinal]);
    }
    private void setEventState(EventState eventState) {
        this.eventState = eventState;
        switch (eventState) {

            case EVENT_DOWNTIME:
                break;

            case EVENT_ANNOUNCEMENT:
                break;

            case EVENT_ACTIVE:
                break;

            case MODIFIER_ACTIVE:
                break;
        }
    }

    public Set<WordModifier> getActiveWordModifiers() {
        return this.activeWordModifiers;
    }
    public void addModifier(WordModifier wordModifier) {
        activeWordModifiers.add(wordModifier);
    }
}