package com.stucom.grupo4.typhone.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.stucom.grupo4.typhone.WordListener;

public class WordToTypeView extends View
        implements WordListener {

    // Word to type
    private String word;
    private Rect wordBounds;
    private int[] wordColors;   // Each letter's color id
    private int cursor = 0;     // Current letter index
    private float x, y;

    // Style parameters
    private final int FONT_SIZE = 100;
    private final int LETTER_SPACING = 0;

    private final TextPaint paint;

    public WordToTypeView(Context context) {
        this(context, null, 0);
    }
    public WordToTypeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public WordToTypeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // Initialize text paints
        paint = new TextPaint();
        paint.setTextSize(FONT_SIZE);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTypeface(Typeface.SANS_SERIF);

        // Initialize word bounds
        wordBounds = new Rect();
    }

    public void setWordToType(String word) {
        this.word = word.toUpperCase();

        // Set word letters to default color
        this.wordColors = new int[word.length()];
        for (int i = 0; i < wordColors.length; i++) {
            wordColors[i] = Color.BLACK;
        }
    }

    public void validateInput(char letterInput) {
        // Request redraw
        this.invalidate();

        // Validate input
        boolean right = isInputRight(letterInput);
        // Take actions based on result
        if (right) {
            rightInput();
        } else {
            wrongInput();
        }

        // Prepare to redraw next letter
        cursor++;
        // Check if word is completed
        if (cursor == word.length()) {
            wordCompleted();
        }
    }
    private boolean isInputRight(char letterInput) {
        String letterToType = String.valueOf(word.charAt(cursor));
        String letterTyped = String.valueOf(letterInput);
        return letterTyped.equalsIgnoreCase(letterToType);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // Save word rect on wordBounds
        paint.getTextBounds(word, 0, word.length(), wordBounds);

        // Center word's Y
        y = getMinimumHeight()
                + (float) wordBounds.height() / 2 + (float) getHeight() / 2;

        // Center word's X
        float wordWidth = paint.measureText(word);
        if (wordWidth < getWidth()) {
            x = (getWidth() - wordWidth) / 2;
        } else {
            x = 0;
        }

        // Draw letters
        char[] letters = word.toCharArray();
        for (int i = 0; i < letters.length; i++) {

            // Draw text in corresponding color
            paint.setColor(wordColors[i]);
            canvas.drawText(String.valueOf(letters[i]), x, y, paint);

            // Get letter width
            float w = paint.measureText(String.valueOf(letters[i]));
            // Move position to next letter
            canvas.translate(w + LETTER_SPACING, 0);
        }
    }

    @Override public void rightInput() {
        // Set current letter color
        wordColors[cursor] = Color.GREEN;

        listener.rightInput();
    }
    @Override public void wrongInput() {
        // Set current letter color
        wordColors[cursor] = Color.RED;

        listener.wrongInput();
    }
    @Override public void wordCompleted() {
        // Reset cursor position
        cursor = 0;

        listener.wordCompleted();
    }

    protected WordListener listener;
    public void setWordListener(WordListener listener) {
        this.listener = listener;
    }
}