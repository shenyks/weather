package com.sks.demo.weather.common;

/**
 * All App related constants should go here.
 * Created by yingkui on 10/4/2017.
 */

public class AppConstants
{
    //TODO: sensitive information like app_id should be put in seperal (more secure) places
    // and included programatically
    private static final String WEATHER_APP_ID = "f70f7cc28805bff6b6c82985028e147d";
    //Open Weather Map's public API URL
    //i.e. http://api.openweathermap.org/data/2.5/weather?q=dallas&APPID=f70f7cc28805bff6b6c82985028e147d
    //by city name (may get ambiguous result)
    public static final String WEATHER_API_URL_BY_CITY_NAME_WITH_APPID = "http://api.openweathermap.org/data/2.5/weather?q=[1]&APPID=" + WEATHER_APP_ID;
    //by city id (more accurate)
    public static final String WEATHER_API_URL_BY_CITY_ID_WITH_APPID = "http://api.openweathermap.org/data/2.5/weather?id=[1]&APPID=" + WEATHER_APP_ID;

    //Weather Icon's public URL
    public static final String WEATHER_ICON_URL = "http://openweathermap.org/img/w/[1].png";

    //default text to display weather
    public static final String WEATHER_NA = "N/A";

}
