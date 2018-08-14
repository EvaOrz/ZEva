package cn.com.zwwl.bayuwen.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
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

import butterknife.BindView;
import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.BottomIntroAdapter;
import cn.com.zwwl.bayuwen.adapter.CategoryAdapter;
import cn.com.zwwl.bayuwen.adapter.CommentAdapter;
import cn.com.zwwl.bayuwen.adapter.MyViewPagerAdapter;
import cn.com.zwwl.bayuwen.adapter.ViewPagerAdapter;
import cn.com.zwwl.bayuwen.api.EcApi;
import cn.com.zwwl.bayuwen.api.fm.RecommentApi;
import cn.com.zwwl.bayuwen.base.BasicFragment;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.EcModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.LastIntromodel;
import cn.com.zwwl.bayuwen.model.Materialmodel;
import cn.com.zwwl.bayuwen.model.MidIntroModel;
import cn.com.zwwl.bayuwen.model.ParentCommentModel;
import cn.com.zwwl.bayuwen.model.SubjectsModel;
import cn.com.zwwl.bayuwen.model.fm.RecommentModel;
import cn.com.zwwl.bayuwen.util.DensityUtil;
import cn.com.zwwl.bayuwen.util.ToastUtil;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.util.UriParse;
import cn.com.zwwl.bayuwen.widget.NoScrollGridView;
import cn.com.zwwl.bayuwen.widget.NoScrollListView;
import cn.com.zwwl.bayuwen.widget.RoundAngleImageView;
import cn.com.zwwl.bayuwen.widget.WrapContentViewPager;


public class ElectiveCourseFragment extends BasicFragment
        implements View.OnClickListener {
    @BindView(R.id.bannerViewPager)
    ViewPager bannerViewPager;
    @BindView(R.id.subjectsViewPager)
    WrapContentViewPager subjectsViewPager;
    @BindView(R.id.ec_subjects_dot_layout)
    LinearLayout ec_subjects_dot_layout;
    @BindView(R.id.bottomSubjectsViewPager)
    WrapContentViewPager bottomSubjectsViewPager;
    @BindView(R.id.bottom_subjects_dot_layout)
    LinearLayout bottom_subjects_dot_layout;
    @BindView(R.id.ec_middle_layout)
    LinearLayout ec_middle_layout;
    private List<RecommentModel> bannerData = new ArrayList<>();
    private LayoutInflater mInflater;
    private List<View> mPagerList;
    private int pageCount;//总的页数
    private int pageSize = 6;//每页显示的个数
    private int curIndex = 0;//当前显示的是第几页
    //底部项目介绍
    private List<View> bottomPagerList;
    private int bottomPageCount;//总的页数
    private int bottomPageSize = 4;//每页显示的个数
    private int bottomCurIndex = 0;//当前显示的是第几页

    private List<SubjectsModel> suList = new ArrayList<SubjectsModel>();
    private List<MidIntroModel> miList = new ArrayList<MidIntroModel>();
    private List<LastIntromodel> liList = new ArrayList<LastIntromodel>();
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 2://初始化banner
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                            (MyApplication.width - 80, 340);
                    params.setMargins(40, 0, 40, 0);
                    bannerViewPager.setLayoutParams(params);
                    List<View> views = new ArrayList<>();
                    for (final RecommentModel recommentModel : bannerData) {
                        RoundAngleImageView r = new RoundAngleImageView(getActivity());
                        r.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        ImageLoader.display(getActivity(), r, recommentModel.getPic());
                        r.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                UriParse.clickZwwl(getActivity(), recommentModel.getLink());
                            }
                        });
                        views.add(r);

                    }
                    MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(views);
                    bannerViewPager.setAdapter(viewPagerAdapter);
                    bannerViewPager.setCurrentItem(0);
                    break;
            }
        }

    };

    /**
     * 带传参的构造方法
     *
     * @param ss
     * @return
     */
    public static ElectiveCourseFragment newInstance(String ss) {
//        Bundle args = new Bundle();
        ElectiveCourseFragment fragment = new ElectiveCourseFragment();
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflater = inflater;
        return inflater.inflate(R.layout.fragment_elective_course, container, false);
    }

    @Override
    protected void initView() {
        bannerViewPager.setOffscreenPageLimit(2);
        bannerViewPager.setPageMargin(30);
    }

    @Override
    protected void initData() {
        new RecommentApi(getActivity(), 31, new RecommentApi.FetchRecommentListListener() {
            @Override
            public void setData(List<RecommentModel> list) {
                bannerData.clear();
                if (Tools.listNotNull(list)) {
                    bannerData.addAll(list);
                }
                handler.sendEmptyMessage(2);
            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });
        new EcApi(getActivity(), new ResponseCallBack<EcModel>() {
            @Override
            public void result(EcModel ecModel, ErrorMsg errorMsg) {
                if (ecModel != null) {
                    //顶部项目列表
                    suList.clear();
                    suList = ecModel.getSubjects();
                    initShortcutFunction();
                    setDotLayout();
                    //中间项目信息
                    miList.clear();
                    miList = ecModel.getMidIntro();
                    if (miList.size() > 0) {
                        ec_middle_layout.removeAllViews();
                        ec_middle_layout.setVisibility(View.VISIBLE);
                        for (int i = 0; i < miList.size(); i++) {
                            addMiddleItem(i);
                        }
                    } else {
                        ec_middle_layout.setVisibility(View.GONE);
                    }

                    //底部的项目介绍
                    liList.clear();
                    liList = ecModel.getLastIntro();
                    initBottomSubjects();
                    setBottomDotLayout();

                } else {
                    ToastUtil.showShortToast(errorMsg.getDesc());
                }
            }


        });
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 初始化顶部项目列表
     */
    public void initShortcutFunction() {
        //总的页数=总数/每页数量，并取整
        pageCount = (int) Math.ceil(suList.size() * 1.0 / pageSize);
        mPagerList = new ArrayList<View>();
        for (int i = 0; i < pageCount; i++) {
            // 每个页面都是inflate出一个新实例
            NoScrollGridView gridView = (NoScrollGridView) mInflater.inflate(R.layout.category_gridview, subjectsViewPager, false);
            gridView.setAdapter(new CategoryAdapter(getActivity(), suList, i, pageSize));
            mPagerList.add(gridView);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
        }
        //设置适配器
        subjectsViewPager.setAdapter(new ViewPagerAdapter(mPagerList));

    }

    /**
     * 设置圆点
     */
    public void setDotLayout() {
        //判断是否显示圆点
        if (pageCount > 1) {
            ec_subjects_dot_layout.removeAllViews();
            for (int i = 0; i < pageCount; i++) {
                View view = mInflater.inflate(R.layout.category_dot, null);
                if (i > 0) {
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp.leftMargin = 10;
                    view.setLayoutParams(lp);
                }
                ec_subjects_dot_layout.addView(view);
            }
            // 默认显示第一页
            ec_subjects_dot_layout.getChildAt(0).findViewById(R.id.category_dot_img)
                    .setBackgroundResource(R.drawable.dots_selected);
            ec_subjects_dot_layout.setVisibility(View.VISIBLE);
            subjectsViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                public void onPageSelected(int position) {
                    // 取消圆点选中
                    ec_subjects_dot_layout.getChildAt(curIndex)
                            .findViewById(R.id.category_dot_img)
                            .setBackgroundResource(R.drawable.dots_unselected);
                    // 圆点选中
                    ec_subjects_dot_layout.getChildAt(position)
                            .findViewById(R.id.category_dot_img)
                            .setBackgroundResource(R.drawable.dots_selected);
                    curIndex = position;
                }

                public void onPageScrolled(int arg0, float arg1, int arg2) {
                }

                public void onPageScrollStateChanged(int arg0) {
                }
            });
        } else {
            ec_subjects_dot_layout.setVisibility(View.GONE);
        }

    }

    /**
     * 初试过中间item
     *
     * @param i
     */
    public void addMiddleItem(int i) {
        View view = mInflater.inflate(R.layout.middle_lv_item, null);
        if (i > 0) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.topMargin = DensityUtil.dip2px(getActivity(), 8);
            view.setLayoutParams(lp);
        }
        TextView middle_item_title = (TextView) view.findViewById(R.id.middle_item_title);
        middle_item_title.setText(miList.get(i).getName());
        RoundAngleImageView middle_item_iv = (RoundAngleImageView) view.findViewById(R.id.middle_item_iv);
        ImageLoader.display(getActivity(), middle_item_iv, miList.get(i).getImg());
        TextView middle_item_desc = (TextView) view.findViewById(R.id.middle_item_desc);
        middle_item_desc.setText("了解" + miList.get(i).getName());
        ImageView middle_item_play_iv = (ImageView) view.findViewById(R.id.middle_item_play_iv);
        if (i == 0) {
            middle_item_desc.setBackgroundResource(R.drawable.orange_bg);
            middle_item_play_iv.setImageResource(R.drawable.orange_play);
        } else {
            middle_item_desc.setBackgroundResource(R.drawable.blue_bg);
            middle_item_play_iv.setImageResource(R.drawable.blue_play);
        }
        NoScrollListView middle_item_lv = (NoScrollListView) view.findViewById(R.id.middle_item_lv);
        List<ParentCommentModel> commentList = miList.get(i).getComments();
        CommentAdapter commentAdapter = new CommentAdapter(getActivity(), commentList);
        middle_item_lv.setAdapter(commentAdapter);
        WrapContentViewPager middle_item_viewpager = (WrapContentViewPager) view.findViewById(R.id.middle_item_viewpager);
        LinearLayout middle_item_dot_layout = (LinearLayout) view.findViewById(R.id.middle_item_dot_layout);

        initMiddleIntro(middle_item_viewpager, miList.get(i).getMaterial(), middle_item_dot_layout);

        ec_middle_layout.addView(view);

    }

    /**
     * 初始化中间项目评论截图
     */
    public void initMiddleIntro(WrapContentViewPager viewPager, List<Materialmodel> materials, LinearLayout middle_item_dot_layout) {
        List<View> middleIntroList = new ArrayList<View>();
        for (int i = 0; i < materials.size(); i++) {
            // 每个页面都是inflate出一个新实例
            View view = mInflater.inflate(R.layout.comment_screenshot_item, viewPager, false);
            RoundAngleImageView comment_screenshot_item_iv = (RoundAngleImageView) view.findViewById(R.id.comment_screenshot_item_iv);
            ImageLoader.display(getActivity(), comment_screenshot_item_iv, materials.get(i).getThumb());
            TextView comment_screenshot_item_title = (TextView) view.findViewById(R.id.comment_screenshot_item_title);
            comment_screenshot_item_title.setText(materials.get(i).getTitle());
            middleIntroList.add(view);

        }
        //设置适配器
        viewPager.setAdapter(new ViewPagerAdapter(middleIntroList));
        setMiddleDotLayout(viewPager, middle_item_dot_layout, materials);

    }

    /**
     * 设置圆点
     */
    public void setMiddleDotLayout(WrapContentViewPager middle_item_viewpager, final LinearLayout middle_item_dot_layout, final List<Materialmodel> materials) {
        //判断是否显示圆点
        if (materials.size() > 1) {
            middle_item_dot_layout.removeAllViews();
            for (int i = 0; i < materials.size(); i++) {
                View view = mInflater.inflate(R.layout.category_dot, null);
                if (i > 0) {
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp.leftMargin = 10;
                    view.setLayoutParams(lp);
                }
                middle_item_dot_layout.addView(view);
            }
            // 默认显示第一页
            middle_item_dot_layout.getChildAt(0).findViewById(R.id.category_dot_img)
                    .setBackgroundResource(R.drawable.dots_selected);
            middle_item_dot_layout.setVisibility(View.VISIBLE);
            middle_item_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                public void onPageSelected(int position) {
                    // 圆点选中
                    middle_item_dot_layout.getChildAt(position)
                            .findViewById(R.id.category_dot_img)
                            .setBackgroundResource(R.drawable.dots_selected);
                    for (int i = 0; i < materials.size(); i++) {
                        if (position != i) {
                            middle_item_dot_layout.getChildAt(i)
                                    .findViewById(R.id.category_dot_img)
                                    .setBackgroundResource(R.drawable.dots_unselected);
                        }
                    }

                }

                public void onPageScrolled(int arg0, float arg1, int arg2) {
                }

                public void onPageScrollStateChanged(int arg0) {
                }
            });
        } else {
            middle_item_dot_layout.setVisibility(View.GONE);
        }

    }

    /**
     * 初始化底部项目列表
     */
    public void initBottomSubjects() {
        //总的页数=总数/每页数量，并取整
        bottomPageCount = (int) Math.ceil(liList.size() * 1.0 / bottomPageSize);
        bottomPagerList = new ArrayList<View>();
        for (int i = 0; i < bottomPageCount; i++) {
            // 每个页面都是inflate出一个新实例
            NoScrollGridView gridView = (NoScrollGridView) mInflater.inflate(R.layout.bottom_gridview, bottomSubjectsViewPager, false);
//            gridView.setNumColumns(2);
            gridView.setAdapter(new BottomIntroAdapter(getActivity(), liList, i, bottomPageSize));
            bottomPagerList.add(gridView);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
        }
        //设置适配器
        bottomSubjectsViewPager.setAdapter(new ViewPagerAdapter(bottomPagerList));

    }

    /**
     * 设置圆点
     */
    public void setBottomDotLayout() {
        //判断是否显示圆点
        if (bottomPageCount > 1) {
            bottom_subjects_dot_layout.removeAllViews();
            for (int i = 0; i < bottomPageCount; i++) {
                View view = mInflater.inflate(R.layout.category_dot, null);
                if (i > 0) {
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp.leftMargin = 10;
                    view.setLayoutParams(lp);
                }
                bottom_subjects_dot_layout.addView(view);
            }
            // 默认显示第一页
            bottom_subjects_dot_layout.getChildAt(0).findViewById(R.id.category_dot_img)
                    .setBackgroundResource(R.drawable.dots_selected);
            bottom_subjects_dot_layout.setVisibility(View.VISIBLE);
            bottomSubjectsViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                public void onPageSelected(int position) {
                    // 取消圆点选中
                    bottom_subjects_dot_layout.getChildAt(bottomCurIndex)
                            .findViewById(R.id.category_dot_img)
                            .setBackgroundResource(R.drawable.dots_unselected);
                    // 圆点选中
                    bottom_subjects_dot_layout.getChildAt(position)
                            .findViewById(R.id.category_dot_img)
                            .setBackgroundResource(R.drawable.dots_selected);
                    bottomCurIndex = position;
                }

                public void onPageScrolled(int arg0, float arg1, int arg2) {
                }

                public void onPageScrollStateChanged(int arg0) {
                }
            });
        } else {
            bottom_subjects_dot_layout.setVisibility(View.GONE);
        }

    }


}
