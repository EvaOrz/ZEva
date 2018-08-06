package cn.com.zwwl.bayuwen.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.AbilityAnalysisActivity;
import cn.com.zwwl.bayuwen.activity.AllXunzhangActivity;
import cn.com.zwwl.bayuwen.activity.CityActivity;
import cn.com.zwwl.bayuwen.activity.CoinListActivity;
import cn.com.zwwl.bayuwen.activity.MainActivity;
import cn.com.zwwl.bayuwen.activity.MessageActivity;
import cn.com.zwwl.bayuwen.adapter.AchieveMainAdapter;
import cn.com.zwwl.bayuwen.adapter.RadarAdapter;
import cn.com.zwwl.bayuwen.api.AchievementApi;
import cn.com.zwwl.bayuwen.api.PintuListApi;
import cn.com.zwwl.bayuwen.db.TempDataHelper;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.model.AchievementModel;
import cn.com.zwwl.bayuwen.model.ChildModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.Index1Model.AdvBean;
import cn.com.zwwl.bayuwen.model.PintuModel;
import cn.com.zwwl.bayuwen.model.UserModel;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.util.UmengLogUtil;
import cn.com.zwwl.bayuwen.view.ChildMenuPopView;
import cn.com.zwwl.bayuwen.widget.CircleImageView;
import cn.com.zwwl.bayuwen.widget.MostGridView;
import cn.com.zwwl.bayuwen.widget.threed.GalleryTransformer;
import cn.com.zwwl.bayuwen.widget.threed.InfinitePagerAdapter;
import cn.com.zwwl.bayuwen.widget.threed.InfiniteViewPager;

/**
 * 学员成就
 */
public class MainFrag1 extends Fragment implements View.OnClickListener {

    private Activity mActivity;
    private TextView childTxt, childName, childGrade, childCoin;
    private TextView locationTv;
    private CircleImageView childImg;
    private View root;
    private RelativeLayout toolbar;//
    private InfiniteViewPager pingPager;// 拼图列表
    private InfinitePagerAdapter pingAdapter;
    private LinearLayout pingtu_indicator;// 拼图指示器
    private MostGridView achieveGrid;// 成就列表
    private TextView achiTv;
    private TextView pintu_title;

    private List<AdvBean> advBeans = new ArrayList<>();// banner数据
    private List<View> pingtuViews = new ArrayList<>();
    private List<ChildModel> childModels = new ArrayList<>();// 学员数据
    private List<AchievementModel> achiveatas = new ArrayList<>();// 成就数据
    private List<PintuModel> pintuModels = new ArrayList<>();// 拼图数据
    private UserModel userModel;

    private int pintuWid, pintuHei;// 拼图item的宽高
    private int paddingLeft, paddingRight, paddingTop, paddingBottom;

    public boolean isCityChanged = false;// 城市状态是否变化

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isCityChanged) {
            getPintuData();
            handler.sendEmptyMessage(1);
        }
    }

    /**
     * 默认fragment创建的时候是可见的，但是不会调用该方法！切换可见状态的时候会调用，但是调用onResume，onPause的时候却不会调用
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (isCityChanged) {
                getPintuData();
                handler.sendEmptyMessage(1);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_main1, container, false);
        initView();
        return root;
    }

    /**
     * 获取拼图数据
     */
    public void getPintuData() {
        new PintuListApi(mActivity, new FetchEntryListListener() {
            @Override
            public void setData(List list) {
                pintuModels.clear();

                if (Tools.listNotNull(list)) {
                    for (int i = 0; i < list.size(); i++)
                        if (Tools.listNotNull(((PintuModel) list.get(i)).getLectureinfo())) {
                            pintuModels.add((PintuModel) list.get(i));
                        }
                }
                initPingtudata();
                handler.sendEmptyMessage(3);
            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });
    }

    /**
     * 获取当前学员的成就
     */
    public void getAchieveData() {
        new AchievementApi(mActivity, new FetchEntryListListener() {
            @Override
            public void setData(List list) {
                achiveatas.clear();
                if (Tools.listNotNull(list)) {
                    for (int i = 0; i < list.size(); i++) {
                        if (((AchievementModel) list.get(i)).getIs_get() == 1) {
                            achiveatas.add((AchievementModel) list.get(i));
                        }
                    }
                }
                handler.sendEmptyMessage(2);
            }

            @Override
            public void setError(ErrorMsg error) {
            }
        });
    }

    /**
     * studentLay宽度固定
     * 计算banner viewpager的宽度
     * 假设banner位置图片宽高比4：3,则banner viewpager高度可计算
     * 需要动态设置studentLay高度
     */
    private void initSize() {
        pintuWid = MyApplication.width - 300;
        pintuHei = (MyApplication.width - 300) * 6 / 9;
        paddingLeft = pintuWid * 50 / 1018;
        paddingRight = pintuWid * 50 / 1018;
        paddingTop = pintuHei * 72 / 676;
        paddingBottom = pintuHei * 112 / 676;

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(pintuWid +
                paddingLeft + paddingRight,
                pintuHei +
                        paddingTop + paddingBottom);
        params1.setMargins(0, 16, 0, 16);
        pingPager.setLayoutParams(params1);

    }

    private void initView() {
        toolbar = root.findViewById(R.id.toolbar);
        childTxt = root.findViewById(R.id.toolbar_title);
        childImg = root.findViewById(R.id.frag1_avatar);
        childName = root.findViewById(R.id.frag1_name);
        childGrade = root.findViewById(R.id.frag1_grade);
        childCoin = root.findViewById(R.id.frag1_coin);
        locationTv = root.findViewById(R.id.position);
        pingtu_indicator = root.findViewById(R.id.pingtu_indicator);
        achieveGrid = root.findViewById(R.id.xunzhang_grid);
        achiTv = root.findViewById(R.id.xunzhang_tv);
        pintu_title = root.findViewById(R.id.pintu_title);

        pingPager = root.findViewById(R.id.pingtu_pager);
        pingPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateLinearPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int scrollState) {
            }
        });
        initSize();

        childTxt.setOnClickListener(this);
        childCoin.setOnClickListener(this);
        root.findViewById(R.id.go_xunzhang).setOnClickListener(this);
        root.findViewById(R.id.toolbar_left).setOnClickListener(this);
        root.findViewById(R.id.toolbar_right).setOnClickListener(this);
        locationTv.setOnClickListener(this);
    }

    /**
     * 更新拼图的选中状态
     *
     * @param position
     */
    private void updateLinearPosition(int position) {
        pintu_title.setText(pintuModels.get(position).getName());
        for (int i = 0; i < pingtu_indicator.getChildCount(); i++) {
            if (i == position) {
                pingtu_indicator.getChildAt(i).setBackgroundResource(R.drawable
                        .dot_gold_6);
            } else {
                pingtu_indicator.getChildAt(i).setBackgroundResource(R.drawable
                        .dot_gray_6);
            }
        }
    }

    /**
     * 获取拼图列表数据
     */
    private void initPingtudata() {
        pingtuViews.clear();
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(pintuWid +
                paddingLeft + paddingRight,
                pintuHei +
                        paddingTop + paddingBottom);
        for (int i = 0; i < pintuModels.size(); i++) {
            final int pos = i;
            final View view = LayoutInflater.from(mActivity).inflate(R.layout.item_pingtu,
                    null);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goDati(pos);
                }
            });
            view.setLayoutParams(params1);
            view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
            RecyclerView recyclerView = view.findViewById(R.id.radar_fragmain1);
            recyclerView.setLayoutParams(new LinearLayout.LayoutParams(pintuWid, pintuHei));

            recyclerView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        view.performClick();  //模拟父控件的点击
                    }
                    return false;
                }
            });

            if (pintuModels.get(i).getStyle() == 2 || pintuModels.get(i).getStyle() == 3) {
                view.setBackgroundResource(R.drawable.pintu_bg_wangzhe);
            } else view.setBackgroundResource(R.drawable.pintu_bg_baiyin);

            List<PintuModel.LectureinfoBean.SectionListBean> models = pintuModels.get(i)
                    .getLectureinfo().get(0).getSectionList();
            if (Tools.listNotNull(models) && models.size() == 54) {
                RadarAdapter radarAdapter = new RadarAdapter(models, pintuWid, pintuModels.get(i)
                        .getIs_pay() == 1);
                recyclerView.setAdapter(radarAdapter);
                recyclerView.setLayoutManager(new GridLayoutManager(mActivity, 9));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
            }

            pingtuViews.add(view);
        }
    }

    private void goDati(int pos) {
        UmengLogUtil.logPintuClick(mActivity);
        Intent i = new Intent(mActivity, AbilityAnalysisActivity.class);
        i.putExtra("pintuModels", (Serializable) pintuModels);
        i.putExtra("pin_pos", pos);
        startActivity(i);
    }

    /**
     * 加载学员列表
     *
     * @param childModels
     */
    public void loadChild(List<ChildModel> childModels) {
        this.childModels.clear();
        this.childModels.addAll(childModels);
        getAchieveData();
        getPintuData();
        handler.sendEmptyMessage(0);
        handler.sendEmptyMessage(1);
    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    for (ChildModel c : childModels) {
                        if (c.getIsdefault().equals("1")) {
                            childName.setText(c.getName());
                            childGrade.setText(c.getGrade());
                            childCoin.setText("学员积分：" + c.getIntegral());
                            childTxt.setText(c.getName() + "(" + c.getGrade() + ")");
                            if (!TextUtils.isEmpty(c.getPic()))
                                ImageLoader.display(mActivity, childImg, c.getPic(), R.drawable
                                        .avatar_placeholder, R.drawable.avatar_placeholder);
                        }
                    }
                    break;
                case 1:// 显示当前城市
                    locationTv.setText(TempDataHelper.getCurrentCity(mActivity));
                    isCityChanged = false;
                    break;
                case 2:// 显示当前学员获得的成就
                    if (achiveatas.size() > 0) {
                        achieveGrid.setVisibility(View.VISIBLE);
                        achiTv.setVisibility(View.GONE);
                        AchieveMainAdapter adapter = new AchieveMainAdapter(mActivity,
                                achiveatas);
                        achieveGrid.setAdapter(adapter);
                    } else {
                        achieveGrid.setVisibility(View.GONE);
                        achiTv.setVisibility(View.VISIBLE);
                    }
                    break;

                case 3:// load 拼图数据
                    pingAdapter = new InfinitePagerAdapter(pingtuViews);
                    pingPager.setAdapter(pingAdapter);
                    pingPager.setOffscreenPageLimit(3);
                    pingPager.setPageTransformer(true, new GalleryTransformer());
                    pingtu_indicator.removeAllViews();
                    for (int i = 0; i < pingtuViews.size(); i++) {
                        ImageView img = new ImageView(mActivity);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams
                                                .WRAP_CONTENT);
                        layoutParams.rightMargin = getResources().getDimensionPixelOffset(R
                                .dimen
                                .dp_5);
                        img.setLayoutParams(layoutParams);
                        img.setBackgroundResource(R.drawable.viewlooper_gray_status);
                        pingtu_indicator.addView(img);
                    }
                    pingPager.setCurrentItem(2);
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_xunzhang:
                UmengLogUtil.logAllChengjiuClick(mActivity);
                startActivity(new Intent(mActivity, AllXunzhangActivity.class));
                break;
            case R.id.toolbar_left:// 打开抽屉
                ((MainActivity) mActivity).openDrawer();
                break;
            case R.id.position:// 选择城市
                Intent intent = new Intent(getActivity(), CityActivity.class);
                startActivity(intent);
                break;
            case R.id.toolbar_right:
                startActivity(new Intent(mActivity, MessageActivity.class));
                break;
            case R.id.toolbar_title:// 切换学生
                if (Tools.listNotNull(childModels)) {
                    ChildMenuPopView childMenuPopView = new ChildMenuPopView(mActivity,
                            childModels, new
                            ChildMenuPopView.OnChildPickListener() {
                                @Override
                                public void onChildPick(ChildModel childModel) {
                                    ((MainActivity) mActivity).setDefaultChild(childModel);
                                }
                            });
                    childMenuPopView.showPopupWindow(toolbar);
                }
                break;
            case R.id.frag1_coin:
                startActivity(new Intent(mActivity, CoinListActivity.class));
                break;
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    /**
     * 带传参的构造方法
     *
     * @param ss
     * @return
     */
    public static MainFrag1 newInstance(String ss) {
        MainFrag1 fragment = new MainFrag1();
        return fragment;
    }
}
