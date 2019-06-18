package com.stucom.grupo4.typhone.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.stucom.grupo4.typhone.R;
import com.stucom.grupo4.typhone.model.Stats;
import com.stucom.grupo4.typhone.tools.DatabaseHelper;

public class StatsActivity extends AppCompatActivity {

    private TextView grade, highScore, score, totalInputs, wordStreak, letterStreak, accuracyPercentage;
    private ProgressBar accuracy;
    private Stats stats;

    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        mDatabaseHelper = new DatabaseHelper(this);
        SQLiteDatabase sdb = mDatabaseHelper.getReadableDatabase();
        Cursor data = sdb.rawQuery("SELECT score FROM scoreboard ORDER BY CAST (score AS INTEGER) DESC;", null);
        data.moveToFirst();

        // Retrieve stats object
        stats = (Stats) getIntent().getSerializableExtra("stats");

        grade = findViewById(R.id.lblGradeStat);
        grade.setTextSize(80);
        grade.setText(getGradeByScore());

        highScore = findViewById(R.id.lblHighScoreStat);
        highScore.setText(data.getString(data.getColumnIndex("score")));

        score = findViewById(R.id.lblScoreStat);
        score.setText(String.valueOf(stats.getScore()));

        totalInputs = findViewById(R.id.lblTotalInputsStat);
        totalInputs.setText(String.valueOf(stats.getInputsTotal()));

        accuracy = findViewById(R.id.lblAccuracyStat);
        accuracy.setMax(100);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            accuracy.setProgress((int)stats.getAccuracy(), true);
        } else {
            accuracy.setProgress((int)stats.getAccuracy());
        }

        accuracyPercentage = findViewById(R.id.lblAccuracyPercentage);
        accuracyPercentage.setText( (Float.isNaN(stats.getAccuracy()) ? 0 : stats.getAccuracy()) + "%");

        wordStreak = findViewById(R.id.lblWordStreakStat);
        wordStreak.setText(String.valueOf(stats.getHiStreakWord()));

        letterStreak = findViewById(R.id.lblLetterStreakStat);
        letterStreak.setText(String.valueOf(stats.getHiStreakLetter()));

        // Replay button
        findViewById(R.id.btnReplay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send to PlayActivity (start new game)
                Intent intent = new Intent(StatsActivity.this, PlayActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Home button
        findViewById(R.id.btnHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send to HomeActivity
                Intent intent = new Intent(StatsActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    public String getGradeByScore(){

        int grade = stats.getScore();

        if(grade < 300){
            return "F";
        }else if(grade > 300 && grade <= 600){
            return "D";
        }else if(grade > 600 && grade <= 1000){
            return "C";
        }else if(grade > 1000 && grade <= 1200){
            return "B";
        }else if(grade > 1200 && grade <= 1600){
            return "A";
        }else if(grade > 1600 && grade <= 2000){
            return "S";
        }else if(grade > 2000 && grade <= 2500){
            return "SS";
        }else{
            return "SSS";
        }
    }

}
