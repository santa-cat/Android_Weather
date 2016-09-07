package com.example.santa.weather;

/**
 * Created by santa on 16/8/1.
 */
public class Hourly {
    private String time;
    private String tmp;
    private String weather;

    public Hourly() {
    }

    public Hourly(String time, String tmp, String weather) {

        this.time = time;
        this.tmp = tmp;
        this.weather = weather;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}
