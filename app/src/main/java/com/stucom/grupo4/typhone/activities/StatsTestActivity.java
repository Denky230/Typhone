package com.stucom.grupo4.typhone.activities;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.stucom.grupo4.typhone.R;
import com.stucom.grupo4.typhone.model.Stats;

public class StatsTestActivity extends AppCompatActivity {

    private TextView grade, highScore, score, totalInputs, maxInputs, avgInputs, wordStreak, letterStreak, accuracyPercentage;
    private ProgressBar accuracy;
    private View fullActivity;
    private Stats stats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_test);

        // Retrieve stats object
        stats = (Stats) getIntent().getSerializableExtra("stats");

        grade = findViewById(R.id.lblGradeStat);
        grade.setTextSize(100);
        grade.setText(getGradeByScore());

        highScore = findViewById(R.id.lblHighScoreStat);

        score = findViewById(R.id.lblScoreStat);
        score.setText(String.valueOf(stats.getScore()));

        totalInputs = findViewById(R.id.lblTotalInputsStat);
        totalInputs.setText(String.valueOf(stats.getInputsTotal()));

        accuracy = findViewById(R.id.lblAccuracyStat);
        accuracy.setMax(100);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            accuracy.setProgress((int)stats.getAccuracy(), true);
        }else {
            accuracy.setProgress((int)stats.getAccuracy());
        }

        accuracyPercentage = findViewById(R.id.lblAccuracyPercentage);
        accuracyPercentage.setText( (Float.isNaN(stats.getAccuracy()) ? 0 : stats.getAccuracy()) + "%");

        //TODO still needs max & avg inputs and word & letter streak

        maxInputs = findViewById(R.id.lblMaxInputsStat);
        maxInputs.setText(String.valueOf(stats.getIpsMax()));

        avgInputs = findViewById(R.id.lblAvgInputsStat);
        avgInputs.setText(String.valueOf(stats.getIpsAvg()));


        wordStreak = findViewById(R.id.lblWordStreakStat);
        wordStreak.setText(String.valueOf(stats.getHiStreakWord()));

        letterStreak = findViewById(R.id.lblLetterStreakStat);
        letterStreak.setText(String.valueOf(stats.getHiStreakLetter()));

        fullActivity = findViewById(R.id.entireView);
        fullActivity.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){

                    Intent intent = new Intent(StatsTestActivity.this, StatsActivity.class);
                    startActivity(intent);
                    finish();

                    return true;
                }
                return false;
            }
        });

    }


    public String getGradeByScore(){

        int grade = stats.getScore();

        if(grade < 300){
            return "F";
        }else if(grade > 300 && grade < 600){
            return "D";
        }else if(grade > 600 && grade < 1000){
            return "C";
        }else if(grade > 1000 && grade < 1200){
            return "B";
        }else if(grade > 1200 && grade < 1600){
            return "A";
        }else if(grade > 1600 && grade < 2000){
            return "S";
        }else{
            return "SS";
        }
    }

}
