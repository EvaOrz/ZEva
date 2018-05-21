package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import cn.com.zwwl.bayuwen.R;

/**
 * 设置页面
 */
public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        findViewById(R.id.setting_back).setOnClickListener(this);
        findViewById(R.id.setting_option1).setOnClickListener(this);
        findViewById(R.id.setting_option2).setOnClickListener(this);
        findViewById(R.id.setting_option3).setOnClickListener(this);
        findViewById(R.id.setting_option4).setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.setting_back:
                finish();
                break;
            case R.id.setting_option1:
                startActivity(new Intent(mContext, SettingOption1Activity.class));
                break;
            case R.id.setting_option2:
                break;
            case R.id.setting_option3:
                break;
            case R.id.setting_option4:
                break;


        }
    }

    @Override
    protected void initData() {

    }
}
