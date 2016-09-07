package com.example.santa.weather;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by santa on 16/7/27.
 */
public class JsonParser {
    private final static String DEBUG = "JsonParser";
    private final static String JSONARRAY = "HeWeather data service 3.0";


    private final static String STATUS = "status";


    //basic
    private final static String BASIC = "basic";
    private final static String CITY = "city";
    private final static String LAT = "lat";  //纬度
    private final static String LON = "lon";  //经度

    //now
    private final static String NOW = "now";
    private final static String COND = "cond";
    private final static String CODE = "code";
    private final static String TXT = "txt"; //天气状况描述
    private final static String TMP = "tmp";   //温度
    private final static String HUM = "hum";  //相对湿度
    private final static String PCPN = "pcpn";//降水量（mm）
    private final static String PRES = "pres";//气压
    private final static String VIS = "vis"; //能见度（km）
    private final static String WIND = "wind"; //风力风向
    private final static String DIR = "dir"; //风向
    private final static String SC = "sc"; //风力
    private final static String SPD = "spd"; //风速（kmph）

    //hourly
    private final static String HOURLY = "hourly_forecast";
    private final static String DATE = "date";


    //daily
    private final static String DAILY = "daily_forecast";
    private final static String TMPMAX = "max";
    private final static String TMPMIN = "min";
    private final static String ASTRO = "astro";
    private final static String SR = "sr";
    private final static String SS = "ss";


    private final static String ERR = "get weather info error";
    private final static String SUCCESS = "get weather info success";


    public static BasicInfo parseBasicInfo(String jsonData) {
        Log.d("DEBUG", "parseBasicInfo in");
        if (null == jsonData) {
            return null;
        }
        BasicInfo basicInfo = null;
        try {
            JSONObject jsonObjectAll = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObjectAll.getJSONArray(JSONARRAY);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            if (jsonObject.getString(STATUS) == null || !jsonObject.getString(STATUS).equals("ok")) {
                Log.d(DEBUG, ERR);
                return null;
            } else {
                Log.d(DEBUG, SUCCESS);
            }
            basicInfo = new BasicInfo();

            basicInfo.setCity(getInfoByJsonKeys(jsonObject, BASIC, CITY));
            basicInfo.setNowTxt(getInfoByJsonKeys(jsonObject, NOW, COND, TXT));
            basicInfo.setNowFl(getInfoByJsonKeys(jsonObject, NOW, TMP)+"°");
            basicInfo.setCode(getInfoByJsonKeys(jsonObject, NOW, COND, CODE));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return basicInfo;
    }


    public static AllInfo parseAllInfo(String jsonData) {
        AllInfo allInfo = null;
        try {
            JSONObject jsonObjectAll = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObjectAll.getJSONArray(JSONARRAY);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            allInfo = new AllInfo();

            allInfo.setCity(getInfoByJsonKeys(jsonObject, BASIC, CITY));
            allInfo.setNowTxt(getInfoByJsonKeys(jsonObject, NOW, COND, TXT));
            allInfo.setCode(getInfoByJsonKeys(jsonObject, NOW, COND, CODE));
            allInfo.setNowFl(getInfoByJsonKeys(jsonObject, NOW, TMP)+"°");
            allInfo.setLat(getInfoByJsonKeys(jsonObject, BASIC, LAT));
            allInfo.setLon(getInfoByJsonKeys(jsonObject, BASIC, LON));
            allInfo.setHum(getInfoByJsonKeys(jsonObject, NOW, HUM)+"%");
            allInfo.setPcpn(getInfoByJsonKeys(jsonObject, NOW, PCPN)+" mm");
            allInfo.setPres(getInfoByJsonKeys(jsonObject, NOW, PRES));
            allInfo.setVis(getInfoByJsonKeys(jsonObject, NOW, VIS)+" km");
            allInfo.setDir(getInfoByJsonKeys(jsonObject, NOW, WIND, DIR));
            allInfo.setSc(getInfoByJsonKeys(jsonObject, NOW, WIND, SC));
            allInfo.setSpd(getInfoByJsonKeys(jsonObject, NOW, WIND, SPD)+" kmph");
//            allInfo.setSunRise();
            setSunTime(jsonObject, allInfo);

            allInfo.setHourlyData(getHourlyData(jsonObject, allInfo.getNowTxt()));
            allInfo.setDailyData(getDailyData(jsonObject));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return allInfo;
    }

    private static void setSunTime(JSONObject jsonObject, AllInfo allInfo) throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray(DAILY);
        JSONObject object = jsonArray.getJSONObject(0);

        allInfo.setSunRise(getInfoByJsonKeys(object, ASTRO, SR));
        allInfo.setSunSet(getInfoByJsonKeys(object, ASTRO, SS));
    }

    private static ArrayList<Hourly> getHourlyData(JSONObject jsonObject, String weather) {
        try {
            ArrayList<Hourly> list = new ArrayList<>();
            JSONArray jsonArray = jsonObject.getJSONArray(HOURLY);
            for (int i = 0 ; i<jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                Hourly hourly = new Hourly();
                hourly.setTime(getHourByTime(object.getString(DATE))+"时");
                hourly.setTmp(object.getString(TMP)+"°");
                hourly.setWeather(weather);
                list.add(hourly);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static ArrayList<Daily> getDailyData(JSONObject jsonObject) {
        try {
            ArrayList<Daily> list = new ArrayList<>();
            JSONArray jsonArray = jsonObject.getJSONArray(DAILY);
            for (int i = 0 ; i<jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                Daily daily = new Daily();
                daily.setTime(getWeekTime(object.getString(DATE)));
                daily.setTmpLow(getInfoByJsonKeys(object, TMP, TMPMIN)+"°");
                daily.setTmpHigh(getInfoByJsonKeys(object, TMP, TMPMAX)+"°");
                daily.setWeather(getInfoByJsonKeys(object, COND, "txt_d"));
                list.add(daily);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    //2015-07-02 01:00
    private static String getHourByTime(String time) {
        String[] str = time.split(" ");
        str = str[1].split(":");
        return String.valueOf(Integer.valueOf(str[0]));
    }

    private static String getWeekTime(String time) {
        String[] str = time.split("-");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.valueOf(str[0]), Integer.valueOf(str[1])-1, Integer.valueOf(str[2]));

        return getDayInWeek(calendar.get(Calendar.DAY_OF_WEEK));
    }


    private static String getDayInWeek(int dayIdx) {
        switch (dayIdx) {
            case 1:return "星期日";
            case 2:return "星期一";
            case 3:return "星期二";
            case 4:return "星期三";
            case 5:return "星期四";
            case 6:return "星期五";
            case 7:return "星期六";
        }
        return "星期六";
    }

//    private

    private static String getInfoByJsonKeys(JSONObject jsonObject , String ...keys) {

        for (int i = 0 ; i<keys.length - 1; i++) {
            try {
                jsonObject = jsonObject.getJSONObject(keys[i]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            return jsonObject.getString(keys[keys.length - 1]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }



}
