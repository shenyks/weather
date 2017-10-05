package com.sks.demo.weather.business;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import com.sks.demo.weather.common.AppConstants;

import java.util.concurrent.TimeUnit;

/**
 * This class will call HTTP services and retrieve Weather Information.
 * TODO: Based on business requirement, this class can also be defined as a singleton.
 *       (For time being, we just use static methods to get the job done)
 * Created by yingkui on 10/4/2017.
 */

public class WeatherRetriever
{
    /**
     * This method takes cityName and embed it into the HTTP Query URL,
     * sends Async call to Weather Service and invoke the Callback afterwards
     * @param cityName city name to get weather info (may cause ambiguous result)
     * @param cityId city id to get weather info (more accurate)
     * @param callback will be called after HTTP call finished
     */
    public static void retrieveWeatherByCity(String cityName, String cityId, okhttp3.Callback callback)
    {
        //generate the query URL based on cityName or cityId
        String fullURL;
        if ( cityId == null )
        {
            fullURL = AppConstants.WEATHER_API_URL_BY_CITY_NAME_WITH_APPID.replace("[1]", cityName);
        }
        else
        {
            fullURL = AppConstants.WEATHER_API_URL_BY_CITY_ID_WITH_APPID.replace("[1]", cityId);
        }

        //Build Http Client and set timeout (10 sec should be enough for a simple JSON call)
        //we can also put the timeout setting to app constants if reused by other components
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
        //build http Request
        Request request = new Request.Builder()
                .url(fullURL)
                .build();
        //Async Call !! (to avoid ANR error)
        client.newCall(request).enqueue(callback);
    }

    /**
     * This method will send Async call to Weather Map Server to retrieve icon picture data,
     * and invoke the Callback afterwards
     * @param iconName city to get weather info
     * @param callback will be called after HTTP call finished
     */
    public static void retrieveWeatherIcon(String iconName, okhttp3.Callback callback)
    {
        //generate the query URL based on cityName
        String fullURL = AppConstants.WEATHER_ICON_URL.replace("[1]", iconName);

        //Build Http Client and set timeout (10 sec should be enough for a simple JSON call)
        //we can also put the timeout setting to app constants if reused by other components
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
        //build http Request
        Request request = new Request.Builder()
                .url(fullURL)
                .build();
        //Async Call !! (to avoid ANR error)
        client.newCall(request).enqueue(callback);
    }
}
