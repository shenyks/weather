package com.sks.demo.weather.util;

import java.text.DecimalFormat;

/**
 * This class is to provide Weather related functionality,
 * For example: convert Kelvin to Fahrenheit.
 * Created by yingkui on 10/4/2017.
 */

public class WeatherUtil
{
    /**
     * convert double number to user-friendly format String
     * @param numberToShow a number to be formated
     * @return a formated String like "37.81"
     */
    public static String showFormattedDouble(double numberToShow)
    {
        return new DecimalFormat("##.##").format(numberToShow);
    }

    /**
     * Convert Kelvin to Fahrenheit for temprature
     * @param kelvinTemp temperature in Kelvin format
     * @return temperature in Fahrenheit format
     */
    public static double convertTempK2F(double kelvinTemp)
    {
        return kelvinTemp * 9.0 / 5.0 - 459.67;
    }

    /**
     * Convert wind speed from m/s to mph
     * @param speedMPS speed in m/s format
     * @return speed in mph format
     */
    public static double convertSpeedS2M(double speedMPS)
    {
        return (speedMPS * 60.0 * 60.0) / 1609.3;
    }

    /**
     * Convert temperature in Kelvin format to user-friendly display Fahrenheit format as string.
     * @param kelvinTemp temperature in Kelvin format
     * @return temperature in Fahrenheit format as a string
     */
    public static String getNiceTempK2F(double kelvinTemp)
    {
        double fTemp = WeatherUtil.convertTempK2F(kelvinTemp);
        return WeatherUtil.showFormattedDouble(fTemp) + " \u2109";
    }

    /**
     * Format Pressure and aattach "hPa" at the end of the string
     * @param pressure pressure as a double number
     * @return a string with pressure + hPa
     */
    public static String getNicePressure(double pressure)
    {
        return WeatherUtil.showFormattedDouble(pressure) + " hPa";
    }

    /**
     * Format Humidity aattach "%" at the end of the string
     * @param humidity humidity as a double number
     * @return a string with pressure + hPa
     */
    public static String getNiceHumidity(double humidity)
    {
        return WeatherUtil.showFormattedDouble(humidity) + " %";
    }

    /**
     * Format speed to mph format from m/s format
     * @param speedMPS speed in m/s format
     * @return a string with speed in MPH format
     */
    public static String getNiceSpeed(double speedMPS)
    {
        double speedMPH = WeatherUtil.convertSpeedS2M(speedMPS);
        return WeatherUtil.showFormattedDouble(speedMPH) + " MPH";
    }
}
