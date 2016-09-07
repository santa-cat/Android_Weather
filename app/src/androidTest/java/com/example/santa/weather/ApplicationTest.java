package com.example.santa.weather;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import java.util.Calendar;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }


    public void testTime() {
        String text = "2015-07-02 19:00";

        String[] str = text.split(" ");
        str = str[1].split(":");

        Log.d("TEST", String.valueOf(Integer.valueOf(str[0])));
    }


    public void testWeekTime() {
        String text = "2016-08-02";
        String[] str = text.split("-");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.valueOf(str[0]), Integer.valueOf(str[1])-1, Integer.valueOf(str[2]));

        Log.d("TEST", calendar.get(Calendar.DAY_OF_WEEK)+"");
        Log.d("TEST", calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH)+"");


    }
}