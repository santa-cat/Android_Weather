package com.example.santa.weather;

import java.util.ArrayList;

/**
 * Created by santa on 16/7/27.
 */
public class AllInfo {
    private String city;
    private String code;
    private String sunRise;
    private String sunSet;
    private String nowTxt; //天气状况
    private String nowFl;  //体感温度
    private String lat;  //纬度
    private String lon;  //经度
    private String hum;  //相对湿度
    private String pcpn;  //降水量（mm）
    private String pres;  //气压
    private String vis;  //能见度（km）
    private String dir;  //风向
    private String sc;  //风力
    private String spd;  //风速（kmph）

    private ArrayList<Hourly> hourlyData;

    public ArrayList<Hourly> getHourlyData() {
        return hourlyData;
    }

    public void setHourlyData(ArrayList<Hourly> hourlyData) {
        this.hourlyData = hourlyData;
    }

    public ArrayList<Daily> getDailyData() {
        return dailyData;
    }

    public void setDailyData(ArrayList<Daily> dailyData) {
        this.dailyData = dailyData;
    }

    private ArrayList<Daily> dailyData;

    public AllInfo() {}

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNowTxt() {
        return nowTxt;
    }

    public void setNowTxt(String nowTxt) {
        this.nowTxt = nowTxt;
    }

    public String getNowFl() {
        return nowFl;
    }

    public void setNowFl(String nowFl) {
        this.nowFl = nowFl;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getPcpn() {
        return pcpn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPcpn(String pcpn) {
        this.pcpn = pcpn;

    }

    public String getPres() {
        return pres;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }

    public String getVis() {
        return vis;
    }

    public void setVis(String vis) {
        this.vis = vis;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getSc() {
        return sc;
    }

    public void setSc(String sc) {
        this.sc = sc;
    }

    public String getSpd() {
        return spd;
    }

    public void setSpd(String spd) {
        this.spd = spd;
    }

    public String getSunRise() {
        return sunRise;
    }

    public void setSunRise(String sunRise) {
        this.sunRise = sunRise;
    }

    public String getSunSet() {
        return sunSet;
    }

    public void setSunSet(String sunSet) {
        this.sunSet = sunSet;
    }
}
