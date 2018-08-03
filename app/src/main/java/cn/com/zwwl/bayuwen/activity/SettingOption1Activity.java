package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.db.UserDataHelper;
import cn.com.zwwl.bayuwen.model.UserModel;

/**
 * 账号与安全页面
 */
public class SettingOption1Activity extends BaseActivity {

    private TextView codeTv, phoneTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_option1);
        initView();
    }

    private void initView() {
        findViewById(R.id.setting_1_back).setOnClickListener(this);
        findViewById(R.id.setting_pwd).setOnClickListener(this);
        codeTv = findViewById(R.id.setting_1_code);
        phoneTv = findViewById(R.id.setting_1_phone);
        UserModel userModel = UserDataHelper.getUserLoginInfo(this);
        codeTv.setText(userModel.getSignCode());
        phoneTv.setText(userModel.getTel());


    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.setting_1_back:
                finish();
                break;
            case R.id.setting_pwd:
                startActivity(new Intent(mContext, ForgetPwdActivity.class));
                break;

        }
    }

    @Override
    protected void initData() {

    }
}
