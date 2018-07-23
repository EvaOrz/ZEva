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
import android.widget.ListView;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.MyViewPagerAdapter;
import cn.com.zwwl.bayuwen.adapter.YueAdapter;
import cn.com.zwwl.bayuwen.api.order.MyyueApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.YueModel;
import cn.com.zwwl.bayuwen.util.Tools;

/**
 * 我的余额页面
 */
public class MyYueActivity extends BaseActivity {
    private ViewPager viewPager;
    private ListView view1, view2;
    private View line1, line2;
    private RadioButton button1, button2;
    private List<View> views = new ArrayList<>();
    private MyViewPagerAdapter adapter;
    private YueAdapter adapter1, adapter2;
    private List<YueModel> shouruDatas = new ArrayList<>(), zhichuDatas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
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
    }

    private void changeRadio(int position) {
        viewPager.setCurrentItem(position);
        if (position == 0) {
            line1.setBackgroundColor(getResources().getColor(R.color.gold));
        } else {
            line1.setBackgroundColor(getResources().getColor(R.color.transparent));
        }
        if (position == 1) {
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

    @Override
    protected void initData() {
        new MyyueApi(mContext, 0, new FetchEntryListListener() {
            @Override
            public void setData(List list) {
                shouruDatas.clear();
                if (Tools.listNotNull(list)) {
                    shouruDatas.addAll(list);
                }
                handler.sendEmptyMessage(0);
            }

            @Override
            public void setError(ErrorMsg error) {
                if (error != null) showToast(error.getDesc());
            }
        });
        new MyyueApi(mContext, 1, new FetchEntryListListener() {
            @Override
            public void setData(List list) {
                zhichuDatas.clear();
                if (Tools.listNotNull(list)) {
                    zhichuDatas.addAll(list);
                }
                handler.sendEmptyMessage(1);
            }

            @Override
            public void setError(ErrorMsg error) {
                if (error != null) showToast(error.getDesc());
            }
        });
    }

}
