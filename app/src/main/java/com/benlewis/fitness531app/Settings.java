package com.benlewis.fitness531app;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

public class Settings extends AppCompatActivity {

    EditText timerEditText;
    Button setTimerButton;
    Button resetAll;

    ToggleButton metricToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        timerEditText = (EditText) findViewById(R.id.settingsTimerEditText);
        setTimerButton = (Button) findViewById(R.id.settingsSetTimerButton);
        metricToggle = (ToggleButton) findViewById(R.id.settingsMetricToggle);
        resetAll = (Button) findViewById(R.id.settingsResetAllButton);

        resetAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.sharedPreferences.edit().putInt("ohp", 0).apply();
                MainActivity.sharedPreferences.edit().putInt("bench", 0).apply();
                MainActivity.sharedPreferences.edit().putInt("squat", 0).apply();
                MainActivity.sharedPreferences.edit().putInt("deadlift", 0).apply();
                MainActivity.sharedPreferences.edit().putLong("timer", 0).apply();
                MainActivity.sharedPreferences.edit().putString("metric", "kg").apply();
            }
        });

        setTimerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Snackbar snackBar1 = Snackbar.make(findViewById(R.id.settingsResetAllButton),
                        "Input a value.", Snackbar.LENGTH_LONG);
                Snackbar snackBar2 = Snackbar.make(findViewById(R.id.settingsResetAllButton),
                        "Timer updated.", Snackbar.LENGTH_LONG);
                try {
                    long i = Long.parseLong(timerEditText.getText().toString());
                    MainActivity.sharedPreferences.edit().putLong("timer", i*1000).apply();
                    snackBar2.show();
                } catch (Exception e) {
                    snackBar1.show();
                }
            }
        });

        metricToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    MainActivity.sharedPreferences.edit().putString("metric", "kg").apply();
                    metricToggle.setAlpha(1f);
                } else {
                    MainActivity.sharedPreferences.edit().putString("metric", "lbs").apply();
                    metricToggle.setAlpha(0.8f);
                }
            }
        });
    }
}
