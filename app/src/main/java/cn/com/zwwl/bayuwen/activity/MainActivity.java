package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.support.v4.app.FragmentTransaction;


import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.fragment.MainFrag1;
import cn.com.zwwl.bayuwen.fragment.MainFrag2;
import cn.com.zwwl.bayuwen.fragment.MainFrag3;
import cn.com.zwwl.bayuwen.fragment.MainFrag4;

/**
 *
 */
public class MainActivity extends BaseActivity {
    private RadioButton tabButton1, tabButton2, tabButton3, tabButton4;
    private LinearLayout mainView;
    private DrawerLayout drawer;// 抽屉

    private Fragment mTempFragment;
    private MainFrag1 mainFrag1;
    private MainFrag2 mainFrag2;
    private MainFrag3 mainFrag3;
    private MainFrag4 mainFrag4;


    private ImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core);
        mContext = this;
        initView();
        initData();


    }

    private void initView() {
        mainView = findViewById(R.id.main_view);
        drawer = findViewById(R.id.drawer_layout);

        DrawerLayout.DrawerListener listen = new DrawerLayout.DrawerListener() {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                mainView.setX(slideOffset * drawerView.getMeasuredWidth());
                mainView.invalidate();
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        };
        drawer.setDrawerListener(listen);


        mainFrag1 = MainFrag1.newInstance("hello world");
        mainFrag2 = MainFrag2.newInstance("hello world");
        mainFrag3 = MainFrag3.newInstance("hello world");
        mainFrag4 = MainFrag4.newInstance("hello world");
        switchFragment(mainFrag1);

        avatar = findViewById(R.id.main_avatar);

        tabButton1 = findViewById(R.id.bottom_nav_1);
        tabButton2 = findViewById(R.id.bottom_nav_2);
        tabButton3 = findViewById(R.id.bottom_nav_3);
        tabButton4 = findViewById(R.id.bottom_nav_4);
        findViewById(R.id.toolbar_left).setOnClickListener(this);
        tabButton1.setOnClickListener(this);
        tabButton2.setOnClickListener(this);
        tabButton3.setOnClickListener(this);
        tabButton4.setOnClickListener(this);
        avatar.setOnClickListener(this);
    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


        }
    };

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.bottom_nav_1:
                switchFragment(mainFrag1);
                break;
            case R.id.bottom_nav_2:
                switchFragment(mainFrag2);
                break;
            case R.id.bottom_nav_3:
                switchFragment(mainFrag3);
                break;
            case R.id.bottom_nav_4:
                switchFragment(mainFrag4);
                break;

            case R.id.main_avatar:
                mContext.startActivity(new Intent(mContext, LoginActivity.class));
                break;
            case R.id.toolbar_left:// 打开抽屉
                if (!drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.openDrawer(GravityCompat.START);
                }
                break;
        }
    }

    /**
     * 切换四个tab
     * @param fragment
     */
    private void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mTempFragment == null) {
            transaction
                    .add(R.id.main_container, fragment).commit();
            mTempFragment = fragment;
            return;
        }

        if (fragment != mTempFragment) {
            if (!fragment.isAdded()) {
                transaction.hide(mTempFragment)
                        .add(R.id.main_container, fragment).commit();
            } else {
                transaction.hide(mTempFragment)
                        .show(fragment).commit();
            }
            mTempFragment = fragment;
        }
    }


    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
