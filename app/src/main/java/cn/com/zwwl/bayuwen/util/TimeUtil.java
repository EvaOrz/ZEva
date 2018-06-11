package cn.com.zwwl.bayuwen.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 时间转化
 * Created by zhumangmang at 2018/6/11 13:12
 */
public class TimeUtil {
    private static String FORMATTER_DEFAULT = "yyyy-MM-dd hh:MM:ss";

    /**
     * Long-->String
     */
    public static String parseTime(long time, String formatterStr) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatterStr, Locale.getDefault());
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        return formatter.format(time);
    }

    /**
     * 获取今日日期
     */
    public static String getCurrentDate(String formatterStr) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatterStr, Locale.getDefault());
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    /**
     * 获取指定的两个日期的时间差
     */
    public static long getDateDifference(String startDate, String endDate) {
        SimpleDateFormat myFormatter = new SimpleDateFormat(FORMATTER_DEFAULT, Locale.getDefault());
        long betweenDate = 0;
        try {
            betweenDate = (myFormatter.parse(endDate).getTime() - myFormatter.parse(startDate).getTime()) / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (betweenDate >= 0) ? betweenDate : -1;
    }

    public static long convertToMillis(String date) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATTER_DEFAULT, Locale.getDefault());
        try {
            c.setTime(sdf.parse(date));
            return c.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取当前年
     */
    public static String getCurrentM(){
        Calendar c = Calendar.getInstance();//
        return String.valueOf(c.get(Calendar.MONTH)+1);
    }
    /**
     * 获取当前月
     */
    public static String getCurrentY(){
        Calendar c = Calendar.getInstance();//
        return String.valueOf(c.get(Calendar.YEAR));
    }
}
