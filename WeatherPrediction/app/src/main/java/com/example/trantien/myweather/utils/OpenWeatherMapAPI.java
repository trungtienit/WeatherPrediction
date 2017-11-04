package com.example.trantien.myweather.utils;

import com.example.trantien.myweather.model.OpenWeatherJSon;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by trantien on 11/4/17.
 */

public class OpenWeatherMapAPI {

    /**
     * api.openweathermap.org/data/2.5/weather?place=London
     * @param place
     * @return
     */
    public static OpenWeatherJSon prediction(String place)
    {
        try {
            String location= URLEncoder.encode(place, "UTF-8");

            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?place="+location);
            InputStreamReader reader = new InputStreamReader(url.openStream(),"UTF-8");
            OpenWeatherJSon results = new Gson().fromJson(reader, OpenWeatherJSon.class);

      //      String idIcon = results.getWeather().get(0).getIcon().toString();
        //    String urlIcon = "http://openweathermap.org/img/w/"+idIcon+".png";
           // URL urlImage = new URL(urlIcon);
            return results;

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * http://api.openweathermap.org/data/2.5/weather?lat=10.778182&lon=106.665504
     * @param lat
     * @param lon
     * @return
     */
    public static OpenWeatherJSon prediction(double lat,double lon)
    {
        try {

            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon);
            InputStreamReader reader = new InputStreamReader(url.openStream(),"UTF-8");
            OpenWeatherJSon results = new Gson().fromJson(reader, OpenWeatherJSon.class);

            String idIcon = results.getWeather().get(0).getIcon().toString();
            String urlIcon = "http://openweathermap.org/img/w/"+idIcon+".png";
            URL urlImage = new URL(urlIcon);

            return results;

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
        return null;
    }

    /**
     * Sửa lại WeatherJSON vì chưa phù hợp trong trường hợp Daily
     * http://api.openweathermap.org/data/2.5/forecast/daily?lat=10.778182&lon=106.66550&cnt=10
     * @param lat
     * @param lon
     * @param cnt
     * @return
     */
    public static OpenWeatherJSon predictionDaily(double lat,double lon,int cnt)
    {
        try {

            URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?lat="+lat+"&lon="+lon+"&cnt="+cnt);
            InputStreamReader reader = new InputStreamReader(url.openStream(),"UTF-8");
            OpenWeatherJSon results = new Gson().fromJson(reader, OpenWeatherJSon.class);

            String idIcon = results.getWeather().get(0).getIcon().toString();
            String urlIcon = "http://openweathermap.org/img/w/"+idIcon+".png";
            URL urlImage = new URL(urlIcon);

            return results;

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
        return null;
    }

    /**
     * http://api.openweathermap.org/data/2.5/forecast/daily?q=Đà lạt&cnt=10
     * @param place
     * @param cnt
     * @return
     */
//    public static OpenWeatherJSon predictionDaily(String place,int cnt)
//    {
//        try {
//            String location= URLEncoder.encode(place, "UTF-8");
//            URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q="+location+"&cnt="+cnt);
//            InputStreamReader reader = new InputStreamReader(url.openStream(),"UTF-8");
//            OpenWeatherJSon results = new Gson().fromJson(reader, OpenWeatherJSon.class);
//
//            String idIcon = results.getWeather().get(0).getIcon().toString();
//            String urlIcon = "http://openweathermap.org/img/w/"+idIcon+".png";
//            URL urlImage = new URL(urlIcon);
//
//            return results;
//
//        } catch (MalformedURLException e) {
//            // TODO Auto-generated catch block
//            //e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            //e.printStackTrace();
//        }
//        return null;
//    }
}
