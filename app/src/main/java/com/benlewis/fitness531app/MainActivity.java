package com.benlewis.fitness531app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView warmUp1;
    TextView warmUp2;
    TextView warmUp3;
    Button refreshButton;
    int OHP1RM;
    int Bench1RM;
    int Squat1RM;
    int Deadlift1RM;

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
        refreshButton = (Button) findViewById(R.id.mainRefreshButton);
        sharedPreferences = this.getSharedPreferences("com.benlewis.fitness531app", Context.MODE_PRIVATE);

        initPreferences();
        updateLifts();
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

        OHP1RM = sharedPreferences.getInt("ohp", 0);
        Bench1RM = sharedPreferences.getInt("bench", 0);
        Squat1RM = sharedPreferences.getInt("squat", 0);
        Deadlift1RM = sharedPreferences.getInt("deadlift", 0);
    }

    public void updateLifts() {
        warmUp1.setText(calcWarmUp(OHP1RM, 1));
        warmUp2.setText(calcWarmUp(OHP1RM, 2));
        warmUp3.setText(calcWarmUp(OHP1RM, 3));
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
