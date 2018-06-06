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
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.FCourseChildAdapter;
import cn.com.zwwl.bayuwen.api.FCourseListApi;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.MyCourseModel;
import cn.com.zwwl.bayuwen.widget.decoration.HSpacesItemDecoration;

/**
 *  已完成课程二级页面l
 *  Created by zhumangmang at 2018/6/2 15:10
 */
public class FCourseListActivity extends BasicActivityWithTitle {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    FCourseChildAdapter adapter;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private String type;
    List<MyCourseModel.UnfinishedBean> lessonModels;

    @Override
    protected int setContentView() {
        return R.layout.activity_trace_record;
    }

    @Override
    protected void initView() {
        type = getIntent().getStringExtra("type");
        setCustomTitle(getIntent().getStringExtra("title"));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new HSpacesItemDecoration(res,R.dimen.dp_5));
    }

    @Override
    protected void initData() {
        lessonModels = new ArrayList<>();
        adapter = new FCourseChildAdapter(lessonModels);
        recyclerView.setAdapter(adapter);
        refresh.setRefreshContent(recyclerView);
        refresh.autoRefresh();
        refresh.setEnableLoadMore(false);
    }

    private void getData() {
        new FCourseListApi(this, type, new ResponseCallBack<List<MyCourseModel.UnfinishedBean>>() {
            @Override
            public void result(List<MyCourseModel.UnfinishedBean> unfinishedBeans, ErrorMsg errorMsg) {
                refresh.finishRefresh();
                if (unfinishedBeans != null && unfinishedBeans.size() != 0) {
                    lessonModels = unfinishedBeans;
                    adapter.setNewData(unfinishedBeans);
                }
            }
        });
    }

    @Override
    protected void setListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent(mActivity,FCourseIndexActivity.class);
                intent.putExtra("kid",lessonModels.get(position).getProducts().getKid());
                intent.putExtra("title",lessonModels.get(position).getProducts().getTitle());
                startActivity(intent);
            }
        });
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                getData();
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
