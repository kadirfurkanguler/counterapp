package com.example.counterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SensorManager sensorManager;
    int count = 0, downLimit , upLimit;
    TextView totalCount;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensorShake = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        SensorEventListener sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent != null) {
                    float x = sensorEvent.values[0];
                    float y = sensorEvent.values[1];
                    float z = sensorEvent.values[2];

                    if (x > 2 || x < -2 || y > 10 || y < -10 || z > 2 || z < -2) {
                        count = 0;
                        totalCount.setText("0");
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        sensorManager.registerListener(sensorEventListener, sensorShake, sensorManager.SENSOR_DELAY_NORMAL);
        setContentView(R.layout.activity_main);
        totalCount = (TextView) findViewById(R.id.totalCount);
        totalCount.setText(Integer.toString(count));
        sharedPreferences= getApplicationContext().getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE);

    }
    public void handleClicked (View v) {
        switch (v.getId()){
            case R.id.azalt:
                if(count>downLimit) {
                    count--;
                }
                break;
            case R.id.arttir:
                if(count<upLimit) {
                    count++;

                }
                break;
            case R.id.settings:
                startActivity(new Intent(MainActivity.this, SettingScreen.class));
                break;
        }

        totalCount.setText(Integer.toString(count));

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)){
            //Do something
            if(count+5>downLimit) {
                count = count - 5;
            }
        }
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP)){
            //Do something
            if(count-5<upLimit) {
                count = count + 5;
            }
        }
        totalCount.setText(Integer.toString(count));

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        downLimit = sharedPreferences.getInt("Down_Val", -20);
        upLimit = sharedPreferences.getInt("Up_Val", 20);

    }
}