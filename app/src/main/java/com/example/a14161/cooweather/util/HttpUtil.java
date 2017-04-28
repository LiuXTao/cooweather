package com.example.a14161.cooweather.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by 14161 on 2017/4/25.
 */

public class HttpUtil {
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient lcient=new OkHttpClient();
        Request request=new Request.Builder().url(address).build();
        lcient.newCall(request).enqueue(callback);
    }
}
