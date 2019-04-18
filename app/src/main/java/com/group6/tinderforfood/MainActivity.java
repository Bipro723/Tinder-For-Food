package com.group6.tinderforfood;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupNavbar();
    }

    private void setupNavbar(){
        BottomNavigationViewEx tvEx = (BottomNavigationViewEx) findViewById(R.id.navbar_layout);
        tvEx.enableAnimation(false);
        tvEx.enableItemShiftingMode(false);
        tvEx.enableShiftingMode(false);
        tvEx.setTextVisibility(false);
        tvEx.setIconSize(35,35);

        tvEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){
                switch (item.getItemId()) {
                    case R.id.profile_icon:
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    break;
                }
                return false;
            }
        });
        Menu menu = tvEx.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
    }
}
