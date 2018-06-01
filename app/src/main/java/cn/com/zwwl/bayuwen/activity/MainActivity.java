package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.GiftAdapter;
import cn.com.zwwl.bayuwen.api.ChildInfoApi;
import cn.com.zwwl.bayuwen.db.TempDataHelper;
import cn.com.zwwl.bayuwen.db.UserDataHelper;
import cn.com.zwwl.bayuwen.fragment.MainFrag1;
import cn.com.zwwl.bayuwen.fragment.MainFrag2;
import cn.com.zwwl.bayuwen.fragment.MainFrag3;
import cn.com.zwwl.bayuwen.fragment.MainFrag4;
import cn.com.zwwl.bayuwen.fragment.MainFrag5;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ChildModel;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.UserModel;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.view.CalendarOptionPopWindow;
import cn.com.zwwl.bayuwen.widget.MostGridView;

/**
 *
 */
public class MainActivity extends BaseActivity implements TencentLocationListener {
    private RadioButton tabButton1, tabButton2, tabButton3, tabButton4, tabButton5;
    private LinearLayout mainView;
    private DrawerLayout drawer;// 抽屉
    private LinearLayout childLayout;// 切换学生layout
    private Fragment mTempFragment;
    private MainFrag1 mainFrag1;
    private MainFrag2 mainFrag2;
    private MainFrag3 mainFrag3;
    private MainFrag4 mainFrag4;
    private MainFrag5 mainFrag5;
    private ImageView avatar;
    private TextView name, yaoqing, gongxun;
    private UserModel userModel;


    private MostGridView giftGridView;
    private GiftAdapter giftAdapter;

    private List<ChildModel> childModels = new ArrayList<>();// 学员数据
    private boolean isCanSetDefaultChild = true;// 是否可以设置默认学员

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core);
        mContext = this;
        initView();
        initData();
    }

    /**
     * 获取当前用户下的所有学员信息
     */
    private void initChildDta() {
        new ChildInfoApi(mContext, new FetchEntryListListener() {
            @Override
            public void setData(List list) {
                if (Tools.listNotNull(list)) {
                    childModels.clear();
                    childModels.addAll(list);
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        userModel = UserDataHelper.getUserLoginInfo(mContext);
        if (userModel == null) {
            mContext.startActivity(new Intent(mContext, LoginActivity.class));
        } else {
            if (MyApplication.loginStatusChange) {
                initLeftLayout();
                mainFrag1.initData(userModel);
                mainFrag5.initData(userModel);
                initChildDta();
                MyApplication.loginStatusChange = false;
            }

        }
    }

    /**
     * 设置默认学员
     */
    public void setDefaultChild(ChildModel child) {
        if (!isCanSetDefaultChild) return;
        isCanSetDefaultChild = false;
        child.setIsdefault("1");
        new ChildInfoApi(mContext, child, true, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                isCanSetDefaultChild = true;
                if (entry != null && entry instanceof ChildModel) {
                    initChildDta();
                }
            }

            @Override
            public void setError(ErrorMsg error) {
                isCanSetDefaultChild = true;
                if (error != null)
                    showToast(error.getDesc());
            }
        });
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
        mainFrag5 = MainFrag5.newInstance("hello world");
        switchFragment(mainFrag1);

        avatar = findViewById(R.id.main_avatar);
        name = findViewById(R.id.main_name);
        yaoqing = findViewById(R.id.main_yaoqingma);
        gongxun = findViewById(R.id.main_gongxun);
        childLayout = findViewById(R.id.child_layout);
        giftGridView = findViewById(R.id.my_gifts);
        giftAdapter = new GiftAdapter(mContext, new ArrayList<CalendarOptionPopWindow
                .CheckStatusModel>());
        giftGridView.setAdapter(giftAdapter);

        tabButton1 = findViewById(R.id.bottom_nav_1);
        tabButton2 = findViewById(R.id.bottom_nav_2);
        tabButton3 = findViewById(R.id.bottom_nav_3);
        tabButton4 = findViewById(R.id.bottom_nav_4);
        tabButton5 = findViewById(R.id.bottom_nav_5);
        tabButton1.setOnClickListener(this);
        tabButton2.setOnClickListener(this);
        tabButton3.setOnClickListener(this);
        tabButton4.setOnClickListener(this);
        tabButton5.setOnClickListener(this);
        avatar.setOnClickListener(this);
        findViewById(R.id.tianxie_code).setOnClickListener(this);
        findViewById(R.id.invite).setOnClickListener(this);
        findViewById(R.id.setting).setOnClickListener(this);
        findViewById(R.id.main_gongxun_rule).setOnClickListener(this);
        findViewById(R.id.child_add).setOnClickListener(this);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:// 更新侧边栏学员列表
                    childLayout.removeAllViews();
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout
                            .LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.bottomMargin = 20;
                    for (final ChildModel childModel : childModels) {
                        childLayout.addView(getChildView(childModel), params);
                    }
                    // 初始化侧边栏学员列表之后，同步fragment1\同步fragment5中学员信息
                    mainFrag1.loadChild(childModels);
                    mainFrag5.loadChild(childModels);
                    break;
                case 1:

                    break;
            }
        }
    };


    private View getChildView(final ChildModel childModel) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_child,
                null);
        View bg = view.findViewById(R.id.item_child_bg);
        TextView name = view.findViewById(R.id.child_name);
        TextView grade = view.findViewById(R.id.child_grade);
        ImageView avat = view.findViewById(R.id.child_avatar);
        name.setText(childModel.getName());
        grade.setText(childModel.getGrade());
        if (!TextUtils.isEmpty(childModel.getPic()))
            Glide.with(mContext).load(childModel.getPic()).into(avat);
        if (childModel.getIsdefault().equals("1")) {
            bg.setBackgroundResource(R.drawable.gold_white_xiangkuang);

        } else {
            bg.setBackgroundResource(R.drawable.gray_white_xiankuang);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawer();
                setDefaultChild(childModel);
            }
        });
        view.findViewById(R.id.item_child_go).setOnClickListener(new View
                .OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, ChildInfoActivity.class);
                i.putExtra("ChildInfoActivity_data", childModel);
                startActivity(i);
            }
        });
        return view;
    }


    /**
     * 切换学生
     */
//    public void changeChild(ChildModel childModel) {
//
//    }
    @Override
    protected void initData() {


    }

    /**
     * 查看功勋等级规则
     */
    public void goWeb() {
        Intent i = new Intent(mContext, WebActivity.class);
        i.putExtra("WebActivity_title", "等级规则");
        i.putExtra("WebActivity_data", "");
        startActivity(i);
    }


    /**
     * 打开抽屉
     */
    public void openDrawer() {
        if (!drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.openDrawer(GravityCompat.START);
        }
    }

    /**
     * 关闭抽屉
     */
    public boolean closeDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        return false;
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
            case R.id.bottom_nav_5:
                switchFragment(mainFrag5);
                break;
            case R.id.main_avatar:
                mContext.startActivity(new Intent(mContext, ParentInfoActivity.class));
                break;
            case R.id.tianxie_code:// 填写团购课程码
                startActivity(new Intent(this, TuanIndexActivity.class));
                break;
            case R.id.invite:// 邀请好友加入大语文
                break;
            case R.id.setting:
                startActivity(new Intent(mContext, SettingActivity.class));
                break;
            case R.id.child_add:// 添加孩子
                startActivity(new Intent(mContext, ChildInfoActivity.class));
                break;
            case R.id.main_gongxun_rule:
                goWeb();
                break;
        }
    }

    /**
     * 切换四个tab
     *
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

    /**
     * 初始化用户信息
     */
    private void initLeftLayout() {
        name.setText(userModel.getName());
        if (!TextUtils.isEmpty(userModel.getPic()))
            Glide.with(mContext).load(userModel.getPic()).into(avatar);
        yaoqing.setText("我的邀请码：" + userModel.getSignCode());
//        gongxun.setText();

    }

    @Override
    public void onBackPressed() {
        if (!closeDrawer()) {
            super.onBackPressed();
        }
    }

    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
        Log.e("sssssss", s + " __ " + tencentLocation.getCity());
        if (TextUtils.isEmpty(TempDataHelper.getCurrentCity(this))) {
            TempDataHelper.setCurrentCity(mContext, tencentLocation.getCity());
        }
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }
}
