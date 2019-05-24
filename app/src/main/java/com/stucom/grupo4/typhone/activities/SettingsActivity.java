package com.stucom.grupo4.typhone.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.stucom.grupo4.typhone.R;
import com.stucom.grupo4.typhone.control.AudioController;

public class SettingsActivity extends AppCompatActivity {

    private AudioController audio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        audio = AudioController.getInstance();

        Switch musicSwitch = findViewById(R.id.switchMusic);
        musicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                audio.muteMusic(!isChecked);
            }
        });

        Switch sfxSwitch = findViewById(R.id.switchSfx);
        sfxSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                audio.muteMusic(!isChecked);
            }
        });
    }

    public void changeLanguages() { }
}
