package com.stucom.grupo4.typhone.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.stucom.grupo4.typhone.R;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

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
}