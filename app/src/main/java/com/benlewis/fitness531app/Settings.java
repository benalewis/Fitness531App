package com.benlewis.fitness531app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Settings extends AppCompatActivity {

    EditText timerEditText;
    Button setTimerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        timerEditText = (EditText) findViewById(R.id.settingsTimerEditText);
        setTimerButton = (Button) findViewById(R.id.settingsSetTimerButton);

        setTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long i = Long.parseLong(timerEditText.getText().toString());
                MainActivity.sharedPreferences.edit().putLong("timer", i).apply();
            }
        });
    }

}
