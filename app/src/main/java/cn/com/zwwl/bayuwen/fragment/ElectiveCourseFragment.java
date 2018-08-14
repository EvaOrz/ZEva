package cn.com.zwwl.bayuwen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.BottomIntroAdapter;
import cn.com.zwwl.bayuwen.adapter.CategoryAdapter;
import cn.com.zwwl.bayuwen.adapter.ViewPagerAdapter;
import cn.com.zwwl.bayuwen.api.EcApi;
import cn.com.zwwl.bayuwen.base.BasicFragment;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.EcModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.LastIntromodel;
import cn.com.zwwl.bayuwen.model.SubjectsModel;
import cn.com.zwwl.bayuwen.util.ToastUtil;
import cn.com.zwwl.bayuwen.widget.NoScrollGridView;
import cn.com.zwwl.bayuwen.widget.WrapContentViewPager;


public class ElectiveCourseFragment extends BasicFragment
        implements View.OnClickListener {
    @BindView(R.id.subjectsViewPager)
    WrapContentViewPager subjectsViewPager;
    @BindView(R.id.ec_subjects_dot_layout)
    LinearLayout ec_subjects_dot_layout;
    @BindView(R.id.bottomSubjectsViewPager)
    WrapContentViewPager bottomSubjectsViewPager;
    @BindView(R.id.bottom_subjects_dot_layout)
    LinearLayout bottom_subjects_dot_layout;
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
    private List<LastIntromodel> liList = new ArrayList<LastIntromodel>();

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

    }

    @Override
    protected void initData() {
        new EcApi(getActivity(), new ResponseCallBack<EcModel>() {
            @Override
            public void result(EcModel ecModel, ErrorMsg errorMsg) {
                if (ecModel != null) {
                    suList.clear();
                    suList = ecModel.getSubjects();
                    initShortcutFunction();
                    setDotLayout();
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
