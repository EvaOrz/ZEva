package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.MyViewPagerAdapter;
import cn.com.zwwl.bayuwen.adapter.YueAdapter;
import cn.com.zwwl.bayuwen.api.order.MyyueApi;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.YueModel;

/**
 * 我的余额页面
 */
public class MyYueActivity extends BaseActivity {
    private ViewPager viewPager;
    private SmartRefreshLayout refresh;
    private ListView view1, view2;
    private View line1, line2;
    private RadioButton button1, button2;
    private List<View> views = new ArrayList<>();
    private MyViewPagerAdapter adapter;
    private YueAdapter adapter1, adapter2;
    private int page1 = 1, page2 = 1;
    private Activity mActivity;
    private List<YueModel> shouruDatas = new ArrayList<>(), zhichuDatas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        mActivity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_yue);
        initView();
        initData();
    }

    private void initView() {
        findViewById(R.id.yue_back).setOnClickListener(this);
        viewPager = findViewById(R.id.my_tuan_viewpager);
        line1 = findViewById(R.id.my_tuan_line1);
        line2 = findViewById(R.id.my_tuan_line2);
        ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
        view1 = new ListView(mContext);
        view1.setDivider(null);
        view1.setSelector(colorDrawable);
        view2 = new ListView(mContext);
        view2.setDivider(null);
        view2.setSelector(colorDrawable);
        views.add(view1);
        views.add(view2);
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
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        adapter1 = new YueAdapter(mContext, 0);
        adapter2 = new YueAdapter(mContext, 1);
        view1.setAdapter(adapter1);
        view1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent i = new Intent(mContext, OrderTuifeeDetailActivity.class);
                i.putExtra("OrderTuifeeDetailActivity_oid", shouruDatas.get(position).getOid());
                i.putExtra("OrderTuifeeDetailActivity_id", shouruDatas.get(position).getId());
                i.putExtra("OrderTuifeeDetailActivity_kid", shouruDatas.get(position).getItem_id());
                startActivity(i);
            }
        });
        view2.setAdapter(adapter2);
        view2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent i = new Intent(mContext, OrderDetailActivity.class);
                i.putExtra("OrderDetailActivity_data", zhichuDatas.get(position).getOid());
                i.putExtra("OrderDetailActivity_type", 2);
                startActivity(i);
            }
        });

        button1 = findViewById(R.id.my_yue_bt1);
        button2 = findViewById(R.id.my_yue_bt2);
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

        refresh = findViewById(R.id.refresh);
        refresh.setRefreshContent(view1);
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                refresh.setNoMoreData(false);
                if (button1.isChecked()) {
                    page1 = 1;
                    getShouruData();
                } else {
                    page2 = 1;
                    getZhichuData();
                }
            }
        });
        refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (button1.isChecked()) {
                    ++page1;
                    getShouruData();
                } else {
                    ++page2;
                    getZhichuData();
                }
            }
        });
    }

    private void changeRadio(int position) {
        viewPager.setCurrentItem(position);
        if (position == 0) {
            refresh.setRefreshContent(view1);
            line1.setBackgroundColor(getResources().getColor(R.color.gold));
        } else {
            line1.setBackgroundColor(getResources().getColor(R.color.transparent));
        }
        if (position == 1) {
            refresh.setRefreshContent(view2);
            line2.setBackgroundColor(getResources().getColor(R.color.gold));
        } else {
            line2.setBackgroundColor(getResources().getColor(R.color.transparent));
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    adapter1.setData(shouruDatas);
                    adapter1.notifyDataSetChanged();
                    break;
                case 1:
                    adapter2.setData(zhichuDatas);
                    adapter2.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.yue_back:
                finish();
                break;
            case R.id.about_option1:

                break;
            case R.id.about_option2:
                startActivity(new Intent(mContext, FeedBackActivity.class));
                break;
        }
    }

    private void getShouruData() {
        new MyyueApi(mActivity, 0, page1, new ResponseCallBack<List<YueModel>>() {
            @Override
            public void result(List<YueModel> yueModels, ErrorMsg errorMsg) {
                refresh.finishRefresh();
                refresh.finishLoadMore();
                if (errorMsg != null) {
                    showToast(errorMsg.getDesc());
                } else {
                    if (page1 == 1) {
                        shouruDatas.clear();
                    }
                    shouruDatas.addAll(yueModels);
                    handler.sendEmptyMessage(0);
                }
            }
        });
    }

    private void getZhichuData() {
        new MyyueApi(mActivity, 1, page2, new ResponseCallBack<List<YueModel>>() {
            @Override
            public void result(List<YueModel> yueModels, ErrorMsg errorMsg) {
                refresh.finishRefresh();
                refresh.finishLoadMore();
                if (errorMsg != null) {
                    showToast(errorMsg.getDesc());
                } else {
                    if (page2 == 1) {
                        zhichuDatas.clear();
                    }
                    zhichuDatas.addAll(yueModels);
                    handler.sendEmptyMessage(1);
                }
            }
        });
    }

    @Override
    protected void initData() {
        getShouruData();
        getZhichuData();
    }

}
