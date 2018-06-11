package cn.com.zwwl.bayuwen.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CheckScrollAdapter;
import cn.com.zwwl.bayuwen.adapter.MyViewPagerAdapter;
import cn.com.zwwl.bayuwen.api.order.MyTuanApi;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.OrderForMyListModel;
import cn.com.zwwl.bayuwen.model.TuanForMyListModel;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 我的团购页面
 */
public class MyTuanActivity extends BaseActivity {
    private ViewPager viewPager;
    private ListView view1, view2;
    private View line1, line2;
    private RadioButton button1, button2;
    private List<View> views = new ArrayList<>();
    private MyViewPagerAdapter adapter;
    private MyTuanAdapter adapter1, adapter2;
    private List<TuanForMyListModel> data = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tuan);
        initView();
        initData();
    }

    private void initView() {
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
        adapter1 = new MyTuanAdapter(mContext);
        adapter2 = new MyTuanAdapter(mContext);
        view1.setAdapter(adapter1);
        view2.setAdapter(adapter2);

        data.add(new TuanForMyListModel());
        data.add(new TuanForMyListModel());
        data.add(new TuanForMyListModel());

        adapter1.setData(data);
        adapter2.setData(data);

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

        button1 = findViewById(R.id.my_tuan_bt1);
        button2 = findViewById(R.id.my_tuan_bt2);
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

        findViewById(R.id.my_tuan_back).setOnClickListener(this);
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


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.my_tuan_back:
                finish();
                break;
        }
    }

    @Override
    protected void initData() {
        showLoadingDialog(true);
        new MyTuanApi(mContext, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {

            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });
    }


    public class MyTuanAdapter extends CheckScrollAdapter<TuanForMyListModel> {
        protected Context mContext;

        public MyTuanAdapter(Context context) {
            super(context);
            mContext = context;
        }


        public void setData(List<TuanForMyListModel> mItemList) {
            clear();
            isScroll = false;
            synchronized (mItemList) {
                for (TuanForMyListModel item : mItemList) {
                    add(item);
                }
            }
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout
                    .item_course_for_tuanlist);
            TuanForMyListModel model = getItem(position);

            TextView item_tuan_tag = viewHolder.getView(R.id.item_tuan_tag);
            TextView item_tuan_title = viewHolder.getView(R.id.item_tuan_title);
            TextView item_tuan_code = viewHolder.getView(R.id.item_tuan_code);
            ImageView item_tuan_pic = viewHolder.getView(R.id.item_tuan_pic);
            TextView item_tuan_teacher = viewHolder.getView(R.id.item_tuan_teacher);
            TextView item_tuan_xiaoqu = viewHolder.getView(R.id.item_tuan_xiaoqu);
            TextView item_tuan_date = viewHolder.getView(R.id.item_tuan_date);
            TextView item_tuan_time = viewHolder.getView(R.id.item_tuan_time);

            TextView price = viewHolder.getView(R.id.item_order_price);
            TextView bt = viewHolder.getView(R.id.item_order_bt);

            return viewHolder.getConvertView();
        }

        public boolean isScroll() {
            return isScroll;
        }


    }
}
