package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.util.ShareTools;

/**
 * 开团页面
 */
public class TuanKaiActivity extends BaseActivity {

    private String tuanCode = "";
    private TextView codeTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuan_kai);
        tuanCode = getIntent().getStringExtra("TuanKaiActivity_data");
        initView();
    }

    private void initView() {
        codeTv = findViewById(R.id.tuan_kai_code);
        codeTv.setText("您的拼团码为：" + tuanCode);

        findViewById(R.id.tuan_kai_back).setOnClickListener(this);
        findViewById(R.id.tuan_kai_copy).setOnClickListener(this);
        findViewById(R.id.tuan_kai_share).setOnClickListener(this);
        findViewById(R.id.tuan_kai_pay).setOnClickListener(this);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tuan_kai_back:
                finish();
                break;
            case R.id.tuan_kai_copy:// 复制拼团码
                ClipboardManager cm = (ClipboardManager) getSystemService(Context
                        .CLIPBOARD_SERVICE);
                cm.setText(tuanCode);
                showToast("已复制到剪切板");
                break;
            case R.id.tuan_kai_share:// 分享拼团
                ShareTools.doShareWeb(this, "", "", "", "http://baidu.com");
                break;
            case R.id.tuan_kai_pay:
                startActivity(new Intent(mContext, TuanPayActivity.class));
                break;
        }

    }

    @Override
    protected void initData() {

    }


}