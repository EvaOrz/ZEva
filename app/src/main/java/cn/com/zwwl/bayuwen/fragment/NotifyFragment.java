package cn.com.zwwl.bayuwen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.CityActivity;
import cn.com.zwwl.bayuwen.activity.CourseMessageActivity;
import cn.com.zwwl.bayuwen.activity.InteractActivity;
import cn.com.zwwl.bayuwen.activity.MessageDetailActivity;
import cn.com.zwwl.bayuwen.activity.OtherMessageActivity;
import cn.com.zwwl.bayuwen.activity.SysMessageActivity;
import cn.com.zwwl.bayuwen.adapter.NotifyMessageAdapter;
import cn.com.zwwl.bayuwen.api.NotifyMessageApi;
import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.base.BasicFragment;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.MessageModel;
import cn.com.zwwl.bayuwen.util.ToastUtil;

//
public class NotifyFragment extends BasicFragment {

    @BindView(R.id.system_notific_id)
    LinearLayout system_message_id;
    @BindView(R.id.interact_message_id)
    LinearLayout interact_message_id;
    @BindView(R.id.course_message_id)
    LinearLayout course_message_id;
    @BindView(R.id.other_message_id)
    LinearLayout other_message_id;
    @BindView(R.id.all_message_list)
    RecyclerView all_message_list;
    @BindView(R.id.refresh)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.all_message_lin)
    LinearLayout all_message_lin;
    Unbinder unbinder;

    private NotifyMessageAdapter notifyMessageAdapter;


    private String url = UrlUtil.getNotifyMessage();
    private List<MessageModel.ListBean> messageModels;

    private int page = 1;
    private int pageSize = 10;


    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notify, container, false);
    }

    @Override
    protected void initView() {

        messageModels = new ArrayList<>();
        HttpData();

        all_message_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        all_message_list.setItemAnimator(new DefaultItemAnimator());
        notifyMessageAdapter = new NotifyMessageAdapter(null);
        notifyMessageAdapter.setEmptyView(R.layout.empty_view, (ViewGroup) all_message_list.getParent());
        all_message_list.setAdapter(notifyMessageAdapter);
        refreshLayout.setRefreshContent(all_message_list);
        refreshLayout.autoRefresh();
        refreshLayout.finishLoadMore(false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                page = 1;
                refreshLayout.setNoMoreData(false);
                HttpData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ++page;
                HttpData();
            }
        });

        notifyMessageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Intent intent = new Intent(activity, MessageDetailActivity.class);
                intent.putExtra("content", messageModels.get(position).getContent());
                intent.putExtra("createTime", messageModels.get(position).getCreatedTime());
                intent.putExtra("title", messageModels.get(position).getTitle());
                intent.putExtra("imageUrl", messageModels.get(position).getUrl());
                startActivity(intent);

            }
        });
    }

    private void HttpData() {
        new NotifyMessageApi(getActivity(), page, pageSize, url, new ResponseCallBack<MessageModel>() {
            @Override
            public void result(MessageModel messageModel, ErrorMsg errorMsg) {
                refreshLayout.finishLoadMore();
                refreshLayout.finishRefresh();
                if (messageModel != null) {
                    messageModels = messageModel.getList();
                    notifyMessageAdapter.setNewData(messageModels);
                } else {
                    ToastUtil.showShortToast(errorMsg.getDesc());
                }
            }


        });
    }
@OnClick({R.id.system_notific_id,R.id.interact_message_id,R.id.course_message_id,R.id.other_message_id})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_notific_id:
                Intent intent = new Intent(getActivity(), SysMessageActivity.class);
                startActivity(intent);
                break;
            case R.id.interact_message_id: //互动通知
                Intent intent1 = new Intent(getActivity(), InteractActivity.class);
                startActivity(intent1);
                break;
            case R.id.course_message_id:
                Intent intent2 = new Intent(getActivity(), CourseMessageActivity.class);
                startActivity(intent2);
                break;
            case R.id.other_message_id:
                Intent intent3 = new Intent(getActivity(), OtherMessageActivity.class);
                startActivity(intent3);
                break;

        }
    }

}
