package cn.com.zwwl.bayuwen.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarTools {
    private static final long ONE_DAY_MS = 24 * 60 * 60 * 1000;

    /**
     * 计算两个日期之间的日期
     *
     * @param date_start
     * @param date_end
     */
    public static List<Date> betweenDays(Date date_start, Date date_end) {
        List<Date> dates = new ArrayList<>();

        //计算日期从开始时间于结束时间的0时计算
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(date_start);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(date_end);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        int s = (int) ((toCalendar.getTimeInMillis() - fromCalendar.getTimeInMillis()) /
                (ONE_DAY_MS));
        for (int i = 0; i <= s; i++) {
            long todayDate = fromCalendar.getTimeInMillis() + i * ONE_DAY_MS;
            Date dd = new Date(todayDate);
            dates.add(dd);
        }
        return dates;
    }

    /**
     * 秒数 -- 00:00时间格式转化
     *
     * @param timeInSeconds
     * @return
     */
    public static String getTime(long timeInSeconds) {
        StringBuffer sb = new StringBuffer();
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

    /**
     * 秒数 -- 00时00分00秒时间格式转化
     *
     * @param timeInSeconds
     * @return
     */
    public static String getTimeLeft(long timeInSeconds) {
        StringBuffer sb = new StringBuffer();
        int days = (int) timeInSeconds / 3600 / 24;
        if (days > 0) {
            sb.append(days).append("天");
        }
        timeInSeconds = timeInSeconds % (3600 * 24);
        int hours = (int) timeInSeconds / 3600;
        if (hours >= 10) {
            sb.append(hours);
        } else if (hours > 0 && hours < 10) {
            sb.append(0).append(hours);
        } else {
            sb.append("00");
        }
        sb.append("时");
        timeInSeconds = timeInSeconds % 3600;
        long minutes = (int) (timeInSeconds / 60);
        if (minutes >= 10) {
            sb.append(minutes);
        } else if (minutes > 0 && minutes < 10) {
            sb.append(0).append(minutes);
        } else {
            sb.append("00");
        }
        sb.append("分");

        int seconds = (int) (timeInSeconds % 60);
        if (seconds >= 10) {
            sb.append(seconds);
        } else if (seconds > 0 && seconds < 10) {
            sb.append(0).append(seconds);
        } else {
            sb.append("00");
        }
        sb.append("秒");
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
            re_time = String.valueOf(l);
        } catch (ParseException e) {
        }
        return Long.valueOf(re_time);
    }

    // 将字符串转为时间戳
    public static long fromStringToLongtime(String user_time) {
        String[] ss = user_time.split(":");
        if (ss.length > 1) {
            Long lh = Long.valueOf(ss[0]) * 60 * 60 * 1000;
            Long lm = Long.valueOf(ss[1]) * 60 * 1000;
            return lh + lm;
        } else return 0;

    }

    // 将字符串转为Calendar
    public static Calendar fromStringToca(String user_time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            Date d = sdf.parse(user_time);
            calendar.setTime(d);

        } catch (ParseException e) {
            // TODO Auto-generated catch block e.printStackTrace();
        }

        return calendar;
    }

    public static Calendar fromStringToca1(String user_time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        try {
            Date d = sdf.parse(user_time);
            calendar.setTime(d);

        } catch (ParseException e) {
            // TODO Auto-generated catch block e.printStackTrace();
        }

        return calendar;
    }

    /**
     * 格式化日期
     *
     * @param time    时间（秒）
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
            format = new SimpleDateFormat(pattern, Locale.getDefault());
            //            }
            return format.format(new Date(time * 1000));
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

        for (int j = -1; j < 2; j++) {
            for (int i = 0; i < 12; i++) {
                Calendar ca = Calendar.getInstance();
                ca.set(current.get(Calendar.YEAR) + j, i, 1);
                mItems.add(ca);
            }
        }

        return mItems;
    }

    /**
     * 根据提供的年月获取该月份的第一天
     *
     * @param calendar
     * @return
     */
    public static Date getBeginDayofMonth(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDate = calendar.getTime();
        return firstDate;
    }

    /**
     * 根据提供的年月获取该月份的最后一天
     *
     * @param calendar
     * @return
     */
    public static Date getEndDayofMonth(Calendar calendar) {
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDate = calendar.getTime();
        return lastDate;
    }

    /**
     * 获取月份选择器的最大年份
     *
     * @return
     */
    public static int getMaxYear() {
        Calendar current = Calendar.getInstance();
        current.setTime(new Date());
        return current.get(Calendar.YEAR) + 1;
    }

    /**
     * 获取当前年份
     *
     * @return
     */
    public static int getCurrentYear() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR);
    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static int getCurrentMonth() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static int getCurrentDay() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DATE);
    }

    /**
     * 获取月份选择器的最小年份
     *
     * @return
     */
    public static int getMinYear() {
        Calendar current = Calendar.getInstance();
        current.setTime(new Date());
        return current.get(Calendar.YEAR) - 1;
    }

    /**
     * 获取日期之间的周数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int countTwoDayWeek(Date startDate, Date endDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(endDate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        Double days = Double.parseDouble(String.valueOf(between_days));
        if ((days / 7) > 0 && (days / 7) <= 1) {
            //不满一周的按一周算
            return 1;
        } else if (days / 7 > 1) {
            int day = days.intValue();
            if (day % 7 > 0) {
                return day / 7 + 1;
            } else {
                return day / 7;
            }
        } else if ((days / 7) == 0) {
            return 0;
        } else {
            //负数返还null
            return 0;
        }
    }


}
