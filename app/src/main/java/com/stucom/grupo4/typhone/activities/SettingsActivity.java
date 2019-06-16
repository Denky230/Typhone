package com.stucom.grupo4.typhone.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.stucom.grupo4.typhone.R;
import com.stucom.grupo4.typhone.control.AudioController;
import com.stucom.grupo4.typhone.tools.Tools;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    private AudioController audio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        audio = AudioController.getInstance();

        // Init music switch
        Switch musicSwitch = findViewById(R.id.switchMusic);
        musicSwitch.setText(R.string.lblMusic);
        musicSwitch.setChecked(!audio.isMusicMuted());
        musicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                audio.muteMusic(!isChecked);
            }
        });

        // Init SFX switch
        Switch sfxSwitch = findViewById(R.id.switchSfx);
        sfxSwitch.setChecked(!audio.isSfxMuted());
        sfxSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                audio.muteSfx(!isChecked);
            }
        });

        Tools.log(Locale.getDefault() + " locale");

        //needs to be done from the phone settings

        Button btnLanguage = findViewById(R.id.btnLanguages);
        btnLanguage.setText(R.string.btnLanguages);
        btnLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Locale locale = new Locale("es_ES");
                Locale.setDefault(locale);
                Configuration config = getResources().getConfiguration();
                config.locale = locale;

                //config.setLocale(locale);
                getApplicationContext().getResources().updateConfiguration(config, getResources().getDisplayMetrics());

                Tools.log(Locale.getDefault() + " locale");

                //recreate();

                Intent intent = new Intent(SettingsActivity.this, SettingsActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
