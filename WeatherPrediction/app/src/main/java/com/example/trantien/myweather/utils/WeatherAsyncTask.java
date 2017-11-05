package com.example.trantien.myweather.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trantien.myweather.MyInfoWindowAdapter;
import com.example.trantien.myweather.R;
import com.example.trantien.myweather.TypePrediction;
import com.example.trantien.myweather.model.OpenWeatherJSon;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by trantien on 11/4/17.
 */

public class WeatherAsyncTask extends AsyncTask<Void, Void, OpenWeatherJSon> {
    ProgressDialog dialog;
    Activity activity;
    TypePrediction typePrediction;
    String place;
    double latitude;
    double longitude;
    NumberFormat format = new DecimalFormat("#0.0");
    Bitmap myBitmap = null;

    Marker marker = null;
    GoogleMap map = null;

    /**
     * Constructor dùng để lấy thời tiết theo địa chỉ bất kỳ
     *
     * @param activity
     * @param place
     */
    public WeatherAsyncTask(Activity activity, String place) {
        this.activity = activity;
        this.typePrediction = TypePrediction.ADDRESS_NAME;
        this.place = place;
        this.dialog = new ProgressDialog(activity);
        this.dialog.setTitle("Đang tải thông tin ...");
        this.dialog.setMessage("Vui lòng chờ...");
        this.dialog.setCancelable(true);
    }

    /**
     * constructor cho phép lấy thông tin thời tiết theo tọa độ bất kỳ
     *
     * @param activity
     * @param latitude
     * @param longitude
     */
    public WeatherAsyncTask(Activity activity, double latitude, double longitude) {
        this.activity = activity;
        this.typePrediction = TypePrediction.LATITUDE_LONGITUDE;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dialog = new ProgressDialog(activity);
        this.dialog.setTitle("Đang tải thông tin ...");
        this.dialog.setMessage("Vui lòng chờ...");
        this.dialog.setCancelable(true);
    }

    /**
     * constructor cho lấy thông tin thời tiết theo tọa độ bất kỳ trên bản đồ
     *
     * @param marker
     * @param map
     * @param activity
     * @param latitude
     * @param longitude
     */
    public WeatherAsyncTask(Marker marker, GoogleMap map, Activity activity, double latitude, double longitude) {
        this(activity, latitude, longitude);
        this.marker = marker;
        this.map = map;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.dialog.show();
    }

    @Override
    protected OpenWeatherJSon doInBackground(Void... params) {
        OpenWeatherJSon openWeatherJSon = null;
        if (typePrediction == TypePrediction.LATITUDE_LONGITUDE)
            openWeatherJSon = OpenWeatherMapAPI.prediction(latitude, longitude);
        else
            openWeatherJSon = OpenWeatherMapAPI.prediction(place);
        try {
            String idIcon = openWeatherJSon.getWeather().get(0).getIcon().toString();
            String urlIcon = "http://openweathermap.org/img/w/" + idIcon + ".png";
            //Tiến hành tạo đối tượng URL
            URL urlConnection = new URL(urlIcon);
            //Mở kết nối
            HttpURLConnection connection = (HttpURLConnection) urlConnection
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            //Đọc dữ liệu
            InputStream input = connection.getInputStream();
            //Tiến hành convert qua hình ảnh
            myBitmap = BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return openWeatherJSon;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(OpenWeatherJSon openWeatherJSon) {
        super.onPostExecute(openWeatherJSon);
        if (map != null) {
            map.setInfoWindowAdapter(new MyInfoWindowAdapter(openWeatherJSon, myBitmap, marker, this.activity, latitude, longitude));
            marker.showInfoWindow();
            this.dialog.dismiss();
            return;
        }
        if(openWeatherJSon==null) {
            return;
        }
        TextView txtTemperature = (TextView) activity.findViewById(R.id.txtTemperature);
        TextView txtCurrentAddressName = (TextView) activity.findViewById(R.id.txtCurrentAddressName);
        ImageView imageView = (ImageView) activity.findViewById(R.id.imgBauTroi);
        TextView txtMaxtemp = (TextView) activity.findViewById(R.id.txtMaxTemp);
        TextView txtMinTemp = (TextView) activity.findViewById(R.id.txtMinTemp);
        TextView txtWind = (TextView) activity.findViewById(R.id.txtWind);
        TextView txtCloudliness = (TextView) activity.findViewById(R.id.txtCloudliness);
        TextView txtPressure = (TextView) activity.findViewById(R.id.txtPressure);
        TextView txtHumidty = (TextView) activity.findViewById(R.id.txtHumidty);
        TextView txtSunrise = (TextView) activity.findViewById(R.id.txtSunrise);
        TextView txtSunset = (TextView) activity.findViewById(R.id.txtSunset);
        double temperature = openWeatherJSon.getMain().getTemp() - 273.15;
        String maxtemp = format.format(openWeatherJSon.getMain().getTemp_max() - 273.15) + "°C";
        String mintemp = format.format(openWeatherJSon.getMain().getTemp_min() - 273.15) + "°C";
        String wind = openWeatherJSon.getWind().getSpeed() + " m/s";
        String mesg = openWeatherJSon.getWeather().get(0).getMain();
        //  Translator translate = Translator.getInstance();
        // String cloudiness=mesg+" ("+translate.translate(mesg, Language.ENGLISH, Language.VIETNAMESE)+")";
        String cloudiness = mesg;
        String pressure = openWeatherJSon.getMain().getPressure() + " hpa";
        String humidity = openWeatherJSon.getMain().getHumidity() + " %";

        Date timeSunrise = new Date(openWeatherJSon.getSys().getSunrise() * 1000);
        String Sunrise = timeSunrise.getHours() + ":" + timeSunrise.getMinutes() + " AM";
        Date timeSunSet = new Date(openWeatherJSon.getSys().getSunset() * 1000);
        String sunset = timeSunSet.getHours() + ":" + timeSunSet.getMinutes();
        txtTemperature.setText(format.format(temperature) + "°C");
        imageView.setImageBitmap(myBitmap);
        txtMaxtemp.setText(maxtemp);
        txtMinTemp.setText(mintemp);
        txtWind.setText(wind);
        txtCloudliness.setText(cloudiness);
        txtPressure.setText(pressure);
        txtHumidty.setText(humidity);
        txtSunrise.setText(Sunrise);
        txtSunset.setText(sunset);

        try {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this.activity, Locale.getDefault());
            if (typePrediction == TypePrediction.LATITUDE_LONGITUDE)
                addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            else {
                addresses = geocoder.getFromLocationName(place, 1);
            }
            Address address = null;
            if (addresses.size() > 0)
                address = addresses.get(0);
            if (address != null) {
                if (typePrediction == TypePrediction.LATITUDE_LONGITUDE)
                    txtCurrentAddressName.setText(address.getAddressLine(0));
                else
                    txtCurrentAddressName.setText(place);
                /*String city = address.getLocality();
                String state = address.getAdminArea();
                String country = address.getCountryName();
                String postalCode = address.getPostalCode();
                String knownName = address.getFeatureName();*/
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.dialog.dismiss();
    }
}