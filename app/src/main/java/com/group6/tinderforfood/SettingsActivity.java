package com.group6.tinderforfood;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.edmodo.rangebar.RangeBar;

import static java.lang.Integer.valueOf;

public class SettingsActivity extends AppCompatActivity {

    private SeekBar ratingbar;
    private SeekBar pricebar;
    private RangeBar rangebar;
    private TextView rating;
    private TextView price;
    private TextView min_distance;
    private TextView max_distance;
    private RadioGroup dietgroup; //this represents the entire radiogroup
    private RadioButton dietbutton; //this is going to store the value of the currently checked radiobutton
    public static String MY_PREFS = "MY_PREFS";
    private SharedPreferences mySharedPreferences;
    int prefMode = Activity.MODE_PRIVATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingslayout);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        mySharedPreferences = getSharedPreferences(MY_PREFS, prefMode);

        ratingbar = (SeekBar) findViewById(R.id.seekBar1);

        pricebar = (SeekBar) findViewById(R.id.seekBar2);

        rangebar = (RangeBar) findViewById(R.id.rangebar1);
        rangebar.setTickCount(20);

        rating = (TextView) findViewById(R.id.textView5);
        price = (TextView) findViewById(R.id.textView7);
        min_distance = (TextView) findViewById(R.id.editText1);
        max_distance = (TextView) findViewById(R.id.editText2);

        ratingbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onStopTrackingTouch(SeekBar seekBar){ //i'm not sure what these 2 methods are supposed to do, but i didn't have to change them.

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar){

            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                rating.setText(String.valueOf(progress+1)); //this changes the value in the textview so that it displays the seekbar value as it updates
                

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this); //this gets the sharedpreferences across the app
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Rating",toString().valueOf(progress+1)); //this is what you push to the sharedpreferences
                editor.apply(); //this completes the action
            }
        });

        pricebar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onStopTrackingTouch(SeekBar seekBar){

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar){

            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                if(valueOf(progress)==0){
                    price.setText("$");
                } else if(valueOf(progress)==1){
                    price.setText("$$");
                } else if(valueOf(progress)==2){
                    price.setText("$$$");
                }
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Price",toString().valueOf(progress));
                editor.apply();
            }
        });

        rangebar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onIndexChangeListener(RangeBar rangeBar, int leftThumbIndex, int rightThumbIndex) {

                min_distance.setText("" + leftThumbIndex);
                max_distance.setText("" + rightThumbIndex);

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Min_Distance",min_distance.getText()+"");
                editor.putString("Max_Distance",max_distance.getText()+"");
                editor.apply();
            }
        });

        // This will get the radiogroup
        dietgroup = (RadioGroup)findViewById(R.id.radioGroup1);
        // This will get the radiobutton in the radiogroup that is checked
        dietbutton = (RadioButton)dietgroup.findViewById(dietgroup.getCheckedRadioButtonId());

        // This overrides the radiogroup onCheckListener
        dietgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                RadioButton dietbutton = (RadioButton)group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = dietbutton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked)
                {
                }

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Diet",(String)dietbutton.getText());
                editor.apply();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp(){ //this changes the back button function

        finish(); //this finishes current activity, making the app go back to the previous activity
        return true;
    }
}
