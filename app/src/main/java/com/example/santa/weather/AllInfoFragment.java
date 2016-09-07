package com.example.santa.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by santa on 16/7/31.
 */
public class AllInfoFragment extends Fragment {
    private View self;
    private static AllInfoFragment instance;
    private RecyclerView rvHourly;
    private RecyclerView rvDaily;
    private String city;

    public static AllInfoFragment getInstance(String city) {
//        this.city = city;
        if (instance == null) {
            synchronized (BasicInfoFragment.class) {
                if (instance == null)
                    instance = new AllInfoFragment();
            }
        }
        return instance;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == this.self) {
            this.self = inflater.inflate(R.layout.fragment_allinfo, container, false);
            //hourly
            rvHourly = (RecyclerView) this.self.findViewById(R.id.all_hourly);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rvHourly.setLayoutManager(layoutManager);
            rvHourly.setHasFixedSize(true);
//            HoutlyAdapter adapter = new HoutlyAdapter(getActivity(), getHourlyData());
//            rvHourly.setAdapter(adapter);

            //daily
            rvDaily = (RecyclerView) this.self.findViewById(R.id.all_daily);
            rvDaily.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvDaily.setHasFixedSize(true);
//            rvDaily.setAdapter(new DailyAdapter(getActivity(), getDailyData()));
        }

        return this.self;
    }

    public void updateForecast(String city) {
        new AllInfoLoader(new AllInfoLoader.Listener() {
            @Override
            public void onComplete(AllInfo allInfo) {
                rvDaily.setAdapter(new DailyAdapter(getActivity(), allInfo.getDailyData()));
                rvHourly.setAdapter(new HoutlyAdapter(getActivity(), allInfo.getHourlyData()));
                setData(allInfo);
            }
        }).execute(city);
    }


    private void setData(AllInfo allInfo) {

        ImageView imageView = (ImageView) this.self.findViewById(R.id.all_weather);
        imageView.setImageDrawable(Utils.getDrawableSmallByWeather(getActivity(), allInfo.getNowTxt()));
        TextView textView = (TextView) this.self.findViewById(R.id.all_city);
        textView.setText(allInfo.getCity());
        textView = (TextView) this.self.findViewById(R.id.all_tmp);
        textView.setText(allInfo.getNowFl());

        textView = (TextView) this.self.findViewById(R.id.all_lat);
        textView.setText(allInfo.getLat());
        textView = (TextView) this.self.findViewById(R.id.all_lon);
        textView.setText(allInfo.getLon());

        textView = (TextView) this.self.findViewById(R.id.all_hum);
        textView.setText(allInfo.getHum());
        textView = (TextView) this.self.findViewById(R.id.all_pcpn);
        textView.setText(allInfo.getPcpn());
        textView = (TextView) this.self.findViewById(R.id.all_pres);
        textView.setText(allInfo.getPres());

        textView = (TextView) this.self.findViewById(R.id.all_dir);
        textView.setText(allInfo.getDir());
        textView = (TextView) this.self.findViewById(R.id.all_sc);
        textView.setText(allInfo.getSc());
        textView = (TextView) this.self.findViewById(R.id.all_spd);
        textView.setText(allInfo.getSpd());

        textView = (TextView) this.self.findViewById(R.id.all_fl);
        textView.setText(allInfo.getNowFl());
        textView = (TextView) this.self.findViewById(R.id.all_vis);
        textView.setText(allInfo.getVis());

        textView = (TextView) this.self.findViewById(R.id.all_sunrise);
        textView.setText(allInfo.getSunRise());
        textView = (TextView) this.self.findViewById(R.id.all_sunset);
        textView.setText(allInfo.getSunSet());
    }



}
