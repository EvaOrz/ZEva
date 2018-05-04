package cn.com.zwwl.bayuwen.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.view.CommonWebView;

/**
 * 内部浏览器
 */
public class WebActivity extends BaseActivity {
    private CommonWebView commonWebView;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra("WebActivity_data");
        setContentView(R.layout.activity_web);
        initView();
        initErrorLayout();
        initData();
    }

    @Override
    protected void initData() {
        if (!Tools.checkNetWork(this)) {
            showError(R.mipmap.blank_no_wifi, R.string.no_wifi);
        }
    }

    private void initView() {
        findViewById(R.id.web_back).setOnClickListener(this);
        commonWebView = findViewById(R.id.web_webview);
        if (!TextUtils.isEmpty(url)) {
            commonWebView.loadUrl(url);
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.web_back:
                finish();
                break;
        }
    }
}
