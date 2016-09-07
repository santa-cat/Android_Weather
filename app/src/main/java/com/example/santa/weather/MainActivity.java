package com.example.santa.weather;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BasicInfoFragment basicInfoFragment;
    private AllInfoFragment allInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        textView = (TextView) findViewById(R.id.text);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        List<Fragment> fragmentList = new ArrayList<>();
        basicInfoFragment = BasicInfoFragment.getinstance();
        allInfoFragment = AllInfoFragment.getInstance("");
        fragmentList.add(allInfoFragment);
        fragmentList.add(basicInfoFragment);


        viewPager.setAdapter(new WeatherFragmentAdapter(getSupportFragmentManager(), fragmentList));
        viewPager.setCurrentItem(fragmentList.size() - 1);

    }


    public void updateAllInfo(String city) {
        if (null != allInfoFragment) {
            allInfoFragment.updateForecast(city);
        }
    }



    public class WeatherFragmentAdapter extends FragmentStatePagerAdapter{
        private List<Fragment> mFragmentList;


        public WeatherFragmentAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            mFragmentList= list;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = this.mFragmentList.get(position);
            if (fragment != null) {
                return this.mFragmentList.get(position);
            } else {
                return null;
            }
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }
}
