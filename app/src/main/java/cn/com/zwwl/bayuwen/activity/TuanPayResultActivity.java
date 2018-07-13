package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CheckScrollAdapter;
import cn.com.zwwl.bayuwen.api.order.GetTuanDiancodesApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.TuanDianModel;
import cn.com.zwwl.bayuwen.model.fm.AlbumModel;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.widget.NoScrollListView;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 团购付费结果页面
 * <p>
 * 垫付页面需要获取垫付成功之后的开团码
 */
public class TuanPayResultActivity extends BaseActivity {
    public static int PAY_SUCCESS = 1;
    public static int PAY_CANCLE = 2;
    public static int PAY_FAILD = 3;
    public static int PAY_UNKNOWN = 0;

    private Timer timer = new Timer();// 初始化定时器
    private int type;
    private TextView tuan_kai_pay;
    private String desc;
    private View tuanma_view;
    private TuanmaAdapter adapter;
    private NoScrollListView listView;
    private List<TuanDianModel> tuanDianModels = new ArrayList<>();

    private String oid = "";
    private String kid = "";
    private String tuanCode = "";
    private boolean isDianFu = false;// 是否是垫付

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuan_pay_result);
        type = getIntent().getIntExtra("TuanPayResultActivity_data", PAY_UNKNOWN);
        desc = getIntent().getStringExtra("TuanPayResultActivity_desc");
        oid = getIntent().getStringExtra("TuanPayResultActivity_oid");
        /**
         * 垫付的数据
         */
        isDianFu = getIntent().getBooleanExtra("is_dianfu", false);
        kid = getIntent().getStringExtra("TuanPayResultActivity_kid");
        tuanCode = getIntent().getStringExtra("TuanPayResultActivity_code");
        initView();
    }

    private void initView() {
        findViewById(R.id.tuan_result_back).setOnClickListener(this);
        tuan_kai_pay = findViewById(R.id.tuan_kai_pay);
        tuan_kai_pay.setOnClickListener(this);
        tuanma_view = findViewById(R.id.tuanma_view);
        listView = findViewById(R.id.tuanma_list);
        TextView status = findViewById(R.id.pay_status);
        status.setText(desc);
        if (type == PAY_FAILD) {
            tuan_kai_pay.setVisibility(View.GONE);
        } else if (type == PAY_SUCCESS) {
            if (isDianFu) {
                tuanma_view.setVisibility(View.VISIBLE);
                initData();
            }
            tuan_kai_pay.setVisibility(View.VISIBLE);
        } else if (type == PAY_CANCLE) {
            tuan_kai_pay.setVisibility(View.GONE);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (Tools.listNotNull(tuanDianModels)) {
                        adapter = new TuanmaAdapter(mContext);
                        listView.setAdapter(adapter);
                        adapter.setData(tuanDianModels);
                        stopTimer();
                    } else {
                        timer.schedule(new TimerTask() {

                            @Override
                            public void run() {
                                initData();
                            }
                        }, 0, 2000);
                    }
                    break;
                case 1:
                    break;
            }
        }
    };

    // 停止定时器
    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            // 一定设置为null，否则定时器不会被回收
            timer = null;
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tuan_result_back:
                finish();
                break;
            case R.id.tuan_kai_pay:
                Intent i = new Intent(mContext, MainActivity.class);
                i.putExtra("Main_frag_no", 3);
                startActivity(i);
                return;
        }
    }

    @Override
    protected void onDestroy() {

        if (type == PAY_FAILD || type == PAY_CANCLE) {// 未完成
            Intent i = new Intent(mContext, OrderDetailActivity.class);
            i.putExtra("OrderDetailActivity_data", oid);
            i.putExtra("OrderDetailActivity_type", 1);
            startActivity(i);
        }
        stopTimer();
        super.onDestroy();
    }

    @Override
    protected void initData() {
        new GetTuanDiancodesApi(mContext, kid, tuanCode, new FetchEntryListListener() {
            @Override
            public void setData(List list) {
                tuanDianModels.clear();
                if (Tools.listNotNull(list)) {
                    tuanDianModels.addAll(list);
                }
                handler.sendEmptyMessage(0);
            }

            @Override
            public void setError(ErrorMsg error) {
                if (error != null)
                    showToast(error.getDesc());

            }
        });
    }

    public class TuanmaAdapter extends CheckScrollAdapter<TuanDianModel> {
        protected Context mContext;

        public TuanmaAdapter(Context context) {
            super(context);
            mContext = context;
        }

        public void setData(List<TuanDianModel> mItemList) {
            clear();
            isScroll = false;
            synchronized (mItemList) {
                for (TuanDianModel item : mItemList) {
                    add(item);
                }
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final TuanDianModel item = getItem(position);
            ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout
                    .item_tuan_dianma);

            TextView title = viewHolder.getView(R.id.tuanma_code);
            TextView desc = viewHolder.getView(R.id.tuanma_copy);
            title.setText(item.getPurchase_code());
            desc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context
                            .CLIPBOARD_SERVICE);
                    cm.setText(item.getPurchase_code());
                    showToast("已复制到剪切板");
                }
            });

            return viewHolder.getConvertView();
        }

        public boolean isScroll() {
            return isScroll;
        }

    }
}