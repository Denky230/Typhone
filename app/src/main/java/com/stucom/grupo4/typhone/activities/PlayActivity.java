package com.stucom.grupo4.typhone.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.TextView;

import com.stucom.grupo4.typhone.R;
import com.stucom.grupo4.typhone.control.AudioController;
import com.stucom.grupo4.typhone.control.GameController;
import com.stucom.grupo4.typhone.model.Stats;
import com.stucom.grupo4.typhone.model.modifiers.Modifier;
import com.stucom.grupo4.typhone.model.modifiers.SpeedUp;
import com.stucom.grupo4.typhone.tools.Tools;
import com.stucom.grupo4.typhone.views.WordTimerView;
import com.stucom.grupo4.typhone.views.WordToTypeView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PlayActivity extends AppCompatActivity
        implements WordToTypeView.WordListener, WordTimerView.WordTimerListener {

    // Game word pool
    private ArrayList<String> wordPool;

    // Audio
    private AudioController audio;

    // Stats
    private Stats stats;

    // Game timer
    private final int GAME_TIME_SECONDS = 60;
    private final int CLOCK_INTERVAL_MILLISECONDS = 10;
    private int lastRemainingMillis;
    private TextView txtGameTimer;
    private CountDownTimer timer;

    // Score
    private final int RIGHT_INPUT = 10;
    private TextView txtScore;
    private int score;

    // Game modifiers
    private List<Modifier> activeModifiers;

    // Word timer
    private WordTimerView wordTimerView;

    // Word to type
    private WordToTypeView wordView;
    private TextView nextWord;
    private String lastWord;

    // When a word is completed, briefly block game
    private boolean wordCompleted;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        // Initialize UI elements
        nextWord = findViewById(R.id.lblNextWord);
        wordTimerView = findViewById(R.id.wordTimerView);
        wordTimerView.setWordTimerListener(this);
        wordView = findViewById(R.id.wordToTypeView);
        wordView.setWordListener(this);
        txtScore = findViewById(R.id.lblScore);
        txtGameTimer = findViewById(R.id.lblGameTimer);

        audio = AudioController.getInstance();
        activeModifiers = new ArrayList<>();
        wordPool = new ArrayList<>();
        stats = new Stats();

        // Set total game time
        lastRemainingMillis = GAME_TIME_SECONDS * 1000;
        txtGameTimer.setText(String.valueOf(GAME_TIME_SECONDS));

        // Load Default words
        readWordPool();
        startGame();
    }
    @Override protected void onResume() {
        super.onResume();

        // Set game music
        audio.setMusic(this, AudioController.Music.GAME, true);
        audio.startMusic();
        // Resume game timer with game time left
        timer = startGameTimer(lastRemainingMillis);
    }
    @Override protected void onPause() {
        // Pause game music
        audio.pauseMusic();
        // Pause game timer
        timer.cancel();

        super.onPause();
    }
    @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!wordCompleted) {
            // Get user input letter
            String key = KeyEvent.keyCodeToString(keyCode);
            char letterTyped = key.substring(key.length() - 1).charAt(0);

            // Pass character input to WordView
            boolean isRight = wordView.validateInput(letterTyped);

            // Add new input to stats
            stats.addInput(isRight);
        }

        return super.onKeyDown(keyCode, event);
    }

    private void startGame() {
        // Reset game variables
        setScore(0);
        lastWord = "";

        // Get first word
        nextWord.setText(pullWordFromWordPool());
        updateWordToType();
    }
    private void gameOver() {
        // Set game score to game stats
        stats.setScore(this.score);

        // Send to StatsActivity
        Intent intent = new Intent(PlayActivity.this, StatsActivity.class);
        // Pass stats object to StatsActivity
        intent.putExtra("stats", stats);
//        startActivity(intent);
    }
    private CountDownTimer startGameTimer(int timerMillis) {
        return new CountDownTimer(timerMillis, CLOCK_INTERVAL_MILLISECONDS) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update game time left
                int secsLeft = (int) millisUntilFinished / 1000;
                setGameTime(secsLeft);

                // Update word time left
                if (!wordCompleted) {
                    int deltaRemainingMillis = lastRemainingMillis - (int) millisUntilFinished;
                    wordTimerView.updateMsLeft(deltaRemainingMillis);
                    lastRemainingMillis = (int) millisUntilFinished;
                }
            }

            @Override
            public void onFinish() {
                gameOver();
            }
        }.start();
    }

    /**
     * Pull non-repeated random word from wordPool.
     * @return random non-repeated word from wordPool
     */
    private String pullWordFromWordPool() {
        // Keep pulling til non-repeated word comes out
        String randWord;
        do {
            int randIndex = (int) (Math.random() * wordPool.size());
            randWord = wordPool.get(randIndex);
        } while (randWord.equals(lastWord));

        // Save word pulled so the process can be repeated
        lastWord = randWord;

        return randWord;
    }
    private void updateWordToType() {
        // Get next word as current word
        String currWord = nextWord.getText().toString();

        // Pass current word to WordView
        wordView.setWordToType(currWord);

        // Pass time to type current word to WordTimerView
        int currWordTotalMs = GameController.LETTER_TIME_MILLISECONDS * currWord.length();
        wordTimerView.setTotalMs(currWordTotalMs);

        // Get random new word
        String newWord = pullWordFromWordPool();
        nextWord.setText(newWord);
    }

    // Add words from the default word pool to array
    private void readWordPool() {
        try {
            InputStreamReader inputStreamReader =
                    new InputStreamReader(getAssets().open("defaultWordPool.csv"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = reader.readLine()) != null) {
                wordPool.add(line);
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    // WordToType
    @Override public void rightInput() {
        updateScore(RIGHT_INPUT);
    }
    @Override public void wrongInput() {

    }
    @Override public void wordCompleted() {
        wordCompleted = true;

        // Wait a bit before showing next word
        int delayMs = 250;
        new CountDownTimer(delayMs, delayMs) {
            @Override
            public void onTick(long millisUntilFinished) {

            }
            @Override
            public void onFinish() {
                wordCompleted = false;
                updateWordToType();
            }
        }.start();
    }

    // WordTimer
    @Override public void timesUp() {
        wordCompleted();
    }

    private void updateScore(int scoreDiff) {
        setScore(this.score + scoreDiff);
    }
    private void setScore(int score) {
        // Update score + textView
        this.score = score;
        String formatScore = String.format(Locale.getDefault(),"%06d", score);
        txtScore.setText(formatScore);
    }

    private void setGameTime(int seconds) {
        int mins = seconds / 60;
        int secs = seconds % 60;
        txtGameTimer.setText(String.format(Locale.getDefault(),"%d:%02d", mins, secs));
    }
}