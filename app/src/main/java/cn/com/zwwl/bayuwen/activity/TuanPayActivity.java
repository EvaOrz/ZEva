package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.api.AddressApi;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.model.AddressModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.view.YouHuiJuanPopWindow;

/**
 * 报名付费页面
 */
public class TuanPayActivity extends BaseActivity {

    private List<AddressModel> addressDatas = new ArrayList<>();
    private LinearLayout adresslayout;
    private int type; // 0：单独参团 1：垫付参团 2：单独购买
    private LinearLayout pinLayout, dianLayout, youhuiLayout;

    private TextView nameTv, phoneTv, addressTv, addTv;
    private TextView tagTv, titleTv, teacherTv, xiaoquTv, dateTv, timeTv;
    private ImageView imgView;
    private TextView dianNumTv, codeTv;
    private KeModel keModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuan_pay);
        if (getIntent().getSerializableExtra("TuanPayActivity_data") != null && getIntent()
                .getSerializableExtra("TuanPayActivity_data") instanceof KeModel) {
            keModel = (KeModel) getIntent().getSerializableExtra("TuanPayActivity_data");
        } else finish();
        type = getIntent().getIntExtra("TuanPayActivity_type", 0);

        initView();
        setGoodsInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void setGoodsInfo() {
        tagTv = findViewById(R.id.ke_tag);
        titleTv = findViewById(R.id.ke_title);
        teacherTv = findViewById(R.id.ke_teacher);
        xiaoquTv = findViewById(R.id.ke_xiaoqu);
        dateTv = findViewById(R.id.ke_date);
        timeTv = findViewById(R.id.ke_time);
        imgView = findViewById(R.id.ke_avatar);

        tagTv.setText(keModel.getTagTxt());
        titleTv.setText(keModel.getTitle());
        teacherTv.setText(keModel.getTname());
        xiaoquTv.setText(keModel.getSchool());
        dateTv.setText(CalendarTools.format(Long.valueOf(keModel.getStartPtime()),
                "yyyy-MM-dd") + " 至 " + CalendarTools.format(Long.valueOf(keModel.getEndPtime()),
                "yyyy-MM-dd"));
        timeTv.setText(keModel.getClass_start_at() + " - " + keModel.getClass_end_at());

        ImageLoader.display(mContext, imgView, keModel.getPic(), R
                .drawable.avatar_placeholder, R.drawable.avatar_placeholder);
    }

    private void initView() {
        nameTv = findViewById(R.id.tuan_pay_name);
        phoneTv = findViewById(R.id.tuan_pay_phone);
        addressTv = findViewById(R.id.tuan_pay_address);
        addTv = findViewById(R.id.tuan_pay_addaddress);
        adresslayout = findViewById(R.id.go_select_layout);
        dianNumTv = findViewById(R.id.dian_num);
        codeTv = findViewById(R.id.tuan_code_tv);

        dianLayout = findViewById(R.id.dianfu_layout);
        pinLayout = findViewById(R.id.pintuan_layout);
        youhuiLayout = findViewById(R.id.youhui_layout);

        findViewById(R.id.go_add_manage).setOnClickListener(this);
        findViewById(R.id.tuan_pay_back).setOnClickListener(this);
        findViewById(R.id.tuikuan_info).setOnClickListener(this);
        findViewById(R.id.tuan_code_copy).setOnClickListener(this);
        youhuiLayout.setOnClickListener(this);
        if (type == 0) {
            pinLayout.setVisibility(View.VISIBLE);
            dianLayout.setVisibility(View.GONE);
            youhuiLayout.setVisibility(View.GONE);
        } else if (type == 1) {
            pinLayout.setVisibility(View.GONE);
            dianLayout.setVisibility(View.VISIBLE);
            youhuiLayout.setVisibility(View.GONE);
        } else if (type == 2) {
            pinLayout.setVisibility(View.GONE);
            dianLayout.setVisibility(View.GONE);
            youhuiLayout.setVisibility(View.VISIBLE);
        }

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    for (AddressModel addressModel : addressDatas) {
                        if (addressModel.getIs_default().equals("1")) {
                            adresslayout.setVisibility(View.VISIBLE);
                            addressTv.setVisibility(View.VISIBLE);
                            addTv.setVisibility(View.GONE);
                            nameTv.setText(addressModel.getTo_user());
                            phoneTv.setText(addressModel.getPhone());
                            addressTv.setText(addressModel.getProvince() + addressModel.getCity()
                                    + addressModel.getDistrict() + addressModel.getAddress());
                        }
                    }
                    break;
                case 1:// 没有收货地址显示添加
                    adresslayout.setVisibility(View.GONE);
                    addressTv.setVisibility(View.GONE);
                    addTv.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tuan_pay_back:
                finish();
                break;
            case R.id.go_add_manage:
                startActivity(new Intent(mContext, AddressManageActivity.class));
                break;
            case R.id.tuikuan_info:// 退款须知
                goWeb();
                break;
            case R.id.tuan_code_copy:// 复制拼团码
                ClipboardManager cm = (ClipboardManager) getSystemService(Context
                        .CLIPBOARD_SERVICE);
                cm.setText("");
                showToast("已复制到剪切板");
                break;
            case R.id.youhui_layout:// 优惠券
                new YouHuiJuanPopWindow(mContext);
                break;
        }

    }

    public void goWeb() {
        Intent i = new Intent(mContext, WebActivity.class);
        i.putExtra("WebActivity_title", "退款须知");
        i.putExtra("WebActivity_data", "");
        startActivity(i);
    }

    @Override
    protected void initData() {
        new AddressApi(mContext, new AddressApi.FetchAddressListListener() {
            @Override
            public void setData(List<AddressModel> list) {
                showLoadingDialog(false);
                if (Tools.listNotNull(list)) {
                    addressDatas.clear();
                    addressDatas.addAll(list);
                    handler.sendEmptyMessage(0);
                } else {
                    handler.sendEmptyMessage(1);
                }
            }

            @Override
            public void setError(ErrorMsg error) {
                showLoadingDialog(false);
                if (error != null) {
                    showToast(error.getDesc());
                }

            }
        });
    }


}