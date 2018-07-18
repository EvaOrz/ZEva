package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CheckScrollAdapter;
import cn.com.zwwl.bayuwen.api.order.FaPiaoHistoryApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.FaPiaoModel;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.util.UmengLogUtil;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 开票历史页面
 */
public class PiaoHistoryActivity extends BaseActivity {

    private ListView listView;
    private List<FaPiaoModel> datas = new ArrayList<>();
    private PiaoHistoryAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piao_history);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {
        listView = findViewById(R.id.piao_h_listview);
        adapter = new PiaoHistoryAdapter(mContext);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(mContext, PiaoKaiActivity.class);
                i.putExtra("PiaoKaiActivity_data", datas.get(position));
                startActivity(i);
            }
        });
        findViewById(R.id.piao_h_back).setOnClickListener(this);
        findViewById(R.id.piao_h_shenqing).setOnClickListener(this);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    adapter.setData(datas);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.piao_h_shenqing:
                UmengLogUtil.logSqFapiao(mContext);
                startActivity(new Intent(mContext, PiaoSelectActivity.class));
                break;
            case R.id.piao_h_back:
                finish();
                break;
        }
    }

    @Override
    protected void initData() {
        showLoadingDialog(true);
        new FaPiaoHistoryApi(mContext, new FetchEntryListListener() {
            @Override
            public void setData(List list) {
                showLoadingDialog(false);
                if (Tools.listNotNull(list)) {
                    datas.clear();
                    datas.addAll(list);
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void setError(ErrorMsg error) {
                showLoadingDialog(false);
                if (error != null) showToast(error.getDesc());

            }
        });

    }

    public class PiaoHistoryAdapter extends CheckScrollAdapter<FaPiaoModel> {
        protected Context mContext;

        public PiaoHistoryAdapter(Context context) {
            super(context);
            mContext = context;
        }

        public void setData(List<FaPiaoModel> mItemList) {
            clear();
            isScroll = false;
            synchronized (mItemList) {
                for (FaPiaoModel item : mItemList) {
                    add(item);
                }
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final FaPiaoModel item = getItem(position);
            ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout
                    .item_piao_history);

            TextView title = viewHolder.getView(R.id.piao_title);
            TextView date = viewHolder.getView(R.id.piao_date);
            TextView price = viewHolder.getView(R.id.piao_price);

            title.setText(item.getInvo_title());
            date.setText(item.getCreated_at());
            price.setText("￥" + item.getInvo_amount());

            return viewHolder.getConvertView();
        }

        public boolean isScroll() {
            return isScroll;
        }

    }


}
