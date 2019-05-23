package com.stucom.grupo4.typhone.model.modifiers;

import com.stucom.grupo4.typhone.control.GameController;

public class SpeedUp extends Modifier {

    private final double SPEED_UP_PERCENT = 20;

    @Override public void activate() {
        GameController.LETTER_TIME_MILLISECONDS *= 1 - (SPEED_UP_PERCENT / 100);
    }
}
