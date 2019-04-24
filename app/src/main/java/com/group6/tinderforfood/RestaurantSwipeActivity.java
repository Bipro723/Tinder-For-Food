package com.group6.tinderforfood;

import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.yelp.fusion.client.connection.YelpFusionApi;
import com.yelp.fusion.client.connection.YelpFusionApiFactory;
import com.yelp.fusion.client.models.ApiKey;
import com.yelp.fusion.client.models.Business;
import com.yelp.fusion.client.models.Coordinates;
import com.yelp.fusion.client.models.SearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantSwipeActivity extends AppCompatActivity {

    TextView restaurantTitle, bottomText;
    ImageView restaurantImage;
    YelpFusionApiFactory apiFactory;
    YelpFusionApi yelpFusionApi;
    Map<String, String> mParams;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_swipe);


        restaurantTitle = (TextView) findViewById(R.id.restaurantTitle);
        bottomText = (TextView) findViewById(R.id.bottomText);
        restaurantImage = (ImageView) findViewById(R.id.restaurantImage);

        Picasso
                .get()
                .load("https://i.imgur.com/DvpvklR.png")
                .into(restaurantImage);


        restaurantImage.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeTop() {
                Toast.makeText(RestaurantSwipeActivity.this, "top", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeRight() {
                Toast.makeText(RestaurantSwipeActivity.this, "right", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeLeft() {
                Toast.makeText(RestaurantSwipeActivity.this, "left", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeBottom() {
                Toast.makeText(RestaurantSwipeActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }
        });


        String apiKey = (String.valueOf(R.string.apiKey));//"hGAW2FySQrZqdHTxFT4s_fY-4OErTolDk-jyWn9r_6GKi0VCBw_mVcJuqidHQgNkfTSid0Rb4CS5pqrr2AoApLauOJUKalIig1V7Ye6aI2eMalROQzZcPTpiy5PAXHYx";
        apiFactory = new YelpFusionApiFactory();


        try {
            yelpFusionApi = apiFactory.createAPI("hGAW2FySQrZqdHTxFT4s_fY-4OErTolDk-jyWn9r_6GKi0VCBw_mVcJuqidHQgNkfTSid0Rb4CS5pqrr2AoApLauOJUKalIig1V7Ye6aI2eMalROQzZcPTpiy5PAXHYx");
        } catch (IOException e) {
            e.printStackTrace();
        }

        mParams = new HashMap<>();
        mParams.put("term", "indian food");
        mParams.put("latitude", "37.7577");
        mParams.put("longitude", "-122.4376");



        new FetchPictures().execute();

    }




     class FetchPictures extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            Call<SearchResponse> call = yelpFusionApi.getBusinessSearch(mParams);
            Response<SearchResponse> response = null;
            try {
                System.out.println("************************************************");
                response = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(response != null) {
                System.out.println("NOTTTT NULLOLLLLLLLLLLLLLLLLL");
                List<Business> businesses = new ArrayList<>();
                businesses = response.body().getBusinesses();

                for (Business b : businesses
                     ) {
                    System.out.println(b.getName());
                }
            }else{

                System.out.println("NNNNNUUUUUUUUUUUUUUUUULLLLLLLLLLLLLLLLLLLLL");
            }
            return null;
        }
    }
}
