package com.example.trantien.myweather.weather.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.trantien.myweather.R;
import com.example.trantien.myweather.weather.utils.WeatherAsyncTask;

/**
 * Created by trantien on 11/7/17.
 */

public class WeatherByAddressActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_detail_weather);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWeatherByAddress();
    }

    private void getWeatherByAddress() {
        Intent i=getIntent();
        //lấy địa chỉ từ bên ChooseAddressActivity gửi qua:
        String q=i.getStringExtra("ADDRESS");
        WeatherAsyncTask task=new WeatherAsyncTask(this,q);
        task.execute();
    }
}
