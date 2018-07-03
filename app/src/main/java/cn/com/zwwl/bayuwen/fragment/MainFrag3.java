package cn.com.zwwl.bayuwen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.CalendarActivity;
import cn.com.zwwl.bayuwen.activity.CityActivity;
import cn.com.zwwl.bayuwen.activity.FCourseListActivity;
import cn.com.zwwl.bayuwen.activity.MainActivity;
import cn.com.zwwl.bayuwen.activity.MessageActivity;
import cn.com.zwwl.bayuwen.activity.ReportIndexActivity;
import cn.com.zwwl.bayuwen.activity.StudyingIndexActivity;
import cn.com.zwwl.bayuwen.activity.TraceSearchActivity;
import cn.com.zwwl.bayuwen.activity.UploadPicActivity;
import cn.com.zwwl.bayuwen.activity.VideoPlayActivity;
import cn.com.zwwl.bayuwen.activity.WebActivity;
import cn.com.zwwl.bayuwen.adapter.CompleteCourseAdapter;
import cn.com.zwwl.bayuwen.adapter.CourseIndexAdapter;
import cn.com.zwwl.bayuwen.adapter.LatestReportAdapter;
import cn.com.zwwl.bayuwen.api.MyCourseApi;
import cn.com.zwwl.bayuwen.base.BasicFragment;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.Index1Model;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.LessonReportModel;
import cn.com.zwwl.bayuwen.model.MyCourseModel;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.util.TimeUtil;
import cn.com.zwwl.bayuwen.util.ToastUtil;
import cn.com.zwwl.bayuwen.widget.decoration.DividerItemDecoration;

import static cn.com.zwwl.bayuwen.MyApplication.mContext;

/**
 * 课程追踪入口
 * Created by zhumangmang at 2018/5/29 15:24
 */
public class MainFrag3 extends BasicFragment {

    @BindView(R.id.study_course)
    RecyclerView studyCourse;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.nest_scroll)
    NestedScrollView nestScroll;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    @BindView(R.id.report)
    RecyclerView report;
    @BindView(R.id.calendar_ri)
    TextView calendarRi;
    @BindView(R.id.calendar_yue)
    TextView calendarYue;
    @BindView(R.id.calendar_kecheng_layout)
    LinearLayout calendarLayout;
    @BindView(R.id.report_layout)
    LinearLayout reportLayout;
    private CompleteCourseAdapter adapter;
    private List<KeModel> finishCourse = new ArrayList<>();
    MyCourseModel courseModel;
    CourseIndexAdapter courseIndexAdapter;
    LatestReportAdapter reportAdapter;
    private Index1Model.CalendarCourseBean calendarCourseBean;// calendar事件数据
    public boolean isCityChanged = false;// 城市状态是否变化
    private List<LessonReportModel> reportModels;

    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main3, container, false);
    }


    /**
     * 默认fragment创建的时候是可见的，但是不会调用该方法！切换可见状态的时候会调用，但是调用onResume，onPause的时候却不会调用
     */
    @Override

    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (isCityChanged) {// 切换城市之后 要重新获取课程tag list,点赞排行不必重新获取
                refresh.autoRefresh();
            }
        }
    }

    @Override
    protected void initView() {
        report.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
        report.addItemDecoration(new DividerItemDecoration(getResources(), R.color.white, R
                .dimen.dp_5, OrientationHelper.HORIZONTAL));
        report.setNestedScrollingEnabled(false);
        reportAdapter = new LatestReportAdapter(null);
        report.setAdapter(reportAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(new DividerItemDecoration(getResources(), R.color.white, R
                .dimen.dp_5, OrientationHelper.VERTICAL));
        adapter = new CompleteCourseAdapter(finishCourse);
        adapter.setEmptyView(R.layout.empty_view, (ViewGroup) recyclerView.getParent());
        recyclerView.setAdapter(adapter);

        studyCourse.setLayoutManager(new LinearLayoutManager(mContext));
        studyCourse.setNestedScrollingEnabled(false);
        studyCourse.addItemDecoration(new DividerItemDecoration(getResources(), R.color.white, R
                .dimen.dp_8, OrientationHelper.VERTICAL));
        courseIndexAdapter = new CourseIndexAdapter(null);
        courseIndexAdapter.setEmptyView(R.layout.empty_view, (ViewGroup) studyCourse.getParent());
        studyCourse.setAdapter(courseIndexAdapter);
        refresh();
        refresh.setEnableLoadMore(false);
    }

    @Override
    protected void initData() {
    }

    private void bindView() {
        if (reportModels == null || reportModels.size() == 0) reportLayout.setVisibility(View.GONE);
        reportAdapter.setNewData(reportModels);
        calendarLayout.removeAllViews();
        if (calendarCourseBean != null && calendarCourseBean.getCourses().size() > 0) {
            Calendar ss = CalendarTools.fromStringToca(calendarCourseBean.getDate());
            calendarRi.setText(String.valueOf(ss.get(Calendar.DATE)));
            calendarYue.setText(String.format("%s月", ss.get(Calendar.MONTH)));

            for (Index1Model.CalendarCourseBean.CoursesBean coursesBean : calendarCourseBean
                    .getCourses()) {
                TextView tip = new TextView(activity);
                tip.setText(String.format("%s %s-%s", coursesBean.getTitle(), TimeUtil.parseToHm(coursesBean
                        .getClass_start_at()), TimeUtil.parseToHm(coursesBean.getClass_end_at())));
                tip.setTextColor(getResources().getColor(R.color.gray_dark));
                tip.setTextSize(14);
                calendarLayout.addView(tip);
            }
        } else {
            TextView tip = new TextView(activity);
            tip.setText("请添加课程日历");
            tip.setTextColor(getResources().getColor(R.color.gray_dark));
            tip.setTextSize(14);
            calendarLayout.addView(tip);
        }
        finishCourse = courseModel.getCompleted();
        courseIndexAdapter.setNewData(courseModel.getUnfinished());
        adapter.setNewData(finishCourse);
    }

    public void refresh() {
        refresh.autoRefresh();
    }

    @Override
    protected void setListener() {
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                new MyCourseApi(activity, new ResponseCallBack<MyCourseModel>() {
                    @Override
                    public void result(MyCourseModel myCourseModel, ErrorMsg errorMsg) {
                        refreshLayout.finishRefresh();
                        if (myCourseModel != null) {
                            nestScroll.setVisibility(View.VISIBLE);
                            courseModel = myCourseModel;
                            calendarCourseBean = courseModel.getCalendarCourse();
                            reportModels = courseModel.getClassReport();
                            bindView();
                        }
                    }
                });
            }
        });
        reportAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(activity, WebActivity.class);
                intent.putExtra("WebActivity_title", reportModels.get(position).getReport_name());
                intent.putExtra("WebActivity_data", reportModels.get(position).getUrl());
                startActivity(intent);

            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(activity, FCourseListActivity.class);
                intent.putExtra("type", courseModel.getCompleted().get(position).getId());
                intent.putExtra("title", courseModel.getCompleted().get(position).getName());
                startActivity(intent);
            }
        });
        courseIndexAdapter.setOnItemChildClickListener(new BaseQuickAdapter
                .OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                MyCourseModel.UnfinishedBean bean = courseModel.getUnfinished().get(position);
                switch (view.getId()) {
                    case R.id.arrow:
                        application.oldKe = bean.getProducts();
                        intent.setClass(activity, StudyingIndexActivity.class);
                        intent.putExtra("kid", bean.getKid());
                        intent.putExtra("title", bean.getProducts().getTitle());
                        intent.putExtra("online", Integer.parseInt(bean.getProducts().getOnline()));
                        startActivity(intent);
                        break;
                    case R.id.work:
                        if (!bean.getPlan().isOpen()) {
                            ToastUtil.showShortToast("该课程尚未开课~");
                        } else if (bean.getPlan().getIs_submit_job() == 1) {
                            ToastUtil.showShortToast("作业已存在，不能重复上传~");
                        } else {
                            intent.putExtra("kid", bean.getKid());
                            intent.putExtra("cid", bean.getPlan().getCurrentLectureId());
                            intent.setClass(activity, UploadPicActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case R.id.look_video:
                        if (!bean.getPlan().isOpen()) {
                            ToastUtil.showShortToast("该课程尚未开课~");
                        } else {
                            intent.setClass(activity, VideoPlayActivity.class);
                            intent.putExtra("VideoPlayActivity_url", bean.getPlan().getPlayUrl());
                            startActivity(intent);
                        }
                        break;
                    case R.id.trace:
                        if (!bean.getPlan().isOpen()) {
                            ToastUtil.showShortToast("该课程尚未开课~");
                        } else {
                            intent.setClass(activity, ReportIndexActivity.class);
                            intent.putExtra("kid", bean.getKid());
                            intent.putExtra("title", bean.getProducts().getTitle());
                            startActivity(intent);
                        }
                        break;
                }

            }
        });
        courseIndexAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                application.oldKe = courseModel.getUnfinished().get(position).getProducts();
                Intent intent = new Intent(activity, StudyingIndexActivity.class);
                intent.putExtra("kid", courseModel.getUnfinished().get(position).getKid());
                intent.putExtra("title", courseModel.getUnfinished().get(position).getProducts().getTitle());
                intent.putExtra("online", Integer.parseInt(courseModel.getUnfinished()
                        .get(position).getProducts().getOnline()));
                startActivity(intent);
            }
        });
    }

    /**
     * 带传参的构造方法
     */
    public static MainFrag3 newInstance() {
        return new MainFrag3();
    }

    @OnClick({R.id.menu_more, R.id.menu_news, R.id.menu_school, R.id.menu_search, R.id.go_calendar})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go_calendar:
                startActivity(new Intent(activity, CalendarActivity.class));
                break;
            case R.id.menu_news:
                startActivity(new Intent(activity, MessageActivity.class));
                break;
            case R.id.menu_more:
                ((MainActivity) activity).openDrawer();
                break;
            case R.id.menu_school:
                Intent intent = new Intent(getActivity(), CityActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_search:
                startActivity(new Intent(activity, TraceSearchActivity.class));
                break;
        }
    }

}
  
