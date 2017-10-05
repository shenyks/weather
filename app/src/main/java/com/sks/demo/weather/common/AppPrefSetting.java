package com.sks.demo.weather.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * This class will be responsible for all setting related data operation.
 * In this app: it will save last city name that has valid weather info retrieved
 * TODO: for complex app, we may want to turn this into a singleton or attach it to a singleton obj
 * Created by yingkui on 10/4/2017.
 */

public class AppPrefSetting
{
    //app Setting Handler
    private static SharedPreferences preferences;

    //the key we used to set/get values to/from user preferences
    private static final String SETTING_KEY_LAST_CITY = "SETTING_KEY_LAST_CITY";

    /**
     * Initialize it with an Activity in order to access user preferences
     * @param context the activity it associated to
     */
    public void init(Context context)
    {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Get last successful retrieved city id
     * @return city id which we got good weather info last time
     */
    public String getLastSuccessCity()
    {
        //just to be safe
        //TODO: we should throw Exception if null value found
        if ( preferences == null )
        {
            return null;
        }

        if ( preferences.contains( SETTING_KEY_LAST_CITY ) )
        {
            return preferences.getString(SETTING_KEY_LAST_CITY, null);
        }

        return null;
    }

    /**
     * Save city id so next time we can retrieve weather info for the same city
     * @param lastSuccessCity city id associated with the weather info
     */
    public void setLastSuccessCity(String lastSuccessCity)
    {
        //just to be safe
        //TODO: we should throw Exception if null value found
        if (preferences == null || lastSuccessCity == null )
        {
            return;
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SETTING_KEY_LAST_CITY, lastSuccessCity);
        editor.commit();
    }

}
