package cn.com.zwwl.bayuwen.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.CalendarActivity;
import cn.com.zwwl.bayuwen.adapter.ImageBannerAdapter;
import cn.com.zwwl.bayuwen.adapter.MainYixuanKeAdapter;
import cn.com.zwwl.bayuwen.model.AlbumModel;
import cn.com.zwwl.bayuwen.widget.BannerView;
import cn.com.zwwl.bayuwen.widget.NoScrollListView;
import cn.com.zwwl.bayuwen.widget.RoundAngleLayout;
import cn.com.zwwl.bayuwen.widget.newrefresh.EvaViewPager;
import cn.com.zwwl.bayuwen.widget.threed.GalleryTransformer;
import cn.com.zwwl.bayuwen.widget.threed.InfiniteViewPager;
import cn.com.zwwl.bayuwen.widget.threed.PingtuAdapter;
import cn.com.zwwl.bayuwen.widget.threed.ZoomOutPageTransformer;

/**
 *
 */
public class MainFrag1 extends Fragment implements View.OnClickListener {

    private Activity mActivity;
    //    private BannerView bannerView;
    private ViewPager bannerViewPager;
    private ImageBannerAdapter imageBannerAdapter;
    private RoundAngleLayout studentLay, parentLay;// banner位的学生信息栏
    private TextView notificationTv;
    private View root;
    private NoScrollListView yixuanKeListView;
    private MainYixuanKeAdapter yixuanKeAdapter;
    private InfiniteViewPager pingPager;
    private PingtuAdapter pingAdapter;

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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

                Log.e("ssssss", width + "___" + studentLay.getHeight());
                bannerWid = MyApplication.width - width * 2 - 5 * 4 * 2;
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
                studentLay.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, bannerHei));
                parentLay.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, bannerHei));

            }
        });


    }

    private void initView() {
        studentLay = root.findViewById(R.id.layout_student);
        parentLay = root.findViewById(R.id.layout_parent);
        bannerViewPager = root.findViewById(R.id.frag1_head);
        initSize();

        for (int i = 0; i < 2; i++) {
            RoundAngleLayout imageView = new RoundAngleLayout(mActivity,10);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(bannerWid, bannerHei));
            imageView.setBackgroundResource(R.drawable.test_banner);
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
        pingAdapter = new PingtuAdapter(mActivity);
        pingPager.setAdapter(pingAdapter);
        pingPager.setOffscreenPageLimit(3);
//        pingPager.setPageTransformer(true, new ZoomOutPageTransformer());
        pingPager.setPageTransformer(true, new GalleryTransformer());

        root.findViewById(R.id.go_calendar).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_calendar:
                startActivity(new Intent(mActivity, CalendarActivity.class));
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
