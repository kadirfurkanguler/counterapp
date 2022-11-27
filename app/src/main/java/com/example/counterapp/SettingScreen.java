package com.example.counterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SettingScreen extends AppCompatActivity {
    int down_limit, up_limit;
    EditText uplimit, downlimit;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_screen);
        // TODO Shared
        uplimit = (EditText) findViewById(R.id.uplimit);
        downlimit = (EditText) findViewById(R.id.downlimit);
        sharedPreferences= getApplicationContext().getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
        down_limit = sharedPreferences.getInt("Down_Val", -20);
        up_limit = sharedPreferences.getInt("Up_Val", 20);

        editor = sharedPreferences.edit();
        uplimit.setText(Integer.toString(up_limit));
        downlimit.setText(Integer.toString(down_limit));
    }
    public void handleClickedSettings (View v) {
        switch (v.getId()){
            case R.id.up_arttir:
                up_limit++;
                break;
            case R.id.up_azalt:
                up_limit--;
                break;
            case R.id.down_arttir:
                down_limit++;
                break;
            case R.id.down_azlat:
                down_limit--;
                break;

        }
        uplimit.setText(Integer.toString(up_limit));
        downlimit.setText(Integer.toString(down_limit));
    }

    @Override
    protected void onPause() {
        super.onPause();
        editor.putInt("Down_Val", Integer.parseInt(downlimit.getText().toString()));
        editor.putInt("Up_Val", Integer.parseInt(uplimit.getText().toString()));
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        down_limit = sharedPreferences.getInt("Down_Val", -20);
        up_limit = sharedPreferences.getInt("Up_Val", 20);

    }
}