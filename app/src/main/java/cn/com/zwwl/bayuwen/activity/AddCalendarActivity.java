package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;


import com.maning.calendarlibrary.listeners.OnCalendarRangeChooseListener;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.view.CalendarOptionPopWindow;

/**
 * 添加课程页面
 */
public class AddCalendarActivity extends BaseActivity {
    private TextView shangkeTv, xiangkeTv, jigouTv, kaiTv1, kaiTv2, jieTv1, jieTv2, weekCountTv;

    private EditText nameEv, cishuEv, addressEv, teacherEv, codeEv;

    private String shangTime, xiaTime;

    private Date startDate, endDate;// 课程开始日期和结束日期
    private List<Date> periods = new ArrayList<>();// 课程日期集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_calendar);
        initView();
    }

    private void initView() {
        findViewById(R.id.add_calendar_back).setOnClickListener(this);
        findViewById(R.id.add_calendar_save).setOnClickListener(this);
        findViewById(R.id.add_zengshan).setOnClickListener(this);
        findViewById(R.id.add_shangkeshijian).setOnClickListener(this);
        findViewById(R.id.add_xiakeshijian).setOnClickListener(this);
        findViewById(R.id.add_kechengjigou).setOnClickListener(this);
        findViewById(R.id.add_period).setOnClickListener(this);

        shangkeTv = findViewById(R.id.content_shangkeshijian);
        xiangkeTv = findViewById(R.id.content_xiakeshijian);
        jigouTv = findViewById(R.id.content_jigou);
        kaiTv1 = findViewById(R.id.kaike_date);
        kaiTv2 = findViewById(R.id.kaike_week);
        jieTv1 = findViewById(R.id.jieke_date);
        jieTv2 = findViewById(R.id.jieke_week);
        weekCountTv = findViewById(R.id.week_count);

        nameEv = findViewById(R.id.content_name);
        cishuEv = findViewById(R.id.content_cishu);
        addressEv = findViewById(R.id.content_address);
        teacherEv = findViewById(R.id.content_teacher);
        codeEv = findViewById(R.id.content_code);
    }

    @Override
    protected void initData() {

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:// 更新上课时间
                    shangkeTv.setText(shangTime);
                    break;
                case 1:// 更新下课时间
                    xiangkeTv.setText(xiaTime);
                    break;
                case 2:// 更新课程机构
                    jigouTv.setText((String) msg.obj);
                    break;

                case 3:// 更新课程日期
                    DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
                    kaiTv1.setText(df.format(startDate));
                    kaiTv2.setText(new SimpleDateFormat("EEE").format(startDate));
                    jieTv1.setText(df.format(endDate));
                    jieTv2.setText(new SimpleDateFormat("EEE").format(endDate));
                    weekCountTv.setText(CalendarTools.countTwoDayWeek(startDate, endDate) + "周");
                    new CalendarOptionPopWindow(mContext, new CalendarOptionPopWindow.MyWeekChooseListener() {
                        @Override
                        public void onWeekChoose(List<Integer> weeks) {
                            List<Date> pickDays = new ArrayList<>();
                            for (Date date : periods) {
                                Calendar c = Calendar.getInstance();
                                c.setTime(date);
                                for (int i : weeks)
                                    if (c.get(Calendar.DAY_OF_WEEK) == i) {
                                        pickDays.add(date);
                                    }
                            }
                            periods.clear();
                            periods.addAll(pickDays);
                        }
                    }, 5);
                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.add_calendar_save:// 保存
                break;
            case R.id.add_calendar_back:
                finish();
                break;

            case R.id.add_zengshan:// 增删课程
                Intent intent = new Intent(mContext, SelectCalendarActivity.class);
                intent.putExtra("SelectCalendarActivity_data", (Serializable) periods);
                startActivityForResult(intent, 100);
                break;
            case R.id.add_xiakeshijian:// 下课时间
                hideJianpan();
                new CalendarOptionPopWindow(mContext, new CalendarOptionPopWindow.MyTimePickListener() {
                    @Override
                    public void onTimePick(int hour, int minute) {
                        xiaTime = hour + ":" + minute;
                        handler.sendEmptyMessage(1);
                    }
                }, 2);
                break;
            case R.id.add_shangkeshijian:// 上课时间
                hideJianpan();
                new CalendarOptionPopWindow(mContext, new CalendarOptionPopWindow.MyTimePickListener() {
                    @Override
                    public void onTimePick(int hour, int minute) {
                        shangTime = hour + ":" + minute;
                        handler.sendEmptyMessage(0);
                    }
                }, 1);
                break;

            case R.id.add_kechengjigou:// 课程机构
                hideJianpan();
                new CalendarOptionPopWindow(mContext, new CalendarOptionPopWindow.MyJigouChooseListener() {
                    @Override
                    public void onJigouChoose(String name) {
                        Message ms = new Message();
                        ms.what = 2;
                        ms.obj = name;
                        handler.sendMessage(ms);
                    }
                }, 3);
                break;

            case R.id.add_period:// 课程时间段选择器
                hideJianpan();
                new CalendarOptionPopWindow(mContext, new CalendarOptionPopWindow.MyPeriodPickListener() {

                    @Override
                    public void onPeriodPick(Date start, Date end) {
                        startDate = start;
                        endDate = end;
                        periods.clear();
                        periods.addAll(CalendarTools.betweenDays(startDate, endDate));
                        handler.sendEmptyMessage(3);
                    }
                }, 4);
                break;
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
