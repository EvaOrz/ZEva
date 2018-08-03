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
import cn.com.zwwl.bayuwen.api.order.CheckCanTuanApi;
import cn.com.zwwl.bayuwen.api.order.GetTuanCodeApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.GroupBuyModel;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.util.UmengLogUtil;

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

    private KeModel keModel;

    private TextView dianNum, dianJiao, dianKe, dianTotal, payTotal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuan_index);
        initView();
        if (getIntent().getSerializableExtra("TuanIndexActivity_data") != null && getIntent()
                .getSerializableExtra("TuanIndexActivity_data")
                instanceof KeModel) {
            keModel = (KeModel) getIntent().getSerializableExtra("TuanIndexActivity_data");
            initData();
        }

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

                    break;
            }
        }
    };

    private void changeRadio(int position) {
        viewPager.setCurrentItem(position);
        if (position == 0) {
            line1.setBackgroundColor(getResources().getColor(R.color.text_red));
            line2.setBackgroundColor(getResources().getColor(R.color.transparent));
        } else if (position == 1) {
            line1.setBackgroundColor(getResources().getColor(R.color.transparent));
            line2.setBackgroundColor(getResources().getColor(R.color.text_red));
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
                getKaiTuanCode(2);
                break;
            case R.id.tuan_index_kaituan:// 我要开团
                UmengLogUtil.logFaqiTianClick(mContext);
                getKaiTuanCode(1);
                break;
            case R.id.tuan_index_code_bt:// 检查可否参团
                String code = codeEditText.getText().toString();
                if (!TextUtils.isEmpty(code))
                    checkCanTuan(code);
                break;

        }
    }


    /**
     * 根据拼团码检查是否可以参团
     *
     * @param code
     */
    private void checkCanTuan(final String code) {
        showLoadingDialog(true);
        new CheckCanTuanApi(mContext, code, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {

            }

            @Override
            public void setError(ErrorMsg error) {
                showLoadingDialog(false);
                if (error == null) {// 可以参团
                    Intent i = new Intent();
                    i.setClass(mContext, PayActivity.class);
                    i.putExtra("TuanPayActivity_data", keModel);
                    i.putExtra("TuanPayActivity_code", code);
                    i.putExtra("TuanPayActivity_type", 0);// 单独参团
                    startActivity(i);
                } else {
                    showToast("拼团码无效");
                }
            }
        });
    }

    /**
     * 获取开团码
     */
    private void getKaiTuanCode(final int type) {
        showLoadingDialog(true);
        new GetTuanCodeApi(mContext, keModel.getGroupbuy().getDiscount().getKid() + "", type, new
                FetchEntryListener() {
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
                        if (entry != null && entry instanceof GroupBuyModel) {
                            // 我要开团获取的开团码
                            String tuanCode = ((GroupBuyModel) entry).getCode();
                            Intent i = new Intent();
                            if (type == 1) {
                                i.setClass(mContext, TuanKaiActivity.class);
                                i.putExtra("TuanKaiActivity_data", keModel);
                                i.putExtra("TuanKaiActivity_code", entry);
                            } else if (type == 2) {
                                i.setClass(mContext, PayActivity.class);
                                i.putExtra("TuanPayActivity_data", keModel);
                                i.putExtra("TuanPayActivity_code", tuanCode);
                                i.putExtra("TuanPayActivity_type", 1);// 垫付
                            }
                            startActivity(i);
                        }
                    }
                });
    }

    @Override
    protected void initData() {
        int num = keModel.getGroupbuy().getDiscount().getLimit_num();
        double totalPrice = keModel.getGroupbuy().getDiscount().getTotal_price();
        dianNum.setText(num + "");
        dianJiao.setText(keModel.getGroupbuy().getDiscount().getMaterial_price() * num + "");
        dianKe.setText(totalPrice - keModel.getGroupbuy().getDiscount().getMaterial_price() * num
                + "");
        dianTotal.setText(totalPrice + "");
        payTotal.setText(totalPrice + "");
    }

}
