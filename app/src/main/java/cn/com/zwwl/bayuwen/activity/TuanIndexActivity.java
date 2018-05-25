package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.MyViewPagerAdapter;
import cn.com.zwwl.bayuwen.api.TuanKaiApi;
import cn.com.zwwl.bayuwen.api.TuanGouApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.TuanInfoModel;

/**
 * 我要参团页面
 */
public class TuanIndexActivity extends BaseActivity {

    private ViewPager viewPager;
    private View view1, view2, line1, line2;
    private RadioButton button1, button2;
    private EditText codeEditText;
    private TextView codeSureTv;
    private List<View> views = new ArrayList<>();
    private MyViewPagerAdapter adapter;

    private TuanInfoModel tuanInfoModel;

    private TextView dianNum, dianJiao, dianKe, dianTotal, payTotal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuan_index);
        initView();
        initData();
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
        dianNum = view2.findViewById(R.id.dian_counts);
        dianJiao = view2.findViewById(R.id.tuan_jiaocai);
        dianKe = view2.findViewById(R.id.tuan_ke);
        dianTotal = view2.findViewById(R.id.tuan_total);
        payTotal = view2.findViewById(R.id.tuan_total1);
        codeSureTv.setOnClickListener(this);
        findViewById(R.id.tuan_index_back).setOnClickListener(this);
        findViewById(R.id.tuan_index_intro).setOnClickListener(this);
        view2.findViewById(R.id.sure_dian).setOnClickListener(this);
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
                case 2:
                    dianNum.setText(tuanInfoModel.getLimit_num());
                    dianJiao.setText(tuanInfoModel.getMaterial_price() + "");
                    dianKe.setText(tuanInfoModel.getDiscount_pintrice() - tuanInfoModel
                            .getMaterial_price() + "");
                    dianTotal.setText(tuanInfoModel.getDiscount_pintrice() + "");
                    payTotal.setText(tuanInfoModel.getTotal_price() + "");
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
            case R.id.tuan_index_kaituan:// 我要开团
                getKaiTuanCode();
                break;
            case R.id.tuan_index_code_bt:// 根据课程码开通课程
                String code = codeEditText.getText().toString();
                if (!TextUtils.isEmpty(code))
                    doKaitongBycode(code);
                break;

        }
    }

    /**
     * 获取开团码
     */
    private void getKaiTuanCode() {
        showLoadingDialog(true);
        new TuanKaiApi(mContext, tuanInfoModel.getItem_id(), true, new FetchEntryListener() {
            @Override
            public void setError(ErrorMsg error) {
                showLoadingDialog(false);
                if (error != null) {
                    showToast(error.getDesc());
                }

            }

            @Override
            public void setData(Entry entry) {
                showLoadingDialog(false);
                if (entry != null && entry instanceof ErrorMsg) {
                    // 我要开团获取的开团码
                    String tuanCode = ((ErrorMsg) entry).getDesc();
                    Intent i = new Intent(mContext, TuanKaiActivity.class);
                    i.putExtra("TuanKaiActivity_data", tuanCode);
                    startActivity(i);
                    finish();
                }
            }
        });
    }

    /**
     * 根据开团码开通课程
     *
     * @param code
     */
    private void doKaitongBycode(String code) {
        showLoadingDialog(true);
        new TuanKaiApi(mContext, code, false, new FetchEntryListener() {
            @Override
            public void setError(ErrorMsg error) {
                showLoadingDialog(false);
                if (error == null) {// 没有错误信息，则操作成功
                    startActivity(new Intent(mContext, TuanPayResultActivity.class));
                    finish();
                } else {
                    showToast(error.getDesc());
                }

            }

            @Override
            public void setData(Entry entry) {

            }
        });

    }

    @Override
    protected void initData() {
        new TuanGouApi(mContext, "7018", new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                if (entry != null && entry instanceof TuanInfoModel) {
                    tuanInfoModel = (TuanInfoModel) entry;
                    handler.sendEmptyMessage(2);
                }
            }

            @Override
            public void setError(ErrorMsg error) {
                if (error != null) {
                    showToast(error.getDesc());
                    finish();
                }
            }
        });
    }

}
