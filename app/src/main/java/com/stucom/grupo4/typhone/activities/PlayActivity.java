package com.stucom.grupo4.typhone.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.TextView;

import com.stucom.grupo4.typhone.R;
import com.stucom.grupo4.typhone.WordListener;
import com.stucom.grupo4.typhone.views.WordTimerView;
import com.stucom.grupo4.typhone.views.WordToTypeView;

import java.util.Locale;

public class PlayActivity extends AppCompatActivity
        implements WordListener, WordTimerView.WordTimerListener {

    /* TEST - Remove this when pulling from word pool */
    String[] wordPool = new String[]{
            "helicopterus", "holabondia", "whatisthis", "sunormal"
    };
    /* ---------------- */

    // Word to type
    private WordToTypeView wordView;
    private String lastWord;

    // Word timer
    private final int LETTER_TIME_MILLISECONDS = 500;
    private WordTimerView wordTimerView;

    // Score
    private final int RIGHT_LETTER = 10;
    private TextView txtScore;
    private int score;

    // Game timer
    private final int GAME_TIME_SECONDS = 10;
    private CountDownTimer gameTimer;
    private TextView txtGameTimer;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        // Initialize UI elements
        wordTimerView = findViewById(R.id.wordTimerView);
        wordTimerView.setWordTimerListener(this);
        wordView = findViewById(R.id.wordToTypeView);
        wordView.setWordListener(this);
        txtScore = findViewById(R.id.txtScore);
        txtGameTimer = findViewById(R.id.txtGameTimer);

        startGame();
    }
    @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Get user input letter
        String key = KeyEvent.keyCodeToString(keyCode);
        char letterTyped = key.substring(key.length() - 1).charAt(0);

        // Pass character input to WordView
        wordView.validateInput(letterTyped);

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

                // Update word timer left UI
                wordTimerView.updateMsLeft();
            }

            @Override
            public void onFinish() {
                gameOver();
            }
        }.start();

        // Get first word
        updateWordToType();
    }

    // WordToType
    @Override public void rightInput() {
        updateScore(RIGHT_LETTER);
    }
    @Override public void wrongInput() {

    }
    @Override public void wordCompleted() {
        updateWordToType();
    }

    // WordTimer
    @Override public void timesUp() {
        updateWordToType();
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
        // Get random new word
        String newWord = pullWordFromWordPool();

        // Pass new word to WordView
        wordView.setWordToType(newWord);

        // Pass time to type new word to WordTimerView
        int newWordTotalMs = LETTER_TIME_MILLISECONDS * newWord.length();
        wordTimerView.setTotalMs(newWordTotalMs);
    }

    private void updateScore(int scoreDiff) {
        setScore(this.score + scoreDiff);
    }
    private void setScore(int score) {
        // Update score + textView
        this.score = score;
        String formatScore = String.format(Locale.getDefault(),"%06d" ,score);
        txtScore.setText(formatScore);
    }

    private void gameOver() {
        // Send to StatsActivity
        Intent intent = new Intent(PlayActivity.this, StatsActivity.class);
        startActivity(intent);
    }
}