package com.group6.tinderforfood;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingslayout);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

    }
    @Override
    public boolean onSupportNavigateUp(){ //this changes the back button function
        finish(); //this finishes current activity, making the app go back to the previous activity
        return true;
    }
}
