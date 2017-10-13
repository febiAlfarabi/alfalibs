package com.alfarabi.alfalibs.tools;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

/**
 * Created by Alfarabi on 7/31/17.
 */

public class ArrayCalendarSpinner {

    static ArrayList<String> allDays = new ArrayList<String>();
    static ArrayList<String> allMonths = new ArrayList<String>();
    static ArrayList<String> allYears = new ArrayList<String>();

    public static ArrayList<String> getAllDays() {
        return allDays;
    }
    public static void setAllDays(ArrayList<String> allDays) {
        ArrayCalendarSpinner.allDays = allDays;
    }
    public static ArrayList<String> getAllMonths() {
        return allMonths;
    }
    public static void setAllMonths(ArrayList<String> allMonths) {
        ArrayCalendarSpinner.allMonths = allMonths;
    }
    public static ArrayList<String> getAllYears() {
        return allYears;
    }
    public static void setAllYears(ArrayList<String> allYears) {
        ArrayCalendarSpinner.allYears = allYears;
    }

    public static void init(){
        Calendar mCalendar = Calendar.getInstance();
        initDateSpinner(0, mCalendar.get(Calendar.DAY_OF_MONTH), String.valueOf(mCalendar.get(Calendar.DAY_OF_MONTH)));
        initMonthSpinner(0, mCalendar.get(Calendar.MONTH), String.valueOf(mCalendar.get(Calendar.MONTH)));
        initYearSpinner(mCalendar.get(Calendar.YEAR)-110, mCalendar.get(Calendar.YEAR), String.valueOf(mCalendar.get(Calendar.YEAR)));
    }

    static {
        Calendar mCalendar = Calendar.getInstance();
        initDateSpinner(0, mCalendar.get(Calendar.DAY_OF_MONTH), String.valueOf(mCalendar.get(Calendar.DAY_OF_MONTH)));
        initMonthSpinner(0, mCalendar.get(Calendar.MONTH), String.valueOf(mCalendar.get(Calendar.MONTH)));
        initYearSpinner(mCalendar.get(Calendar.YEAR)-110, mCalendar.get(Calendar.YEAR), String.valueOf(mCalendar.get(Calendar.YEAR)));
    }

    public static void initDateSpinner(int min, int max, String def){
        allDays.clear();
        for (int i = max; i > min ; i--) {
            allDays.add(String.valueOf(i));
        }
    }

    public static void initMonthSpinner(int min, int max, String def){
        allMonths.clear();
        for (int i = max; i >= min ; i--) {
            allMonths.add(DateFormatSymbols.getInstance().getMonths()[i]);
        }
        Collections.reverse(allMonths);
    }

    public static void initYearSpinner(int min, int max, String def){
        allYears.clear();
        for (int i = max; i > min ; i--) {
            allYears.add(String.valueOf(i));
        }
    }

    public static int getMonth(String input){
        DateFormat df = new SimpleDateFormat("MMMM");
        try {
            Date date = df.parse(input);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal.get(Calendar.MONTH);

        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
