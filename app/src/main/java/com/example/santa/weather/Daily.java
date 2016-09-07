package com.example.santa.weather;

/**
 * Created by santa on 16/8/1.
 */
public class Daily {
    private String time;
    private String tmpHigh;
    private String tmpLow;
    private String weather;

    public Daily() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTmpHigh() {
        return tmpHigh;
    }

    public void setTmpHigh(String tmpHigh) {
        this.tmpHigh = tmpHigh;
    }

    public String getTmpLow() {
        return tmpLow;
    }

    public void setTmpLow(String tmpLow) {
        this.tmpLow = tmpLow;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}
