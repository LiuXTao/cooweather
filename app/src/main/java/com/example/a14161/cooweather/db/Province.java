package com.example.a14161.cooweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by 14161 on 2017/4/22.
 */

public class Province extends DataSupport {
    private int id;
    private String provinceName;
    private int provinceCode;
    public int getID(){
        return this.id;
    }
    public String getProvinceName(){
        return this.provinceName;
    }
    public int getProvinceCode(){
        return this.provinceCode;
    }
    public void setID(int id){
        this.id=id;
    }
    public void setProvinceName(String name){
        this.provinceName=name;
    }
    public void setProvinceCode(int cod){
        this.provinceCode=cod;
    }
    public Province(){
    }
}
