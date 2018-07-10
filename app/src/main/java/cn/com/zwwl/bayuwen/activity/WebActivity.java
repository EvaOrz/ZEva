package cn.com.zwwl.bayuwen.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.widget.CommonWebView;

/**
 * 内部浏览器
 */
public class WebActivity extends BaseActivity {
    private CommonWebView commonWebView;
    private String url;
    private TextView title;
    private String titleString;
    private ImageView id_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra("WebActivity_data");
        setContentView(R.layout.activity_web);
        titleString = getIntent().getStringExtra("WebActivity_title");
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
        title = findViewById(R.id.title_name);
        if (!TextUtils.isEmpty(titleString))
            title.setText(titleString);
        findViewById(R.id.id_back).setOnClickListener(this);
        commonWebView = findViewById(R.id.web_webview);
        if (!TextUtils.isEmpty(url)) {
            commonWebView.loadUrl(url);
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.id_back:
                finish();
                break;

        }
    }
}
