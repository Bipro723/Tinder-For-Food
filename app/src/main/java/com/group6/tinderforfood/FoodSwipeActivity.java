package com.group6.tinderforfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FoodSwipeActivity extends AppCompatActivity {

    List<FoodCategory> categories;
    ImageView foodImage;
    TextView foodTitle;
    ProgressBar mLoading;

    //NAVBAR VARS
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_swipe);

        //NAVBAR CODE

        dl = (DrawerLayout) findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);

        dl.addDrawerListener(abdt);
        abdt.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        final NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.profile_icon) {
                    Toast.makeText(FoodSwipeActivity.this, "MyProfile", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FoodSwipeActivity.this, ProfileActivity.class);
                    startActivity(intent);
                } else if (id == R.id.settings) {
                    Toast.makeText(FoodSwipeActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FoodSwipeActivity.this, SettingsActivity.class);
                    startActivity(intent);
                } else if (id == R.id.refresh) {
                    Toast.makeText(FoodSwipeActivity.this, "Refreshing...", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(getIntent());
                }

                return true;
            }
        });

        //END OF NAVBAR

        foodTitle = (TextView) findViewById(R.id.foodTitle);
        //mRating = (TextView) findViewById(R.id.bottomText);
        foodImage = (ImageView) findViewById(R.id.foodImage);
        mLoading = (ProgressBar) findViewById(R.id.loading);
        categories = new ArrayList<>();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}
