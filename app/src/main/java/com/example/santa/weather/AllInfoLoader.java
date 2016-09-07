package com.example.santa.weather;

import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by santa on 16/7/27.
 */
public class AllInfoLoader extends AsyncTask<String, Integer, AllInfo> {
    private Listener mListener;


    public AllInfoLoader(Listener listener) {
        mListener = listener;
    }

    @Override
    protected AllInfo doInBackground(String... params) {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        HttpConnentionHelp httpConnentionHelp = new HttpConnentionHelp();
        return JsonParser.parseAllInfo(httpConnentionHelp.doGet(params[0]));

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(AllInfo allInfo) {
        if (mListener != null) {
            mListener.onComplete(allInfo);
        }
    }


    public interface Listener{
        void onComplete(AllInfo allInfo);
    }
}
