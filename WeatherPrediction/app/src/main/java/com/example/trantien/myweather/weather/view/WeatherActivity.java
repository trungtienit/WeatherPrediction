package com.example.trantien.myweather.weather.view;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.trantien.myweather.weather.CheckPermission;
import com.example.trantien.myweather.R;
import com.example.trantien.myweather.weather.WeatherMapsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.trantien.myweather.Common.toast;

public class WeatherActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.weather_by_address)
    View weatherByAddress;

    @BindView(R.id.weather_by_map)
    View weatherByMap;

    @BindView(R.id.current_weather)
    View currentWeather;

    @BindView(R.id.daily_forecast)
    View dailyForecast;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        CheckPermission.checkAndRequestPermissions(this);
        init();
    }

    private void init() {
        weatherByAddress.setOnClickListener(this);
        weatherByMap.setOnClickListener(this);
        currentWeather.setOnClickListener(this);
        dailyForecast.setOnClickListener(this);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            return false;
        } else
            return true;
    }

    @Override
    public void onClick(View v) {
        if (!isNetworkConnected()) {
            toast(this, "Network connection error!");
            return;
        }
        switch (v.getId()) {

            case R.id.current_weather:
                showCurrentWeather();
                break;
            case R.id.weather_by_address:
                showWeatherByAddress();
                break;
            case R.id.weather_by_map:
                showMap();
                break;
            case R.id.daily_forecast:
                toast(this, "Comming soon!");
                break;
        }
    }

    private void showMap() {
        intent = new Intent(WeatherActivity.this, WeatherMapsActivity.class);
        startActivity(intent);
    }

    private void showWeatherByAddress() {
        intent = new Intent(WeatherActivity.this, ChooseAddressActivity.class);
        startActivity(intent);
    }

    private void showCurrentWeather() {
        intent = new Intent(WeatherActivity.this, WeatherCurrentLocationActivity.class);
        startActivity(intent);
    }
}
