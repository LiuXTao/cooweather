package com.example.a14161.cooweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 14161 on 2017/4/26.
 */

public class AQI {

    public AQICity city;
    public class AQICity{
        public String api;
        public String pm25;
    }
}
