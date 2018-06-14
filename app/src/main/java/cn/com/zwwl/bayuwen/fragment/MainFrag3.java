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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.ConvertClassActivity;
import cn.com.zwwl.bayuwen.activity.MainActivity;
import cn.com.zwwl.bayuwen.activity.MessageActivity;
import cn.com.zwwl.bayuwen.activity.SearchCourseActivity;
import cn.com.zwwl.bayuwen.activity.StudyingCourseActivity;
import cn.com.zwwl.bayuwen.activity.UnitIndexActivity;
import cn.com.zwwl.bayuwen.activity.UploadPicActivity;
import cn.com.zwwl.bayuwen.activity.VideoPlayActivity;
import cn.com.zwwl.bayuwen.adapter.CompleteCourseAdapter;
import cn.com.zwwl.bayuwen.adapter.CourseIndexAdapter;
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

    @BindView(R.id.study_course)
    RecyclerView studyCourse;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.nest_scroll)
    NestedScrollView nestScroll;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private CompleteCourseAdapter adapter;
    private List<KeModel> finishCourse = new ArrayList<>();
    MyCourseModel courseModel;
    CourseIndexAdapter courseIndexAdapter;

    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main3, container, false);
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(getResources(), R.color.white, R.dimen.dp_5, OrientationHelper.VERTICAL));
        adapter = new CompleteCourseAdapter(finishCourse);
        adapter.setEmptyView(R.layout.empty_view, (ViewGroup) recyclerView.getParent());
        recyclerView.setAdapter(adapter);
        studyCourse.setLayoutManager(new LinearLayoutManager(mContext));
        studyCourse.addItemDecoration(new DividerItemDecoration(getResources(), R.color.white, R.dimen.dp_5, OrientationHelper.VERTICAL));
        courseIndexAdapter = new CourseIndexAdapter(null);
        courseIndexAdapter.setEmptyView(R.layout.empty_view, (ViewGroup) studyCourse.getParent());
        studyCourse.setAdapter(courseIndexAdapter);
        refresh.autoRefresh();
        refresh.finishLoadMore(false);
    }
    @Override
    protected void initData() {
    }

    private void bindView() {
        finishCourse = courseModel.getCompleted();
        courseIndexAdapter.setNewData(courseModel.getUnfinished());
        adapter.setNewData(finishCourse);
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
                            bindView();
                        }
                    }
                });
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(activity, ConvertClassActivity.class);
//                Intent intent = new Intent(activity, FCourseListActivity.class);
                intent.putExtra("type", courseModel.getCompleted().get(position).getId());
                intent.putExtra("title", courseModel.getCompleted().get(position).getName());
                startActivity(intent);
            }
        });
        courseIndexAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                MyCourseModel.UnfinishedBean bean = courseModel.getUnfinished().get(position);
                int type = Tools.getCourseType(bean.getPlan().getOnline(), bean.getPlan().getSource(), bean.getProducts().getEnd_at());
                switch (view.getId()) {
                    case R.id.arrow:
                        intent.setClass(activity, UnitIndexActivity.class);
                        intent.putExtra("kid", courseModel.getUnfinished().get(position).getKid());
                        intent.putExtra("cid", courseModel.getUnfinished().get(position).getPlan().getCurrentLectureId());
                        intent.putExtra("online", Integer.parseInt(courseModel.getUnfinished().get(position).getProducts().getOnline()));
                        intent.putExtra("video", 0);
                        break;
                    case R.id.work:
                        intent.putExtra("kid", bean.getKid());
                        intent.putExtra("cid", bean.getPlan().getCurrentLectureId());
                        intent.setClass(activity, UploadPicActivity.class);
                        break;
                    case R.id.look_video:
                        intent.setClass(activity, VideoPlayActivity.class);
                        intent.putExtra("VideoPlayActivity_url", courseModel.getUnfinished().get(position).getPlan().getPlayUrl());
                        break;
                    case R.id.trace:
                        application.oldKe = bean.getProducts();
                        intent.putExtra("kid", bean.getKid());
                        intent.putExtra("title", bean.getProducts().getTitle());
                        intent.putExtra("course_type", type);
                        intent.putExtra("online", Integer.parseInt(courseModel.getUnfinished().get(position).getProducts().getOnline()));
                        intent.setClass(activity, StudyingCourseActivity.class);
                        break;
                }
                startActivity(intent);
            }
        });
        courseIndexAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(activity, UnitIndexActivity.class);
                intent.putExtra("kid", courseModel.getUnfinished().get(position).getKid());
                intent.putExtra("cid", courseModel.getUnfinished().get(position).getPlan().getCurrentLectureId());
                intent.putExtra("online", Integer.parseInt(courseModel.getUnfinished().get(position).getProducts().getOnline()));
                intent.putExtra("video", 0);
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
    @OnClick({R.id.menu_more,R.id.menu_news,R.id.menu_school,R.id.menu_search})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu_news:
                startActivity(new Intent(activity, MessageActivity.class));
                break;
            case R.id.menu_more:
                ((MainActivity) activity).openDrawer();
                break;
            case R.id.menu_school:
                break;
            case R.id.menu_search:
                startActivity(new Intent(activity, SearchCourseActivity.class));
                break;
        }
    }
}
  
