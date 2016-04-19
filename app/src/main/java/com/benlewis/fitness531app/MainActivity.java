package com.benlewis.fitness531app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView warmUp1;
    TextView warmUp2;
    TextView warmUp3;
    TextView fiveThreeOne1;
    TextView fiveThreeOne2;
    TextView fiveThreeOne3;

    int ohp1rm;
    int bench1rm;
    int squat1rm;
    int deadlift1rm;

    String liftSpinnerString = "";

    static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        warmUp1 = (TextView) findViewById(R.id.warmup1TextView);
        warmUp2 = (TextView) findViewById(R.id.warmup2TextView);
        warmUp3 = (TextView) findViewById(R.id.warmup3TextView);

        fiveThreeOne1 = (TextView) findViewById(R.id.main531_1);
        fiveThreeOne2 = (TextView) findViewById(R.id.main531_2);
        fiveThreeOne3 = (TextView) findViewById(R.id.main531_3);

        sharedPreferences = this.getSharedPreferences("com.benlewis.fitness531app", Context.MODE_PRIVATE);

        initPreferences();
        updateLifts();

        //Spinner code
        Spinner spinner = (Spinner) findViewById(R.id.mainLiftSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.calcArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String r = parent.getItemAtPosition(position).toString();
                liftSpinnerString = r.toLowerCase();
                updateLifts();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void initPreferences() {

        //Get the data, if there's no data it hasn't been initialised. Init if not
        try {
            sharedPreferences.getInt("ohp", 0);
        } catch (Exception e) {
            e.printStackTrace();
            sharedPreferences.edit().putInt("ohp", 0).apply();
            sharedPreferences.edit().putInt("bench", 0).apply();
            sharedPreferences.edit().putInt("squat", 0).apply();
            sharedPreferences.edit().putInt("deadlift", 0).apply();
        }

        ohp1rm = sharedPreferences.getInt("ohp", 0);
        bench1rm = sharedPreferences.getInt("bench", 0);
        squat1rm = sharedPreferences.getInt("squat", 0);
        deadlift1rm = sharedPreferences.getInt("deadlift", 0);
    }

    public void updateLifts() {
        //Called every time the activity is loaded.
        //Fills the text fields with the correct data
        switch (liftSpinnerString) {
            case "ohp":
                warmUp1.setText(calcWarmUp(ohp1rm, 1));
                warmUp2.setText(calcWarmUp(ohp1rm, 2));
                warmUp3.setText(calcWarmUp(ohp1rm, 3));
                fiveThreeOne1.setText(calc531(ohp1rm, 1));
                fiveThreeOne2.setText(calc531(ohp1rm, 2));
                fiveThreeOne3.setText(calc531(ohp1rm, 3));
                break;
            default:
                break;
        }
    }

    public String calcWarmUp(int oneRepMax, int warmUpNumber) {

        double adjustment = 0;
        int reps = 0;
        String warmUp = "";

        switch (warmUpNumber) {
            case 1:
                adjustment = 0.4;
                reps = 5;
                break;
            case 2:
                adjustment = 0.5;
                reps = 5;
                break;
            case 3:
                adjustment = 0.6;
                reps = 3;
                break;
        }

        warmUp = (((int) (oneRepMax * adjustment)) + " x " + reps);
        return  warmUp;
    }

    public String calc531(int oneRepMax, int liftNumber) {
        double adjustment = 0;
        int reps = 0;
        String warmUp = "";

        switch (liftNumber) {
            case 1:
                adjustment = 0.75;
                reps = 5;
                break;
            case 2:
                adjustment = 0.85;
                reps = 5;
                break;
            case 3:
                adjustment = 0.95;
                reps = 5;
                break;
        }

        warmUp = (((int) (oneRepMax * adjustment)) + " x " + reps);
        return  warmUp;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.settings) {

            Intent i = new Intent(getApplicationContext(), Settings.class);
            startActivity(i);

            return true;
        }

        if (id == R.id.calculator) {

            Intent i = new Intent(getApplicationContext(), Calculator.class);
            startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
