package com.example.a14161.cooweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by 14161 on 2017/4/22.
 */

public class County extends DataSupport{
    private int id;
    private String countyName;
    private String weatherId;
    private int cityId;
    public void setId(int id){
        this.id=id;
    }
    public void setCountyName(String name){
        countyName=name;
    }
    public void setCityId(int id){
        this.cityId=id;
    }
    public void setWeatherId(String id){
        this.weatherId=id;
    }
    public int getId(){
        return this.id;
    }
    public String getCountyName(){
        return countyName;
    }
    public String getWeatherId(){
        return weatherId;
    }
    public int getCityId(){
        return cityId;
    }
    public County(){

    }
}
