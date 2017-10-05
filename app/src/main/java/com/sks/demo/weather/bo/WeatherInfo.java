package com.sks.demo.weather.bo;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Simple POJO to hold information of JSON from openWeatherMap's Service
 * Created by yingkui on 10/4/2017.
 */

public class WeatherInfo
{
    @SerializedName("coord")
    public Coord coord;

    public class Coord
    {
        @SerializedName("lon")
        public String lon;
        @SerializedName("lat")
        public String lat;

        @Override
        public String toString()
        {
            return "Coord{" +
                    "lon='" + lon + '\'' +
                    ", lat='" + lat + '\'' +
                    '}';
        }
    }

    @SerializedName("weather")
    public List<Weather> weather;

    public class Weather
    {
        @SerializedName("id")
        public String weatherId;
        @SerializedName("main")
        public String weatherMain;
        @SerializedName("description")
        public String description;
        @SerializedName("icon")
        public String icon;

        @Override
        public String toString()
        {
            return "Weather{" +
                    "weatherId='" + weatherId + '\'' +
                    ", weatherMain='" + weatherMain + '\'' +
                    ", description='" + description + '\'' +
                    ", icon='" + icon + '\'' +
                    '}';
        }
    }

    @SerializedName("base")
    public String base;

    @SerializedName("main")
    public MainData mainData;

    public class MainData
    {
        @SerializedName("temp")
        public double temp;
        @SerializedName("pressure")
        public double pressure;
        @SerializedName("humidity")
        public double humidity;
        @SerializedName("temp_min")
        public double tempMin;
        @SerializedName("temp_max")
        public double tempMax;
        @SerializedName("sea_level")
        public double seaLevel;
        @SerializedName("grnd_level")
        public double grnd_Level;

        @Override
        public String toString()
        {
            return "MainData{" +
                    "temp=" + temp +
                    ", pressure=" + pressure +
                    ", humidity=" + humidity +
                    ", tempMin=" + tempMin +
                    ", tempMax=" + tempMax +
                    ", seaLevel=" + seaLevel +
                    ", grnd_Level=" + grnd_Level +
                    '}';
        }
    }

    @SerializedName("wind")
    public Wind wind;

    public class Wind
    {
        @SerializedName("speed")
        public double speed;
        @SerializedName("deg")
        public double deg;

        @Override
        public String toString()
        {
            return "Wind{" +
                    "speed=" + speed +
                    ", deg=" + deg +
                    '}';
        }
    }

    @SerializedName("rain")
    public Rain rain;

    public class Rain
    {
        @SerializedName("3h")
        public double rain3H;

        @Override
        public String toString()
        {
            return "Rain{" +
                    "rain3H=" + rain3H +
                    '}';
        }
    }

    @SerializedName("clouds")
    public Clouds clouds;

    public class Clouds
    {
        @SerializedName("all")
        public double cloudsAll;

        @Override
        public String toString()
        {
            return "Clouds{" +
                    "cloudsAll=" + cloudsAll +
                    '}';
        }
    }

    @SerializedName("sys")
    public Sys sys;

    public class Sys
    {
        @SerializedName("message")
        public double message;
        @SerializedName("country")
        public String country;
        @SerializedName("sunrise")
        public long sunRise;
        @SerializedName("sunset")
        public long sunset;

        @Override
        public String toString()
        {
            return "Sys{" +
                    "message=" + message +
                    ", country='" + country + '\'' +
                    ", sunRise=" + sunRise +
                    ", sunset=" + sunset +
                    '}';
        }
    }

    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;
    @SerializedName("cod")
    public int cod;


    @Override
    public String toString()
    {
        return "WeatherInfo{" +
                "coord=" + coord +
                ", weather=" + weather +
                ", base='" + base + '\'' +
                ", mainData=" + mainData +
                ", wind=" + wind +
                ", rain=" + rain +
                ", clouds=" + clouds +
                ", sys=" + sys +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", cod=" + cod +
                '}';
    }
}
