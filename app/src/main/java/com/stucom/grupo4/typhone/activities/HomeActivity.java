package com.stucom.grupo4.typhone.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.stucom.grupo4.typhone.R;
import com.stucom.grupo4.typhone.control.AudioController;

public class HomeActivity extends AppCompatActivity {

    private AudioController audio;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        audio = AudioController.getInstance();

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
        // Settings button
        findViewById(R.id.btn_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override protected void onResume() {
        super.onResume();

        // Set Menu music
        audio.setMusic(this, AudioController.Music.MENU, true);
        audio.startMusic();
    }

    private void setupMainWindowDisplayMode() {
        View decorView = setSystemUIVisibilityMode();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                setSystemUIVisibilityMode(); // Needed to avoid exiting immersive_sticky when keyboard is displayed
            }
        });
    }
    private View setSystemUIVisibilityMode() {
        View decorView = getWindow().getDecorView();
        int options =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        decorView.setSystemUiVisibility(options);
        return decorView;
    }
}