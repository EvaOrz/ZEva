package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.fragment.NotifyFragment;
import cn.com.zwwl.bayuwen.fragment.TopicFragment;
import cn.com.zwwl.bayuwen.util.UmengLogUtil;

/**
 * 通知/话题页面
 */
public class MessageActivity extends BaseActivity {

    private RadioButton notification, topic;
    private FrameLayout fg_view;
    private Fragment[] mFragments;
    private ImageView message_add;
    private int mIndex;
    private Intent intent;
    private View line1, line2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        UmengLogUtil.logMessageClick(mContext);
        setContentView(R.layout.activity_message);
        intent = getIntent();

        initView();
    }

    private void initView() {
        notification = findViewById(R.id.message_bt1);
        topic = findViewById(R.id.message_bt2);
        fg_view = findViewById(R.id.fg_view);
        line1 = findViewById(R.id.radio_line1);
        line2 = findViewById(R.id.radio_line2);
        message_add = findViewById(R.id.message_add);
        message_add.setOnClickListener(this);
        initFragment();
        notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setIndexSelected(0);
                }

            }
        });
        topic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setIndexSelected(1);
                }
            }
        });

        findViewById(R.id.message_back).setOnClickListener(this);
    }

    private void initFragment() {
        //通知
        NotifyFragment notifyFragment = new NotifyFragment();
        TopicFragment topicFragment = new TopicFragment();

        //添加到数组
        mFragments = new Fragment[]{notifyFragment, topicFragment};
        //开启事务
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (intent.getStringExtra("ass") == null) {
//                appointment_Id.setEnabled(false);
            ft.add(R.id.fg_view, notifyFragment).commit();
            setIndexSelected(0);
            notification.setChecked(true);
        } else {
//                appointment_Id.setEnabled(false);
            ft.add(R.id.fg_view, topicFragment).commit();
            setIndexSelected(0);
            topic.setChecked(true);
        }


    }

    private void setIndexSelected(int index) {

        if (mIndex == index) {
            return;
        }
        if (index == 0) {
            message_add.setVisibility(View.GONE);
            line1.setVisibility(View.VISIBLE);
            line2.setVisibility(View.INVISIBLE);
        }else {
            message_add.setVisibility(View.VISIBLE);
            line1.setVisibility(View.INVISIBLE);
            line2.setVisibility(View.VISIBLE);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        //隐藏
        ft.hide(mFragments[mIndex]);
        //判断是否添加
        if (!mFragments[index].isAdded()) {
            ft.add(R.id.fg_view, mFragments[index]).show(mFragments[index]);
        } else {
            ft.show(mFragments[index]);
        }

        ft.commit();
        //再次赋值
        mIndex = index;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.message_back:
                finish();
                break;
//            case R.id.message_bt1:
//                notification.setBackgroundResource(R.drawable.gray_dark_circle);
//                message_add.setVisibility(View.INVISIBLE);
//                setIndexSelected(0);
//                topic.setBackground(null);
//                break;
//            case R.id.message_bt2:
//                topic.setBackgroundResource(R.drawable.gray_dark_circle);
//                message_add.setVisibility(View.VISIBLE);
//                setIndexSelected(1);
//                notification.setBackground(null);
//                break;
            case R.id.message_add:
                Intent intent2 = new Intent(mContext, CreateTopicActivity.class);
                startActivity(intent2);
                break;

        }
    }

    @Override
    protected void initData() {

    }


}
