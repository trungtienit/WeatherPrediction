package com.example.trantien.myweather.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.trantien.myweather.R;

/**
 * Created by trantien on 11/5/17.
 */

public class WeatherMapsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_maps);
        Toast.makeText(WeatherMapsActivity.this,"OK",Toast.LENGTH_SHORT).show();
    }

}
