package com.example.a14161.cooweather;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a14161.cooweather.db.City;
import com.example.a14161.cooweather.db.County;
import com.example.a14161.cooweather.db.Province;
import com.example.a14161.cooweather.util.HttpUtil;
import com.example.a14161.cooweather.util.Unity;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 14161 on 2017/4/26.
 */
public class ChooseAreaActivity extends AppCompatActivity {

        public static final int LEVEL_PROVINCE=0;
        public static final int LEVEL_CITY=1;
        public static final int LEVEL_COUNTY=2;

        private int currentLevel;

        private ProgressDialog progressDialog;
        private TextView titleText;
        private Button backButton;
        private ListView listView;
        private ArrayAdapter<String> adapter;
        private List<String> dataList=new ArrayList<>();
        private List<Province>provinceList=new ArrayList<>();
        private List<City>citiesList=new ArrayList<>();
        private List<County>countyList=new ArrayList<>();
        private Province selectedProvince;
        private City selectedCity;
        //   private County selectedCounty;
       //String TAG = "ChooseAreaActivity";
        //Boolean isFromWeatherActivity;
    @Override
        //创建视图
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
           // isFromWeatherActivity=getIntent().getBooleanExtra("from_weather_activity",false);
            //Log.d(TAG,"isFromWeatherActivity"+isFromWeatherActivity);
            //Log.d("Test","ok1");
            setContentView(R.layout.choose_area);
            //Log.d("Test","ok2");
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
            titleText=(TextView)findViewById(R.id.title_view);
            backButton=(Button)findViewById(R.id.back_button);
            listView=(ListView)findViewById(R.id.list_view);

            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position,long id){
                if(currentLevel==LEVEL_PROVINCE){
                    selectedProvince=provinceList.get(position);
                    querryCities();
                }else if(currentLevel==LEVEL_CITY) {
                    selectedCity = citiesList.get(position);
                    querryCounties();
                    }else if(currentLevel==LEVEL_COUNTY){
                    Log.d("test1","HERE");
                    String weatherId=countyList.get(position).getWeatherId();
                    Log.d("test1",weatherId);
                    Intent intent=new Intent(ChooseAreaActivity.this,WeatherActivity.class);
                    intent.putExtra("weather_id",weatherId);
                    startActivity(intent);
                    Log.d("test1","????");
                    ChooseAreaActivity.this.finish();
                }

                }
            });
                backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if(currentLevel==LEVEL_COUNTY){
                    querryCities();
                }else if(currentLevel==LEVEL_CITY){
                    querryProvinces();
                }

            }
        });
        querryProvinces();
    }

        //查询全国所有省，优先从数据库查询，如果没有查询到再去服务器查询
        public void querryProvinces(){

            titleText.setText("China");

            backButton.setVisibility(View.GONE);
            provinceList= DataSupport.findAll(Province.class);
            if(provinceList.size()>0){
                dataList.clear();
                for(Province province:provinceList){
                    dataList.add(province.getProvinceName());
                }
                adapter.notifyDataSetChanged();
                listView.setSelection(0);
                currentLevel=LEVEL_PROVINCE;
            }else{
                String address="http://guolin.tech/api/china";
                querryFromServer(address,"province");
            }
        }
        //查询选中省份的所有城市，优先从数据库查询，如果没有再去服务器查询
        public void querryCities(){
            titleText.setText(selectedProvince.getProvinceName());
            backButton.setVisibility(View.VISIBLE);
            citiesList=DataSupport.where("provinceid=?",String.valueOf(selectedProvince.getID())).find(City.class);
            if(citiesList.size()>0){
                dataList.clear();
                for(City city:citiesList){
                    dataList.add(city.getCityName());
                }
                adapter.notifyDataSetChanged();
                listView.setSelection(0);
                currentLevel=LEVEL_CITY;
            }else{
                String address="http://guolin.tech/api/china";
                int provinceCode=selectedProvince.getProvinceCode();
                String addressC=address+"/"+provinceCode;
                querryFromServer(addressC,"city");
            }
        }
        //查询选中的县，优先从数据库查询，如果没有则从服务器上查询
        public void querryCounties(){
          //  Log.d("Test","查询县");
            titleText.setText(selectedCity.getCityName());
            backButton.setVisibility(View.VISIBLE);
            countyList=DataSupport.where("cityid=?",String.valueOf(selectedCity.getID())).find(County.class);
            if(countyList.size()>0){
                dataList.clear();
                for (County county:countyList){
                    dataList.add(county.getCountyName());
                }
                adapter.notifyDataSetChanged();
                listView.setSelection(0);
                currentLevel=LEVEL_COUNTY;
            }else{
                String address="http://guolin.tech/api/china";
                int provinceCode=selectedProvince.getProvinceCode();
                int cityCode=selectedCity.getCityCode();
                String addressCC=address+"/"+provinceCode+"/"+cityCode;
                querryFromServer(addressCC,"county");
                //Log.d("Test","查询县2");
            }
        }
        public void querryFromServer(String url,final String type){
            showProgressDialog();
            HttpUtil.sendOkHttpRequest(url, new Callback() {
                @Override
                public void onResponse(Call call, Response response)throws IOException {
                    String responseText=response.body().string();
                    boolean result=false;
                    if("province".equals(type)){
                        result= Unity.handleProvinceResponse(responseText);

                    }else if("city".equals(type)){
                        result=Unity.handleCityResponse(responseText,selectedProvince.getID());
                    }
                    else if("county".equals(type)){
                       // Log.d("Test","查询县3");
                        result=Unity.handleCountyResponse(responseText,selectedCity.getID());
                    }
                    if(result){
                        runOnUiThread(new Runnable(){
                            @Override
                            public void run(){
                                closeProgressDialog();
                                if("province".equals(type)){
                                    querryProvinces();
                                }else if("city".equals(type)){
                                    querryCities();
                                }else if("county".equals(type)){
                                   // Log.d("Test","查询县4");
                                    querryCounties();
                                }
                            }
                        });
                    }
                }
                @Override
                public void onFailure(Call call,IOException e){
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            closeProgressDialog();
                            Toast.makeText(ChooseAreaActivity.this,"加载失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            });
        }
        public void showProgressDialog(){
            if(progressDialog==null){
                progressDialog=new ProgressDialog(ChooseAreaActivity.this);
                progressDialog.setMessage("正在加载..");
                progressDialog.setCanceledOnTouchOutside(false);
            }
            progressDialog.show();
        }
        public void closeProgressDialog(){
            if(progressDialog!=null){
                progressDialog.dismiss();
            }
        }
}




