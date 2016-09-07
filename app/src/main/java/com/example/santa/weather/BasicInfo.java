package com.example.santa.weather;

/**
 * Created by santa on 16/7/27.
 */

public class BasicInfo {
    private String code;
    private String city;
    private String nowTxt; //天气状况
    private String nowFl;  //体感温度


    public BasicInfo() {}

    public BasicInfo(String city, String code, String nowTxt, String nowFl) {
        this.city = city;
        this.code = code;
        this.nowTxt = nowTxt;
        this.nowFl = nowFl;
    }

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
