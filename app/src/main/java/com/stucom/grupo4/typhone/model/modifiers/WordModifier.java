package com.stucom.grupo4.typhone.model.modifiers;

import android.graphics.Paint;
import android.view.View;

import com.stucom.grupo4.typhone.model.Word;

public abstract class WordModifier {

    public abstract void modifyWord(Word word, Paint paint, View view);
}
