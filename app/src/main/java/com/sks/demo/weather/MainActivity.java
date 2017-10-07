package com.sks.demo.weather;

import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import android.os.Bundle;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import com.sks.demo.weather.bo.WeatherInfo;
import com.sks.demo.weather.business.WeatherRetriever;
import com.sks.demo.weather.common.AppConstants;
import com.sks.demo.weather.common.AppPrefSetting;
import com.sks.demo.weather.util.JSONHelper;
import com.sks.demo.weather.util.WeatherUtil;

/**
 * This is the launching main activity.
 * TODO: Given more time, I would prefer move the heavy async coding to another class
 *   so the Activity would look cleaner.
 *  For example: we could create a separate AsyncTask and pass this Activity as a parameter
 *
 * Created by yingkui on 10/4/2017.
 */
public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";

    AppPrefSetting appSetting;

    ProgressBar loadingProgressBar;
    TextView messageTextView;
    View weatherDisplayView;

    TextView displayCityTextView;
    TextView currentTempTextView;
    TextView pressureTextView;
    TextView humidityTextView;
    TextView windTextView;

    ImageView weatherIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //show Icon
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        //hide title
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //init the setting handler
        appSetting = new AppPrefSetting();
        appSetting.init(this);

        //get UI elements ready
        loadingProgressBar = (ProgressBar)findViewById(R.id.progress_bar);
        messageTextView = (TextView)findViewById(R.id.display_message);
        weatherDisplayView = findViewById(R.id.weather_display);

        displayCityTextView = (TextView)findViewById(R.id.display_cityname);
        currentTempTextView = (TextView)findViewById(R.id.weather_temp);
        pressureTextView = (TextView)findViewById(R.id.weather_pressure);
        humidityTextView = (TextView)findViewById(R.id.weather_humidity);
        windTextView = (TextView)findViewById(R.id.weather_wind);
        weatherIcon = (ImageView)findViewById(R.id.weather_icon);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        //find weather for last city (if any)
        checkLastCity();
    }

    /**
     * This method here is mainly used to setup the search View on title bar
     * @param menu where to put the search view
     * @return true for the menu to be displayed; false to be hidden
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        final SearchView searchViewAndroidActionBar = (SearchView) searchViewItem.getActionView();

        //For time being, we only support query after user tap "search"/"submit"
        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                //hide keyboard
                searchViewAndroidActionBar.clearFocus();
                //retrieve weather for input city
                MainActivity.this.retrieveWeatherByCity(query, null);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText)
            {
                //TODO: it will be nice if wecan can display matching search history when typing
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    /**
     * Check to see if we have successful retrieval history, if any, retrieve it again
     */
    private void checkLastCity()
    {
        String lastCityId = appSetting.getLastSuccessCity();
        if ( lastCityId != null )
        {
            retrieveWeatherByCity(null, lastCityId);
        }
    }

    /**
     * This helper method will retrieve Weather info based on city name
     * @param cityName city Name (it may cause ambiguous result)
     * @param cityId city id (we will use it to retrieve more accurate data)
     */
    private void retrieveWeatherByCity(String cityName, String cityId)
    {
        //display progress bar to user
        loadingProgressBar.setVisibility(View.VISIBLE);

        //retrieve weather info from server (in an asynchronous process)
        WeatherRetriever.retrieveWeatherByCity(cityName, cityId, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                //make sure we got 200 status
                final int respStatusCode = response.code();
                if ( respStatusCode != 200 )
                {
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            {
                                MainActivity.this.displayErrorInfo(
                                        "Error retrieving data (" + respStatusCode + ")" );
                            }
                        }
                    });
                    return;
                }
                //parse retrieved JSON data
                final String responseText = response.body().string();
                final WeatherInfo weather = JSONHelper.parseJSON(responseText);
                //display the weather info on UI Thread
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        {
                            MainActivity.this.displayWeatherInfo(weather);
                        }
                    }
                });
            }
            @Override
            public void onFailure(Call call, IOException e)
            {
                final String localizedMsg = e.getLocalizedMessage();
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        {
                            MainActivity.this.displayErrorInfo(localizedMsg);
                        }
                    }
                });
            }
        });
    }

    /**
     * Verify returned Weather information and display required information to user
     * @param weatherInfo weather information retrieved from server
     */
    private void displayWeatherInfo(WeatherInfo weatherInfo)
    {
        //hide progress bar as we don't need it any more
        loadingProgressBar.setVisibility(View.GONE);

        //always check null
        if ( weatherInfo == null )
        {
            //no data to display
            displayErrorInfo("No data available");
            return;
        }

        //Cache the city name to be used as a default city next time when we launch the app
        //just to make sure we have the city as returned city name
        String cityName = weatherInfo.name;

        //hide info/error section
        messageTextView.setVisibility(View.GONE);

        //show weather section
        weatherDisplayView.setVisibility(View.VISIBLE);

        //Are we limiting the search to US city only? (Requirement doesn't mention that)
        //  We didn't add any limitation because OpenWeatherMap can handle any search query
        //  Just to make it clear for user, we will add "Country" code to the display
        if ( weatherInfo.sys != null )
        {
            String countryCode = weatherInfo.sys.country;
            if ( countryCode != null && countryCode.length() > 1 )
            {
                cityName += ", " + countryCode;
            }
        }

        //cache the city id to be used next time upon app launch
        String cityId = weatherInfo.id;
        if ( cityId != null && cityId.length() > 0 )
        {
            appSetting.setLastSuccessCity(cityId);
        }

        //display City (,country) if available
        displayCityTextView.setText("Current City: " + cityName);
        if ( weatherInfo.mainData != null )
        {
            //temperature
            double degKelvin = weatherInfo.mainData.temp;
            currentTempTextView.setText(WeatherUtil.getNiceTempK2F(degKelvin));
            //Pressure
            double pressure = weatherInfo.mainData.pressure;
            pressureTextView.setText(WeatherUtil.getNicePressure(pressure));
            //Huminity
            double humidity = weatherInfo.mainData.humidity;
            humidityTextView.setText(WeatherUtil.getNiceHumidity(humidity));
        }
        else
        {
            //Just in case we don't get the data, display "n/a" to user
            currentTempTextView.setText(AppConstants.WEATHER_NA);
            pressureTextView.setText(AppConstants.WEATHER_NA);
            humidityTextView.setText(AppConstants.WEATHER_NA);
        }
        if ( weatherInfo.wind != null )
        {
            double windSpeed = weatherInfo.wind.speed;
            windTextView.setText(WeatherUtil.getNiceSpeed(windSpeed));
        }
        else
        {
            //just in case we don't get the data, display "n/a" to user
            windTextView.setText(AppConstants.WEATHER_NA);
        }

        //clear weather Icon first so the old icon won't confuse user
        weatherIcon.setImageBitmap(null);

        //For multiple Weather retrieved in single response, we can use a ViewPager
        //  and user can swipe left/right to view all of them
        //For now, we will only display the first Weather element in the response
        if ( weatherInfo.weather != null && weatherInfo.weather.size() > 0 )
        {
            //try to get the icon
            WeatherInfo.Weather firstWeather = weatherInfo.weather.get(0);
            String weatherIcon = firstWeather.icon;
            if ( weatherIcon != null && weatherIcon.length() > 0 )
            {
                //TBD: if the weather icon from OpenWeatherMap doesn't change so often,
                //  we should cache the downloaded png files and re-use them later
                //  instead of downloading it again every time
                downloadWeatherIcon(weatherIcon);

            }
        }

    }

    /**
     * Display any error info occurred during the weather info retrieve procuess
     * @param errorInfo A string to be displayed to user
     */
    private void displayErrorInfo(String errorInfo)
    {
        //hide progress bar as we don't need it any more
        loadingProgressBar.setVisibility(View.GONE);

        //hide the detailed weather info section and display the error/info section
        weatherDisplayView.setVisibility(View.GONE);

        //show the info/error section
        messageTextView.setVisibility(View.VISIBLE);

        //show the error information
        messageTextView.setText(errorInfo);
    }

    /**
     * This helper method will download the icon file associated with the weather info and
     * display it on UI.
     * Please note: Since we already have all other Weather info retrieved and displayed,
     * if there is an error durng icon downloading, we don't display error again.
     * @param iconName the icon file's name (without .png suffix)
     */
    private void downloadWeatherIcon(String iconName)
    {
        //retrieve weather icon from server (in an asynchronous process)
        WeatherRetriever.retrieveWeatherIcon(iconName, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                //make sure we got 200 status
                final int respStatusCode = response.code();
                if ( respStatusCode != 200 )
                {
                    //log the error and return
                    Log.e(TAG, "onResponse: Downloading Weather Icon Failed. status code: " + respStatusCode);
                    return;
                }
                //get the bitmap then
                //display the icon on UI Thread
                final Bitmap iconBitmap = BitmapFactory.decodeStream(response.body().byteStream());
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        {
                            MainActivity.this.displayWeatherIcon(iconBitmap);
                        }
                    }
                });
            }
            @Override
            public void onFailure(Call call, IOException e)
            {
                //don't show error to user, just log it
                Log.e(TAG, "onFailure: Retrieve weather icon failed", e);
            }
        });
    }

    /**
     * Display Weather Icon retrieved from server
     * @param iconBitmap image data retrieved from server
     */
    private void displayWeatherIcon(Bitmap iconBitmap)
    {
        weatherIcon.setImageBitmap(iconBitmap);
    }
}
