package com.example.a14161.cooweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.d("Test","herra");
        setContentView(R.layout.activity_main);
        //Log.d("Test","herre");
        //启动活动ChooseAreaActivity
       // Intent intent=new Intent(MainActivity.this,ChooseAreaActivity.class);
        //startActivity(intent);
        //finish();

        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        if(prefs.getString("weather",null)!=null){
            Intent intent=new Intent(this,WeatherActivity.class);
            startActivity(intent);
            finish();
        }


    }
}
