package com.brightlight.padzmj.myfootballscores.Fixtures.Controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by PadzMJ on 19/02/2016.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    List<Fragment> fragmentList = new ArrayList<>();
    List<String> dateTitle = new ArrayList<>();
    String dayName;
    private static String YESTERDAY = "Yesterday";
    private static String TODAY = "Today";
    private static String TOMORROW = "Tomorrow";

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String dateTitle){
        this.fragmentList.add(fragment);
        //setDayName(dateTitle);
        this.dateTitle.add(setDayName(dateTitle));
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return dateTitle.get(position);
    }


    private String setDayName(String day){

        String dayName = null;
        Calendar todayCalendar = Calendar.getInstance();
        Calendar tomorrowCalendar = Calendar.getInstance();
        Calendar yesterdayCalendar = Calendar.getInstance();


        tomorrowCalendar.add(Calendar.DAY_OF_WEEK, 1);
        yesterdayCalendar.add(Calendar.DAY_OF_WEEK, -1);

        String todayDayName = todayCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.UK);
        String tomorrowDayName = tomorrowCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.UK);
        String yesterdayDayName = yesterdayCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.UK);

        //calendar. = calendar.get(Calendar.DAY_OF_WEEK);
        //int tomorrow = calendar.get()

        if(day.equals(todayDayName)){
            Log.i("Day", "TODAY");
            return TODAY;
        }
        else if(day.equals(tomorrowDayName)){
            Log.i("Day", "TOMORROW");
            return TOMORROW;
        }
        else if(day.equals(yesterdayDayName)){
            Log.i("Day", "YESTERDAY");
            return YESTERDAY;
        }else return day;
    }
}
