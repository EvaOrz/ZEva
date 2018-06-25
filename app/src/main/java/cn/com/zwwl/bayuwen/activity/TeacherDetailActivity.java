package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.TCourseListAdapter;
import cn.com.zwwl.bayuwen.api.TDetailListApi;
import cn.com.zwwl.bayuwen.api.TeacherApi;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.listener.OnItemClickListener;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.BaseResponse;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.TeacherModel;
import cn.com.zwwl.bayuwen.widget.decoration.DividerItemDecoration;

/**
 * 教师详情页面
 */
public class TeacherDetailActivity extends BaseActivity implements OnItemClickListener {
    private TextView tv_name;
    private TextView lecture_tv;
    private TextView label_tv;
    private TextView educal_background_tv;
    private TextView teaching_idea_tv;
    private ImageView iv_avatar;
    private SmartRefreshLayout refreshLayout;


    private RecyclerView recyclerView;
    NestedScrollView nestScroll;
    private List<KeModel> keModels = new ArrayList<>();
    private TCourseListAdapter tCourseListAdapter;
    private TeacherModel teacherDetailModel;

    private String tid;
    private int page = 1,totalPage;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    tv_name.setText(teacherDetailModel.getName());
                    lecture_tv.setText(teacherDetailModel.getKe_main());
                    label_tv.setText(teacherDetailModel.getT_style());
                    educal_background_tv.setText(teacherDetailModel.getT_desc());
                    teaching_idea_tv.setText(teacherDetailModel.getT_idea());
                    ImageLoader.display(mContext, iv_avatar, teacherDetailModel.getPic(), R
                            .drawable.avatar_placeholder, R.drawable.avatar_placeholder);
                    break;
            }
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_detail);
        tid = getIntent().getStringExtra("tid");
        initView();
        initData();
        setListener();
    }

    private void setListener() {
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ++page;
                getCourseList();
            }
        });
    }

    @Override
    protected void initData() {
        showLoadingDialog(true);
        new TeacherApi(mContext, tid, new FetchEntryListener() {

            @Override
            public void setData(Entry entry) {
                showLoadingDialog(false);
                if (entry != null && entry instanceof TeacherModel) {
                    teacherDetailModel = (TeacherModel) entry;
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void setError(ErrorMsg error) {
                showLoadingDialog(false);
                if (error != null) showToast(error.getDesc());
            }
        });
        getCourseList();
    }

    /**
     * 获取课程列表
     */
    private void getCourseList() {
        new TDetailListApi(this, tid, page, new ResponseCallBack<BaseResponse>() {
            @Override
            public void result(BaseResponse baseResponse, ErrorMsg errorMsg) {
                refreshLayout.finishLoadMore();
                if (baseResponse != null) {
                    totalPage=baseResponse.getTotal_count()/baseResponse.getPagesize();
                    if (baseResponse.getTotal_count()%baseResponse.getPagesize()>0)
                        totalPage+=1;
                    keModels.addAll(baseResponse.getCourse());
                    tCourseListAdapter.notifyDataSetChanged();
                    if (page ==totalPage)
                        refreshLayout.finishLoadMoreWithNoMoreData();
                }

            }
        });
    }

    private void initView() {
        tv_name = findViewById(R.id.tv_name);
        lecture_tv = findViewById(R.id.lecture_tv);
        label_tv = findViewById(R.id.label_tv);
        educal_background_tv = findViewById(R.id.educal_background_tv);
        teaching_idea_tv = findViewById(R.id.teaching_idea_tv);
        iv_avatar = findViewById(R.id.iv_avatar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(getResources(), R.color
                .gray_line, R
                .dimen.dp_1, OrientationHelper.VERTICAL));
        tCourseListAdapter = new TCourseListAdapter(mContext, keModels);
        recyclerView.setAdapter(tCourseListAdapter);
        refreshLayout = findViewById(R.id.refresh);
         nestScroll = findViewById(R.id.nest_scroll);
        refreshLayout.setRefreshContent(nestScroll);
        tCourseListAdapter.setOnItemClickListener(this);
        findViewById(R.id.teacher_back).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.teacher_back:
                finish();
                break;
        }
    }


    @Override
    public void setOnItemClickListener(View view, int position) {
        Intent intent = new Intent();
        intent.putExtra("CourseDetailActivity_id", keModels.get(position).getKid());
        intent.setClass(mContext, CourseDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void setOnLongItemClickListener(View view, int position) {

    }
}
