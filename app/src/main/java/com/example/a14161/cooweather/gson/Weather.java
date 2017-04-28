package com.example.a14161.cooweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 14161 on 2017/4/26.
 */

public class Weather {
    public String status;
    public Basic basic;
    public AQI aqi;
    public Now now;
    public Suggestion suggestion;
    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
}
