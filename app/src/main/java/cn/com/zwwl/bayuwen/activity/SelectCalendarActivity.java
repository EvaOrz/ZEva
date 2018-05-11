package cn.com.zwwl.bayuwen.activity;

import android.os.Bundle;
import android.view.View;

import cn.com.zwwl.bayuwen.R;

/**
 * 增减课程
 */
public class SelectCalendarActivity extends BaseActivity {
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
        }
    }
}
