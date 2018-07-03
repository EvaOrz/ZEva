package cn.com.zwwl.bayuwen.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.HashMap;

import butterknife.ButterKnife;
import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;


/**
 * 带标题的基类activity
 * Created by zhumangmang at 2018/5/30 11:28
 */

public abstract class BasicActivityWithTitle extends AppCompatActivity {
    protected Activity mActivity;
    protected Context mContext;
    public MyApplication mApplication;
    protected Resources res;
    protected HashMap<String, String> map;
    //代码处理类
    private IActivityImpl mViewCode;
    //是否右上角按钮
    private int mMenuCode = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initView();
        initData();
        setListener();
    }

    void init() {
        mViewCode = new IActivityImpl(this);
        setContentView(setContentView());
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //默认显示返回箭头
        setDisplayHomeAsUpEnabled(true);
        map = new HashMap<>();
        ButterKnife.bind(this);
        mActivity = this;
        mContext = getApplicationContext();
        mApplication = (MyApplication) getApplication();
        res = getResources();
    }

    /**
     * 设置rootView是否可以滚动，意思是如果期望外层View是个ScrollView
     * 就返回true,否则就返回false
     *
     * @return true & false
     */
    public boolean setParentScrollable() {
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base, menu);
        mViewCode.showMenu(mMenuCode);
        return true;
    }

    /**
     * 设置显示自定居中的title
     *
     * @param showCustomTitleEnabled true 为显示   false为不显示
     */
    public void setDisplayShowCustomTitleEnabled(boolean showCustomTitleEnabled) {
        mViewCode.setDisplayShowCustomTitleEnabled(showCustomTitleEnabled);
    }


    /**
     * 显示返回箭头
     *
     * @param showHomeEnabled true 为显示   false为不显示
     */
    public void setDisplayHomeAsUpEnabled(boolean showHomeEnabled) {
        mViewCode.setDisplayHomeAsUpEnabled(showHomeEnabled);
    }


    /**
     * 显示默认title居左
     *
     * @param showTitleEnabled true 为显示   false为不显示
     */
    public void setDisplayShowTitleEnabled(boolean showTitleEnabled) {
        mViewCode.setDisplayShowTitleEnabled(showTitleEnabled);
    }


    /**
     * 设置主体布局1
     *
     * @param layoutResID 主体布局资源id
     */
    @Override
    public void setContentView(@LayoutRes final int layoutResID) {
        mViewCode.setContentView(layoutResID);
    }


    /**
     * 设置主体布局2
     *
     * @param view 布局控件
     */
    @Override
    public void setContentView(View view) {
        mViewCode.setContentView(view);
    }

    public void setUpIndicator(int resId) {
        mViewCode.setUpIndicator(resId);
    }

    public void showMenu(int menuCode) {
        this.mMenuCode = menuCode;
    }

    /**
     * 设置自定义title
     *
     * @param title 自定title
     */
    public void setCustomTitle(CharSequence title) {
        mViewCode.setCustomTitle(title);
    }

    /**
     * 设置自定义title
     *
     * @param title 自定title
     */
    public void setCustomTitle(int title) {
        mViewCode.setCustomTitle(title);
    }

    @Override
    protected void onResume() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onResume();
    }

    /**
     * 隐藏软键盘
     */
    public void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context
                .INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    protected abstract int setContentView();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void setListener();

    public void close() {
    }

    public void onMenuClick(int menuCode) {

    }

    public abstract void onClick(View view);
}

