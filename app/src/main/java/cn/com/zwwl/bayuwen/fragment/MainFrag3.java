package cn.com.zwwl.bayuwen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.StudyingCourseActivity;
import cn.com.zwwl.bayuwen.activity.TraceRecordActivity;
import cn.com.zwwl.bayuwen.adapter.CompleteCourseAdapter;
import cn.com.zwwl.bayuwen.adapter.CoursePageAdapter;
import cn.com.zwwl.bayuwen.api.MyCourseApi;
import cn.com.zwwl.bayuwen.base.BasicFragment;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.StudyCourseModel;
import cn.com.zwwl.bayuwen.view.PagerSlidingTabStrip;
import cn.com.zwwl.bayuwen.widget.decoration.DividerItemDecoration;

import static cn.com.zwwl.bayuwen.MyApplication.mContext;

/**
 * 课程追踪入口
 * Created by zhumangmang at 2018/5/29 15:24
 */
public class MainFrag3 extends BasicFragment {

    @BindView(R.id.praiseTv)
    AppCompatTextView praiseTv;
    @BindView(R.id.on_course_name)
    AppCompatTextView onCourseName;
    @BindView(R.id.on_progress_value)
    AppCompatTextView onProgressValue;
    @BindView(R.id.on_progress)
    ProgressBar onProgress;
    @BindView(R.id.on_per)
    AppCompatTextView onPer;
    @BindView(R.id.tab)
    PagerSlidingTabStrip tab;
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;
    @BindView(R.id.on_work)
    AppCompatButton onWork;
    @BindView(R.id.on_trace)
    AppCompatButton onTrace;
    @BindView(R.id.off_course_name)
    AppCompatTextView offCourseName;
    @BindView(R.id.off_progress_value)
    AppCompatTextView offProgressValue;
    @BindView(R.id.off_progress)
    ProgressBar offProgress;
    @BindView(R.id.off_per)
    AppCompatTextView offPer;
    @BindView(R.id.feedback)
    AppCompatTextView feedback;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private CompleteCourseAdapter adapter;
    private CoursePageAdapter mViewPagerAdapter;
    private List<Fragment> list = new ArrayList<>();
    private List<KeModel> finishCourse = new ArrayList<>();
    private List<String> mItemTitleList = new ArrayList<>();
    StudyCourseModel courseModel;

    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main3, container, false);
    }

    @Override
    protected void initView() {
        mItemTitleList.add("顾问对学生的评价");
        mItemTitleList.add("顾问对家长的评价");
        FgEvaluate evaluate1 = FgEvaluate.newInstance("ASJDFOISDJFIJFDODJOIJASF");
        list.add(evaluate1);
        FgEvaluate evaluate2 = FgEvaluate.newInstance("ASJDFOISDJFIJFDasdfdsODJOIJASF");
        list.add(evaluate2);
        mViewPagerAdapter = new CoursePageAdapter(getFragmentManager(), list, mItemTitleList);
        mViewPager.setAdapter(mViewPagerAdapter);
        tab.setViewPager(mViewPager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, OrientationHelper.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getResources(), R.color.white, R.dimen.dp_5, OrientationHelper.VERTICAL));
        adapter = new CompleteCourseAdapter( finishCourse);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {

        new MyCourseApi(activity, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                if (entry != null) {
                    courseModel = (StudyCourseModel) entry;
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bindView();
                        }
                    });

                }
            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });
    }

    private void bindView() {
        finishCourse=courseModel.getCompleted();
      adapter.setNewData(finishCourse);
    }

    @Override
    protected void setListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getActivity(), TraceRecordActivity.class));
            }
        });
    }

    /**
     * 带传参的构造方法
     */
    public static MainFrag3 newInstance(String ss) {
        return new MainFrag3();
    }

    @OnClick({R.id.on_arrow, R.id.off_arrow})
    @Override
    public void onClick(View view) {
        startActivity(new Intent(activity, StudyingCourseActivity.class));
    }
}
  
