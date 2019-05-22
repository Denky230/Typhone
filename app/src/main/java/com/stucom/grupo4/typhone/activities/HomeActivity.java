package com.stucom.grupo4.typhone.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.stucom.grupo4.typhone.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // No title, no nav bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_home);

        // Play button
        findViewById(R.id.btnPlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send to PlayActivity (start new game)
                Intent intent = new Intent(HomeActivity.this, PlayActivity.class);
                startActivity(intent);
            }
        });

        // Score button
        findViewById(R.id.btnScore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send to ScoreboardActivity
                Intent intent = new Intent(HomeActivity.this, ScoreboardActivity.class);
                startActivity(intent);
            }
        });
    }
}