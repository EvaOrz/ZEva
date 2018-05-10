package cn.com.zwwl.bayuwen.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CalendarKeAdapter;
import cn.com.zwwl.bayuwen.adapter.CalendarMonthAdapter;
import cn.com.zwwl.bayuwen.model.AlbumModel;

/**
 * 日历页面
 */
public class CalendarActivity extends BaseActivity {
    private RecyclerView keRecyclerView;// 课程卡片
    private RecyclerView monthRecyclerView;// 年月卡片
    private CalendarKeAdapter calendarKeAdapter;
    private CalendarMonthAdapter calendarMonthAdapter;

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
        calendarKeAdapter = new CalendarKeAdapter(new ArrayList<AlbumModel>());
        keRecyclerView.setAdapter(calendarKeAdapter);

        monthRecyclerView = findViewById(R.id.month_recyclerview);
        LinearLayoutManager ms = new LinearLayoutManager(this);
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);
        monthRecyclerView.setLayoutManager(ms);
        calendarMonthAdapter = new CalendarMonthAdapter(this);  //初始化适配器
        monthRecyclerView.setAdapter(calendarMonthAdapter);

        findViewById(R.id.calendar_back).setOnClickListener(this);
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
        }

    }
}
