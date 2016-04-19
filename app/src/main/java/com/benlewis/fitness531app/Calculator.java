package com.benlewis.fitness531app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Calculator extends AppCompatActivity {

    EditText weight;
    EditText reps;
    TextView result;
    Button calculateButton;
    Button pushPreferencesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        result = (TextView) findViewById(R.id.calcResultTextView);
        calculateButton = (Button) findViewById(R.id.calcButton);
        weight = (EditText) findViewById(R.id.calcWeightEditText);
        reps = (EditText) findViewById(R.id.calcRepsEditText);
        pushPreferencesButton = (Button) findViewById(R.id.calcPushPreferencesButton);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double w = Double.parseDouble(weight.getText().toString());
                double r = Double.parseDouble(reps.getText().toString());

                result.setText(calc1RM(w,r));
            }
        });

        pushPreferencesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int r = Integer.parseInt(result.getText().toString());
                MainActivity.sharedPreferences.edit().putInt("ohp", r).apply();
            }
        });
    }

    public String calc1RM (double weight, double reps) {

        double r = (weight * (1 + (reps/30)));

        r = Math.round(r);

        return Integer.toString((int)r);
    }
}
