package com.example.a14161.cooweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by 14161 on 2017/4/22.
 */

public class City extends DataSupport{
    private int id;
    private String cityName;
    private int cityCode;
    private int provinceId;
    public int getID(){
        return this.id;
    }
    public String getCityName(){
        return this.cityName;
    }
    public int getCityCode(){
        return this.cityCode;
    }
    public void setID(int id){
        this.id=id;
    }
    public void sstCityName(String name){
        this.cityName=name;
    }
    public void setCityCode(int cod){
        this.cityCode=cod;
    }
    public void setProvinceId(int id){
        this.provinceId=id;
    }
    public int getProvinceId(){
        return this.provinceId;
    }
    public City(){
    }
}
