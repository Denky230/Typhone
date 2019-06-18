package com.stucom.grupo4.typhone.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.TextView;

import com.stucom.grupo4.typhone.R;
import com.stucom.grupo4.typhone.control.AudioController;
import com.stucom.grupo4.typhone.model.Stats;
import com.stucom.grupo4.typhone.tools.DatabaseHelper;
import com.stucom.grupo4.typhone.tools.Tools;
import com.stucom.grupo4.typhone.views.EventView;
import com.stucom.grupo4.typhone.views.WordTimerView;
import com.stucom.grupo4.typhone.views.WordToTypeView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PlayActivity extends AppCompatActivity
        implements WordToTypeView.WordListener, WordTimerView.WordTimerListener {

    // Database
    private DatabaseHelper mDatabaseHelper;

    // Game timer
    private final int GAME_TIME_SECONDS = 60;
    private final int TIMER_INTERVAL_MILLISECONDS = 1000;
    private int lastRemainingMs;
    private TextView txtGameTimer;
    private CountDownTimer timer;

    // Score
    private final int RIGHT_INPUT = 10;
    private TextView txtScore;
    private int score;

    // In-game stats
    private Stats stats;

    // Word pool currently pulling from
    private List<String> wordPool;

    // WordTimer
    public static int LETTER_TIME_MILLISECONDS = 350;
    private WordTimerView wordTimerView;

    // WordToType
    private WordToTypeView wordView;
    private TextView nextWord;
    private String lastWord;

    // Event view
    private EventView eventView;

    // When a word is completed, briefly block game
    private boolean wordCompleted;

    private AudioController audio;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        // Keeps screen on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Initialize UI elements
        nextWord = findViewById(R.id.lblNextWord);
        wordTimerView = findViewById(R.id.wordTimerView);
        wordTimerView.setWordTimerListener(this);
        wordView = findViewById(R.id.wordToTypeView);
        wordView.setWordListener(this);
        txtScore = findViewById(R.id.lblScoreStat);
        eventView = findViewById(R.id.eventView);
        txtScore = findViewById(R.id.lblScoreStat);
        txtGameTimer = findViewById(R.id.lblGameTimer);
        txtGameTimer.setTranslationY(-25f);

        audio = AudioController.getInstance();
        wordPool = new ArrayList<>();
        stats = new Stats();

        // Set total game time
        lastRemainingMs = GAME_TIME_SECONDS * 1000;
        txtGameTimer.setText(String.valueOf(GAME_TIME_SECONDS));

        // Initiates db
        mDatabaseHelper = new DatabaseHelper(this);

        // Load Default words
        readWordPool();

        startGame();
    }
    @Override protected void onResume() {
        super.onResume();

        // Set game music
        audio.setMusic(this, AudioController.Music.GAME, true);
        audio.startMusic();

        // Resume timers with time left
        startGameTimer(lastRemainingMs);
        wordTimerView.resumeTimer();
    }
    @Override protected void onPause() {
        // Pause game music
        audio.pauseMusic();

        // Pause timers
        timer.cancel();
        wordTimerView.stopTimer();

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

        addNewScore();

        Tools.log("max words: " + words);
        Tools.log("max letters: " + letters);

        // Assign max streaks to stats
        stats.setHiStreakWord(words);
        stats.setHiStreakLetter(letters);

        // Send to FinalActivity
        Intent intent = new Intent(PlayActivity.this, StatsActivity.class);
        // Pass stats object to FinalActivity
        intent.putExtra("stats", stats);
        startActivity(intent);
        finish();
    }
    private void startGameTimer(int totalMs) {
        timer = new CountDownTimer(totalMs, TIMER_INTERVAL_MILLISECONDS) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update game time left
                int secsLeft = (int) millisUntilFinished / 1000;
                setGameTime(secsLeft);

                // Check elapsed game time
                int gameTimeSecs = GAME_TIME_SECONDS - secsLeft;
                Tools.log(String.valueOf(gameTimeSecs));
                if (gameTimeSecs >= eventView.nextEventStateSeconds) {
                    eventView.nextEventState();
                }
            }

            @Override
            public void onFinish() {
                gameOver();
            }
        }.start();
    }

    // Pull non-repeated random word from wordPool
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
        int currWordTotalMs = LETTER_TIME_MILLISECONDS * currWord.length();
        wordTimerView.startTimer(currWordTotalMs);

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

    // Add score to SQLite
    private void addNewScore() {
        if (this.score == 0) return;

        SQLiteDatabase sdb = mDatabaseHelper.getReadableDatabase();
        Cursor data = sdb.rawQuery("SELECT score FROM scoreboard ORDER BY CAST (score AS INTEGER) DESC;", null);
        data.moveToFirst();

        // Si no es null o si esta vacia
        if (data.getCount() >= 10) {

            do {
                // Sobreescribe todas las puntuaciones... Change
                if (this.score > Integer.parseInt(data.getString(data.getColumnIndex("score")))) {

                    // SQLite replace scores
                    String holder, score = String.valueOf(this.score);
                    do {

                        holder = data.getString(data.getColumnIndex("score"));
                        mDatabaseHelper.removeData(holder);
                        mDatabaseHelper.addData(score);
                        score = holder;

                    } while (data.moveToNext());

                    return;
                }

            } while (data.moveToNext());

        } else {
            mDatabaseHelper.addData(String.valueOf(this.score));
        }

        data.close();
    }

    // WordToType view
    @Override public void rightInput() {
        updateScore(RIGHT_INPUT);
    }
    @Override public void wrongInput() {

    }
    @Override public void wordCompleted(boolean perfect) {
        wordCompleted = true;

        // Stop word timer to avoid calling this again
        wordTimerView.stopTimer();

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

        if (perfect){
            updateScore(30);
            audio.playSfx(this, AudioController.Music.CASH);
        }
    }

    int words, letters = 0;
    @Override public void wordStreak(int word) {
        if (words < word) {
            words = word;
        }
        Tools.log("adding word " + word);
    }
    @Override public void letterStreak(int letter) {
        if (letters < letter) {
            letters = letter;
        }
        Tools.log("adding letter: " + letter);
    }

    // WordTimer view
    @Override public void timesUp() {
        wordCompleted(false);
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