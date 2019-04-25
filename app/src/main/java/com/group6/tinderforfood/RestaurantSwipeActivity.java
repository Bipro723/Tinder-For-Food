package com.group6.tinderforfood;

import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;

public class RestaurantSwipeActivity extends AppCompatActivity {

    TextView restaurantTitle, bottomText;
    ImageView restaurantImage;
    YelpFusionApiFactory apiFactory;
    YelpFusionApi yelpFusionApi;
    Map<String, String> mParams;
    OkHttpClient mClient;
    List<Restaurant> mRestaurants;
    int i;
    ProgressBar mLoading;
    boolean waiting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_swipe);


        mClient = new OkHttpClient();
        restaurantTitle = (TextView) findViewById(R.id.restaurantTitle);
        bottomText = (TextView) findViewById(R.id.bottomText);
        restaurantImage = (ImageView) findViewById(R.id.restaurantImage);
        mLoading = (ProgressBar) findViewById(R.id.loading);
        mRestaurants = new ArrayList<>();
        i=0;



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
        waitForRestaurant(true);

    }

    synchronized public void waitForRestaurant(boolean client) {
        if(client) {
            if(mRestaurants.size() > i &&
                    mRestaurants.get(i).getPictures().size() > mRestaurants.get(i).getCurrPic()) {
                // have data
                restaurantCallback();
            }else{
                waiting = true;
                mLoading.setVisibility(View.VISIBLE);
            }
        }else{
            if(waiting) {
                restaurantCallback();
                waiting = false;
                mLoading.setVisibility(View.INVISIBLE);
            }
        }
    }


    public void restaurantCallback() {
        displayRestaurant(mRestaurants.get(i));
    }

    public void displayRestaurant(Restaurant r) {
        
    }

     class FetchPictures extends AsyncTask<String, Restaurant, String> {

        List<Restaurant>  restaurants;
         @Override
         protected void onProgressUpdate(Restaurant... values) {
             super.onProgressUpdate(values);
             if (values != null) {
                 mRestaurants.add(values[0]);
                 waitForRestaurant(false);
             } else {
                 Toast.makeText(RestaurantSwipeActivity.this, "No data available for your location", Toast.LENGTH_SHORT).show();
             }
         }
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
                List<Business> businessList = new ArrayList<>();
                businessList= response.body().getBusinesses();

                restaurants = new ArrayList<>();
                Restaurant r;
                int i = 0;
                for (Business b : businessList) {
                    r = new Restaurant(b.getName(), b.getUrl());
                    r.setRating(b.getRating()+"");
                    fetchPictures(r, i);
                    i++;
                }
            }else{

                System.out.println("NNNNNUUUUUUUUUUUUUUUUULLLLLLLLLLLLLLLLLLLLL");
            }
            return null;
        }

        private void fetchPictures(Restaurant r, final int pos) {

                Request request = new Request.Builder()
                        .url(r.getPicUrl())
                        .build();

                mClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(okhttp3.Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {

                        List<String> pictures = RestaurantParser.getPictures(response.body().string());
                        if (pictures.size() > 0 ) {
                            restaurants.get(pos).setPictures(pictures);
                            onProgressUpdate(restaurants.get(pos));
                        }

                    }
                }); {

                }


        }
    }
}
