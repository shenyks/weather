package com.sks.demo.weather.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
/**
 * Put useful, common app-level helper methods here,
 * or put it in a seperate module/library to be shared with other app.
 * Created by yingkui on 10/4/2017.
 */

public class AppUtil
{
    /**
     * Check Network status of the status. For example: return false when in Airplane mode.
     * @param context Activity from where this method is called
     * @return true: if device is connected to network; false if device is disconnected
     */
    public static boolean isNetworkConnected(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork == null)
        {
            return false;
        }

        return true;
    }
}
