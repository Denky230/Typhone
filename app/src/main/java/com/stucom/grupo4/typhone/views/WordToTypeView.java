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
import android.view.View;

import com.stucom.grupo4.typhone.model.Letter;
import com.stucom.grupo4.typhone.model.Word;
import com.stucom.grupo4.typhone.tools.Tools;

public class WordToTypeView extends View {

    // Word to type
    private String word;
    private Rect wordBounds;
    private int[] wordColors;   // Each letter's color id
    private int cursor = 0;     // Current letter index
    private float x;

    // Style parameters
    private final int FONT_SIZE = 100;

    private final TextPaint paint;

    /* TEST */
    private Word palabro;

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
        // Request redraw
        this.invalidate();

        // Set WordToType to new word
        this.word = word.toUpperCase();
        /* TEST */
        this.palabro = new Word(this.word);

        // Reset cursor position
        cursor = 0;

        // Set word letters to default color
        this.wordColors = new int[word.length()];
        for (int i = 0; i < wordColors.length; i++) {
            wordColors[i] = Color.BLACK;
        }
    }

    public boolean validateInput(char letterInput) {
        // Request redraw
        this.invalidate();

        // Validate input
        boolean right = isInputRight(letterInput);
        // Take actions based on result
        if (right) {
            wordColors[cursor] = Color.GREEN;
            listener.rightInput();
        } else {
            wordColors[cursor] = Color.RED;
            listener.wrongInput();
        }

        // Prepare to redraw next letter
        cursor++;
        // Check if word is completed
        if (cursor == word.length()) {
            listener.wordCompleted();
        }
        return right;
    }
    private boolean isInputRight(char letterInput) {
        String letterToType = String.valueOf(word.charAt(cursor));
        String letterTyped = String.valueOf(letterInput);
        return letterTyped.equalsIgnoreCase(letterToType);
    }

    @Override protected void onDraw(Canvas canvas) {
        drawTest(canvas);
    }

    private void drawBien(Canvas canvas) {
        // Save word rect on wordBounds
        paint.getTextBounds(word, 0, word.length(), wordBounds);

        // Center word's Y
        float y = getMinimumHeight()
                + (float) wordBounds.height() / 2 + (float) getHeight() / 2;

        // Figure out word's X
        // Check if word fits in view
        float wordWidth = paint.measureText(word);
        if (wordWidth < getWidth()) {

            // Center word's X
            x = (getWidth() - wordWidth) / 2;

        } else {

            // Update word's X so current letter is always on screen
            float screenThreshold = (float) (getWidth() * 0.6);
            float writtenLettersWidth = paint.measureText(word.substring(0, cursor));
            float xOffset = screenThreshold - writtenLettersWidth;
            float wordEnd = x + wordBounds.width();

            // Check if writtenLettersWidth has reached screenThreshold
            if (xOffset < 0) {
                // Check if word goes out of screen
                if (wordEnd > getWidth()) {
                    x = xOffset;
                }
            } else {
                x = 0;
            }

            // region TEST - Bars
            // View
//            paint.setColor(Color.CYAN);
//            canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
//            // Word
//            paint.setColor(Color.LTGRAY);
//            canvas.drawRect(x, y - wordBounds.height(), wordEnd, y, paint);
//            // Written letters
//            paint.setColor(Color.YELLOW);
//            canvas.drawRect(x, y, writtenLettersWidth, getHeight(), paint);
//            // Threshold
//            paint.setColor(Color.MAGENTA);
//            canvas.drawRect(screenThreshold ,0, screenThreshold + 5, getHeight(), paint);
//
//            paint.setColor(Color.BLACK);
            // endregion
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
            canvas.translate(w, 0);
        }
    }
    private void drawTest(Canvas canvas) {

        ///Save word currently displayed
        String word = palabro.getWord();
        // Save word rect on wordBounds
        paint.getTextBounds(word, 0, word.length(), wordBounds);

        // Center word's Y
        float y = getMinimumHeight()
                + (float) wordBounds.height() / 2 + (float) getHeight() / 2;

        // Figure out word's X
        // Check if word fits in view
        float wordWidth = paint.measureText(word);

        // Set word's first letter's X
        x = (getWidth() - wordWidth) / 2;
        palabro.getLetterAt(0).setX(x);
        // Set word's other letters' Xs
        for (int i = 1; i < palabro.getLength(); i++) {
            char prevChar = word.charAt(i - 1);
            // Measure previous letter + add amount to X
            x += paint.measureText(String.valueOf(prevChar));
            // Set accumulated X to curr letter
            palabro.getLetterAt(i).setX(x);
        }

        if (wordWidth < getWidth()) {

        } else {

            // Update word's X so current letter is always on screen
            float screenThreshold = (float) (getWidth() * 0.6);
            float writtenLettersWidth = paint.measureText(word.substring(0, cursor));
            float xOffset = screenThreshold - writtenLettersWidth;
            float wordEnd = x + wordBounds.width();

            // Check if writtenLettersWidth has reached screenThreshold
            if (xOffset < 0) {
                // Check if word goes out of screen
                if (wordEnd > getWidth()) {
                    x = xOffset;
                }
            } else {
                x = 0;
            }

            // region TEST - Bars
            // View
//            paint.setColor(Color.CYAN);
//            canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
//            // Word
//            paint.setColor(Color.LTGRAY);
//            canvas.drawRect(x, y - wordBounds.height(), wordEnd, y, paint);
//            // Written letters
//            paint.setColor(Color.YELLOW);
//            canvas.drawRect(x, y, writtenLettersWidth, getHeight(), paint);
//            // Threshold
//            paint.setColor(Color.MAGENTA);
//            canvas.drawRect(screenThreshold ,0, screenThreshold + 5, getHeight(), paint);
//
//            paint.setColor(Color.BLACK);
            // endregion
        }

        // Draw letters
        Letter[] letters = palabro.getLetters();
        for (int i = 0; i < letters.length; i++) {

            Letter letter = letters[i];

            // Draw letter using its own color & position
            paint.setColor(wordColors[i]);
            canvas.drawText(String.valueOf(letter.getCharacter()), letter.getX(), y, paint);
        }
    }

    public interface WordListener {
        void rightInput();
        void wrongInput();
        void wordCompleted();
    }
    protected WordListener listener;
    public void setWordListener(WordListener listener) {
        this.listener = listener;
    }
}