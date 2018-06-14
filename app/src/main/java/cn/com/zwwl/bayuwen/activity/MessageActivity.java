package cn.com.zwwl.bayuwen.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.fragment.NotifyFragment;
import cn.com.zwwl.bayuwen.fragment.TopicFragment;

/**
 * 通知/话题页面
 */
public class MessageActivity extends BaseActivity {

    private RadioButton notification, topic;
    private FrameLayout fg_view;
    private Fragment[] mFragments;
    private int mIndex;


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
        fg_view=  findViewById(R.id.fg_view);
        initFragment();
        notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    notification.setBackgroundResource(R.drawable.gray_dark_circle);
                  setIndexSelected(0);
                }
                else {
                    notification.setBackground(null);
                }

            }
        });
        topic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    topic.setBackgroundResource(R.drawable.gray_dark_circle);
                    setIndexSelected(1);
                }
                else topic.setBackground(null);
            }
        });
       notification.setChecked(true);
        findViewById(R.id.message_back).setOnClickListener(this);
    }
    private void initFragment() {
        //通知
        NotifyFragment notifyFragment = new NotifyFragment();
        TopicFragment topicFragment= new TopicFragment();

        //添加到数组
        mFragments = new Fragment[]{notifyFragment, topicFragment};
        //开启事务
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
          notification.setBackgroundResource(R.drawable.gray_dark_circle);
           topic.setBackground(null);
//                appointment_Id.setEnabled(false);
                ft.add(R.id.fg_view, notifyFragment).commit();
                setIndexSelected(0);


    }

    private void setIndexSelected(int index) {

        if (mIndex == index) {
            return;
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
            case R.id.message_bt1:
                notification.setBackgroundResource(R.drawable.gray_dark_circle);
                setIndexSelected(0);
                topic.setBackground(null);
                break;
            case R.id.message_bt2:
                topic.setBackgroundResource(R.drawable.gray_dark_circle);
                setIndexSelected(1);
                notification.setBackground(null);
                break;

        }
    }

    @Override
    protected void initData() {

    }


}
