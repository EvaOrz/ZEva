package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CheckScrollAdapter;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 开票历史页面
 */
public class PiaoHistoryActivity extends BaseActivity {

    private ListView listView;
    private List<KeModel> datas = new ArrayList<>();
    private PiaoHistoryAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piao_history);
        initView();
    }

    private void initView() {
        listView = findViewById(R.id.piao_h_listview);
        adapter = new PiaoHistoryAdapter(mContext);
        listView.setAdapter(adapter);

        datas.add(new KeModel());
        datas.add(new KeModel());
        datas.add(new KeModel());
        handler.sendEmptyMessage(0);

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


    }

    public class PiaoHistoryAdapter extends CheckScrollAdapter<KeModel> {
        protected Context mContext;

        public PiaoHistoryAdapter(Context context) {
            super(context);
            mContext = context;
        }

        public void setData(List<KeModel> mItemList) {
            clear();
            isScroll = false;
            synchronized (mItemList) {
                for (KeModel item : mItemList) {
                    add(item);
                }
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final KeModel item = getItem(position);
            ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout
                    .item_piao_history);

            TextView title = viewHolder.getView(R.id.album_title);
            TextView desc = viewHolder.getView(R.id.album_desc);
            ImageView img = viewHolder.getView(R.id.album_img);
            TextView learn = viewHolder.getView(R.id.album_learn_count);
            TextView per = viewHolder.getView(R.id.album_period);

            return viewHolder.getConvertView();
        }

        public boolean isScroll() {
            return isScroll;
        }

    }


}
