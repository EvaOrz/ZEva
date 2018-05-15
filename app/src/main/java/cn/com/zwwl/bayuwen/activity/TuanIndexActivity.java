package cn.com.zwwl.bayuwen.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;

/**
 * 我要参团页面
 */
public class TuanIndexActivity extends BaseActivity {

    private ViewPager viewPager;
    private View view1, view2, line1, line2;
    private RadioButton button1, button2;
    private EditText codeEditText;
    private TextView sure;
    private List<View> views = new ArrayList<>();
    private TuanViewPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuan_index);
        initView();
    }

    private void initView() {
        viewPager = findViewById(R.id.tuan_index_viewpager);
        view1 = LayoutInflater.from(this).inflate(R.layout.view_tuanindex_page1, null);
        view2 = LayoutInflater.from(this).inflate(R.layout.view_tuanindex_page2, null);
        views.add(view1);
        views.add(view2);
        adapter = new TuanViewPagerAdapter(views);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeRadio(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        button1 = findViewById(R.id.tuan_index_bt1);
        button2 = findViewById(R.id.tuan_index_bt2);
        line1 = findViewById(R.id.tuan_index_line1);
        line2 = findViewById(R.id.tuan_index_line2);
        findViewById(R.id.tuan_index_back).setOnClickListener(this);
        findViewById(R.id.tuan_index_intro).setOnClickListener(this);
    }

    private void changeRadio(int position) {
        if (position == 0) {
            button1.setChecked(true);
            line1.setBackgroundColor(getResources().getColor(R.color.gold));
            line2.setBackgroundColor(getResources().getColor(R.color.transparent));
        } else if (position == 1) {
            button2.setChecked(true);
            line1.setBackgroundColor(getResources().getColor(R.color.transparent));
            line2.setBackgroundColor(getResources().getColor(R.color.gold));
        }


    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tuan_index_back:
                finish();
                break;
            case R.id.tuan_index_intro:// 团购说明
                break;
        }

    }

    @Override
    protected void initData() {

    }

    public class TuanViewPagerAdapter extends PagerAdapter {
        private List<View> list;

        public TuanViewPagerAdapter(List<View> list) {
            this.list = list;
        }

        @Override
        public int getCount() {

            if (list != null && list.size() > 0) {
                return list.size();
            } else {
                return 0;
            }
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

    }
}
