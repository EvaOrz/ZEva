package cn.com.zwwl.bayuwen.fragment;

import android.annotation.SuppressLint;
import android.app.ActionBar;
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
import cn.com.zwwl.bayuwen.activity.CourseCenterActivity;
import cn.com.zwwl.bayuwen.activity.MainActivity;
import cn.com.zwwl.bayuwen.activity.MessageActivity;
import cn.com.zwwl.bayuwen.activity.SearchCourseActivity;
import cn.com.zwwl.bayuwen.activity.VideoPlayActivity;
import cn.com.zwwl.bayuwen.adapter.MyViewPagerAdapter;
import cn.com.zwwl.bayuwen.api.KeTagListApi;
import cn.com.zwwl.bayuwen.api.fm.RecommentApi;
import cn.com.zwwl.bayuwen.db.TempDataHelper;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.Index2Model;
import cn.com.zwwl.bayuwen.model.Index2Model.*;
import cn.com.zwwl.bayuwen.model.Index2Model.TagCourseModel.ClassifyBean.*;
import cn.com.zwwl.bayuwen.model.Index2Model.TagCourseModel.VideoBean.*;
import cn.com.zwwl.bayuwen.model.fm.RecommentModel;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.widget.CircleImageView;
import cn.com.zwwl.bayuwen.widget.RoundAngleImageView;

/**
 * 选课
 */
public class MainFrag2 extends Fragment
        implements View.OnClickListener {
    private Activity mActivity;
    private ViewPager bannerView;
    private LinearLayout mLinearPosition;
    private LinearLayout tagView, tagView2, tagView3;
    private LinearLayout videoLayout1, videoLayout2, videoLayout3;
    private View root;
    private RelativeLayout mToolbarView;
    private TextView cityTv;

    private TagCourseModel part1Model, part2Model, part3Model;
    private List<RecommentModel> bannerData = new ArrayList<>();
    private int videoLayoutPadding = 40;
    private int nomalItemWidth = (MyApplication.width - videoLayoutPadding * 4) / 3;

    public boolean isCityChanged = false;// 城市状态是否变化

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (part1Model != null) {
                        tagView.removeAllViews();
                        LinearLayout.LayoutParams tagparams = new LinearLayout.LayoutParams
                                ((MyApplication.width - videoLayoutPadding * 2) / part1Model
                                        .getClassify().getDetails()
                                        .size(), ActionBar.LayoutParams.WRAP_CONTENT);
                        tagparams.weight = 1;
                        for (DetailsBean detailsBean : part1Model.getClassify().getDetails()) {
                            tagView.addView(getTagItemView(detailsBean), tagparams);
                        }

                        videoLayout1.removeAllViews();
                        videoLayout1.setPadding(videoLayoutPadding, 0, 0, videoLayoutPadding);
                        // 宽度稍微缩小一下，为了滑动能看到后面的item
                        int width = nomalItemWidth - 10;
                        LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams
                                (width, width / 2);
                        itemParams.setMargins(0, 0, videoLayoutPadding, 0);
                        for (final DetailsBeanX detailsBean : part1Model.getVideo().getDetails()) {
                            videoLayout1.addView(getVideoItemView(detailsBean), itemParams);
                        }
                    }

                    if (part2Model != null) {
                        tagView2.removeAllViews();
                        tagView2.setPadding(videoLayoutPadding, videoLayoutPadding,
                                videoLayoutPadding, videoLayoutPadding);

                        for (int i = 0; i < part2Model.getClassify().getDetails().size(); i++) {
                            final DetailsBean detailsBean = part2Model.getClassify().getDetails()
                                    .get(i);
                            LinearLayout.LayoutParams tag2Params = new LinearLayout.LayoutParams
                                    (nomalItemWidth, nomalItemWidth / 2);
                            RoundAngleImageView imageView = new RoundAngleImageView(mActivity);
                            ImageLoader.display(mActivity, imageView, detailsBean.getImg());
                            if (i == 1) {
                                tag2Params.setMargins(videoLayoutPadding, 0, videoLayoutPadding, 0);
                            }
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    goTagIndex(detailsBean);
                                }
                            });
                            tagView2.addView(imageView, tag2Params);
                        }

                        videoLayout2.removeAllViews();
                        for (int i = 0; i < part2Model.getVideo().getDetails().size(); i++) {
                            final DetailsBeanX detailsBean = part2Model.getVideo().getDetails()
                                    .get(i);
                            View view = LayoutInflater.from(mActivity).inflate(R.layout
                                    .item_frag2_video, null);
                            view.setPadding(videoLayoutPadding, videoLayoutPadding / 2,
                                    videoLayoutPadding, videoLayoutPadding / 2);
                            view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    goVideoIndex(detailsBean);
                                }
                            });
                            TextView title = view.findViewById(R.id.frag2_video_title);
                            LinearLayout layout = view.findViewById(R.id.frag2_video_layout);
                            layout.addView(getVideoItemView(detailsBean), new LinearLayout
                                    .LayoutParams(nomalItemWidth, nomalItemWidth / 2));
//                            title.setText(detailsBean.);
                            videoLayout2.addView(view);
                            // 不是最后一个，加diliver
                            if (i < part2Model.getVideo().getDetails().size() - 1) {
                                View line = new View(mActivity);
                                line.setBackgroundColor(mActivity.getResources().getColor(R.color
                                        .gray_light));
                                videoLayout2.addView(line, MyApplication.width -
                                        videoLayoutPadding * 2, 1);
                            }
                        }
                    }

                    if (part3Model != null) {
                        tagView3.removeAllViews();
                        tagView3.setPadding(videoLayoutPadding, videoLayoutPadding,
                                videoLayoutPadding, videoLayoutPadding);

                        for (int i = 0; i < part3Model.getClassify().getDetails().size(); i++) {
                            final DetailsBean detailsBean = part3Model.getClassify().getDetails()
                                    .get(i);
                            LinearLayout.LayoutParams tag3Params = new LinearLayout.LayoutParams
                                    (nomalItemWidth, nomalItemWidth);
                            View view = LayoutInflater.from(mActivity).inflate(R.layout
                                            .item_xuanke_tagbg,
                                    null);
                            TextView title = view.findViewById(R.id.xuanke_title);
                            ImageView imageView = view.findViewById(R.id.xuanke_img);
                            ImageLoader.display(mActivity, imageView, detailsBean.getImg());
                            title.setText(detailsBean.getName());
                            if (i == 0) {
                                view.setBackground(mActivity.getResources().getDrawable(R
                                        .drawable.xuanke_bg1));
                            } else if (i == 1) {
                                view.setBackground(mActivity.getResources().getDrawable(R
                                        .drawable.xuanke_bg2));
                                tag3Params.setMargins(videoLayoutPadding, 0, videoLayoutPadding, 0);
                            } else if (i == 2) {
                                view.setBackground(mActivity.getResources().getDrawable(R
                                        .drawable.xuanke_bg3));
                            }
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    goTagIndex(detailsBean);
                                }
                            });
                            tagView3.addView(view, tag3Params);
                        }
                        videoLayout3.removeAllViews();
                        videoLayout3.setPadding(videoLayoutPadding, 0, 0, videoLayoutPadding);
                        LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams
                                (nomalItemWidth, nomalItemWidth / 2);
                        itemParams.setMargins(0, 0, videoLayoutPadding, 0);
                        for (final DetailsBeanX detailsBean : part3Model.getVideo().getDetails()) {
                            videoLayout3.addView(getVideoItemView(detailsBean), itemParams);
                        }

                    }

                    break;
                case 2:// 初始化bannerview
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                            (MyApplication.width - 80, 340);
                    params.setMargins(40, 0, 40, 0);
                    bannerView.setLayoutParams(params);

                    mLinearPosition.removeAllViews();
                    List<View> views = new ArrayList<>();
                    for (RecommentModel recommentModel : bannerData) {
                        RoundAngleImageView r = new RoundAngleImageView(mActivity);
                        r.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        ImageLoader.display(mActivity, r, recommentModel.getPic());
                        views.add(r);

                        ImageView img = new ImageView(getContext());
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams
                                (RelativeLayout.LayoutParams.WRAP_CONTENT,
                                        RelativeLayout.LayoutParams
                                                .WRAP_CONTENT);
                        layoutParams.rightMargin = getResources().getDimensionPixelOffset(R.dimen
                                .dimen_9dp);
                        img.setLayoutParams(layoutParams);
                        img.setBackgroundResource(R.drawable.viewlooper_gray_status);
                        mLinearPosition.addView(img);
                    }
                    MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(views);
                    bannerView.setAdapter(viewPagerAdapter);
                    bannerView.setCurrentItem(0);
                    updateLinearPosition(0);
                    break;
            }
        }
    };

    /**
     * 获取videoview
     *
     * @param detailsBean
     * @return
     */
    private View getVideoItemView(final DetailsBeanX detailsBean) {
        RelativeLayout relative = new RelativeLayout(mActivity);

        RoundAngleImageView imageView = new RoundAngleImageView(mActivity);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setBackgroundColor(mActivity.getResources().getColor(R.color
                .gray_light));
        ImageLoader.display(mActivity, imageView, detailsBean.getImg());
        relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goVideoIndex(detailsBean);
            }
        });
        relative.addView(imageView);
        ImageView img = new ImageView(mActivity);
        img.setImageResource(R.mipmap.icon_play);
        RelativeLayout.LayoutParams playParams = new RelativeLayout.LayoutParams
                (100, 100);
        playParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        relative.addView(img, playParams);
        return relative;
    }

    /**
     * 获取六个tag分类view
     *
     * @return
     */
    private View getTagItemView(final DetailsBean detailsBean) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_frag2_tag, null);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTagIndex(detailsBean);
            }
        });
        TextView title = view.findViewById(R.id.cdetail_t_name);
        CircleImageView img = view.findViewById(R.id.cdetail_t_avatar);

        title.setText(detailsBean.getName());
        if (!TextUtils.isEmpty(detailsBean.getImg()))
            ImageLoader.display(mActivity, img, detailsBean.getImg(), R
                    .mipmap.image_placeholder, R.mipmap.image_placeholder);
        return view;
    }

    private void goTagIndex(DetailsBean detailsBean) {
        Intent intent = new Intent();
        intent.putExtra("SearchCourseActivity_id", detailsBean.getId() + "");
        intent.setClass(mActivity, CourseCenterActivity.class);
        startActivity(intent);
    }

    private void goVideoIndex(DetailsBeanX detailsBeanX) {
        Intent i = new Intent(mActivity, VideoPlayActivity.class);
        i.putExtra("VideoPlayActivity_url", detailsBeanX.getUrl());
        i.putExtra("VideoPlayActivity_pic", detailsBeanX.getImg());
        startActivity(i);
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
    public void onResume() {
        super.onResume();
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
        bannerView = root.findViewById(R.id.frag2_viewpager);
        bannerView.setOffscreenPageLimit(2);
        bannerView.setPageMargin(30);
        mLinearPosition = root.findViewById(R.id.frag2_viewpager_indicator);
        mToolbarView = root.findViewById(R.id.main2_toolbar);
        tagView = root.findViewById(R.id.frag2_tags);
        videoLayout1 = root.findViewById(R.id.ganshou_ketang1);
        tagView2 = root.findViewById(R.id.frag2_tags_2);
        videoLayout2 = root.findViewById(R.id.ganshou_ketang2);
        tagView3 = root.findViewById(R.id.frag2_tags_3);
        videoLayout3 = root.findViewById(R.id.ganshou_ketang3);

        cityTv = mToolbarView.findViewById(R.id.position);
        mToolbarView.findViewById(R.id.menu_more).setOnClickListener(this);
        mToolbarView.findViewById(R.id.menu_news).setOnClickListener(this);
        cityTv.setOnClickListener(this);
        mToolbarView.findViewById(R.id.menu_search).setOnClickListener(this);
        root.findViewById(R.id.frag2_look_teacher).setOnClickListener(this);
        root.findViewById(R.id.frag2_look_ban).setOnClickListener(this);

        bannerView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
    }

    private void updateLinearPosition(int position) {
        for (int i = 0; i < mLinearPosition.getChildCount(); i++) {
            if (i == position) {
                mLinearPosition.getChildAt(i).setBackgroundResource(R.drawable
                        .viewlooper_gold_status);
            } else {
                mLinearPosition.getChildAt(i).setBackgroundResource(R.drawable
                        .viewlooper_gray_status);
            }
        }
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
            case R.id.position:
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
