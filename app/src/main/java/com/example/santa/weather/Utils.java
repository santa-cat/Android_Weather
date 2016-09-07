package com.example.santa.weather;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by santa on 16/7/28.
 */
public class Utils {

    public static int getBackgroundColorByCode(Context context, String codeString) {
        int code = Integer.valueOf(codeString);
        int id = R.color.cloudyBk;
        if (100 == code) {
            id = R.color.sunnyBk;
        } else if(code >100 && code <=103 || code >= 105 && code <= 204) {
            id = R.color.cloudyBk;
        } else if (code == 104 || code >= 205 && code <= 304) {
            id = R.color.overcast;
        }
        return context.getResources().getColor(id);
    }

    public static int getColorByCode(Context context, String codeString) {
        int code = Integer.valueOf(codeString);
        int id = R.color.cloudy;
        if (100 == code) {
            id = R.color.sunny;
        }
        return context.getResources().getColor(id);
    }




    public static Drawable getDrawableCheckedByWeather(Context context, String weather) {
        int id = R.mipmap.cloudy_big;
        if (weather.equals("晴")) {
            id = R.mipmap.sun_big;
        } else if (weather.equals("多云") || weather.equals("少云")) {
            id = R.mipmap.cloudy_big;
        } else if (weather.equals("晴间多云")) {
            id = R.mipmap.suncloudy_big;
        } else if (weather.equals("阴")) {
            id = R.mipmap.cloudy_big;
        } else if (weather.contains("风")) {
            id = R.mipmap.wind_big;
        } else if (weather.contains("雷阵雨")) {
            id = R.mipmap.thunder_big;
        } else if (weather.contains("阵雨")) {
            id = R.mipmap.shower_big;
        } else if (weather.contains("雨")) {
            id = R.mipmap.rian_big;
        } else if (weather.contains("雪")) {
            id = R.mipmap.snow_big;
        }
        return context.getResources().getDrawable(id);
    }

    public static Drawable getDrawableByWeather(Context context, String weather) {
        int id = R.mipmap.cloudy_dark;
        if (weather.equals("晴")) {
            id = R.mipmap.sun_drak;
        } else if (weather.equals("多云") || weather.equals("少云")) {
            id = R.mipmap.cloudy_dark;
        } else if (weather.equals("晴间多云")) {
            id = R.mipmap.suncloudy_dark;
        } else if (weather.equals("阴")) {
            id = R.mipmap.cloudy_dark;
        } else if (weather.contains("风")) {
            id = R.mipmap.wind_dark;
        } else if (weather.contains("雷阵雨")) {
            id = R.mipmap.thunder_dark;
        } else if (weather.contains("阵雨")) {
            id = R.mipmap.shower_dark;
        } else if (weather.contains("雨")) {
            id = R.mipmap.rain_dark;
        } else if (weather.contains("雪")) {
            id = R.mipmap.snow_dark;
        }

        return context.getResources().getDrawable(id);
    }

    public static Drawable getDrawableSmallByWeather(Context context, String weather) {
        int id = R.mipmap.cloudy_small;
        if (weather.equals("晴")) {
            id = R.mipmap.sun_small;
        } else if (weather.equals("多云") || weather.equals("少云")) {
            id = R.mipmap.cloudy_small;
        } else if (weather.equals("晴间多云")) {
            id = R.mipmap.suncloudy_small;
        } else if (weather.equals("阴")) {
            id = R.mipmap.cloudy_small;
        } else if (weather.contains("风")) {
            id = R.mipmap.wind_small;
        } else if (weather.contains("雷阵雨")) {
            id = R.mipmap.thunder_small;
        } else if (weather.contains("阵雨")) {
            id = R.mipmap.shower_small;
        } else if (weather.contains("雨")) {
            id = R.mipmap.rain_small;
        } else if (weather.contains("雪")) {
            id = R.mipmap.snow_small;
        }

        return context.getResources().getDrawable(id);
    }
}
