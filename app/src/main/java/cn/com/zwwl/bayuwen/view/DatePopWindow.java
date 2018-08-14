package cn.com.zwwl.bayuwen.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.widget.wheel.OnWheelChangedListener;
import cn.com.zwwl.bayuwen.widget.wheel.OnWheelScrollListener;
import cn.com.zwwl.bayuwen.widget.wheel.WheelView;
import cn.com.zwwl.bayuwen.widget.wheel.adapters.AbstractWheelTextAdapter;

/**
 * 年月日选择器
 */
public class DatePopWindow implements View.OnClickListener {
    private Context mContext;
    private PopupWindow window;
    private MyDatePickListener listener;

    private WheelView wvYear;
    private WheelView wvMonth;
    private WheelView wvDay;

    private ArrayList<String> arry_years = new ArrayList<>();
    private ArrayList<String> arry_months = new ArrayList<>();
    private ArrayList<String> arry_days = new ArrayList<>();
    private CalendarTextAdapter mYearAdapter;
    private CalendarTextAdapter mMonthAdapter;
    private CalendarTextAdapter mDaydapter;

    private int month;
    private int day;

    private int currentYear = CalendarTools.getCurrentYear();
    private int currentMonth = CalendarTools.getCurrentMonth();
    private int currentDay = CalendarTools.getCurrentDay();

    private int maxTextSize = 24;
    private int minTextSize = 14;

    private boolean isNeedDay = true;// 是否需要日
    private boolean isBeforeToday = true;// 是否需要早于今天
    private int afterToYearNum = 3;// 取今年过后（包括今年在内）的年数

    // 选中的时间项
    private String selectYear;
    private String selectMonth;
    private String selectDay;


    /**
     * @param isBeforeToday 日历事件（今天之后）、奖状获取事件（今天之前）
     * @param context
     */
    public DatePopWindow(Context context, boolean isBeforeToday, MyDatePickListener listener) {
        mContext = context;
        this.listener = listener;
        this.isBeforeToday = isBeforeToday;
        setDate(currentYear, currentMonth, currentDay);
        init();
    }

    /**
     * 带默认年月日的构造
     *
     * @param context
     * @param isNeedDay
     * @param y
     * @param m
     * @param d
     * @param listener
     */
    public DatePopWindow(Context context, boolean isBeforeToday, boolean isNeedDay, int y, int m, int d,
                         MyDatePickListener listener) {
        mContext = context;
        this.isNeedDay = isNeedDay;
        this.isBeforeToday = isBeforeToday;
        this.listener = listener;
        setDate(y, m, d);
        init();
    }

    public void init() {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.pop_datepicker, null);
        view.findViewById(R.id.btn_myinfo_sure)
                .setOnClickListener(this);
        view.findViewById(R.id.btn_myinfo_cancel)
                .setOnClickListener(this);
        window = new PopupWindow(view, RelativeLayout.LayoutParams.FILL_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.setAnimationStyle(R.style.fetch_image_popup_anim);
        window.update();
        window.setBackgroundDrawable(new BitmapDrawable());
        window.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        wvYear = (WheelView) view.findViewById(R.id.wv_birth_year);
        wvMonth = (WheelView) view.findViewById(R.id.wv_birth_month);
        wvDay = (WheelView) view.findViewById(R.id.wv_birth_day);


        if (!isNeedDay) {
            wvDay.setVisibility(View.GONE);
        }

        initYears(isBeforeToday);
        mYearAdapter = new CalendarTextAdapter(mContext, arry_years, setYear(currentYear),
                maxTextSize, minTextSize);
        wvYear.setVisibleItems(5);
        wvYear.setViewAdapter(mYearAdapter);
        wvYear.setCurrentItem(setYear(currentYear));

        initMonths(month);
        mMonthAdapter = new CalendarTextAdapter(mContext, arry_months, setMonth(currentMonth),
                maxTextSize, minTextSize);
        wvMonth.setVisibleItems(5);
        wvMonth.setViewAdapter(mMonthAdapter);
        wvMonth.setCurrentItem(setMonth(currentMonth));

        initDays(day);
        mDaydapter = new CalendarTextAdapter(mContext, arry_days, currentDay - 1, maxTextSize,
                minTextSize);
        wvDay.setVisibleItems(5);
        wvDay.setViewAdapter(mDaydapter);
        wvDay.setCurrentItem(currentDay - 1);

        wvYear.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) mYearAdapter.getItemText(wheel.getCurrentItem());
                selectYear = currentText;
                setTextviewSize(currentText, mYearAdapter);
                currentYear = Integer.parseInt(currentText);
                setYear(currentYear);
                initMonths(month);
                mMonthAdapter = new CalendarTextAdapter(mContext, arry_months, 0, maxTextSize,
                        minTextSize);
                wvMonth.setVisibleItems(5);
                wvMonth.setViewAdapter(mMonthAdapter);
                wvMonth.setCurrentItem(0);
            }
        });

        wvYear.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentText = (String) mYearAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, mYearAdapter);
            }
        });

        wvMonth.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = (String) mMonthAdapter.getItemText(wheel.getCurrentItem());
                selectMonth = currentText;
                setTextviewSize(currentText, mMonthAdapter);
                setMonth(Integer.parseInt(currentText));
                initDays(day);
                mDaydapter = new CalendarTextAdapter(mContext, arry_days, 0, maxTextSize,
                        minTextSize);
                wvDay.setVisibleItems(5);
                wvDay.setViewAdapter(mDaydapter);
                wvDay.setCurrentItem(0);
            }
        });

        wvMonth.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentText = (String) mMonthAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, mMonthAdapter);
            }
        });

        wvDay.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = (String) mDaydapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, mDaydapter);
                selectDay = currentText;
            }
        });

        wvDay.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) mDaydapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, mDaydapter);
            }

        });

    }

    public void initYears(boolean isBeforeToday) {
        if (isBeforeToday) {
            for (int i = CalendarTools.getCurrentYear(); i > 2000; i--) {
                arry_years.add(i + "");
            }
        } else {
            for (int i = CalendarTools.getCurrentYear() + afterToYearNum - 1; i >= CalendarTools
                    .getCurrentYear();
                 i--) {
                arry_years.add(i + "");
            }

        }

    }

    /**
     * @param months
     */
    public void initMonths(int months) {
        arry_months.clear();
        if (isBeforeToday) {
            for (int i = 1; i <= months; i++) {
                arry_months.add(i + "");
            }
        } else {
            if (Integer.parseInt(selectYear) == CalendarTools.getCurrentYear())
                for (int i = CalendarTools.getCurrentMonth(); i <= 12; i++) {
                    arry_months.add(i + "");
                }
            else
                for (int i = 1; i <= 12; i++) {
                    arry_months.add(i + "");
                }
        }
    }

    public void initDays(int days) {
        arry_days.clear();
        if (isBeforeToday) {
            for (int i = 1; i <= days; i++) {
                arry_days.add(i + "");
            }
        } else {
            int num2 = CalendarTools.fromStringToca1(selectYear + "-" + selectMonth).getActualMaximum(Calendar.DAY_OF_MONTH);
            if (Integer.parseInt(selectYear) == CalendarTools.getCurrentYear() && Integer.parseInt(selectMonth) == CalendarTools.getCurrentMonth()) {
                for (int i = days; i <= num2; i++) {
                    arry_days.add(i + "");
                }
            } else {
                for (int i = 1; i <= num2; i++) {
                    arry_days.add(i + "");
                }
            }

        }
    }

    /**
     * 时间选择监听
     */
    public interface MyDatePickListener {
        public void onDatePick(int year, int month, int day);
    }


    @Override


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_myinfo_sure:

                listener.onDatePick(Integer.valueOf(selectYear), Integer.valueOf(selectMonth),
                        Integer.valueOf(selectDay));
                break;
            case R.id.btn_myinfo_cancel:

                break;
        }
        window.dismiss();
    }

    private class CalendarTextAdapter extends AbstractWheelTextAdapter {
        ArrayList<String> list;

        protected CalendarTextAdapter(Context context, ArrayList<String> list, int currentItem,
                                      int maxsize, int minsize) {
            super(context, R.layout.item_birth_year, NO_RESOURCE, currentItem, maxsize, minsize);
            this.list = list;
            setItemTextResource(R.id.tempValue);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index) + "";
        }
    }

    /**
     * 设置字体大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setTextviewSize(String curriteItemText, CalendarTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textvew.setTextSize(maxTextSize);
            } else {
                textvew.setTextSize(minTextSize);
            }
        }
    }


    /**
     * 设置年月日
     *
     * @param year
     * @param month
     * @param day
     */
    public void setDate(int year, int month, int day) {
        selectYear = year + "";
        selectMonth = month + "";
        selectDay = day + "";
        this.currentYear = year;
        this.currentMonth = month;
        this.currentDay = day;
        if (year == CalendarTools.getCurrentYear()) {
            this.month = CalendarTools.getCurrentMonth();
        } else {
            this.month = 12;
        }
        calDays(year, month);
    }

    /**
     * 设置年份
     *
     * @param year
     */
    public int setYear(int year) {

        if (year != CalendarTools.getCurrentYear()) {
            this.month = 12;
        } else {
            this.month = CalendarTools.getCurrentMonth();
        }
        if (isBeforeToday) {// 取今年之前的日期
            int yearIndex = 0;
            for (int i = CalendarTools.getCurrentYear(); i > 2000; i--) {
                if (i == year) {
                    return yearIndex;
                }
                yearIndex++;
            }
            return yearIndex;
        } else {
            int yearIndex = afterToYearNum - 1;
            for (int i = CalendarTools.getCurrentYear(); i < CalendarTools.getCurrentYear() +
                    afterToYearNum;
                 i++) {
                if (i == year) {
                    return yearIndex;
                }
                yearIndex--;
            }
            return yearIndex;
        }
    }

    /**
     * 设置月份
     *
     * @param month
     * @return
     */
    public int setMonth(int month) {
        int monthIndex = 0;
        calDays(currentYear, month);
        for (int i = 1; i < this.month; i++) {
            if (month == i) {
                return monthIndex;
            } else {
                monthIndex++;
            }
        }
        return monthIndex;
    }

    /**
     * 计算每月多少天
     *
     * @param month
     * @param year
     */
    public void calDays(int year, int month) {
        boolean leayyear = false;
        if (year % 4 == 0 && year % 100 != 0) {
            leayyear = true;
        } else {
            leayyear = false;
        }
        for (int i = 1; i <= 12; i++) {
            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    this.day = 31;
                    break;
                case 2:
                    if (leayyear) {
                        this.day = 29;
                    } else {
                        this.day = 28;
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    this.day = 30;
                    break;
            }
        }
        if (year == CalendarTools.getCurrentYear() && month == CalendarTools.getCurrentMonth()) {
            this.day = CalendarTools.getCurrentDay();
        }
    }


}
