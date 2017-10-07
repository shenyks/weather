package com.sks.demo.weather.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Date;

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

    //the key we used to set/get last queried city
    private static final String SETTING_KEY_LAST_CITY = "SETTING_KEY_LAST_CITY";

    //the key we used to set/get last query time
    private static final String SETTING_KEY_LAST_QUERY_DATE = "SETTING_KEY_LAST_QUERY_DATE";

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

    /**
     * Get last successful retrieved date
     * @return date when we got good weather info last time
     */
    public Date getLastSuccessDate()
    {
        //just to be safe
        if ( preferences == null )
        {
            return null;
        }

        //we save the millisec instead of Date object
        if ( preferences.contains( SETTING_KEY_LAST_QUERY_DATE ) )
        {
            Long timeMilliSec = preferences.getLong(SETTING_KEY_LAST_QUERY_DATE, 0);
            if ( timeMilliSec == 0 )
            {
                return null;
            }
            return new Date(timeMilliSec);
        }

        return null;
    }

    /**
     * Save the date/time when we got good weather info
     * @param lastSuccessDate Date/time when we got good weather info
     */
    public void setLastSuccessDate(Date lastSuccessDate)
    {
        //just to be safe
        if (preferences == null || lastSuccessDate == null )
        {
            return;
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(SETTING_KEY_LAST_QUERY_DATE, lastSuccessDate.getTime());
        editor.commit();
    }

}
