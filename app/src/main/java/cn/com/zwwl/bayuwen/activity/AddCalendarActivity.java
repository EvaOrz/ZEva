package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.view.CalendarOptionPopWindow;

/**
 * 添加课程页面
 */
public class AddCalendarActivity extends BaseActivity {
    private TextView shangkeTv, xiangkeTv, jigouTv;
    private EditText nameEv, cishuEv, addressEv, teacherEv, codeEv;

    private String shangTime, xiaTime;

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

        shangkeTv = findViewById(R.id.content_shangkeshijian);
        xiangkeTv = findViewById(R.id.content_xiakeshijian);
        jigouTv = findViewById(R.id.content_jigou);

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
                case 1:
                    xiangkeTv.setText(xiaTime);
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
                startActivityForResult(new Intent(mContext, SelectCalendarActivity.class), 100);
                break;
            case R.id.add_xiakeshijian:// 下课时间
                new CalendarOptionPopWindow(mContext, new CalendarOptionPopWindow.MyTimePickListener() {
                    @Override
                    public void onTimePick(int hour, int minute) {
                        xiaTime = hour + ":" + minute;
                        handler.sendEmptyMessage(1);
                    }
                }, 2);
                break;
            case R.id.add_shangkeshijian:// 上课时间
                new CalendarOptionPopWindow(mContext, new CalendarOptionPopWindow.MyTimePickListener() {
                    @Override
                    public void onTimePick(int hour, int minute) {
                        shangTime = hour + ":" + minute;
                        handler.sendEmptyMessage(0);
                    }
                }, 1);
                break;

            case R.id.add_kechengjigou:// 课程机构
                new CalendarOptionPopWindow(mContext, new CalendarOptionPopWindow.MyJigouChooseListener() {
                    @Override
                    public void onJigouChoose(String name) {

                    }
                }, 3);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
