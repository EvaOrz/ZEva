package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.MyViewPagerAdapter;

/**
 * 我要参团页面
 */
public class TuanIndexActivity extends BaseActivity {

    private ViewPager viewPager;
    private View view1, view2, line1, line2;
    private RadioButton button1, button2;
    private EditText codeEditText;
    private int dianCounts = 0;// 垫付数量
    private TextView codeSureTv, dianCountsTv;
    private List<View> views = new ArrayList<>();
    private MyViewPagerAdapter adapter;

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
        adapter = new MyViewPagerAdapter(views);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

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

        button1 = findViewById(R.id.tuan_index_bt1);
        button2 = findViewById(R.id.tuan_index_bt2);
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
        line1 = findViewById(R.id.tuan_index_line1);
        line2 = findViewById(R.id.tuan_index_line2);
        codeEditText = view1.findViewById(R.id.tuan_index_code);
        codeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    handler.sendEmptyMessage(0);
                } else {
                    handler.sendEmptyMessage(1);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        codeSureTv = view1.findViewById(R.id.tuan_index_code_bt);
        dianCountsTv = view2.findViewById(R.id.dian_counts);

        findViewById(R.id.tuan_index_back).setOnClickListener(this);
        findViewById(R.id.tuan_index_intro).setOnClickListener(this);
        view2.findViewById(R.id.sure_dian).setOnClickListener(this);
        view2.findViewById(R.id.dian_minus).setOnClickListener(this);
        view2.findViewById(R.id.dian_plus).setOnClickListener(this);
        view1.findViewById(R.id.tuan_index_kaituan).setOnClickListener(this);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    codeSureTv.setBackground(getResources().getDrawable(R.drawable.gold_circle));
                    break;
                case 1:
                    codeSureTv.setBackground(getResources().getDrawable(R.drawable.gray_circle));
                    break;
                case 2:// 垫付数量变更
                    dianCountsTv.setText(dianCounts + "");
                    break;
            }
        }
    };

    private void changeRadio(int position) {
        viewPager.setCurrentItem(position);
        if (position == 0) {
            line1.setBackgroundColor(getResources().getColor(R.color.gold));
            line2.setBackgroundColor(getResources().getColor(R.color.transparent));
        } else if (position == 1) {
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
            case R.id.sure_dian:// 确认垫付
                startActivity(new Intent(mContext, TuanPayActivity.class));
                break;
            case R.id.tuan_index_kaituan:
                startActivity(new Intent(mContext, TuanKaiActivity.class));
                break;
            case R.id.dian_minus:// 垫付-1
                if (dianCounts > 0) dianCounts--;
                handler.sendEmptyMessage(2);
                break;
            case R.id.dian_plus:// 垫付+1
                dianCounts++;
                handler.sendEmptyMessage(2);
                break;
        }

    }

    @Override
    protected void initData() {

    }

}
