package com.stucom.grupo4.typhone.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.stucom.grupo4.typhone.R;

import static com.stucom.grupo4.typhone.activities.HomeActivity.mediaPlayer;

public class SettingsActivity extends AppCompatActivity {

    private Switch music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        music = findViewById(R.id.music_switch);
        music.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mediaPlayer.start();
                }else{
                    mediaPlayer.stop();
                }
            }
        });

        //findViewById(R.id.btn_languages).setOnClickListener();
    }

    public void changeLanguages(){

    }

}
