package com.stucom.grupo4.typhone.model.modifiers;

import android.graphics.Paint;
import android.view.View;

import com.stucom.grupo4.typhone.constants.Style;
import com.stucom.grupo4.typhone.model.Word;

import java.util.Calendar;

public class BlinkingWords extends WordModifier {

    public BlinkingWords(int iconResID) {
        super(iconResID);
    }

    @Override public void modifyWord(Word word, Paint paint, View view) {

        Calendar calendar = Calendar.getInstance();
        int ms = calendar.get(Calendar.MILLISECOND);

        // Blink every .5 seconds
        if (ms >= 500) {
            paint.setTextSize(Style.FONT_SIZE);
        } else {
            paint.setTextSize(0);
        }

        // Request canvas redraw
        view.invalidate();
    }
}
