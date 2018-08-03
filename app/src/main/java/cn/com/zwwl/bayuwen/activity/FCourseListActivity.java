package cn.com.zwwl.bayuwen.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.FCourseChildAdapter;
import cn.com.zwwl.bayuwen.api.FCourseListApi;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.MyCourseModel;
import cn.com.zwwl.bayuwen.widget.decoration.HSpacesItemDecoration;

/**
 * 已完成课程二级页面l
 * Created by zhumangmang at 2018/6/2 15:10
 */
public class FCourseListActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    FCourseChildAdapter adapter;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    @BindView(R.id.id_back)
    ImageView idBack;
    @BindView(R.id.title_name)
    TextView titleName;
    private String type;
    private Resources res;
    List<MyCourseModel.UnfinishedBean> lessonModels;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trace_record);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        mActivity = this;
        initView1();
        SetData();
        setListener1();
    }

    @Override
    protected void initData() {

    }


    protected void initView1() {
        res = getResources();
        type = getIntent().getStringExtra("type");
        titleName.setText(getIntent().getStringExtra("title"));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new HSpacesItemDecoration(res, R.dimen.dp_5));
        idBack.setOnClickListener(this);
    }


    protected void SetData() {
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


    protected void setListener1() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mActivity, ReportIndexActivity.class);
                intent.putExtra("kid", lessonModels.get(position).getProducts().getKid());
                intent.putExtra("title", lessonModels.get(position).getProducts().getTitle());
                intent.putExtra("online", lessonModels.get(position).getProducts().getOnline());
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
       switch (view.getId()){
           case R.id.id_back:
               finish();
               break;
       }
    }



}
