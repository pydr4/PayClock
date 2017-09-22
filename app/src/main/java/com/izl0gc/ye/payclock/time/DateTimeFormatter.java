package com.izl0gc.ye.payclock.time;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateTimeFormatter {
    public static long DateToLong(String date){
        long convertedDate = 0;
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(
                    date));
            convertedDate = calendar.getTimeInMillis();
        }
        catch (Exception e) {
            Log.e("Error", "Date is in wrong format");

        }

        return convertedDate;

    }

    public static String DateToString(int year, int monthOfYear, int dayOfMonth){
        Calendar myCalendar = Calendar.getInstance();
        // TODO Auto-generated method stub
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        return sdf.format(myCalendar.getTime());
    }

    public static double TimeToNumber(int hour, int min){
            return ((double)hour) + ((((double)min) * 60)/3600);
    }

    public static String TimeToString(double time){
        int hour = (int)time;

        int min = (int)((time - hour) * 3600)/60;

        if(min < 10) {
            return hour + ":0" + min;
        }else{

            return hour + ":"+min;
        }
    }
}