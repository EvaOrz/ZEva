package cn.com.zwwl.bayuwen.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarTools {

    /**
     * 秒数 -- 00:00时间格式转化
     *
     * @param timeInSeconds
     * @return
     */
    public static String getTime(long timeInSeconds) {
        StringBuffer sb = new StringBuffer();
//        int hours = (int) timeInSeconds / 3600;
//        if (hours >= 10) {
//            sb.append(hours);
//            sb.append(":");
//
//        } else if (hours > 0 && hours < 10) {
//            sb.append(0).append(hours);
//            sb.append(":");
//        }

        long minutes = (int) (timeInSeconds / 60);
        if (minutes >= 10) {
            sb.append(minutes);
        } else if (minutes > 0 && minutes < 10) {
            sb.append(0).append(minutes);
        } else {
            sb.append("00");
        }
        sb.append(":");

        int seconds = (int) (timeInSeconds % 60);
        if (seconds >= 10) {
            sb.append(seconds);
        } else if (seconds > 0 && seconds < 10) {
            sb.append(0).append(seconds);
        } else {
            sb.append("00");
        }
        return sb.toString();
    }

    // 将字符串转为时间戳
    public static long fromStringTotime(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);
        } catch (ParseException e) {
            // TODO Auto-generated catch block e.printStackTrace();
        }
        return Long.valueOf(re_time);
    }

    /**
     * 格式化日期
     *
     * @param time    时间（毫秒）
     * @param pattern 日期格式
     * @return
     */
    public static String format(long time, String pattern) {
        if (TextUtils.isEmpty(pattern)) return "";
        pattern = pattern.replace("@n", "\n");
        try {
            SimpleDateFormat format;
            //            if (TextUtils.equals("english", language)) {
            //                format = new SimpleDateFormat(pattern, Locale.ENGLISH);
            //            } else {
            format = new SimpleDateFormat(pattern);
            //            }
            return format.format(new Date(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取当前年份的前后1年的calendar列表
     *
     * @return
     */
    public static List<Calendar> initCalendarForMonthView() {
        Calendar current = Calendar.getInstance();
        current.setTime(new Date());

        List<Calendar> mItems = new ArrayList<>();

        for (int j = -1; j < 3; j++) {
            for (int i = 0; i < 12; i++) {
                Calendar ca = Calendar.getInstance();
                ca.set(current.get(Calendar.YEAR )+j, i, 1);
                mItems.add(ca);
            }
        }

        return mItems;
    }


}
