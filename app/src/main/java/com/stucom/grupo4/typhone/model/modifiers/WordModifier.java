package com.stucom.grupo4.typhone.model.modifiers;

import android.graphics.Paint;
import android.view.View;

import com.stucom.grupo4.typhone.model.Word;

public interface WordModifier {

    void modifyWord(Word word, Paint paint, View view);
}
