package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.UnitTableAdapter;
import cn.com.zwwl.bayuwen.api.ChildCourseApi;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.BaseResponse;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.LessonModel;

public class TraceRecordActivity extends BasicActivityWithTitle {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    UnitTableAdapter adapter;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private int page = 1;
    private String kid;
    List<LessonModel> lessonModels;

    @Override
    protected int setContentView() {
        return R.layout.activity_trace_record;
    }

    @Override
    protected void initView() {
        kid = getIntent().getStringExtra("kid");
        setCustomTitle(getIntent().getStringExtra("title"));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initData() {
        lessonModels = new ArrayList<>();
        adapter = new UnitTableAdapter(lessonModels);
        recyclerView.setAdapter(adapter);
        refresh.setRefreshContent(recyclerView);
        refresh.autoRefresh();
    }

    private void getData() {
        new ChildCourseApi(this, "7018", String.valueOf(page), new ResponseCallBack<BaseResponse>() {
            @Override
            public void result(BaseResponse o, ErrorMsg errorMsg) {
                if (refresh.getState() == RefreshState.Refreshing) refresh.finishRefresh();
                if (refresh.getState() == RefreshState.Loading) refresh.finishLoadMore();
                if (o != null) {
                    if (page != 1) {
                        lessonModels.addAll(o.getLectures());
                    } else {
                        lessonModels.clear();
                        lessonModels.addAll(o.getLectures());
                    }
                }
                adapter.setNewData(lessonModels);
            }
        });
    }

    @Override
    protected void setListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mActivity, FCourseIndexActivity.class));
            }
        });
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                page = 1;
                getData();

            }
        });
        refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                ++page;
                if (page > 5) {
                    refreshlayout.finishLoadMoreWithNoMoreData();
                } else {
                    getData();

                }
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean setParentScrollable() {
        return false;
    }

    @Override
    public void close() {
        finish();
    }

}
