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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.AllXunzhangActivity;
import cn.com.zwwl.bayuwen.activity.CalendarActivity;
import cn.com.zwwl.bayuwen.activity.ChildInfoActivity;
import cn.com.zwwl.bayuwen.activity.MainActivity;
import cn.com.zwwl.bayuwen.activity.MessageActivity;
import cn.com.zwwl.bayuwen.activity.ParentInfoActivity;
import cn.com.zwwl.bayuwen.adapter.MainYixuanKeAdapter;
import cn.com.zwwl.bayuwen.adapter.MyViewPagerAdapter;
import cn.com.zwwl.bayuwen.adapter.RadarAdapter;
import cn.com.zwwl.bayuwen.api.Index1Api;
import cn.com.zwwl.bayuwen.db.TempDataHelper;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ChildModel;
import cn.com.zwwl.bayuwen.model.CommonModel;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.Index1Model;
import cn.com.zwwl.bayuwen.model.Index1Model.AdvBean;
import cn.com.zwwl.bayuwen.model.Index1Model.CalendarCourseBean;
import cn.com.zwwl.bayuwen.model.Index1Model.SelectedCourseBean;
import cn.com.zwwl.bayuwen.model.UserModel;
import cn.com.zwwl.bayuwen.model.fm.AlbumModel;
import cn.com.zwwl.bayuwen.util.AddressTools;
import cn.com.zwwl.bayuwen.util.AppValue;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.view.AddressPopWindow;
import cn.com.zwwl.bayuwen.view.ChildMenuPopView;
import cn.com.zwwl.bayuwen.widget.CircleImageView;
import cn.com.zwwl.bayuwen.widget.LoopViewPager;
import cn.com.zwwl.bayuwen.widget.NoScrollListView;
import cn.com.zwwl.bayuwen.widget.RoundAngleImageView;
import cn.com.zwwl.bayuwen.widget.RoundAngleLayout;
import cn.com.zwwl.bayuwen.widget.threed.GalleryTransformer;
import cn.com.zwwl.bayuwen.widget.threed.InfiniteViewPager;
import cn.jzvd.JZUtils;

/**
 *
 */
public class MainFrag1 extends Fragment implements View.OnClickListener {

    private Activity mActivity;
    private LoopViewPager bannerView;
    private RoundAngleLayout studentLay, parentLay;// banner位的学生信息栏
    private TextView notificationTv, childTxt;
    private CircleImageView childImg, parentImg;
    private View root;
    private NoScrollListView yixuanKeListView;// 已选课程列表
    private MainYixuanKeAdapter yixuanKeAdapter;
    private RelativeLayout toolbar;//
    private InfiniteViewPager pingPager;// 拼图列表
    private MyViewPagerAdapter pingAdapter;
    private TextView calendarRi, calendarYue;
    private LinearLayout calendarLayout;
    private LinearLayout pingtu_indicator;// 拼图指示器

    private List<AdvBean> advBeans = new ArrayList<>();// banner数据
    private CalendarCourseBean calendarCourseBean;// calendar事件数据
    private List<SelectedCourseBean> selectedCourses = new ArrayList<>();// 已选课程
    private List<View> pingtuData = new ArrayList<>();
    private List<ChildModel> childModels = new ArrayList<>();// 学员数据
    private UserModel userModel;

    private int bannerWid, bannerHei;// 轮播位宽高
    private int pintuWid, pintuHei;// 拼图item的宽高

    public boolean isCityChanged = false;// 城市状态是否变化

    private List<AlbumModel> yixuanDatas = new ArrayList<>();// 已选课程data

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        bannerView.startLoop(true);

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
            if (isCityChanged)
                loadData();
        } else {
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
     * studentLay宽度固定
     * 计算banner viewpager的宽度
     * 假设banner位置图片宽高比4：3,则banner viewpager高度可计算
     * 需要动态设置studentLay高度
     */
    private void initSize() {
        ViewTreeObserver vto = studentLay.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                int width = studentLay.getWidth();

                bannerWid = MyApplication.width - width * 2 - JZUtils.dip2px(mActivity, 5) * 4;
                bannerHei = bannerWid * 9 / 16;
                // 成功调用一次后，移除 Hook 方法，防止被反复调用
                // removeGlobalOnLayoutListener() 方法在 API 16 后不再使用
                // 使用新方法 removeOnGlobalLayoutListener() 代替
                studentLay.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout
                        .LayoutParams.MATCH_PARENT, bannerHei);
                bannerView.setSize(params);

                studentLay.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout
                        .LayoutParams.WRAP_CONTENT, bannerHei));
                parentLay.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                        .WRAP_CONTENT, bannerHei));

                pintuWid = MyApplication.width - 300;
                pintuHei = (MyApplication.width - 300) * 6 / 9;
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(pintuWid + 20,
                        pintuHei +
                                20);
                params1.setMargins(0, 16, 0, 16);
                pingPager.setLayoutParams(params1);

                //
                initPingtudata();
                pingAdapter = new MyViewPagerAdapter(pingtuData);
                pingPager.setAdapter(pingAdapter);
                pingPager.setOffscreenPageLimit(3);
                pingPager.setPageTransformer(true, new GalleryTransformer());
                pingtu_indicator.removeAllViews();
                for (int i = 0; i < pingtuData.size(); i++) {
                    ImageView img = new ImageView(getContext());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                            (LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams
                                            .WRAP_CONTENT);
                    layoutParams.rightMargin = getResources().getDimensionPixelOffset(R.dimen
                            .dp_5);
                    img.setLayoutParams(layoutParams);
                    img.setBackgroundResource(R.drawable.viewlooper_gray_status);
                    pingtu_indicator.addView(img);
                }
                pingPager.setCurrentItem(2);

            }
        });

    }

    public void initData(UserModel userModel) {
        this.userModel = userModel;
        if (!TextUtils.isEmpty(userModel.getPic())) {
            ImageLoader.display(mActivity, parentImg, userModel.getPic(), R
                    .drawable.avatar_placeholder, R.drawable.avatar_placeholder);
        }
    }

    /**
     * 城市信息变换之后，页面需要刷新数据
     */
    public void loadData() {
        new Index1Api(mActivity, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                if (entry != null && entry instanceof Index1Model) {
                    advBeans.clear();
                    Index1Model index1Model = (Index1Model) entry;
                    if (Tools.listNotNull(index1Model.getAdv())) {
                        advBeans.addAll(index1Model.getAdv());
                    }
                    calendarCourseBean = index1Model.getCalendarCourse();

                    selectedCourses.clear();
                    if (Tools.listNotNull(index1Model.getSelectedCourse())) {
                        selectedCourses.addAll(index1Model.getSelectedCourse());
                    }
                    handler.sendEmptyMessage(1);
                    isCityChanged = false;
                }

            }

            @Override
            public void setError(ErrorMsg error) {
                if (error != null) {
                    AppValue.showToast(mActivity, error.getDesc());
                }
            }
        });
    }


    @Override
    public void onStop() {
        super.onStop();
        bannerView.startLoop(false);
    }

    private void initView() {
        toolbar = root.findViewById(R.id.toolbar);
        studentLay = root.findViewById(R.id.layout_student);
        parentLay = root.findViewById(R.id.layout_parent);
        bannerView = root.findViewById(R.id.frag1_head);
        childTxt = root.findViewById(R.id.toolbar_title);
        childImg = root.findViewById(R.id.frag1_child_avatar);
        parentImg = root.findViewById(R.id.frag1_parent_avater);
        pingtu_indicator = root.findViewById(R.id.pingtu_indicator);

        calendarRi = root.findViewById(R.id.calendar_ri);
        calendarYue = root.findViewById(R.id.calendar_yue);
        calendarLayout = root.findViewById(R.id.calendar_kecheng_layout);
        notificationTv = root.findViewById(R.id.main_notification);
        notificationTv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        notificationTv.setSingleLine(true);
        notificationTv.setSelected(true);
        notificationTv.setFocusable(true);
        notificationTv.setFocusableInTouchMode(true);

        yixuanKeListView = root.findViewById(R.id.main_yixuan);
        yixuanKeAdapter = new MainYixuanKeAdapter(mActivity);
        yixuanDatas.add(new AlbumModel());
        yixuanDatas.add(new AlbumModel());
        yixuanKeListView.setAdapter(yixuanKeAdapter);
        yixuanKeAdapter.setData(yixuanDatas);
        yixuanKeAdapter.notifyDataSetChanged();

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
            public void onPageScrollStateChanged(int state) {

            }
        });
        initSize();

        studentLay.setOnClickListener(this);
        parentLay.setOnClickListener(this);
        childTxt.setOnClickListener(this);
        root.findViewById(R.id.go_calendar).setOnClickListener(this);
        root.findViewById(R.id.go_xunzhang).setOnClickListener(this);
        root.findViewById(R.id.toolbar_left).setOnClickListener(this);
        root.findViewById(R.id.toolbar_right).setOnClickListener(this);
        root.findViewById(R.id.toolbar_city).setOnClickListener(this);
    }

    /**
     * 更新拼图的选中状态
     *
     * @param position
     */
    private void updateLinearPosition(int position) {
        for (int i = 0; i < pingtu_indicator.getChildCount(); i++) {
            if (i == position) {
                pingtu_indicator.getChildAt(i).setBackgroundResource(R.drawable
                        .viewlooper_gold_status);
            } else {
                pingtu_indicator.getChildAt(i).setBackgroundResource(R.drawable
                        .viewlooper_gray_status);
            }
        }
    }

    /**
     * 获取拼图列表数据
     */
    private void initPingtudata() {
        pingtuData.clear();
        for (int i = 0; i < 5; i++) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.item_pingtu, null);
            view.setLayoutParams(new LinearLayout.LayoutParams(pintuWid + 20, pintuHei +
                    20));
            RecyclerView recyclerView = view.findViewById(R.id.radar_fragmain1);
            List<CommonModel> models = new ArrayList<>();
            for (int j = 0; j < 54; j++) {
                CommonModel model = new CommonModel();
                model.setContent("");
                models.add(model);
            }
            RadarAdapter radarAdapter = new RadarAdapter(models, pintuWid);
            recyclerView.setAdapter(radarAdapter);
            recyclerView.setLayoutManager(new GridLayoutManager(mActivity, 9));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            pingtuData.add(view);
        }
    }

    /**
     * 加载学员列表
     *
     * @param childModels
     */
    public void loadChild(List<ChildModel> childModels) {
        this.childModels.clear();
        this.childModels.addAll(childModels);
        loadData();
        handler.sendEmptyMessage(0);
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
                            childTxt.setText(c.getName() + "(" + c.getGrade() + ")");
                            if (!TextUtils.isEmpty(c.getPic()))
                                Glide.with(mActivity).load(c.getPic()).into(childImg);
                        }
                    }
                    break;
                case 1:// 初始化页面数据
                    List<View> views = new ArrayList<>();
                    for (AdvBean advBean : advBeans) {
                        RoundAngleImageView r = new RoundAngleImageView(mActivity);
                        r.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        ImageLoader.display(mActivity, r, advBean.getPic(), R.mipmap.apply_logo,
                                R.mipmap.apply_logo);
                        views.add(r);
                    }
                    bannerView.setViewList(views);
                    bannerView.startLoop(true);

                    calendarLayout.removeAllViews();
                    if (calendarCourseBean != null && calendarCourseBean.getCourses().size() > 0) {
                        Calendar ss = CalendarTools.fromStringToca(calendarCourseBean.getDate());
                        calendarRi.setText(ss.get(Calendar.DATE) + "");
                        calendarYue.setText(ss.get(Calendar.MONTH) + "月");

                        for (CalendarCourseBean.CoursesBean coursesBean : calendarCourseBean
                                .getCourses()) {
                            TextView tip = new TextView(mActivity);
                            tip.setText(coursesBean.getTitle() + " " + coursesBean
                                    .getClass_start_at() + "-" + coursesBean.getClass_end_at());
                            tip.setTextColor(getResources().getColor(R.color.gray_dark));
                            tip.setTextSize(14);
                            calendarLayout.addView(tip);

                        }
                    } else {
                        TextView tip = new TextView(mActivity);
                        tip.setText("请添加课程日历");
                        tip.setTextColor(getResources().getColor(R.color.gray_dark));
                        tip.setTextSize(14);
                        calendarLayout.addView(tip);
                    }
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_calendar:
                startActivity(new Intent(mActivity, CalendarActivity.class));
                break;
            case R.id.go_xunzhang:
                startActivity(new Intent(mActivity, AllXunzhangActivity.class));
                break;
            case R.id.toolbar_left:// 打开抽屉
                ((MainActivity) mActivity).openDrawer();
                break;
            case R.id.toolbar_city:// 选择城市
                new AddressPopWindow(mActivity, 1, new AddressPopWindow.OnAddressCListener() {

                    @Override
                    public void onClick(AddressTools.ProvinceModel province, AddressTools
                            .CityModel city, AddressTools.DistModel dist) {
                        if (city.getCtxt().equals("市辖区")) {
                            TempDataHelper.setCurrentCity(mActivity, province.getPtxt());
                        } else
                            TempDataHelper.setCurrentCity(mActivity, city.getCtxt());
                        loadData();
                        ((MainActivity) mActivity).changeCity(0);
                    }

                });
                break;
            case R.id.toolbar_right:
                startActivity(new Intent(mActivity, MessageActivity.class));
                break;
            case R.id.layout_student:
                startActivity(new Intent(mActivity, ChildInfoActivity.class));
                break;
            case R.id.layout_parent:
                startActivity(new Intent(mActivity, ParentInfoActivity.class));
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
//        Bundle args = new Bundle();
        MainFrag1 fragment = new MainFrag1();
//        fragment.setArguments(args);
        return fragment;
    }
}
