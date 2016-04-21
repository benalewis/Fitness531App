package com.benlewis.fitness531app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView warmUp1;
    TextView warmUp2;
    TextView warmUp3;
    TextView fiveThreeOne1;
    TextView fiveThreeOne2;
    TextView fiveThreeOne3;
    TextView timerTextView;

    Button stopStartButton;
    Button resetButton;

    int ohp1rm;
    int bench1rm;
    int squat1rm;
    int deadlift1rm;
    long timer;
    String metric;

    String liftSpinnerString = "";
    String weekSpinnerString = "";

    String[] repsArray = { "5", "5", "3"};
    String[] week1Array = {"5", "5", "5+"};
    String[] week2Array = {"3", "3", "3+"};
    String[] week3Array = {"5", "3", "1+"};

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

        timerTextView = (TextView) findViewById(R.id.mainTimerTextView);
        resetButton = (Button) findViewById(R.id.mainResetButton);
        stopStartButton = (Button) findViewById(R.id.mainStopStartButton);

        sharedPreferences = this.getSharedPreferences("com.benlewis.fitness531app", Context.MODE_PRIVATE);

        initPreferences();
        updateLifts();

        //Lifts spinner code
        Spinner liftSpinner = (Spinner) findViewById(R.id.mainLiftSpinner);
        ArrayAdapter<CharSequence> liftAdapter = ArrayAdapter.createFromResource(this, R.array.calcArray, android.R.layout.simple_spinner_item);
        liftAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        liftSpinner.setAdapter(liftAdapter);

        liftSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        //Week spinner code
        Spinner weekSpinner = (Spinner) findViewById(R.id.mainWeekSpinner);
        ArrayAdapter<CharSequence> weekAdapter = ArrayAdapter.createFromResource(this, R.array.weekArray, android.R.layout.simple_spinner_item);
        weekAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weekSpinner.setAdapter(weekAdapter);

        weekSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String r = parent.getItemAtPosition(position).toString();
                weekSpinnerString = r.toLowerCase();
                updateLifts();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Timer values passed are in ms, 2nd value is how often to call the first method
        final CountDownTimer countDownTimer = new CountDownTimer(timer, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                int totalSeconds = (int)millisUntilFinished / 1000;
                String minutes = Integer.toString(totalSeconds/60);
                String secondsLeft = Integer.toString(totalSeconds%60);

                timerTextView.setText("Time Left: " + minutes + ":" + secondsLeft);
            }

            @Override
            public void onFinish() {
                timerTextView.setText("Finished");
                stopStartButton.setText("Start");

            }
        };

        stopStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String b = stopStartButton.getText().toString().toLowerCase();

                if (b.equals("start")) {
                    countDownTimer.start();
                    stopStartButton.setText("Stop");
                }

                if (b.equals("stop")) {
                    countDownTimer.cancel();
                    stopStartButton.setText("Start");
                }

            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                countDownTimer.start();
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
            sharedPreferences.edit().putLong("timer", 0).apply();
            sharedPreferences.edit().putString("metric", "kg").apply();
        }

        ohp1rm = sharedPreferences.getInt("ohp", 0);
        bench1rm = sharedPreferences.getInt("bench", 0);
        squat1rm = sharedPreferences.getInt("squat", 0);
        deadlift1rm = sharedPreferences.getInt("deadlift", 0);
        timer = sharedPreferences.getLong("timer", 0);
        metric = sharedPreferences.getString("metric", "");

        //Timer
        int totalSeconds = (int)timer / 1000;
        String minutes = Integer.toString(totalSeconds/60);
        String secondsLeft = Integer.toString(totalSeconds%60);
        timerTextView.setText("Time Left: " + minutes + ":" + secondsLeft);
    }

    public void updateLifts() {
        //Called every time the activity is loaded.
        //Fills the text fields with the correct data

        switch (liftSpinnerString) {
            case "ohp":
                warmUp1.setText(calcWarmUp(ohp1rm, 1));
                warmUp2.setText(calcWarmUp(ohp1rm, 2));
                warmUp3.setText(calcWarmUp(ohp1rm, 3));
                fiveThreeOne1.setText(calc531(ohp1rm, 1, weekSpinnerString));
                fiveThreeOne2.setText(calc531(ohp1rm, 2, weekSpinnerString));
                fiveThreeOne3.setText(calc531(ohp1rm, 3, weekSpinnerString));
                break;
            case "bench":
                warmUp1.setText(calcWarmUp(bench1rm, 1));
                warmUp2.setText(calcWarmUp(bench1rm, 2));
                warmUp3.setText(calcWarmUp(bench1rm, 3));
                fiveThreeOne1.setText(calc531(bench1rm, 1, weekSpinnerString));
                fiveThreeOne2.setText(calc531(bench1rm, 2, weekSpinnerString));
                fiveThreeOne3.setText(calc531(bench1rm, 3, weekSpinnerString));
                break;
            case "squat":
                warmUp1.setText(calcWarmUp(squat1rm, 1));
                warmUp2.setText(calcWarmUp(squat1rm, 2));
                warmUp3.setText(calcWarmUp(squat1rm, 3));
                fiveThreeOne1.setText(calc531(squat1rm, 1, weekSpinnerString));
                fiveThreeOne2.setText(calc531(squat1rm, 2, weekSpinnerString));
                fiveThreeOne3.setText(calc531(squat1rm, 3, weekSpinnerString));
                break;
            case "deadlift":
                warmUp1.setText(calcWarmUp(deadlift1rm, 1));
                warmUp2.setText(calcWarmUp(deadlift1rm, 2));
                warmUp3.setText(calcWarmUp(deadlift1rm, 3));
                fiveThreeOne1.setText(calc531(deadlift1rm, 1, weekSpinnerString));
                fiveThreeOne2.setText(calc531(deadlift1rm, 2, weekSpinnerString));
                fiveThreeOne3.setText(calc531(deadlift1rm, 3, weekSpinnerString));
                break;
            default:
                break;
        }
    }

    public String calcWarmUp(int oneRepMax, int warmUpNumber) {

        double adjustment = 0;
        String s = "";
        int repsArrayRef  = 0;

        switch (warmUpNumber) {
            case 1:
                adjustment = 0.4;
                repsArrayRef = 0;
                break;
            case 2:
                adjustment = 0.5;
                repsArrayRef = 1;
                break;
            case 3:
                adjustment = 0.6;
                repsArrayRef = 2;
                break;
        }


        s = (((int) (oneRepMax * adjustment)) + metric + " x " + repsArray[repsArrayRef]);
        return s;
    }

    public String calc531(int oneRepMax, int liftNumber, String weekSpinnerString) {
        double adjustment = 0;
        String warmUp;
        double weekAdjustment = 1;
        String[] calc531array = { "", "", ""};
        int arrayRef = 0;

        switch (weekSpinnerString) {
            case "1":
                weekAdjustment = 1;
                calc531array = week1Array.clone();
                break;
            case "2":
                weekAdjustment = 1.05;
                calc531array = week2Array.clone();
                break;
            case "3":
                weekAdjustment = 1.1;
                calc531array = week3Array.clone();
                break;
        }

        switch (liftNumber) {
            case 1:
                adjustment = 0.75;
                arrayRef = 0;
                break;
            case 2:
                adjustment = 0.85;
                arrayRef = 1;
                break;
            case 3:
                adjustment = 0.95;
                arrayRef = 2;
                break;
        }

        warmUp = (((int) (oneRepMax * adjustment * weekAdjustment)) + metric + " x " + calc531array[arrayRef]);
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
