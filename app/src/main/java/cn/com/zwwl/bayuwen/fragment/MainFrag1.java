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
import android.text.TextUtils;
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
import java.util.List;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.AllXunzhangActivity;
import cn.com.zwwl.bayuwen.activity.CalendarActivity;
import cn.com.zwwl.bayuwen.activity.ChildInfoActivity;
import cn.com.zwwl.bayuwen.activity.MainActivity;
import cn.com.zwwl.bayuwen.activity.MessageActivity;
import cn.com.zwwl.bayuwen.activity.ParentInfoActivity;
import cn.com.zwwl.bayuwen.activity.VideoPlayActivity;
import cn.com.zwwl.bayuwen.adapter.ImageBannerAdapter;
import cn.com.zwwl.bayuwen.adapter.MainYixuanKeAdapter;
import cn.com.zwwl.bayuwen.glide.GlideApp;
import cn.com.zwwl.bayuwen.model.ChildModel;
import cn.com.zwwl.bayuwen.model.UserModel;
import cn.com.zwwl.bayuwen.model.fm.AlbumModel;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.view.ChildMenuPopView;
import cn.com.zwwl.bayuwen.widget.NoScrollListView;
import cn.com.zwwl.bayuwen.widget.RoundAngleLayout;
import cn.com.zwwl.bayuwen.widget.threed.GalleryTransformer;
import cn.com.zwwl.bayuwen.widget.threed.InfiniteViewPager;
import cn.com.zwwl.bayuwen.widget.threed.ThreeDAdapter;
import cn.jzvd.JZUtils;

/**
 *
 */
public class MainFrag1 extends Fragment implements View.OnClickListener {

    private Activity mActivity;
    private ViewPager bannerViewPager;
    private ImageBannerAdapter imageBannerAdapter;
    private RoundAngleLayout studentLay, parentLay;// banner位的学生信息栏
    private TextView notificationTv, childTxt;
    private ImageView childImg, parentImg;
    private View root;
    private NoScrollListView yixuanKeListView;// 已选课程列表
    private MainYixuanKeAdapter yixuanKeAdapter;
    private RelativeLayout toolbar;//
    private InfiniteViewPager pingPager;// 拼图列表
    private ThreeDAdapter pingAdapter;
    private List<View> pingtuData = new ArrayList<>();
    private List<ChildModel> childModels = new ArrayList<>();// 学员数据
    private UserModel userModel;

    private int bannerWid, bannerHei;

    private List<View> bannerDatas = new ArrayList<>();
    private List<AlbumModel> yixuanDatas = new ArrayList<>();// 已选课程data

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
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
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, bannerHei);
                params.weight = 1;
                params.leftMargin = 10;
                params.rightMargin = 10;
                bannerViewPager.setLayoutParams(params);
                studentLay.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout
                        .LayoutParams.WRAP_CONTENT, bannerHei));
                parentLay.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                        .WRAP_CONTENT, bannerHei));

            }
        });


    }

    public void initData(UserModel userModel) {
        this.userModel = userModel;
        if (!TextUtils.isEmpty(userModel.getPic())) {
            GlideApp.with(mActivity).load(userModel.getPic()).placeholder(R.drawable
                    .avatar_placeholder).error(R.drawable
                    .avatar_placeholder).into(parentImg);
        }

    }

    private void initView() {
        toolbar = root.findViewById(R.id.toolbar);
        studentLay = root.findViewById(R.id.layout_student);
        parentLay = root.findViewById(R.id.layout_parent);
        bannerViewPager = root.findViewById(R.id.frag1_head);
        childTxt = root.findViewById(R.id.toolbar_title);
        childImg = root.findViewById(R.id.frag1_child_avatar);
        parentImg = root.findViewById(R.id.frag1_parent_avater);
        initSize();

        for (int i = 0; i < 2; i++) {
            RoundAngleLayout imageView = new RoundAngleLayout(mActivity, 10);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(bannerWid, bannerHei));
            imageView.setBackgroundResource(R.drawable.test_banner);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(mActivity, VideoPlayActivity.class));
                }
            });
            bannerDatas.add(imageView);
        }
        imageBannerAdapter = new ImageBannerAdapter(mActivity, bannerDatas);
        imageBannerAdapter.notifyDataSetChanged();
        bannerViewPager.setAdapter(imageBannerAdapter);

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
        pingAdapter = new ThreeDAdapter(mActivity, pingtuData);
        initPingtudata();// todo??
        pingPager.setAdapter(pingAdapter);
        pingPager.setOffscreenPageLimit(3);
        pingPager.setPageTransformer(true, new GalleryTransformer());
        pingPager.setCurrentItem(2);

        studentLay.setOnClickListener(this);
        parentLay.setOnClickListener(this);
        childTxt.setOnClickListener(this);
        root.findViewById(R.id.go_calendar).setOnClickListener(this);
        root.findViewById(R.id.go_xunzhang).setOnClickListener(this);
        root.findViewById(R.id.toolbar_left).setOnClickListener(this);
        root.findViewById(R.id.toolbar_right).setOnClickListener(this);
    }

    /**
     * 获取拼图列表数据
     */
    private void initPingtudata() {
        pingtuData.clear();
        for (int i = 0; i < 5; i++) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.item_pingtu, null);
            ImageView img = view.findViewById(R.id.img_3d);
            pingtuData.add(view);

        }
        pingAdapter.notifyDataSetChanged();
    }

    /**
     * 加载学员列表
     *
     * @param childModels
     */
    public void loadChild(List<ChildModel> childModels) {
        this.childModels.clear();
        this.childModels.addAll(childModels);
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
