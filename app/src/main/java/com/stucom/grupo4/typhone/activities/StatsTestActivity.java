package com.stucom.grupo4.typhone.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.stucom.grupo4.typhone.R;
import com.stucom.grupo4.typhone.model.Stats;

public class StatsTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_test);

        // Retrieve stats object
        Stats stats = (Stats) getIntent().getSerializableExtra("stats");
    }
}
