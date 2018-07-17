package cn.com.zwwl.bayuwen.base;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

import butterknife.ButterKnife;
import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.activity.LoginActivity;
import cn.com.zwwl.bayuwen.db.UserDataHelper;
import cn.com.zwwl.bayuwen.util.MyActivityManager;


/**
 * Activity 基类
 * Create by zhumangmang at 2018/5/26 10:07
 */

public abstract class BasicActivity extends AppCompatActivity {

    protected Activity mActivity;
    protected Context mContext;
    public MyApplication mApplication;
    protected Resources res;
    protected HashMap<String, String> para;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyActivityManager.getInstance().addActivity(this);
        setContentView(setContentView());
        init();
        initView();
        initData();
        setListener();
    }

    public void init() {
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager
                .LayoutParams.FLAG_FULLSCREEN);
        para = new HashMap<>();
        ButterKnife.bind(this);
        mActivity = this;
        mContext = getApplicationContext();
        mApplication = (MyApplication) getApplication();
        res = getResources();
    }

    /**
     * 隐藏软键盘
     */
    public void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context
                .INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    @Override
    protected void onResume() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onResume();
        MobclickAgent.onResume(this); //统计时长
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    protected abstract int setContentView();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void setListener();

    public abstract void onClick(View view);
}
