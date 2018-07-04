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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.AllTeacherActivity;
import cn.com.zwwl.bayuwen.activity.CityActivity;
import cn.com.zwwl.bayuwen.activity.MainActivity;
import cn.com.zwwl.bayuwen.activity.MessageActivity;
import cn.com.zwwl.bayuwen.activity.CourseCenterActivity;
import cn.com.zwwl.bayuwen.activity.SearchCourseActivity;
import cn.com.zwwl.bayuwen.activity.VideoPlayActivity;
import cn.com.zwwl.bayuwen.adapter.KeTagGridAdapter;
import cn.com.zwwl.bayuwen.api.KeTagListApi;
import cn.com.zwwl.bayuwen.api.fm.RecommentApi;
import cn.com.zwwl.bayuwen.db.TempDataHelper;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.Index2Model;
import cn.com.zwwl.bayuwen.model.Index2Model.*;
import cn.com.zwwl.bayuwen.model.Index2Model.TagCourseModel.VideoBean.*;
import cn.com.zwwl.bayuwen.model.fm.RecommentModel;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.widget.LoopViewPager;
import cn.com.zwwl.bayuwen.widget.MostGridView;
import cn.com.zwwl.bayuwen.widget.RoundAngleImageView;
import cn.com.zwwl.bayuwen.widget.observable.ObservableScrollView;
import cn.com.zwwl.bayuwen.widget.observable.ObservableScrollViewCallbacks;
import cn.com.zwwl.bayuwen.widget.observable.ScrollState;
import cn.com.zwwl.bayuwen.widget.observable.ScrollUtils;

/**
 * 选课
 */
public class MainFrag2 extends Fragment
        implements View.OnClickListener, ObservableScrollViewCallbacks {
    private Activity mActivity;
    private LoopViewPager bannerView;
    private View root;
    private RelativeLayout mToolbarView;
    private ObservableScrollView mScrollView;
    private LinearLayout contain;
    private TextView cityTv;

    private int mParallaxImageHeight;
    private TagCourseModel part1Model, part2Model, part3Model;
    private List<RecommentModel> bannerData = new ArrayList<>();

    public boolean isCityChanged = false;// 城市状态是否变化

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    contain.removeAllViews();
                    if (part1Model != null) {
                        contain.addView(getIndex2View(part1Model, 0));
                    }
                    if (part2Model != null) {
                        contain.addView(getIndex2View(part2Model, 1));
                    }
                    if (part3Model != null) {
                        contain.addView(getIndex2View(part3Model, 2));
                    }
                    break;
                case 2:
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                            (RelativeLayout
                                    .LayoutParams.MATCH_PARENT, MyApplication.width * 9 / 16);
                    bannerView.setSize(params);
                    List<View> views = new ArrayList<>();
                    for (RecommentModel recommentModel : bannerData) {
                        ImageView r = new ImageView(mActivity);
                        r.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        ImageLoader.display(mActivity, r, recommentModel.getPic());
                        views.add(r);
                    }
                    bannerView.setViewList(views);
                    bannerView.startLoop(true);
                    break;
            }
        }
    };

    private View getIndex2View(final TagCourseModel model, int type) {
        View itemView = LayoutInflater.from(mActivity).inflate(R.layout.item_index2, null);
        MostGridView grid = itemView.findViewById(R.id.index2_grid);
        TextView title = itemView.findViewById(R.id.index2_title);
        LinearLayout con = itemView.findViewById(R.id.index2_contain);

        KeTagGridAdapter adapter = new KeTagGridAdapter(mActivity, type, model.getClassify()
                .getDetails
                        ());
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("SearchCourseActivity_id", model.getClassify().getDetails
                        ().get(position).getId() + "");
                intent.setClass(mActivity, CourseCenterActivity.class);
                startActivity(intent);
            }
        });
        title.setText(model.getVideo().getTitle());
        int width = (MyApplication.width - 60 - 40 * 3) / 3;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width * 9 / 16);
        params.setMargins(20, 0, 20, 0);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(100, 100);
        params1.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        for (final DetailsBeanX detailsBean : model.getVideo().getDetails()) {

            RelativeLayout relative = new RelativeLayout(mActivity);

            RoundAngleImageView imageView = new RoundAngleImageView(mActivity);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setBackgroundColor(mActivity.getResources().getColor(R.color.gray_light));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mActivity, VideoPlayActivity.class);
                    i.putExtra("VideoPlayActivity_url", detailsBean.getUrl());
                    i.putExtra("VideoPlayActivity_pic", detailsBean.getImg());
                    startActivity(i);
                }
            });
            relative.addView(imageView);
            ImageView img = new ImageView(mActivity);
            img.setImageResource(R.mipmap.icon_play);
            relative.addView(img, params1);
            con.addView(relative);
        }

        return itemView;
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
            checkCityChange();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        bannerView.startLoop(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        bannerView.startLoop(true);
        checkCityChange();
        cityTv.setText(TempDataHelper.getCurrentCity(mActivity));
    }

    private void checkCityChange() {
        if (isCityChanged) {
            cityTv.setText(TempDataHelper.getCurrentCity(mActivity));
            getEleCourseList();
            getBannerList();
            isCityChanged = false;
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_main2, container, false);
        initView();
        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getEleCourseList();
        getBannerList();
    }

    private void getBannerList() {
        new RecommentApi(mActivity, new RecommentApi.FetchRecommentListListener() {
            @Override
            public void setData(List<RecommentModel> list) {
                if (Tools.listNotNull(list)) {
                    bannerData.clear();
                    for (RecommentModel r : list) {
                        if (r.getParent().equals("19")) {
                            bannerData.add(r);
                        }
                    }
                    handler.sendEmptyMessage(2);
                }
            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });
    }

    /**
     * 带传参的构造方法
     *
     * @param ss
     * @return
     */
    public static MainFrag2 newInstance(String ss) {
//        Bundle args = new Bundle();
        MainFrag2 fragment = new MainFrag2();
//        fragment.setArguments(args);
        return fragment;
    }

    private void initView() {
        mParallaxImageHeight = MyApplication.width * 9 / 16;
        bannerView = root.findViewById(R.id.frag2_head);
        mToolbarView = root.findViewById(R.id.main2_toolbar);
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor
                (R.color.transparent)));

        mScrollView = root.findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);
        mScrollView.setZoomView(bannerView);
        contain = root.findViewById(R.id.frag2_contain);

        cityTv = mToolbarView.findViewById(R.id.position);
        mToolbarView.findViewById(R.id.menu_more).setOnClickListener(this);
        mToolbarView.findViewById(R.id.menu_news).setOnClickListener(this);
        mToolbarView.findViewById(R.id.menu_school).setOnClickListener(this);
        mToolbarView.findViewById(R.id.menu_search).setOnClickListener(this);
        root.findViewById(R.id.frag2_look_teacher).setOnClickListener(this);
        root.findViewById(R.id.frag2_look_ban).setOnClickListener(this);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        int baseColor = getResources().getColor(R.color.body_gray);
        float alpha = Math.min(1, (float) scrollY / mParallaxImageHeight * 1.4f);
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_news:
                startActivity(new Intent(mActivity, MessageActivity.class));
                break;
            case R.id.menu_more:
                ((MainActivity) mActivity).openDrawer();
                break;
            case R.id.menu_school:
                Intent intent = new Intent(getActivity(), CityActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_search:
                startActivity(new Intent(mActivity, SearchCourseActivity.class));
                break;
            case R.id.frag2_look_teacher:
                goAllTeacher(1);
                break;
            case R.id.frag2_look_ban:
                goAllTeacher(2);
                break;

        }
    }

    private void goAllTeacher(int flag) {
        Intent i = new Intent(mActivity, AllTeacherActivity.class);
        i.putExtra("AllTeacherActivity_data", flag);
        startActivity(i);
    }

    /**
     * 获取课程标签数据
     */
    private void getEleCourseList() {
        new KeTagListApi(getActivity(), new FetchEntryListener() {

            @Override
            public void setData(Entry entry) {
                if (entry != null && entry instanceof Index2Model) {
                    part1Model = ((Index2Model) entry).getPartOne();
                    part2Model = ((Index2Model) entry).getPartTwo();
                    part3Model = ((Index2Model) entry).getPartThree();
                    handler.sendEmptyMessage(0);
                    isCityChanged = false;
                }
            }

            @Override
            public void setError(ErrorMsg error) {
            }
        });
    }

}
