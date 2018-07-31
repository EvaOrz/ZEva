package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.db.TempDataHelper;
import cn.com.zwwl.bayuwen.db.UserDataHelper;
import cn.com.zwwl.bayuwen.push.NewPushManager;

/**
 * 设置页面
 */
public class SettingActivity extends BaseActivity {

    private Switch aSwitch;

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
        findViewById(R.id.setting_logout).setOnClickListener(this);
        aSwitch = findViewById(R.id.push_switch);
        aSwitch.setChecked(TempDataHelper.getPushStatus(mContext));
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showToast("推送消息开启");
                    NewPushManager.getInstance(mContext).closePush(mContext);
                } else {
                    showToast("推送消息关闭");
                    NewPushManager.getInstance(mContext).onresume(mContext);
                }
            }
        });
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
                startActivity(new Intent(mContext, AboutActivity.class));
                break;
            case R.id.setting_logout:
                UserDataHelper.clearLoginInfo(mContext);
                TempDataHelper.clearLoginInfo(mContext);
                finish();
                break;

        }
    }

    @Override
    protected void initData() {

    }
}
