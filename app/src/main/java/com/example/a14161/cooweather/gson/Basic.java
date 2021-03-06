package com.example.a14161.cooweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 14161 on 2017/4/26.
 */

public class Basic {
    @SerializedName("city")
    public String cityName;
    @SerializedName("id")
    public String weatherId;
    public Update update;

    public class Update{
        @SerializedName("loc")
        public String updateTime;
    }

}
