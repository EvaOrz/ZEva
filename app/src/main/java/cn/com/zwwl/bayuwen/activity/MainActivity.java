package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.GiftAdapter;
import cn.com.zwwl.bayuwen.api.ChildInfoApi;
import cn.com.zwwl.bayuwen.api.HonorListApi;
import cn.com.zwwl.bayuwen.api.ReportPushApi;
import cn.com.zwwl.bayuwen.api.WebUrlApi;
import cn.com.zwwl.bayuwen.db.TempDataHelper;
import cn.com.zwwl.bayuwen.db.UserDataHelper;
import cn.com.zwwl.bayuwen.dialog.FinalEvalDialog;
import cn.com.zwwl.bayuwen.fragment.MainFrag1;
import cn.com.zwwl.bayuwen.fragment.MainFrag2;
import cn.com.zwwl.bayuwen.fragment.MainFrag3;
import cn.com.zwwl.bayuwen.fragment.MainFrag4;
import cn.com.zwwl.bayuwen.fragment.MainFrag5;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ChildModel;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.GiftAndJiangModel;
import cn.com.zwwl.bayuwen.model.ReportModel;
import cn.com.zwwl.bayuwen.push.NewPushManager;
import cn.com.zwwl.bayuwen.util.AppValue;
import cn.com.zwwl.bayuwen.util.DialogUtil;
import cn.com.zwwl.bayuwen.util.ShareTools;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.util.UmengLogUtil;
import cn.com.zwwl.bayuwen.util.UriParse;
import cn.com.zwwl.bayuwen.view.music.MusicWindow;
import cn.com.zwwl.bayuwen.widget.MostGridView;

/**
 *
 */
public class MainActivity extends BaseActivity {
    private RadioButton tabButton1, tabButton2, tabButton3, tabButton4, tabButton5;
    private LinearLayout mainView;
    private DrawerLayout drawer;// 抽屉
    private LinearLayout childLayout;// 切换学生layout
    private LinearLayout childAddBt;// 添加学生button
    private Fragment mTempFragment;
    private MainFrag1 mainFrag1;
    private MainFrag2 mainFrag2;
    private MainFrag3 mainFrag3;
    private MainFrag4 mainFrag4;
    private MainFrag5 mainFrag5;
    private ImageView avatar;
    private TextView lookLevelInfo;
    private TextView name, yaoqing, gongxun;

    private MostGridView giftGridView;
    private GiftAdapter giftAdapter;
    private View barLayout;

    private List<ChildModel> childModels = new ArrayList<>();// 学员数据
    private List<GiftAndJiangModel> giftModels = new ArrayList<>();// 礼物数据
    private boolean isCanSetDefaultChild = true;// 是否可以设置默认学员
    private FinalEvalDialog evalDialog;
    private ReportModel reportModel;

    public boolean isGoAlbumActivity = false;
    private long lastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core);
        mContext = this;
        NewPushManager.getInstance(this).register();
        initView();
        userModel = UserDataHelper.getUserLoginInfo(mContext);
        initData();
        getWebUrl();
        parsePushMessage(getIntent());
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        parsePushMessage(intent);
        // 解析tab跳转
        int flag = intent.getIntExtra("Main_frag_no", 0);
        if (flag > 0) {
            changeTab(flag);
            switch (flag) {
                case 1:
                    switchFragment(mainFrag1);
                    break;
                case 2:
                    switchFragment(mainFrag2);
                    break;
                case 3:
                    UmengLogUtil.courseTrackClick(mContext);
                    switchFragment(mainFrag3);
                    break;
                case 4:
                    switchFragment(mainFrag4);
                    break;
                case 5:
                    switchFragment(mainFrag5);
                    break;
            }
        }

    }

    /**
     * 解析push消息
     *
     * @param i
     */
    private void parsePushMessage(Intent i) {
        String pushMessage = i.getStringExtra("push_message");
        if (TextUtils.isEmpty(pushMessage)) return;
        UriParse.clickZwwl(mContext, pushMessage);
    }

    private void changeTab(int flag) {
        if (flag == 1) {
            tabButton1.setChecked(true);
        } else tabButton1.setChecked(false);
        if (flag == 2) {
            tabButton2.setChecked(true);
        } else tabButton2.setChecked(false);
        if (flag == 3) {
            tabButton3.setChecked(true);
        } else tabButton3.setChecked(false);
        if (flag == 4) {
            tabButton4.setChecked(true);
        } else tabButton4.setChecked(false);
        if (flag == 5) {
            tabButton5.setChecked(true);
        } else tabButton5.setChecked(false);
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
        NewPushManager.getInstance(this).onresume(this);
        MusicWindow.getInstance(this).movetoController(getViewHeight(barLayout));

        isGoAlbumActivity = false;
        super.onResume();
        if (MyApplication.loginStatusChange) {
            Log.e("********", "登录状态变化");
            initData();
            MyApplication.loginStatusChange = false;
        }
        if (MyApplication.userStatusChange) {
            Log.e("********", "用户和学员信息变化");
            initData();
            MyApplication.userStatusChange = false;
        }
        if (MyApplication.cityStatusChange) {
            mainFrag1.isCityChanged = true;
            mainFrag2.isCityChanged = true;
            MyApplication.cityStatusChange = false;
        }
    }

    @Override
    protected void onStop() {
        if (!isGoAlbumActivity)
            MusicWindow.getInstance(this).hidePopupWindow();
        super.onStop();
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
        evalDialog = new FinalEvalDialog(this);
        evalDialog.setSubmitListener(new FinalEvalDialog.SubmitListener() {
            @Override
            public void show() {
                evalDialog.showAtLocation(mainView, Gravity.BOTTOM, 0, 0);
            }

            @Override
            public void ok() {
                if (reportModel.getKeReport() != null) {
                    Intent intent = new Intent(MainActivity.this, WebActivity.class);
                    intent.putExtra("WebActivity_data", reportModel.getKeReport().getUrl());
                    startActivity(intent);
                } else if (reportModel.getMonthReport() != null) {
                    Intent intent = new Intent(MainActivity.this, WebActivity.class);
                    intent.putExtra("WebActivity_data", reportModel.getMonthReport().getUrl());
                    startActivity(intent);
                }
            }

            @Override
            public void error(int code) {
                evalDialog.dismiss();
            }
        });
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
        mainFrag3 = MainFrag3.newInstance();
        mainFrag4 = MainFrag4.newInstance("hello world");
        mainFrag5 = MainFrag5.newInstance("hello world");
        switchFragment(mainFrag1);

        avatar = findViewById(R.id.main_avatar);
        name = findViewById(R.id.main_name);
        yaoqing = findViewById(R.id.main_yaoqingma);
        gongxun = findViewById(R.id.main_gongxun);
        childLayout = findViewById(R.id.child_layout);
        childAddBt = findViewById(R.id.child_add);
        giftGridView = findViewById(R.id.my_gifts);

        tabButton1 = findViewById(R.id.bottom_nav_1);
        tabButton2 = findViewById(R.id.bottom_nav_2);
        tabButton3 = findViewById(R.id.bottom_nav_3);
        tabButton4 = findViewById(R.id.bottom_nav_4);
        tabButton5 = findViewById(R.id.bottom_nav_5);
        barLayout = findViewById(R.id.botoom_nav);
        tabButton1.setOnClickListener(this);
        tabButton2.setOnClickListener(this);
        tabButton3.setOnClickListener(this);
        tabButton4.setOnClickListener(this);
        tabButton5.setOnClickListener(this);
        avatar.setOnClickListener(this);
        findViewById(R.id.tianxie_code).setOnClickListener(this);
        findViewById(R.id.invite).setOnClickListener(this);
        lookLevelInfo = findViewById(R.id.main_gongxun_rule);
        lookLevelInfo.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        lookLevelInfo.setOnClickListener(this);
        childAddBt.setOnClickListener(this);
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

                    for (int i = 0; i < childModels.size(); i++) {
                        childLayout.addView(getChildView(childModels.get(i)), params);
                    }
                    if (childModels.size() < 3) {
                        childAddBt.setVisibility(View.VISIBLE);
                    } else childAddBt.setVisibility(View.GONE);
                    // 初始化侧边栏学员列表之后，同步fragment1\同步fragment5中学员信息
                    mainFrag1.loadChild(childModels);
                    if (mainFrag3.isAdded() && !mainFrag3.isHidden())
                        mainFrag3.refresh();
                    mainFrag5.loadChild(childModels);
                    break;
                case 1:// 更新礼物数据
                    giftAdapter = new GiftAdapter(mContext, giftModels);
                    giftGridView.setAdapter(giftAdapter);
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
            ImageLoader.display(mContext, avat, childModel.getPic(), R.drawable
                    .avatar_placeholder, R.drawable.avatar_placeholder);
        if (childModel.getIsdefault().equals("1")) {
            bg.setBackgroundResource(R.drawable.red_white_circle);

        } else {
            bg.setBackgroundResource(R.drawable.white_btn_bg);
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

    @Override
    protected void initData() {
        if (userModel == null) return;
        initLeftLayout();
        mainFrag5.initData(userModel);
        initChildDta();
//        getReport();
    }


    /**
     * 获取最新报告
     */
    private void getReport() {
        new ReportPushApi(this, new ResponseCallBack<ReportModel>() {
            @Override
            public void result(ReportModel model, ErrorMsg errorMsg) {
                reportModel = model;
                if (reportModel != null) {

                    if ((reportModel.getMonthReport() != null && !TextUtils.isEmpty(reportModel
                            .getMonthReport().getUrl())) || (reportModel.getKeReport() != null &&
                            !TextUtils.isEmpty(reportModel.getKeReport().getUrl())))
                        DialogUtil.showDoubleDialog(MainActivity.this, R.string.hint_title, R
                                .string.report_hint, R.string.eval_look, R.string.cancel, new
                                DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (reportModel.getKeReport() != null) {
                                            if (reportModel.getKeReport().getComment_id() == null) {
                                                evalDialog.setData(1, reportModel.getKeReport()
                                                        .getKid());

                                            } else {
                                                Intent intent = new Intent(MainActivity.this,
                                                        WebActivity
                                                                .class);
                                                intent.putExtra("WebActivity_data", reportModel
                                                        .getKeReport().getUrl());
                                                startActivity(intent);
                                            }
                                        } else if (reportModel.getMonthReport() != null) {
                                            if (reportModel.getMonthReport().getComment_id() ==
                                                    null) {
                                                evalDialog.setData(2, reportModel.getMonthReport()
                                                        .getYear(), reportModel.getMonthReport()
                                                        .getMonth
                                                                ());
                                            } else {
                                                Intent intent = new Intent(MainActivity.this,
                                                        WebActivity
                                                                .class);
                                                intent.putExtra("WebActivity_data", reportModel
                                                        .getMonthReport().getUrl());
                                                startActivity(intent);
                                            }
                                        }
                                    }
                                }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                }
            }
        });
    }

    /**
     * 查看功勋等级规则
     */
    public void goWeb() {
        Intent i = new Intent(mContext, WebActivity.class);
        i.putExtra("WebActivity_data", AppValue.gongxunUrl);
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
                UmengLogUtil.courseTrackClick(mContext);
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
                startActivity(new Intent(this, TuanCodeUseActivity.class));
                break;
            case R.id.invite:// 邀请好友加入大语文
                UmengLogUtil.logInviteClick(mContext);
                ShareTools.doShareWeb(this, "大语文", "大语文", "http://dev" +
                        ".umeng.com/images/tab2_1.png", AppValue.inviteUrl);
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
     * 切换五个tab
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
        gongxun.setText(userModel.getLevel() + "");

        // 获取礼物数据
        new HonorListApi(mContext, 2, TempDataHelper.getCurrentChildNo(mContext), new
                FetchEntryListListener() {
                    @Override
                    public void setData(List list) {
                        if (Tools.listNotNull(list)) {
                            giftModels.clear();
                            giftModels.addAll(list);
                            handler.sendEmptyMessage(1);
                        }
                    }

                    @Override
                    public void setError(ErrorMsg error) {

                    }
                });
    }

    // 获取说明weburl
    private void getWebUrl() {
        new WebUrlApi(mContext);
    }

    @Override
    public void onBackPressed() {
        if (!closeDrawer()) {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            long clickTime = System.currentTimeMillis() / 1000;
            if (clickTime - lastClickTime >= 3) {
                lastClickTime = clickTime;
                showToast(R.string.exit_app);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
