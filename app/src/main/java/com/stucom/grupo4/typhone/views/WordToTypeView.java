package com.stucom.grupo4.typhone.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.stucom.grupo4.typhone.constants.Style;
import com.stucom.grupo4.typhone.control.GameController;
import com.stucom.grupo4.typhone.model.Letter;
import com.stucom.grupo4.typhone.model.Word;
import com.stucom.grupo4.typhone.model.modifiers.WordModifier;

public class WordToTypeView extends View {

    private final GameController gameController;

    private Word word;      // Word to type
    private int cursor;     // Current letter index

    private final TextPaint paint;

    public WordToTypeView(Context context) {
        this(context, null, 0);
    }
    public WordToTypeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public WordToTypeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        gameController = GameController.getInstance();

        // Initialize text paints
        paint = new TextPaint();
        paint.setTextSize(Style.FONT_SIZE);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTypeface(Typeface.SANS_SERIF);
    }

    public void setWordToType(String word) {
        // Request redraw
        this.invalidate();
        // Set WordToType to new word
        this.word = new Word(word);
        // Reset cursor position
        cursor = 0;
    }

    public boolean validateInput(char letterInput) {
        // Request redraw
        this.invalidate();

        // Validate input
        boolean right = isInputRight(letterInput);
        // Take actions based on result
        if (right) {
            word.getLetterAt(cursor).setColor(Style.RIGHT);
            listener.rightInput();
        } else {
            word.getLetterAt(cursor).setColor(Style.WRONG);
            listener.wrongInput();
        }

        // Prepare to redraw next letter
        cursor++;
        // Check if word is completed
        if (cursor == word.getLength()) {
            listener.wordCompleted();
        }

        return right;
    }
    private boolean isInputRight(char letterInput) {
        String letterToType = String.valueOf(word.getLetterAt(cursor).getCharacter());
        String letterTyped = String.valueOf(letterInput);
        return letterTyped.equalsIgnoreCase(letterToType);
    }

    @Override protected void onDraw(Canvas canvas) {
        drawTest(canvas);
    }

    // region drawBien
//    private void drawBien(Canvas canvas) {
//        // Save word rect on wordBounds
//        paint.getTextBounds(word, 0, word.length(), wordBounds);
//
//        // Center word's Y
//        float y = getMinimumHeight()
//                + (float) wordBounds.height() / 2 + (float) getHeight() / 2;
//
//        // Figure out word's X
//        // Check if word fits in view
//        float wordWidth = paint.measureText(word);
//        if (wordWidth < getWidth()) {
//
//            // Center word's X
//            x = (getWidth() - wordWidth) / 2;
//
//        } else {
//
//            // Update word's X so current letter is always on screen
//            float screenThreshold = (float) (getWidth() * 0.6);
//            float writtenLettersWidth = paint.measureText(word.substring(0, cursor));
//            float xOffset = screenThreshold - writtenLettersWidth;
//            float wordEnd = x + wordBounds.width();
//
//            // Check if writtenLettersWidth has reached screenThreshold
//            if (xOffset < 0) {
//                // Check if word goes out of screen
//                if (wordEnd > getWidth()) {
//                    x = xOffset;
//                }
//            } else {
//                x = 0;
//            }
//
//            // region TEST - Bars
//            // View
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
//        }
//
//        // Draw letters
//        char[] letters = word.toCharArray();
//        for (int i = 0; i < letters.length; i++) {
//            // Draw text in corresponding color
//            paint.setColor(wordColors[i]);
//            canvas.drawText(String.valueOf(letters[i]), x, y, paint);
//
//            // Get letter width
//            float w = paint.measureText(String.valueOf(letters[i]));
//            // Move position to next letter
//            canvas.translate(w, 0);
//        }
//    }
    // endregion

    private void drawTest(Canvas canvas) {

        // Save word currently displayed
        String sWord = word.getWord();

        // Center word's Y
        float y = getMinimumHeight()
                + (float) Style.FONT_SIZE / 2 + (float) getHeight() / 2;

        // Figure out word's X
        float x;
        // Check if word fits in view
        float wordWidth = paint.measureText(sWord);
        if (wordWidth < getWidth()) {

            // Center word in view
            x = (getWidth() - wordWidth) / 2;

        } else {

            // Update word's X so current letter is always on screen
            float screenThreshold = (float) (getWidth() * 0.6);
            float writtenLettersWidth = paint.measureText(sWord.substring(0, cursor));
            float xOffset = screenThreshold - writtenLettersWidth;
            float wordEnd = word.getLetterAt(word.getLength() - 1).getX()
                    + paint.measureText(String.valueOf(word.getLetterAt(word.getLength() - 1).getCharacter()));

            // Check if writtenLettersWidth has reached screenThreshold
            if (xOffset < 0) {
                // Check if word goes out of screen
                if (wordEnd > getWidth()) {
                    x = xOffset;
                } else {
                    x = word.getLetterAt(0).getX();
                }
            } else {
                x = 0;
            }

            // region TEST BARS
//            // View
//            paint.setColor(Color.CYAN);
//            canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
//            // Word
//            paint.setColor(Color.LTGRAY);
//            canvas.drawRect(x, 0, wordEnd, getHeight(), paint);
//            // Written letters
//            paint.setColor(Color.YELLOW);
//            canvas.drawRect(x, y, writtenLettersWidth, getHeight(), paint);
//            // Threshold
//            paint.setColor(Color.MAGENTA);
//            canvas.drawRect(screenThreshold ,0, screenThreshold + 5, getHeight(), paint);
            // endregion
        }

        // Set word's first letter's X
        word.getLetterAt(0).setX(x);
        // Set word's other letters' Xs
        for (int i = 1; i < word.getLength(); i++) {
            char prevChar = sWord.charAt(i - 1);
            // Measure previous letter + add amount to X
            x += paint.measureText(String.valueOf(prevChar));
            // Set accumulated X to curr letter
            word.getLetterAt(i).setX(x);
        }

        // Apply word modifiers here
        for (WordModifier wordModifier : gameController.getActiveWordModifiers()) {
            wordModifier.modifyWord(word, paint, this);
        }

        // Conform canvas scale.x to word scale.x
        int scale = word.getScaleX();
        canvas.scale(scale, 1);

        // Draw letters
        for (Letter letter : word.getLetters()) {
            // Draw letter using its own color & position
            paint.setColor(letter.getColor());
            canvas.drawText(String.valueOf(letter.getCharacter()), letter.getX() * scale, y, paint);
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