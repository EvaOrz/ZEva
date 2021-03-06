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
import cn.com.zwwl.bayuwen.activity.ReplayListActivity;
import cn.com.zwwl.bayuwen.activity.ReportIndexActivity;
import cn.com.zwwl.bayuwen.activity.StudyingIndexActivity;
import cn.com.zwwl.bayuwen.activity.TraceSearchActivity;
import cn.com.zwwl.bayuwen.activity.UploadPicActivity;
import cn.com.zwwl.bayuwen.activity.VideoPlayActivity;
import cn.com.zwwl.bayuwen.activity.WebActivity;
import cn.com.zwwl.bayuwen.activity.WebReportActivity;
import cn.com.zwwl.bayuwen.activity.WorkDetailsActivity;
import cn.com.zwwl.bayuwen.activity.WorkListActivity;
import cn.com.zwwl.bayuwen.adapter.CompleteCourseAdapter;
import cn.com.zwwl.bayuwen.adapter.CourseIndexAdapter;
import cn.com.zwwl.bayuwen.adapter.LatestReportAdapter;
import cn.com.zwwl.bayuwen.api.MyCourseApi;
import cn.com.zwwl.bayuwen.base.BasicFragment;
import cn.com.zwwl.bayuwen.bean.LiveInfo;
import cn.com.zwwl.bayuwen.db.TempDataHelper;
import cn.com.zwwl.bayuwen.db.UserDataHelper;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.live.CustomizedLiveActivity;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.Index1Model;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.LessonReportModel;
import cn.com.zwwl.bayuwen.model.MyCourseModel;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.util.TimeUtil;
import cn.com.zwwl.bayuwen.util.ToastUtil;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.util.UmengLogUtil;
import cn.com.zwwl.bayuwen.widget.decoration.DividerItemDecoration;

import static cn.com.zwwl.bayuwen.MyApplication.mContext;

/**
 * 课程跟踪入口
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
    @BindView(R.id.position)
    TextView position;
    @BindView(R.id.calendar_yue)
    TextView calendarYue;
    @BindView(R.id.calendar_kecheng_layout)
    LinearLayout calendarLayout;
    @BindView(R.id.report_layout)
    LinearLayout reportLayout;
    //	 @BindView(R.id.report_divider)
//    View reportDivider;
    private CompleteCourseAdapter adapter;
    private List<KeModel> finishCourse = new ArrayList<>();
    MyCourseModel courseModel;
    CourseIndexAdapter courseIndexAdapter;
    LatestReportAdapter reportAdapter;
    private Index1Model.CalendarCourseBean calendarCourseBean;// calendar事件数据
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
            refresh.autoRefresh();// 该tab被切换时一律刷新数据
            position.setText(TempDataHelper.getCurrentCity(mContext));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh.autoRefresh();// 该tab恢复时一律刷新数据
        position.setText(TempDataHelper.getCurrentCity(mContext));
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
        //正在进行的课程
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
        if (reportModels == null || reportModels.size() == 0) {
            reportLayout.setVisibility(View.GONE);
//			reportDivider.setVisibility(View.GONE);
        }
        reportAdapter.setNewData(reportModels);
        calendarLayout.removeAllViews();
        if (calendarCourseBean != null && calendarCourseBean.getCourses().size() > 0) {
            Calendar ss = CalendarTools.fromStringToca(calendarCourseBean.getDate());
            calendarRi.setText(String.valueOf(ss.get(Calendar.DATE)));
            calendarYue.setText(String.format("%s月", ss.get(Calendar.MONTH) + 1));

            for (Index1Model.CalendarCourseBean.CoursesBean coursesBean : calendarCourseBean
                    .getCourses()) {
                TextView tip = new TextView(activity);
                tip.setText(String.format("%s %s-%s", coursesBean.getTitle(), TimeUtil.parseToHm
                        (coursesBean
                                .getClass_start_at()), TimeUtil.parseToHm(coursesBean
                        .getClass_end_at())));
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
                Intent intent = new Intent(activity, WebReportActivity.class);
                if (reportModels.get(position).getType() == 1) {
                    UmengLogUtil.courseReportClick(activity);
                    intent.putExtra("WebActivity_type", 3);
                } else if (reportModels.get(position).getType() == 2) {
                    UmengLogUtil.QiZhongReportClick(activity);
                    intent.putExtra("WebActivity_type", 1);
                } else if (reportModels.get(position).getType() == 3) {
                    UmengLogUtil.QiMoReportClick(activity);
                    intent.putExtra("WebActivity_type", 2);
                }
                intent.putExtra("WebActivity_url", reportModels.get(position).getUrl());
                startActivity(intent);

            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (courseModel != null && Tools.listNotNull(courseModel.getCompleted()) &&
                        courseModel.getCompleted().get(position) instanceof KeModel) {
                    Intent intent = new Intent(activity, FCourseListActivity.class);
                    intent.putExtra("type", courseModel.getCompleted().get(position).getId());
                    intent.putExtra("title", courseModel.getCompleted().get(position).getName());
                    startActivity(intent);
                }
            }
        });
        //正在进行的课程
        courseIndexAdapter.setOnItemChildClickListener(new BaseQuickAdapter
                .OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                MyCourseModel.UnfinishedBean bean = courseModel.getUnfinished().get(position);
                switch (view.getId()) {
                    //item
                    case R.id.linear_bg:
                        application.oldKe = bean.getProducts();
                        intent.putExtra("kid", bean.getKid());
                        intent.putExtra("title", bean.getProducts().getTitle());
                        if ("1".equals(bean.getProducts().getOnline())) {
                            UmengLogUtil.PlayBackClick(activity);
                            intent.setClass(activity, ReplayListActivity.class);
                        } else {
                            intent.setClass(activity, StudyingIndexActivity.class);
                        }
//                        intent.putExtra("online", Integer.parseInt(bean.getProducts().getOnline
// ()));
                        startActivity(intent);
                        break;
                    //作业
                    case R.id.work_title:
                        UmengLogUtil.CourseWorkClick(activity);
                        if (!bean.getPlan().isOpen()) {
                            ToastUtil.showShortToast("该课程尚未开课~");
//                        } else if (bean.getPlan().getJob() != null && bean.getPlan()
//                                .getIs_submit_job() == 1) {
//                            intent.setClass(activity, WorkDetailsActivity.class);
//                            intent.putExtra("model", bean.getPlan().getJob());
//                            startActivity(intent);
                        } else {
                            intent.putExtra("kid", bean.getKid());
//                            intent.putExtra("cid", bean.getPlan().getCurrentLectureId());
//                            intent.setClass(activity, UploadPicActivity.class);
                            intent.setClass(activity, WorkListActivity.class);
                            startActivity(intent);
                        }
                        break;
                    //直播
                    case R.id.course_cover:
                        if (!bean.getPlan().isOpen()) {
                            ToastUtil.showShortToast("该课程尚未开课~");
                        } else {
//                            if ("1".equals(bean.getProducts().getOnline())) {
//                                intent.setClass(activity, WebActivity.class);
//                                intent.putExtra("WebActivity_data", bean.getPlan()
//                                        .getPlayUrl());
//                            } else {
//                                intent.setClass(activity, VideoPlayActivity.class);
//                                intent.putExtra("VideoPlayActivity_url", bean.getPlan()
//                                        .getPlayUrl());
//                            }
//                            startActivity(intent);
                            if ("1".equals(bean.getProducts().getOnline())) {
                                //跳转到原生直播界面
                                LiveInfo liveInfo = new LiveInfo();
                                liveInfo.setUuid(UserDataHelper.getUserLoginInfo(mContext).getUid());
                                liveInfo.setNickname(UserDataHelper.getUserLoginInfo(mContext).getName());
                                liveInfo.setRoomId(bean.getPlan().getRoomId());
                                CustomizedLiveActivity.startCustomizedActivity(getActivity(), liveInfo);
                            }
                        }
                        break;
                    //课题报告
                    case R.id.trace_title:
                        UmengLogUtil.AgoCourseReportClick(activity);
                        if (!bean.getPlan().isOpen()) {
                            ToastUtil.showShortToast("该课程尚未开课~");
                        } else {
                            intent.setClass(activity, ReportIndexActivity.class);
                            intent.putExtra("kid", bean.getKid());
                            intent.putExtra("title", bean.getProducts().getTitle());
                            startActivity(intent);
                        }
                        break;
                    //回放
                    case R.id.playback_title:
                        UmengLogUtil.PlayBackClick(activity);
                        application.oldKe = bean.getProducts();
                        intent.putExtra("kid", bean.getKid());
                        intent.putExtra("title", bean.getProducts().getTitle());
                        intent.setClass(activity, ReplayListActivity.class);
                        startActivity(intent);

                        break;
                }

            }
        });
        //正在进行的课程
        courseIndexAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                application.oldKe = courseModel.getUnfinished().get(position).getProducts();
                Intent intent = new Intent();
                if ("1".equals(courseModel.getUnfinished()
                        .get(position).getProducts().getOnline())) {//线上
                    intent.setClass(activity, ReplayListActivity.class);
                } else {//线下
                    intent.setClass(activity, StudyingIndexActivity.class);
                }
                intent.putExtra("kid", courseModel.getUnfinished().get(position).getKid());
                intent.putExtra("title", courseModel.getUnfinished().get(position).getProducts()
                        .getTitle());
//                intent.putExtra("online", Integer.parseInt(courseModel.getUnfinished()
//                        .get(position).getProducts().getOnline()));

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

    @OnClick({R.id.menu_more, R.id.menu_news, R.id.position, R.id.menu_search, R.id.go_calendar})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go_calendar:
                UmengLogUtil.logRiliClick(mContext);
                startActivity(new Intent(activity, CalendarActivity.class));
                break;
            case R.id.menu_news:
                startActivity(new Intent(activity, MessageActivity.class));
                break;
            case R.id.menu_more:
                ((MainActivity) activity).openDrawer();
                break;
            case R.id.position:
                Intent intent = new Intent(getActivity(), CityActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_search:
                startActivity(new Intent(activity, TraceSearchActivity.class));
                break;
        }
    }

}
  
