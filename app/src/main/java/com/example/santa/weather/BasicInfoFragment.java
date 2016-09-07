package com.example.santa.weather;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by santa on 16/7/28.
 */
public class BasicInfoFragment extends Fragment {
    private String[] city ={"杭州", "嘉兴", "北京"};
    private View self;
    private static BasicInfoFragment instance;
    private RecyclerView recyclerView;
    private BasicListAdapter adapter;
    private final static String USERDATA = "userDatas";
    private final static String CITYS = "citys";

    public static BasicInfoFragment getinstance() {
        if (instance == null) {
            synchronized (BasicInfoFragment.class) {
                if (instance == null)
                    instance = new BasicInfoFragment();
            }
        }
//        instance.setArguments();
        return instance;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        if (null == this.self) {
            this.self = inflater.inflate(R.layout.fragment_basicinfo, null);


            recyclerView = (RecyclerView) this.self.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setHasFixedSize(true);
            initAddCity(this.self);

            new WeatherInfoLoader(new WeatherInfoLoader.Listener() {
                @Override
                public void onComplete(ArrayList<BasicInfo> list) {
                    Log.d("DEBUG", "onComplete list = "+list.size());
                    adapter = new BasicListAdapter(getActivity(), list, new BasicListAdapter.ChooseListener() {
                        @Override
                        public void onUpdate(String city) {
                            ((MainActivity)getActivity()).updateAllInfo(city);
                        }

                        @Override
                        public void onDelete(String city) {
                            deleteCitys(city);
                        }
                    });
                    recyclerView.setAdapter(adapter);
//                    ItemTouchHelper.Callback callback = new BasicInfoItemTouchHelperCallback(adapter);
//                    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
//                    itemTouchHelper.attachToRecyclerView(recyclerView);
                }
            }).execute(readCitys());

        }
        if (this.self.getParent() != null) {
            ViewGroup parent = (ViewGroup) this.self.getParent();
            parent.removeView(this.self);
        }
        return this.self;
    }



    private void initAddCity(View view) {
        HideEditLayout hideEditLayout = (HideEditLayout) view.findViewById(R.id.addcity);
        hideEditLayout.setAddCityListener(new HideEditLayout.AddCityListener() {
            @Override
            public void onAdd(String cityName) {
                saveCitys(cityName);

                Log.d("DEBUG", cityName);
                new WeatherInfoLoader(new WeatherInfoLoader.Listener() {
                    @Override
                    public void onComplete(ArrayList<BasicInfo> list) {
                        if (null != list && list.size() > 0) {
                            adapter.addcity(list.get(0));
                        }
                    }
                }).execute(cityName);

            }
        });

    }

    private String[] readCitys() {
        SharedPreferences sp = getActivity().getSharedPreferences(USERDATA, Context.MODE_PRIVATE);
        Set<String> citys = new HashSet<>();
        citys = sp.getStringSet(CITYS, citys);
        if (citys.size() > 0) {
            city = citys.toArray(new String[citys.size() - 1]);
        }
        Log.d("DEBUG", "size = "+citys.size());

        return city;
    }

    private void saveCitys(String cityNew) {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(USERDATA, Context.MODE_PRIVATE).edit();
        Set<String> citys = new HashSet<>();
        for (int i = 0 ; i<city.length; i++) {
            citys.add(city[i]);
        }
        citys.add(cityNew);
        editor.putStringSet(CITYS, citys);
        editor.apply();
    }

    private void deleteCitys(String cityItem) {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(USERDATA, Context.MODE_PRIVATE).edit();
        Set<String> citys = new HashSet<>();
        for (int i = 0 ; i<city.length; i++) {
            citys.add(city[i]);
        }
        citys.remove(cityItem);
        editor.putStringSet(CITYS, citys);
        editor.apply();
    }

//    private void saveToFile(String cityNew) {
//        FileOutputStream outputStream = null;
//        BufferedWriter
//
//    }


    private ArrayList<BasicInfo> getData() {
        ArrayList<BasicInfo> arrayList = new ArrayList<>();

        {
            BasicInfo basicInfo = new BasicInfo("杭州", "100", "晴", "37°");
            arrayList.add(basicInfo);
        }
        {
            BasicInfo basicInfo = new BasicInfo("上海", "103", "晴间多云", "34°");
            arrayList.add(basicInfo);
        }
        {
            BasicInfo basicInfo = new BasicInfo("嘉兴", "103", "晴间多云", "35°");
            arrayList.add(basicInfo);
        }
        {
            BasicInfo basicInfo = new BasicInfo("北京", "101", "多云", "33°");
            arrayList.add(basicInfo);
        }
        {
            BasicInfo basicInfo = new BasicInfo("北京", "101", "多云", "33°");
            arrayList.add(basicInfo);
        }
        {
            BasicInfo basicInfo = new BasicInfo("北京", "101", "多云", "33°");
            arrayList.add(basicInfo);
        }
        {
            BasicInfo basicInfo = new BasicInfo("北京", "101", "多云", "33°");
            arrayList.add(basicInfo);
        }
        {
            BasicInfo basicInfo = new BasicInfo("北京", "101", "多云", "33°");
            arrayList.add(basicInfo);
        }
        {
            BasicInfo basicInfo = new BasicInfo("北京", "101", "多云", "33°");
            arrayList.add(basicInfo);
        }
        {
            BasicInfo basicInfo = new BasicInfo("北京", "101", "多云", "33°");
            arrayList.add(basicInfo);
        }

        return arrayList;
    }


}
