package cn.com.zwwl.bayuwen.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CheckScrollAdapter;
import cn.com.zwwl.bayuwen.api.order.TuiReasonApi;
import cn.com.zwwl.bayuwen.api.order.TuifeeApi;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.OrderForMyListModel;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.view.TuifeeReasinPopWindow;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 选择要退费的课程页面
 */
public class OrderTuifeeListActivity extends BaseActivity implements TuifeeReasinPopWindow
        .OnReasonPickListener {

    private ListView listView;
    private TuifeeAdapter adapter;
    private OrderForMyListModel orderForMyListModel;
    private List<String> offlineReasons = new ArrayList<>(), onlineReasons = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tuifee_list);
        if (getIntent().getSerializableExtra("OrderTuifeeActivity_data") != null) {
            orderForMyListModel = (OrderForMyListModel) getIntent().getSerializableExtra
                    ("OrderTuifeeActivity_data");
            initView();
            initData();
        } else {
            finish();
        }

    }

    private void initView() {
        findViewById(R.id.tuifee_l_back).setOnClickListener(this);
        listView = findViewById(R.id.tuifee_l_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KeModel keModel = orderForMyListModel.getKeModels().get(position);
                if (keModel.getOnline().equals("0")) {// 线下
                    new TuifeeReasinPopWindow(mContext, offlineReasons, keModel.getDetailId(),
                            OrderTuifeeListActivity.this);
                } else {// 线上
                    new TuifeeReasinPopWindow(mContext, onlineReasons, keModel.getDetailId(),
                            OrderTuifeeListActivity.this);
                }

            }
        });
        adapter = new TuifeeAdapter(mContext);
        listView.setAdapter(adapter);
        /**
         *  0:未退款 1:部分退款 2:全额退款 3:退款中 4:退款被拒绝
         *  只有0、1、4可以显示
         */
        List<KeModel> data = new ArrayList<>();
        for (KeModel k : orderForMyListModel.getKeModels()) {
            if (k.getRefund().equals("0") || k.getRefund().equals("1") || k.getRefund().equals
                    ("4")) {
                data.add(k);
            }
        }
        adapter.setData(data);

    }

    @Override
    public void onReasonPick(String reason, String detail_id, int type) {
        doTuifee(reason, detail_id, type);
    }


    private void doTuifee(String reason, String detail_id, int type) {
        showLoadingDialog(true);
        new TuifeeApi(mContext, type, detail_id, reason, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
            }

            @Override
            public void setError(ErrorMsg error) {
                showLoadingDialog(false);
                if (error == null) {
                    finish();
                } else showToast(error.getDesc());

            }
        });
    }

    @Override
    protected void initData() {
        // 退款页面，先获取退款理由
        new TuiReasonApi(mContext, new FetchEntryListListener() {
            @Override
            public void setData(List list) {
                if (Tools.listNotNull(list)) {
                    for (int i = 0; i < list.size(); i++) {
                        ErrorMsg e = (ErrorMsg) list.get(i);
                        if (e.getNo() == 0) {// offline
                            offlineReasons.add(e.getDesc());
                        } else {
                            onlineReasons.add(e.getDesc());
                        }
                    }
                }

            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });
    }

    public class TuifeeAdapter extends CheckScrollAdapter<KeModel> {
        protected Context mContext;

        public TuifeeAdapter(Context context) {
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
                    .item_course_for_order);
            ImageView tag = viewHolder.getView(R.id.item_order_tag);
            TextView title = viewHolder.getView(R.id.item_order_title);
            TextView teacher = viewHolder.getView(R.id.item_order_teacher);
            TextView date = viewHolder.getView(R.id.item_order_date);
            TextView time = viewHolder.getView(R.id.item_order_time);
            TextView xiaoqu = viewHolder.getView(R.id.item_order_xiaoqu);
            ImageView pic = viewHolder.getView(R.id.item_order_pic);
            ImageLoader.display(mContext, pic, item.getPic());

            tag.setImageResource(item.getTagImg());
            title.setText(item.getTitle());
            teacher.setText(item.getTname());
            date.setText(CalendarTools.format(Long.valueOf(item.getStartPtime()),
                    "yyyy-MM-dd") + " 至 " + CalendarTools.format(Long.valueOf(item
                            .getEndPtime()),
                    "yyyy-MM-dd"));
            time.setText(item.getClass_start_at() + " - " + item.getClass_end_at
                    ());
            xiaoqu.setText(item.getSchool());
            return viewHolder.getConvertView();
        }

        public boolean isScroll() {
            return isScroll;
        }

    }

}
