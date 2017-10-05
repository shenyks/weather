package com.sks.demo.weather.util;

import com.google.gson.Gson;

import com.sks.demo.weather.bo.WeatherInfo;

/**
 * A simple helper to parse JSON string to Weather Info object
 * Created by yingkui on 10/4/2017.
 */

public class JSONHelper
{
    /**
     * parse JSON string into WeatherInfo object
     * @param responseText a string in JSON format representing Weather info
     * @return a WeatherInfo object or null if any parsing error
     */
    public static WeatherInfo parseJSON(String responseText)
    {
        try {
            return new Gson().fromJson(responseText, WeatherInfo.class);
        } catch (Exception e) {
            //TODO: exception handling and pass a friendly error to user
            //  for Time being, we just pring the exception here
            e.printStackTrace();
        }
        return null;
    }
}
