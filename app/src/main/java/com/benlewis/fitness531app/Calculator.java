package com.benlewis.fitness531app;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Calculator extends AppCompatActivity {

    EditText weight;
    EditText reps;
    TextView result;
    Button calculateButton;
    Button pushPreferencesButton;
    String spinnerString = "";

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

        //Spinner code
        Spinner spinner = (Spinner) findViewById(R.id.calcSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.calcArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String r =parent.getItemAtPosition(position).toString();
                spinnerString = r.toLowerCase();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snack bar Code
                final Snackbar snackbar = Snackbar.make(findViewById(R.id.calcResultTextView),
                        "Enter data in all fields", Snackbar.LENGTH_LONG);

                try {
                double w = Double.parseDouble(weight.getText().toString());
                double r = Double.parseDouble(reps.getText().toString());

                result.setText(calc1RM(w,r));
                } catch (Exception e) {
                    snackbar.show();
                }
            }
        });

        pushPreferencesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snack bar Code
                final Snackbar snackBar1 = Snackbar.make(findViewById(R.id.calcResultTextView),
                        "Result must be calculated first", Snackbar.LENGTH_LONG);

                try {
                    int r = Integer.parseInt(result.getText().toString());
                    MainActivity.sharedPreferences.edit().putInt(spinnerString, r).apply();
                    final Snackbar snackBar2 = Snackbar.make(findViewById(R.id.calcResultTextView),
                            spinnerString + " updated.", Snackbar.LENGTH_LONG);
                    snackBar2.show();
                } catch (Exception e ) {
                    e.printStackTrace();
                    snackBar1.show();
                }
            }
        });
    }

    public String calc1RM (double weight, double reps) {

        double r = (weight * (1 + (reps/30)));

        r = Math.round(r);

        return Integer.toString((int)r);
    }
}
