package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import cn.com.zwwl.bayuwen.R;

/**
 * 团购付费结果页面
 */
public class TuanPayResultActivity extends BaseActivity {
    public static int PAY_SUCCESS = 1;
    public static int PAY_CANCLE = 2;
    public static int PAY_FAILD = 3;
    public static int PAY_UNKNOWN = 0;

    private int type;
    private String desc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuan_pay_result);
        type = getIntent().getIntExtra("TuanPayResultActivity_data", PAY_UNKNOWN);
        desc = getIntent().getStringExtra("TuanPayResultActivity_desc");
        initView();
    }

    private void initView() {
        findViewById(R.id.tuan_result_back).setOnClickListener(this);
        TextView status = findViewById(R.id.pay_status);
        status.setText(desc);
        if (type == PAY_FAILD) {
        } else if (type == PAY_SUCCESS) {
        } else if (type == PAY_CANCLE) {

        }

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
            case R.id.tuan_result_back:
                finish();
                break;

        }

    }

    @Override
    protected void initData() {

    }


}