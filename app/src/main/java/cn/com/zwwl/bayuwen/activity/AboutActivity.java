package cn.com.zwwl.bayuwen.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import cn.com.zwwl.bayuwen.R;
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
        version = findViewById(R.id.about_version);
        version.setText(getResources().getString(R.string.app_name) + " " + Tools
                .getAppVersionName(mContext));
        findViewById(R.id.about_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.about_back:
                finish();
                break;
        }
    }

    @Override
    protected void initData() {

    }
}
