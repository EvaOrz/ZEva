package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.haibin.calendarview.CalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CalendarKeAdapter;
import cn.com.zwwl.bayuwen.model.AlbumModel;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.view.CalendarMonthSelectView;

/**
 * 日历页面
 */
public class CalendarActivity extends BaseActivity implements CalendarView.OnMonthChangeListener, CalendarView.OnDateSelectedListener {
    private RecyclerView keRecyclerView;// 课程卡片

    private CalendarMonthSelectView calendarMonthSelectView;// 月份选择器

    private CalendarKeAdapter calendarKeAdapter;
    private CalendarView calendarView;
    private List<Calendar> months = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        initView();
    }

    private void initView() {
        keRecyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        keRecyclerView.setLayoutManager(layoutmanager);
        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnMonthChangeListener(this);
        calendarKeAdapter = new CalendarKeAdapter(new ArrayList<AlbumModel>());
        keRecyclerView.setAdapter(calendarKeAdapter);

        calendarMonthSelectView = findViewById(R.id.month_layout);
        months.addAll(CalendarTools.initCalendarForMonthView());
        calendarMonthSelectView.setData(months);

        findViewById(R.id.calendar_back).setOnClickListener(this);
        findViewById(R.id.calendar_add).setOnClickListener(this);
    }


    /**
     * 月份选择器更改状态
     *
     * @param calendar
     */
    public void doMonthSelect(Calendar calendar) {
        calendarView.scrollToCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, 1);
    }

    @Override
    protected void initData() {

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

    }
}
