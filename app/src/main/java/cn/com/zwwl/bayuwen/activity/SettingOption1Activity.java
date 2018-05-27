package cn.com.zwwl.bayuwen.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import cn.com.zwwl.bayuwen.R;

/**
 * 账号与安全页面
 */
public class SettingOption1Activity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_option1);
        initView();
    }

    private void initView() {
        findViewById(R.id.setting_1_back).setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.setting_1_back:
                finish();
                break;



        }
    }

    @Override
    protected void initData() {

    }
}
