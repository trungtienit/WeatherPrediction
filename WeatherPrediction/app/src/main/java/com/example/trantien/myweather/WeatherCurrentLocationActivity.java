package com.example.trantien.myweather;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.trantien.myweather.utils.WeatherAsyncTask;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by trantien on 11/5/17.
 */

public class WeatherCurrentLocationActivity extends AppCompatActivity implements LocationListener {
    NumberFormat format = new DecimalFormat("#0.0");
    LocationManager locationManager;
    private boolean canGetLocation;
    private Location location;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_detail_weather);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateDataLocation();
    }

    private void updateDataLocation() {
        if (getLocation() != null) {
            //ta lấy được thì truyền vĩ độ, kinh độ để xem thời tiết
            WeatherAsyncTask task = new WeatherAsyncTask(this, location.getLatitude(), location.getLongitude());
            task.execute();
        }

    }

    public Location getLocation() {
        int MIN_TIME_BW_UPDATES = 10000;
        int MIN_DISTANCE_CHANGE_FOR_UPDATES = 10000;
        try {
            locationManager = (LocationManager) getApplicationContext()
                    .getSystemService(LOCATION_SERVICE);

            boolean isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            boolean isPassiveEnabled = locationManager
                    .isProviderEnabled(LocationManager.PASSIVE_PROVIDER);

            boolean isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (isGPSEnabled || isNetworkEnabled || isPassiveEnabled) {

                this.canGetLocation = true;
                if (isNetworkEnabled && location == null) {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    }
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network Enabled");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled && location == null) {

                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("GPS", "GPS Enabled");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }
                }
                if (isPassiveEnabled && location == null) {
                    locationManager.requestLocationUpdates(
                            LocationManager.PASSIVE_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network Enabled");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                    }
                }


            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
