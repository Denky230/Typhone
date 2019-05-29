package com.stucom.grupo4.typhone.model.modifiers;

import android.graphics.Paint;
import android.view.View;

import com.stucom.grupo4.typhone.model.Letter;
import com.stucom.grupo4.typhone.model.Word;

public class MirroredWords extends WordModifier {

    public MirroredWords(int iconResID) {
        super(iconResID);
    }

    @Override public void modifyWord(Word word, Paint paint, View view) {

        // Invert word's scale.x
        word.setScaleX(-1);

        // Add own letter width as offset to letter.x
        // to correct canvas inverted scale.x
        for (Letter letter : word.getLetters()) {
            float width = paint.measureText(String.valueOf(letter.getCharacter()));
            letter.setX(letter.getX() + width);
        }
    }
}
