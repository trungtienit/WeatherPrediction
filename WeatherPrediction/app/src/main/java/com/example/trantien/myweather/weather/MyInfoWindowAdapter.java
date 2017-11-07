package com.example.trantien.myweather.weather;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trantien.myweather.R;
import com.example.trantien.myweather.weather.model.OpenWeatherJSon;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by drthanh on 12/05/2015.
 */
public class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Activity context;
    Marker maker=null;
    OpenWeatherJSon openWeatherJSon=null;
    Bitmap myBitmap=null;
    NumberFormat format = new DecimalFormat("#0.0");
    double latitude;
    double longitude;
    public MyInfoWindowAdapter(Activity context)
    {
        this.context=context;
    }
    public MyInfoWindowAdapter(OpenWeatherJSon openWeatherJSon, Bitmap myBitmap, Marker maker, Activity context)
    {
        this(context);
        this.maker=maker;
        this.openWeatherJSon=openWeatherJSon;
        this.myBitmap=myBitmap;
    }
    public MyInfoWindowAdapter(OpenWeatherJSon openWeatherJSon, Bitmap myBitmap, Marker maker, Activity context, double latitude, double longitude)
    {
        this(openWeatherJSon,myBitmap,maker,context);
        this.latitude=latitude;
        this.longitude=longitude;
    }
    @Override
    public View getInfoWindow(Marker marker) {
        View v = this.context.getLayoutInflater().inflate(R.layout.view_detail_weather, null);

        TextView txtTemperature=(TextView) v.findViewById(R.id.txtTemperature);
        TextView txtCurrentAddressName=(TextView) v.findViewById(R.id.txtCurrentAddressName);
        ImageView imageView=(ImageView) v.findViewById(R.id.imgBauTroi);
        TextView txtMaxtemp=(TextView) v.findViewById(R.id.txtMaxTemp);
        TextView txtMinTemp=(TextView) v.findViewById(R.id.txtMinTemp);
        TextView txtWind=(TextView) v.findViewById(R.id.txtWind);
        TextView txtCloudliness= (TextView) v.findViewById(R.id.txtCloudliness);
        TextView txtPressure= (TextView) v.findViewById(R.id.txtPressure);
        TextView txtHumidty= (TextView) v.findViewById(R.id.txtHumidty);
        TextView txtSunrise= (TextView) v.findViewById(R.id.txtSunrise);
        TextView txtSunset= (TextView) v.findViewById(R.id.txtSunset);
        double temperature=openWeatherJSon.getMain().getTemp()-273.15;
        String maxtemp= format.format(openWeatherJSon.getMain().getTemp_max()-273.15)+"°C";
        String mintemp= format.format(openWeatherJSon.getMain().getTemp_min()-273.15)+"°C";
        String wind= openWeatherJSon.getWind().getSpeed()+" m/s";
        String mesg = openWeatherJSon.getWeather().get(0).getMain();
        //  Translator translate = Translator.getInstance();
        // String cloudiness=mesg+" ("+translate.translate(mesg, Language.ENGLISH, Language.VIETNAMESE)+")";
        String cloudiness=mesg;
        String pressure= openWeatherJSon.getMain().getPressure()+" hpa";
        String humidity=openWeatherJSon.getMain().getHumidity()+" %";

        Date timeSunrise = new Date(openWeatherJSon.getSys().getSunrise()*1000);
        String Sunrise= timeSunrise.getHours()+":"+timeSunrise.getMinutes()+" AM";
        Date timeSunSet = new Date(openWeatherJSon.getSys().getSunset()*1000);
        String sunset= timeSunSet.getHours()+":"+timeSunSet.getMinutes();
        txtTemperature.setText(format.format(temperature)+"°C");
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
            geocoder = new Geocoder(this.context, Locale.getDefault());
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            Address address=null;
            if(addresses.size()>0)
                address=addresses.get(0);
            if(address!=null)
            {
               txtCurrentAddressName.setText(address.getAddressLine(0));
                /*String city = address.getLocality();
                String state = address.getAdminArea();
                String country = address.getCountryName();
                String postalCode = address.getPostalCode();
                String knownName = address.getFeatureName();*/
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        v.setBackgroundColor (Color.WHITE);
        return v;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
