package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.haibin.calendarview.CalendarView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CalendarKeAdapter;
import cn.com.zwwl.bayuwen.api.CalendarEventListApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.model.CalendarEventModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.fm.AlbumModel;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.view.CalendarMonthSelectView;

/**
 * 日历页面
 */
public class CalendarActivity extends BaseActivity implements CalendarView.OnMonthChangeListener,
        CalendarView.OnDateSelectedListener {
    private RecyclerView keRecyclerView;// 课程卡片

    private CalendarMonthSelectView calendarMonthSelectView;// 月份选择器

    private CalendarKeAdapter calendarKeAdapter;
    private CalendarView calendarView;
    private List<Calendar> months = new ArrayList<>();// 月份列表
    // key:date value:日历事件列表
    private Map<String, List<CalendarEventModel>> mapDatas = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        initView();
        initData();
    }

    private void initView() {
        keRecyclerView = findViewById(R.id.recyclerView);
        calendarMonthSelectView = findViewById(R.id.month_layout);
        months.addAll(CalendarTools.initCalendarForMonthView());
        calendarMonthSelectView.setData(months);

        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        keRecyclerView.setLayoutManager(layoutmanager);
        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnMonthChangeListener(this);
        calendarView.setRange(CalendarTools.getMinYear(), 1, CalendarTools.getMaxYear(), 12);

        setScheme(CalendarTools.getCurrentYear(), CalendarTools.getCurrentMonth());
        handler.sendEmptyMessageDelayed(0, 200);
        findViewById(R.id.calendar_back).setOnClickListener(this);
        findViewById(R.id.calendar_add).setOnClickListener(this);
    }

    /**
     * 设置日历事件的下标
     */
    private void setScheme(int year, int month) {
        List<com.haibin.calendarview.Calendar> schemes = new ArrayList<>();

        schemes.add(getSchemeCalendar(year, month, 3, 0xFFdcaa40, "1"));
        schemes.add(getSchemeCalendar(year, month, 6, 0xFFdcaa40, "2"));
        schemes.add(getSchemeCalendar(year, month, 9, 0xFFdcaa40, "4"));
        schemes.add(getSchemeCalendar(year, month, 13, 0xFFdcaa40, "22"));
        schemes.add(getSchemeCalendar(year, month, 14, 0xFFdcaa40, "5"));
        schemes.add(getSchemeCalendar(year, month, 15, 0xFFdcaa40, "8"));
        schemes.add(getSchemeCalendar(year, month, 18, 0xFFdcaa40, "20"));
        schemes.add(getSchemeCalendar(year, month, 25, 0xFFdcaa40, "1"));
        calendarView.setSchemeDate(schemes);
    }

    private com.haibin.calendarview.Calendar getSchemeCalendar(int year, int month, int day, int
            color, String text) {
        com.haibin.calendarview.Calendar calendar = new com.haibin.calendarview.Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:// 定位到当前日期
                    calendarView.scrollToCurrent(true);
                    break;
            }

        }
    };

    /**
     * 月份选择器更改状态
     *
     * @param calendar
     */
    public void doMonthSelect(Calendar calendar) {
        calendarView.scrollToCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) +
                1, 1);
        setScheme(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
    }

    @Override
    protected void initData() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startDay = sdf.format(CalendarTools.getBeginDayofMonth(months.get(0)));
        String endDay = sdf.format(CalendarTools.getEndDayofMonth(months.get(months.size() - 1)));
        new CalendarEventListApi(mContext, startDay, endDay, new CalendarEventListApi
                .FetchCalendarEventMapListener() {
            @Override
            public void setData(Map<String, List<CalendarEventModel>> maps) {
                if (maps != null && !maps.isEmpty()) {
                    mapDatas = maps;
                }
            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.calendar_back:
                finish();
                break;
            case R.id.calendar_add:
                startActivity(new Intent(mContext, AddCalendarActivity.class));
                break;
        }
    }

    @Override
    public void onMonthChange(int year, int month) {
        Calendar ca = Calendar.getInstance();
        ca.set(year, month - 1, 1);
        calendarMonthSelectView.setSelectedItemForChild(ca);
    }

    @Override
    public void onDateSelected(com.haibin.calendarview.Calendar calendar, boolean isClick) {
        String dataString = calendar.getYear() + "-" + calendar.getMonth() + "-" + calendar
                .getDay();
        Log.e("sssssss", dataString);
        List<CalendarEventModel> calendarEventModels = new ArrayList<>();
        if (mapDatas.containsKey(dataString)) {
            calendarEventModels.addAll(mapDatas.get(dataString));
        }
        calendarKeAdapter = new CalendarKeAdapter(mContext, calendarEventModels);
        keRecyclerView.setAdapter(calendarKeAdapter);
    }
}
