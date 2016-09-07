package com.example.santa.weather;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by santa on 16/7/27.
 */
public class WeatherInfoLoader extends AsyncTask<String, Integer, ArrayList<BasicInfo>> {
    private Listener mListener;


    public WeatherInfoLoader(Listener listener) {
        mListener = listener;
    }

    @Override
    protected ArrayList<BasicInfo> doInBackground(String... params) {
        ArrayList<BasicInfo> arrayList = new ArrayList<>();
        Log.d("DEBUG", "doInBackground params = "+params.length);

        for (int i = 0 ; i<params.length; i++) {
            Log.d("DEBUG", "params = "+params[i]);
            HttpConnentionHelp httpConnentionHelp = new HttpConnentionHelp();
            Log.d("DEBUG", "httpConnentionHelp");
            BasicInfo basicInfo = JsonParser.parseBasicInfo(httpConnentionHelp.doGet(params[i]));
            Log.d("DEBUG", "parseBasicInfo out");
            if (null != basicInfo) {
                arrayList.add(basicInfo);
            }
        }

        return arrayList;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(ArrayList<BasicInfo> list) {
        if (mListener != null) {
            mListener.onComplete(list);
        }
    }


    public interface Listener{
        void onComplete(ArrayList<BasicInfo> list);
    }
}
