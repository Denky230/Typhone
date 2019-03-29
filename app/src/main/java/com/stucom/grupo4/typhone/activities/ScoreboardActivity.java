package com.stucom.grupo4.typhone.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.stucom.grupo4.typhone.R;

public class ScoreboardActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        recyclerView = findViewById(R.id.recyclerView);

    }


    protected void onPause(){
        super.onPause();
    }
}
