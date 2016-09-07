package com.example.santa.weather;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by santa on 16/7/27.
 */
public class HttpConnentionHelp {
    private String urlAddress = "" ;
    private HttpURLConnection uRLConnection;

    //向服务器发送get请求
    public String doGet(String city){
        Log.d("DEBUG", "doGet in");

        String httpUrl = "http://apis.baidu.com/heweather/weather/free";
        String httpArg = "city="+city.trim();
        String apikey = "eb0f99c5b5dbd36a5bd68f7c30d499f9";
        String response = null;
        try {
            URL url = new URL(httpUrl+"?"+httpArg);
            uRLConnection = (HttpURLConnection)url.openConnection();
            uRLConnection.setRequestMethod("GET");
            uRLConnection.setConnectTimeout(5000);
            uRLConnection.setReadTimeout(5000);
            uRLConnection.setRequestProperty("apikey", apikey);
            if (uRLConnection.getResponseCode() == 200) {

                InputStream is = uRLConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String readLine = null;
                response = "";
                while ((readLine = br.readLine()) != null) {
                    //response = br.readLine();
                    response = response + readLine;
                }
                is.close();
                br.close();
                return response;
            } else {
                return null;
            }
        }  catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (uRLConnection != null) {
                uRLConnection.disconnect();
            }
        }
    }

}
