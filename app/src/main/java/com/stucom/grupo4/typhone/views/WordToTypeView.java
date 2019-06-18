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
import com.stucom.grupo4.typhone.model.modifiers.Modifier;
import com.stucom.grupo4.typhone.model.modifiers.WordModifier;

public class WordToTypeView extends View {

    private final GameController gameController;

    private Word word;          // Word to type
    private int cursor;         // Current letter index
    private boolean perfect;    // Word correct
    private int wordStreak = 0, letterStreak = 0;

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
    }

    private void initPaint() {
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
        perfect = true;
    }

    public boolean validateInput(char letterInput) {
        // Request redraw
        this.invalidate();

        // Validate input
        boolean right = isInputRight(letterInput);
        // Take actions based on result
        if (right) {
            letterStreak++;
            word.getLetterAt(cursor).setColor(Style.RIGHT);
            listener.rightInput();
        } else {
            listener.letterStreak(letterStreak);
            letterStreak = 0;
            perfect = false;
            word.getLetterAt(cursor).setColor(Style.WRONG);
            listener.wrongInput();
        }

        if( perfect) {
            wordStreak++;
        } else {
            // Before setting it to 0 we add it to a variable
            listener.wordStreak(wordStreak);
            wordStreak = 0;
        }

        // Prepare to redraw next letter
        cursor++;
        // Check if word is completed
        if (cursor == word.getLength()) {
            listener.wordCompleted(perfect);
        }

        return right;
    }
    private boolean isInputRight(char letterInput) {
        String letterToType = String.valueOf(word.getLetterAt(cursor).getCharacter());
        String letterTyped = String.valueOf(letterInput);
        return letterTyped.equalsIgnoreCase(letterToType);
    }

    @Override protected void onDraw(Canvas canvas) {

        // Save word currently displayed
        String sWord = word.getWord();

        // Set up paint attributes
        initPaint();

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

        // Apply word modifiers
        for (Modifier modifier : gameController.getActiveModifiers()) {
            if (modifier instanceof WordModifier) {
                WordModifier wordModifier = (WordModifier) modifier;
                wordModifier.modifyWord(word, paint, this);
            }
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
        void wordCompleted(boolean perfect);
        void wordStreak(int word);
        void letterStreak(int letter);
    }
    protected WordListener listener;
    public void setWordListener(WordListener listener) {
        this.listener = listener;
    }
}