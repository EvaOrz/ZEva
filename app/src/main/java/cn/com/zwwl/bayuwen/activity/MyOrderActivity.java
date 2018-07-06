package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CartAdapter;
import cn.com.zwwl.bayuwen.adapter.MyOrderAdapter;
import cn.com.zwwl.bayuwen.adapter.MyViewPagerAdapter;
import cn.com.zwwl.bayuwen.api.order.CartApi;
import cn.com.zwwl.bayuwen.api.order.GetTuiFeeListApi;
import cn.com.zwwl.bayuwen.api.order.MyOrderApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.OrderForMyListModel;
import cn.com.zwwl.bayuwen.util.Tools;

/**
 * 我的订单页面
 */
public class MyOrderActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private ViewPager viewPager;
    private ListView view1, view2, view3, view4;
    private View line1, line2, line3, line4;
    private RadioButton button1, button2, button3, button4;
    private List<View> views = new ArrayList<>();
    private MyViewPagerAdapter adapter;
    private CartAdapter adapter1;
    private MyOrderAdapter adapter2, adapter3, adapter4;
    private LinearLayout payLayout;
    private TextView deleteBt, totalPrice;

    private List<KeModel> data1List = new ArrayList<>();
    private String deleteIds = "";// 可删除的课程串儿
    private List<OrderForMyListModel> data2List = new ArrayList<>();
    private List<OrderForMyListModel> data3List = new ArrayList<>();
    private List<OrderForMyListModel> data4List = new ArrayList<>();


    private int initTabNum = 0; // 初始tab选中

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        initTabNum = getIntent().getIntExtra("MyOrderActivity_data", 0);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {
        viewPager = findViewById(R.id.my_order_viewpager);
        line1 = findViewById(R.id.my_order_line1);
        line2 = findViewById(R.id.my_order_line2);
        line3 = findViewById(R.id.my_order_line3);
        line4 = findViewById(R.id.my_order_line4);
        payLayout = findViewById(R.id.my_order_pay_layout);
        deleteBt = findViewById(R.id.my_order_delete);
        totalPrice = findViewById(R.id.total_price);

        ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
        view1 = new ListView(mContext);
        view1.setDivider(null);
        view1.setSelector(colorDrawable);
        view1.setOnItemClickListener(this);
        view2 = new ListView(mContext);
        view2.setDivider(null);
        view2.setSelector(colorDrawable);
        view2.setOnItemClickListener(this);
        view3 = new ListView(mContext);
        view3.setDivider(null);
        view3.setSelector(colorDrawable);
        view3.setOnItemClickListener(this);
        view4 = new ListView(mContext);
        view4.setDivider(null);
        view4.setSelector(colorDrawable);
        view4.setOnItemClickListener(this);

        adapter1 = new CartAdapter(this, false, new CartAdapter.OnItemCheckChangeListener() {
            @Override
            public void onCheckChange(int position, boolean cStatus) {
                if (data1List.size() > position) {
                    data1List.get(position).setHasSelect(cStatus);
                    checkDeleteBtShow();
                    handler.sendEmptyMessage(6);// 重置价格
                }
            }

            @Override
            public void onDelete(int position) {
                if (data1List.size() > position) {
                    deleteIds = data1List.get(position).getCartId();
                    doDelete();
                }

            }
        });
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

        adapter = new MyViewPagerAdapter(views);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                initTabNum = position;
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
        findViewById(R.id.order_d_bt2).setOnClickListener(this);
        deleteBt.setOnClickListener(this);
    }

    private void checkDeleteBtShow() {
        boolean has = false;
        for (KeModel k : data1List) {
            if (k.isHasSelect()) has = true;
        }
        if (has) {// 显示
            handler.sendEmptyMessage(3);
        } else handler.sendEmptyMessage(5);// 隐藏
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    adapter2.setData(data2List);
                    adapter2.notifyDataSetChanged();
                    break;
                case 1:
                    adapter3.setData(data3List);
                    adapter3.notifyDataSetChanged();
                    break;
                case 2:
                    adapter4.setData(data4List);
                    adapter4.notifyDataSetChanged();
                    break;
                case 3:// 显示删除按钮

                    deleteBt.setVisibility(View.VISIBLE);

                    break;
                case 4:
                    adapter1.setData(data1List);
                    adapter1.notifyDataSetChanged();
                    break;
                case 5:// 隐藏删除按钮
                    deleteBt.setVisibility(View.GONE);
                    break;
                case 6:// 购课单价格计算
                    Double price = 0.00;
                    deleteIds = "";
                    for (int i = 0; i < data1List.size(); i++) {
                        if (data1List.get(i).isHasSelect()) {// 选中
                            deleteIds += data1List.get(i).getCartId() + ",";
                            price += Double.valueOf(data1List.get(i).getBuyPrice());
                        }
                    }
                    totalPrice.setText("合计：￥" + Tools.getTwoDecimal(price));
                    break;
            }
        }
    };

    private void changeRadio(int position) {
        viewPager.setCurrentItem(position);
        initTabNum = position;
        if (position == 0) {
            deleteBt.setText("删除");
            checkDeleteBtShow();
            line1.setBackgroundColor(getResources().getColor(R.color.gold));
            payLayout.setVisibility(View.VISIBLE);
        } else {
            line1.setBackgroundColor(getResources().getColor(R.color.transparent));
            payLayout.setVisibility(View.GONE);
        }
        if (position == 1) {
            deleteBt.setVisibility(View.GONE);
            line2.setBackgroundColor(getResources().getColor(R.color.gold));
        } else {
            line2.setBackgroundColor(getResources().getColor(R.color.transparent));
        }
        if (position == 2) {
            deleteBt.setText("开发票");
            deleteBt.setVisibility(View.VISIBLE);
            line3.setBackgroundColor(getResources().getColor(R.color.gold));
        } else {
            line3.setBackgroundColor(getResources().getColor(R.color.transparent));
        }
        if (position == 3) {
            deleteBt.setVisibility(View.GONE);
            line4.setBackgroundColor(getResources().getColor(R.color.gold));
        } else {
            line4.setBackgroundColor(getResources().getColor(R.color.transparent));
        }
    }

    private void initCartData() {
        new CartApi(mContext, new FetchEntryListListener() {
            @Override
            public void setData(List entry) {
                if (entry != null) {
                    data1List.clear();
                    data1List.addAll(entry);
                    handler.sendEmptyMessage(4);
                }
            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });
    }

    @Override
    protected void initData() {
        initCartData();

        new MyOrderApi(mContext, 2, new FetchEntryListListener() {
            @Override
            public void setData(List entry) {
                if (entry != null) {
                    data2List.clear();
                    data2List.addAll(entry);
                    handler.sendEmptyMessage(0);
                }

            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });
        new MyOrderApi(mContext, 3, new FetchEntryListListener() {
            @Override
            public void setData(List entry) {
                if (entry != null) {
                    data3List.clear();
                    data3List.addAll(entry);
                    handler.sendEmptyMessage(1);
                }
            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });
        new GetTuiFeeListApi(mContext, new FetchEntryListListener() {
            @Override
            public void setData(List entry) {
                if (entry != null) {
                    data4List.clear();
                    data4List.addAll(entry);
                    handler.sendEmptyMessage(2);
                }
            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.my_order_back:
                finish();
                break;
            case R.id.my_order_delete:
                if (initTabNum == 2) {
                    startActivity(new Intent(mContext, PiaoHistoryActivity.class));
                } else if (initTabNum == 0) {
                    doDelete();
                }
                break;
            case R.id.order_d_bt2:// 购课单付费
                List<KeModel> kes = new ArrayList<>();
                for (int i = 0; i < data1List.size(); i++) {
                    if (data1List.get(i).isHasSelect()) {// 选中
                        kes.add(data1List.get(i));
                    }
                }
                if (kes.size() > 0) {
                    Intent i = new Intent(mContext, PayActivity.class);
                    i.putExtra("TuanPayActivity_datas", (Serializable) kes);
                    i.putExtra("TuanPayActivity_type", 3);
                    startActivity(i);
                }

                break;
        }
    }

    private void doDelete() {
        showLoadingDialog(true);
        new CartApi(mContext, deleteIds, 0, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {

            }

            @Override
            public void setError(ErrorMsg error) {
                showLoadingDialog(false);
                deleteIds = "";
                if (error == null) {
                    initCartData();
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (initTabNum) {
            case 0:
                Intent i = new Intent(mContext, CourseDetailActivity.class);
                i.putExtra("CourseDetailActivity_id", data1List.get(position).getKid());
                startActivity(i);
                break;
            case 1:
                Intent i1 = new Intent(mContext, OrderDetailActivity.class);
                i1.putExtra("OrderDetailActivity_data", data2List.get(position).getOid());
                i1.putExtra("OrderDetailActivity_type", 1);
                startActivity(i1);
                break;
            case 2:
                Intent i2 = new Intent(mContext, OrderDetailActivity.class);
                i2.putExtra("OrderDetailActivity_data", data3List.get(position).getOid());
                i2.putExtra("OrderDetailActivity_type", 2);
                startActivity(i2);
                break;
            case 3:
                OrderForMyListModel o = data4List.get(position);
                Intent i3 = new Intent(mContext, OrderTuifeeDetailActivity.class);
                i3.putExtra("OrderTuifeeDetailActivity_id", o.getId());
                i3.putExtra("OrderTuifeeDetailActivity_oid", o.getOid());
                i3.putExtra("OrderTuifeeDetailActivity_kid", o.getKeModels().get(0).getKid());
                startActivity(i3);
                break;
        }

    }
}
