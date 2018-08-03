package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.api.FeedbackApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 意见反馈页面
 */
public class FeedBackActivity extends BaseActivity {

    private EditText ev;
    private TextView tv;
    private int limitNum = 200;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initView();
    }

    private void initView() {
        findViewById(R.id.feed_back).setOnClickListener(this);
        findViewById(R.id.feed_commit).setOnClickListener(this);
        tv = findViewById(R.id.feed_left);
        ev = findViewById(R.id.feed_ev);
        ev.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                limitNum = 200 - s.length();
                handler.sendEmptyMessage(0);
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    tv.setText("还可以输入" + limitNum + "字");
                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.feed_back:
                finish();
                break;
            case R.id.feed_commit:
                String content = ev.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    showToast("请输入反馈内容");
                } else if (limitNum < 0) {
                    showToast("反馈内容过长");
                } else {
                    showLoadingDialog(true);
                    new FeedbackApi(mContext, content, 3, new FetchEntryListener() {
                        @Override
                        public void setData(Entry entry) {

                        }

                        @Override
                        public void setError(ErrorMsg error) {
                            showLoadingDialog(false);
                            if (error == null) {
                                showToast("提交成功");
                                finish();
                            } else {
                                showToast(error.getDesc());
                            }
                        }
                    });
                }

                break;
        }

    }

    @Override
    protected void initData() {

    }
}
