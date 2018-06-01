package cn.com.zwwl.bayuwen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import cn.com.zwwl.bayuwen.activity.UploadPicActivity;
import cn.com.zwwl.bayuwen.activity.VideoPlayActivity;
import cn.com.zwwl.bayuwen.adapter.CompleteCourseAdapter;
import cn.com.zwwl.bayuwen.adapter.CoursePageAdapter;
import cn.com.zwwl.bayuwen.api.MyCourseApi;
import cn.com.zwwl.bayuwen.base.BasicFragment;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.MyCourseModel;
import cn.com.zwwl.bayuwen.util.Tools;
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
    TabLayout tab;
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
    @BindView(R.id.off_status)
    AppCompatTextView offStatus;

    private CompleteCourseAdapter adapter;
    private CoursePageAdapter mViewPagerAdapter;
    private List<Fragment> list = new ArrayList<>();
    private List<KeModel> finishCourse = new ArrayList<>();
    private List<String> mItemTitleList = new ArrayList<>();
    MyCourseModel courseModel;

    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main3, container, false);
    }

    @Override
    protected void initView() {
        tab.setTabMode(TabLayout.MODE_FIXED);
        tab.setTabTextColors(ContextCompat.getColor(activity, R.color.text_color_default), ContextCompat.getColor(activity, R.color.gold));
        tab.setSelectedTabIndicatorColor(ContextCompat.getColor(activity, R.color.gold));
        tab.setupWithViewPager(mViewPager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, OrientationHelper.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getResources(), R.color.white, R.dimen.dp_5, OrientationHelper.VERTICAL));
        adapter = new CompleteCourseAdapter(finishCourse);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {

        new MyCourseApi(activity, new ResponseCallBack<MyCourseModel>() {
            @Override
            public void result(MyCourseModel myCourseModel, ErrorMsg errorMsg) {
                if (myCourseModel!=null){
                    courseModel = myCourseModel;
                    bindView();
                }
            }
        });
    }

    MyCourseModel.UnfinishedBean bean;

    private void bindView() {
        finishCourse = courseModel.getCompleted();
        adapter.setNewData(finishCourse);
        bean = courseModel.getUnfinished().get(0);
        onCourseName.setText(bean.getProducts().getTitle());
        onProgressValue.setText(String.format("课程进度（%s/%s）", bean.getPlan().getCurrent(), bean.getPlan().getCount()));
        onProgress.setMax(bean.getPlan().getCount());
        onProgress.setProgress(bean.getPlan().getCurrent());
        onPer.setText(Tools.parseDecimal((double) bean.getPlan().getCurrent() / bean.getPlan().getCount()));
        mItemTitleList.add("顾问对学生的评价");
        mItemTitleList.add("顾问对家长的评价");
        FgEvaluate evaluate1 = FgEvaluate.newInstance(bean.getComments().getStudent());
        list.add(evaluate1);
        FgEvaluate evaluate2 = FgEvaluate.newInstance(bean.getComments().getParent());
        list.add(evaluate2);
        mViewPagerAdapter = new CoursePageAdapter(getFragmentManager(), list, mItemTitleList);
        mViewPager.setAdapter(mViewPagerAdapter);
        bean = courseModel.getUnfinished().get(1);
        offCourseName.setText(bean.getProducts().getTitle());
        offProgressValue.setText(String.format("课程进度（%s/%s）", bean.getPlan().getCurrent(), bean.getPlan().getCount()));
        offProgress.setMax(bean.getPlan().getCount());
        offProgress.setProgress(bean.getPlan().getCurrent());
        offPer.setText(Tools.parseDecimal((double) bean.getPlan().getCurrent() / bean.getPlan().getCount()));
        feedback.setText(bean.getComments().getJob());
        offStatus.setText(String.format("下次上课时间:%s", bean.getPlan().getNextTime()));
    }

    @Override
    protected void setListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent(activity,TraceRecordActivity.class);
                intent.putExtra("kid",courseModel.getCompleted().get(position).getId());
                intent.putExtra("title",courseModel.getCompleted().get(position).getTitle());
                startActivity(intent);
            }
        });
    }

    /**
     * 带传参的构造方法
     */
    public static MainFrag3 newInstance(String ss) {
        return new MainFrag3();
    }

    @OnClick({R.id.on_arrow, R.id.off_arrow, R.id.on_work, R.id.off_work, R.id.on_trace, R.id.off_trace, R.id.look_video})
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.on_arrow:
                intent.putExtra("kid", courseModel.getUnfinished().get(0).getKid());
                intent.putExtra("title", courseModel.getUnfinished().get(0).getProducts().getTitle());
                intent.setClass(activity, StudyingCourseActivity.class);
                break;
            case R.id.off_arrow:
                intent.putExtra("kid", courseModel.getUnfinished().get(1).getKid());
                intent.putExtra("title", courseModel.getUnfinished().get(1).getProducts().getTitle());
                intent.setClass(activity, StudyingCourseActivity.class);
                break;
            case R.id.on_work:
                intent.putExtra("kid", courseModel.getUnfinished().get(0).getKid());
                intent.setClass(activity, UploadPicActivity.class);
                break;
            case R.id.off_work:
                intent.putExtra("kid", courseModel.getUnfinished().get(1).getKid());
                intent.setClass(activity, UploadPicActivity.class);
                break;
            case R.id.look_video:
                intent.setClass(activity, VideoPlayActivity.class);
                break;
            case R.id.on_trace:
            case R.id.off_trace:
                intent.setClass(activity, TraceRecordActivity.class);
                break;
        }
        startActivity(intent);

    }

}
  
