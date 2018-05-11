package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.view.CalendarOptionPopWindow;

/**
 * 添加课程页面
 */
public class AddCalendarActivity extends BaseActivity {
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
    }

    @Override
    protected void initData() {

    }

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
                new CalendarOptionPopWindow(mContext, 2);
                break;
            case R.id.add_shangkeshijian:// 上课时间
                new CalendarOptionPopWindow(mContext, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
