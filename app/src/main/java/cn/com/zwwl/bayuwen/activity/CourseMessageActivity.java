package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.NotifyMessageAdapter;
import cn.com.zwwl.bayuwen.api.ChildMessageApi;
import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.MessageModel;
import cn.com.zwwl.bayuwen.util.ToastUtil;

public class CourseMessageActivity extends BaseActivity {
    @BindView(R.id.recyclerview_id)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.id_back)
    ImageView idBack;
    @BindView(R.id.title_name)
    TextView titleName;
    private int page = 1;
    private int pageSize = 10;
    private String type = "COURSE";
    private String url = UrlUtil.getNotifyMessage();
    private List<MessageModel.ListBean> messageModels;
    private NotifyMessageAdapter notifyMessageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_message);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

        initView1();
        setListener1();
    }

    @Override
    protected void initData() {

    }

    protected void initView1() {
        titleName.setText("课程消息");
        messageModels = new ArrayList<>();
        HttpData();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        notifyMessageAdapter = new NotifyMessageAdapter(messageModels);
        notifyMessageAdapter.setEmptyView(R.layout.empty_message_view, (ViewGroup) recyclerView.getParent());
        recyclerView.setAdapter(notifyMessageAdapter);
        smartRefreshLayout.setRefreshContent(recyclerView);
        smartRefreshLayout.autoRefresh();

    }

    private void HttpData() {
        new ChildMessageApi(this, type, page, pageSize, url, new ResponseCallBack<MessageModel>() {
            @Override
            public void result(MessageModel messageModel, ErrorMsg errorMsg) {
                smartRefreshLayout.finishRefresh();
                smartRefreshLayout.finishLoadMore();
                if (messageModel != null) {
                    messageModels = messageModel.getList();
                    if (messageModels.size() > 0) {
                        notifyMessageAdapter.setNewData(messageModels);
                    } else {
                        smartRefreshLayout.finishRefresh();
                    }
                } else {
                    ToastUtil.showShortToast(errorMsg.getDesc());
                }
            }


        });
    }


    protected void setListener1() {
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                page = 1;
                smartRefreshLayout.setNoMoreData(false);
                HttpData();
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ++page;
                HttpData();
            }
        });

        notifyMessageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Intent intent = new Intent(CourseMessageActivity.this, MessageDetailActivity.class);
                intent.putExtra("content", messageModels.get(position).getContent());
                intent.putExtra("createTime", messageModels.get(position).getCreatedTime());
                intent.putExtra("title", messageModels.get(position).getTitle());
                intent.putExtra("imageUrl", messageModels.get(position).getUrl());
                startActivity(intent);

            }
        });
    }

    @OnClick({R.id.id_back})
    @Override
    public void onClick(View view) {
        finish();
    }

}
