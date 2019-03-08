package com.stucom.grupo4.typhone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

public class PlayActivity extends AppCompatActivity {

    private TextView txtWord;
    private int index;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtWord = findViewById(R.id.txtWord);
        index = 0;

        // Make sure word to type is ALL CAPS
        txtWord.setAllCaps(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        String key = KeyEvent.keyCodeToString(keyCode);
        char letterTyped = key.substring(key.length() - 1).charAt(0);

        char letterToType = txtWord.getText().charAt(index);
        if (letterTyped == letterToType) {
            Log.d("dky", "gud");
        } else {
            Log.d("dky", "bad");
        }

        index++;

        return super.onKeyDown(keyCode, event);
    }
}