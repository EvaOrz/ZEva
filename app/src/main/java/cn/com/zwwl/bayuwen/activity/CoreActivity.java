package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import cn.com.zwwl.bayuwen.R;

/**
 *
 */
public class CoreActivity extends BaseActivity {
    private RadioButton tabButton1, tabButton2, tabButton3, tabButton4;
    private LinearLayout container;
    private View view1, view2, view3, view4;// 首页的四个view

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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        view1 = new View(mContext);
        view2 = new View(mContext);
        view3 = new View(mContext);
        view4 = new View(mContext);

        avatar = findViewById(R.id.main_avatar);

        container = findViewById(R.id.main_container);
        tabButton1 = findViewById(R.id.bottom_nav_1);
        tabButton2 = findViewById(R.id.bottom_nav_2);
        tabButton3 = findViewById(R.id.bottom_nav_3);
        tabButton4 = findViewById(R.id.bottom_nav_4);
        tabButton1.setOnClickListener(this);
        tabButton2.setOnClickListener(this);
        tabButton3.setOnClickListener(this);
        tabButton4.setOnClickListener(this);
        avatar.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.bottom_nav_1:
                changTab(1);
                break;
            case R.id.bottom_nav_2:
                changTab(2);
                break;
            case R.id.bottom_nav_3:
                changTab(3);
                break;
            case R.id.bottom_nav_4:
                changTab(4);
                break;

            case R.id.main_avatar:
                startActivity(new Intent(mContext, LoginActivity.class));
                break;
        }
    }

    private void changTab(int i) {
        container.removeAllViewsInLayout();
        if (i == 1) {
            container.addView(view1, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        } else if (i == 2) {
            container.addView(view2, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        } else if (i == 3) {
            container.addView(view3, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        } else if (i == 4) {
            container.addView(view4, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
