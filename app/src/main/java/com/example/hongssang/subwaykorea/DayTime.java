package com.example.hongssang.subwaykorea;

import java.util.Calendar;

/**
 * Created by 최현우 on 2016-01-13.
 */
public class DayTime {
    Calendar cal;
    // getHoilyDay

    public DayTime(){
        cal = Calendar.getInstance();
    }

    public int getHoilyDay() {
        int dayNum = cal.get(Calendar.DAY_OF_WEEK);
        boolean day = false;
        boolean holiday = false;
        int month = (cal.get(cal.MONTH) + 1);
        int date = cal.get(cal.DATE);
        int holidayNum = (month*100)+date;
        int value;
        switch (dayNum){
            case 1:
            case 7:
                day = true;
                break;
            default:
                day = false;
                break;
        }
        switch (holidayNum) {
            case 101 :
            case 113 :
                holiday = true;
                break;
            default:
                holiday = false;
                break;
        }
        if(day || holiday){
            value = 1;
        } else
            value = 0;
        return value;
    }

    public int getTime(){
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        int sec = cal.get(Calendar.SECOND);

        return hour*10000+min*100+sec;
    }
}
