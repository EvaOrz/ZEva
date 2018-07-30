package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.util.AppValue;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.util.Tools;

/**
 * 关于页面
 */
public class AboutActivity extends BaseActivity {

    private TextView version;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initView();
    }

    private void initView() {
        findViewById(R.id.about_option1).setOnClickListener(this);
        findViewById(R.id.about_option2).setOnClickListener(this);
        version = findViewById(R.id.about_version);
        version.setText(getResources().getString(R.string.app_name) + " " + Tools
                .getAppVersionName(mContext));
//        version.setText(getResources().getString(R.string.app_name) + " " + Tools
//                .getAppVersionName(mContext) + " (" + CalendarTools.format(System
//                .currentTimeMillis() / 1000, "MM-dd HH:mm") + ")");
        findViewById(R.id.about_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.about_back:
                finish();
                break;
            case R.id.about_option1:
                Intent i = new Intent(mContext, WebActivity.class);
                i.putExtra("WebActivity_data", AppValue.aboutUrl);
                startActivity(i);
                break;
            case R.id.about_option2:
                startActivity(new Intent(mContext, FeedBackActivity.class));
                break;
        }
    }

    @Override
    protected void initData() {

    }
}
