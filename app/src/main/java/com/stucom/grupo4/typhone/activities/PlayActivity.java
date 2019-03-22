package com.stucom.grupo4.typhone.activities;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.KeyEvent;
import android.widget.TextView;

import com.stucom.grupo4.typhone.R;

public class PlayActivity extends AppCompatActivity {

    /* TEST - Remove this when pulling from word pool */
    String[] wordPool = new String[]{
            "helicopterus", "holabondia", "whatisthis", "sunormal"
    };
    /* ---------------- */

    // Word to type
    private TextView txtWord;
    private String[] wordColors;
    private String lastWord;
    private int cursor;

    // Score
    private final int RIGHT_LETTER = 10;
    private TextView txtScore;
    private int score;

    // Game timer
    private final int GAME_TIME_SECONDS = 60;
    private CountDownTimer gameTimer;
    private TextView txtGameTimer;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        // Initialize UI elements
        txtWord = findViewById(R.id.txtWord);
        txtWord.setHorizontallyScrolling(true);
        txtScore = findViewById(R.id.txtScore);
        txtGameTimer = findViewById(R.id.txtGameTimer);

        startGame();
    }
    @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Get user input letter
        String key = KeyEvent.keyCodeToString(keyCode);
        char letterTyped = key.substring(key.length() - 1).charAt(0);

        // Validate input letter
        boolean right = isInputRight(letterTyped);
        // Take actions based on result
        if (right) rightInput();
        else wrongInput();
        // Update letter color based on result
        updateLetterStyle(right);

        cursor++;
        // Word completed = go next word
        if (cursor == txtWord.length()) {
            updateWordToType();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void startGame() {
        // Reset game variables
        lastWord = "";
        setScore(0);

        // Start game timer
        txtGameTimer.setText(String.valueOf(GAME_TIME_SECONDS));
        gameTimer = new CountDownTimer(GAME_TIME_SECONDS * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update game time left UI
                int secsLeft = (int) millisUntilFinished / 1000;
                txtGameTimer.setText(String.valueOf(secsLeft));
            }

            @Override
            public void onFinish() {
                // TO DO: Game Over
                txtGameTimer.setText("Game Over");
            }
        }.start();

        // Get first word
        updateWordToType();
    }

    private boolean isInputRight(char letterInput) {
        String letterToType = String.valueOf(txtWord.getText().charAt(cursor));
        String letterTyped = String.valueOf(letterInput);
        return letterTyped.equalsIgnoreCase(letterToType);
    }
    private void rightInput() {
        updateScore(RIGHT_LETTER);
    }
    private void wrongInput() {

    }

    /**
     * Pull non-repeated random word from wordPool.
     * @return random non-repeated word from wordPool
     */
    private String pullWordFromWordPool() {
        // Keep pulling til non-repeated word comes out
        String randWord;
        do {
            int randIndex = (int) (Math.random() * wordPool.length);
            randWord = wordPool[randIndex];
        } while (randWord.equals(lastWord));
        
        // Save word pulled so the process can be repeated
        lastWord = randWord;
        return randWord;
    }

    private void updateWordToType() {
        String newWord = pullWordFromWordPool();
        txtWord.setText(newWord);
        initWordStyle(newWord);
        updateWordStyle();
        cursor = 0;
    }
    private void initWordStyle(String word) {
        // Init style array with capacity = word.length
        wordColors = new String[word.length()];
        // Fill style array with color for each letter
        for (int i = 0; i < wordColors.length; i++) {
            wordColors[i] = "black";
        }
    }
    private void updateLetterStyle(boolean isInputRight) {
        // Determine current letter color
        String color = isInputRight ? "green" : "red";
        // Update current letter style
        wordColors[cursor] = color;
        // Re-draw word with updated style
        updateWordStyle();
    }
    private void updateWordStyle() {
        // Build styled word string
        StringBuilder style = new StringBuilder();
        char[] letters = txtWord.getText().toString().toCharArray();
        for (int i = 0; i < letters.length; i++) {
            style.append("<font color='").append(wordColors[i]).append("'>")
                                        .append(letters[i]).append("</font>");
        }
        // Set textView to styled word string
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            txtWord.setText(Html.fromHtml(style.toString(),  Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
        else txtWord.setText(Html.fromHtml(style.toString()), TextView.BufferType.SPANNABLE);
    }

    private void updateScore(int scoreDiff) {
        setScore(this.score + scoreDiff);
    }
    private void setScore(int score) {
        // Update score + textView
        this.score = score;
        txtScore.setText(String.valueOf(score));
    }
}