package cn.com.zwwl.bayuwen.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import cn.com.zwwl.bayuwen.R;

/**
 * 通知/话题页面
 */
public class MessageActivity extends BaseActivity {

    private RadioButton notification, topic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initView();
    }

    private void initView() {
        notification = findViewById(R.id.message_bt1);
        topic = findViewById(R.id.message_bt2);
        notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    notification.setBackgroundResource(R.drawable.gray_dark_circle);
                else notification.setBackground(null);

            }
        });
        topic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    topic.setBackgroundResource(R.drawable.gray_dark_circle);
                else topic.setBackground(null);
            }
        });
        notification.setChecked(true);
        findViewById(R.id.message_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.message_back:
                finish();
                break;

        }
    }

    @Override
    protected void initData() {

    }
}
