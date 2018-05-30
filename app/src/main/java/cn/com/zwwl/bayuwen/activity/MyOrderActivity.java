package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CheckScrollAdapter;
import cn.com.zwwl.bayuwen.adapter.MyViewPagerAdapter;
import cn.com.zwwl.bayuwen.api.order.MyOrderApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 我的订单页面
 */
public class MyOrderActivity extends BaseActivity {
    private ViewPager viewPager;
    private ListView view1, view2, view3, view4;
    private View line1, line2, line3, line4;
    private RadioButton button1, button2, button3, button4;
    private List<View> views = new ArrayList<>();
    private MyViewPagerAdapter adapter;
    private MyOrderAdapter adapter1, adapter2, adapter3, adapter4;
    private LinearLayout payLayout;

    private int initTabNum = 0; // 初始tab选中

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        initTabNum = getIntent().getIntExtra("MyOrderActivity_data", 0);
        initView();
        initData();
    }

    private void initView() {
        viewPager = findViewById(R.id.my_order_viewpager);
        line1 = findViewById(R.id.my_order_line1);
        line2 = findViewById(R.id.my_order_line2);
        line3 = findViewById(R.id.my_order_line3);
        line4 = findViewById(R.id.my_order_line4);
        payLayout = findViewById(R.id.my_order_pay_layout);

        ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
        view1 = new ListView(mContext);
        view1.setDivider(null);
        view1.setSelector(colorDrawable);
        view2 = new ListView(mContext);
        view2.setDivider(null);
        view2.setSelector(colorDrawable);
        view3 = new ListView(mContext);
        view3.setDivider(null);
        view3.setSelector(colorDrawable);
        view4 = new ListView(mContext);
        view4.setDivider(null);
        view4.setSelector(colorDrawable);

        adapter1 = new MyOrderAdapter(this, 1);
        adapter2 = new MyOrderAdapter(this, 2);
        adapter3 = new MyOrderAdapter(this, 3);
        adapter4 = new MyOrderAdapter(this, 4);
        view1.setAdapter(adapter1);
        view2.setAdapter(adapter2);
        view3.setAdapter(adapter3);
        view4.setAdapter(adapter4);
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);

        view1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(mContext, OrderDetailActivity.class));
            }
        });
        view2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(mContext, OrderDetailActivity.class));
            }
        });
        view3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(mContext, OrderDetailActivity.class));
            }
        });
        view4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(mContext, OrderDetailActivity.class));
            }
        });


        adapter = new MyViewPagerAdapter(views);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0)
                    button1.setChecked(true);
                else if (position == 1)
                    button2.setChecked(true);
                else if (position == 2)
                    button3.setChecked(true);
                else if (position == 3)
                    button4.setChecked(true);


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        button1 = findViewById(R.id.my_order_bt1);
        button2 = findViewById(R.id.my_order_bt2);
        button3 = findViewById(R.id.my_order_bt3);
        button4 = findViewById(R.id.my_order_bt4);
        button1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    changeRadio(0);

            }
        });
        button2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    changeRadio(1);
            }
        });
        button3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    changeRadio(2);
            }
        });
        button4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    changeRadio(3);
            }
        });
        changeRadio(initTabNum);
        findViewById(R.id.my_order_back).setOnClickListener(this);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    break;
            }
        }
    };

    private void changeRadio(int position) {
        viewPager.setCurrentItem(position);
        if (position == 0) {
            line1.setBackgroundColor(getResources().getColor(R.color.gold));
            payLayout.setVisibility(View.VISIBLE);
        } else {
            line1.setBackgroundColor(getResources().getColor(R.color.transparent));
            payLayout.setVisibility(View.GONE);
        }
        if (position == 1) {
            line2.setBackgroundColor(getResources().getColor(R.color.gold));
        } else {
            line2.setBackgroundColor(getResources().getColor(R.color.transparent));
        }
        if (position == 2) {
            line3.setBackgroundColor(getResources().getColor(R.color.gold));
        } else {
            line3.setBackgroundColor(getResources().getColor(R.color.transparent));
        }
        if (position == 3) {
            line4.setBackgroundColor(getResources().getColor(R.color.gold));
        } else {
            line4.setBackgroundColor(getResources().getColor(R.color.transparent));
        }


    }


    @Override
    protected void initData() {

        new MyOrderApi(mContext, 1, new FetchEntryListListener() {
            @Override
            public void setData(List entry) {

            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });
        new MyOrderApi(mContext, 2, new FetchEntryListListener() {
            @Override
            public void setData(List entry) {

            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });
        new MyOrderApi(mContext, 3, new FetchEntryListListener() {
            @Override
            public void setData(List entry) {

            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });
        new MyOrderApi(mContext, 4, new FetchEntryListListener() {
            @Override
            public void setData(List entry) {

            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });


        List<String> data = new ArrayList<>();
        data.add("11");
        adapter1.setData(data);
        adapter1.notifyDataSetChanged();
        adapter2.setData(data);
        adapter2.notifyDataSetChanged();
        adapter3.setData(data);
        adapter3.notifyDataSetChanged();
        adapter4.setData(data);
        adapter4.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.my_order_back:
                finish();
                break;
//            case R.id.my_order_you:// 优惠券
//                break;
        }
    }

    public class MyOrderAdapter extends CheckScrollAdapter<String> {
        protected Context mContext;
        protected List<String> mItemList = new ArrayList<>();
        private int type;

        public MyOrderAdapter(Context context, int type) {
            super(context);
            this.type = type;
            mContext = context;
        }

        public void setData(List<String> mItemList) {
            clearData();
            clear();
            isScroll = false;
            synchronized (mItemList) {
                for (String item : mItemList) {
                    add(item);
                }
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout.item_order);
            TextView goPay = viewHolder.getView(R.id.go_pay);
            TextView pingjia = viewHolder.getView(R.id.wait_ping);
            TextView tuifei = viewHolder.getView(R.id.tuifei);
            TextView status = viewHolder.getView(R.id.waiting_pay_status);
            TextView price = viewHolder.getView(R.id.item_order_price);
            CheckBox checkBox = viewHolder.getView(R.id.item_order_check);
            LinearLayout linearLayout = viewHolder.getView(R.id.item_order_layout);

            if (type == 1) {// 购课单
                checkBox.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
                price.setTextColor(mContext.getResources().getColor(R.color.gold));
                status.setVisibility(View.GONE);

            } else if (type == 2) {// 待付款
                checkBox.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                goPay.setVisibility(View.GONE);
                pingjia.setVisibility(View.VISIBLE);
                tuifei.setVisibility(View.VISIBLE);
                price.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
                status.setVisibility(View.VISIBLE);
                status.setText(R.string.waiting_pay);

            } else if (type == 3) {// 已付款
                checkBox.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                goPay.setVisibility(View.GONE);
                pingjia.setVisibility(View.VISIBLE);
                tuifei.setVisibility(View.VISIBLE);
                price.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
                status.setVisibility(View.GONE);
            } else if (type == 4) {// 退款/售后
                checkBox.setVisibility(View.GONE);
                linearLayout.setVisibility(View.GONE);
                price.setTextColor(mContext.getResources().getColor(R.color.gold));
                status.setVisibility(View.GONE);

            }

            return viewHolder.getConvertView();
        }

        public void clearData() {
            mItemList.clear();
        }

        public boolean isScroll() {
            return isScroll;
        }

    }
}
