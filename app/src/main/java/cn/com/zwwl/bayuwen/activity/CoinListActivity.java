package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CoinAdapter;
import cn.com.zwwl.bayuwen.api.order.CoinListApi;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.CoinModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 积分列表页面
 */
public class CoinListActivity extends BaseActivity {

    private SmartRefreshLayout refresh;
    private ListView listView;
    private CoinAdapter adapter;
    private int page = 1;
    private Activity mActivity;
    private List<CoinModel> yueModels = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        mActivity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_list);
        initView();
    }

    private void initView() {
        findViewById(R.id.coin_back).setOnClickListener(this);
        listView = findViewById(R.id.coin_listview);
        adapter = new CoinAdapter(mContext);
        listView.setAdapter(adapter);
        refresh = findViewById(R.id.refresh);
        refresh.setRefreshContent(listView);
        refresh.autoRefresh();
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                refresh.setNoMoreData(false);
                page = 1;
                initData();
            }
        });
        refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ++page;
                initData();
            }
        });

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.coin_back:
                finish();
                break;

        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    adapter.setData(yueModels);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void initData() {
        new CoinListApi(mActivity, page, new ResponseCallBack<List<CoinModel>>() {
            @Override
            public void result(List<CoinModel> datas, ErrorMsg errorMsg) {
                refresh.finishRefresh();
                refresh.finishLoadMore();
                if (errorMsg != null) {
                    showToast(errorMsg.getDesc());
                } else {
                    if (page == 1) {
                        yueModels.clear();
                    }
                    yueModels.addAll(datas);
                    handler.sendEmptyMessage(0);
                }
            }
        });
    }
}
