package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.api.AddressApi;
import cn.com.zwwl.bayuwen.model.AddressModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.util.Tools;

/**
 * 开发票页面
 */
public class PiaoKaiActivity extends BaseActivity {

    private List<AddressModel> addressDatas = new ArrayList<>();
    private AddressModel currentAddress;// 当前收货地址

    private LinearLayout adresslayout;
    private TextView nameTv, phoneTv, addressTv, addTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piao_kai);
        initView();
        initData();
    }

    private void initView() {
        nameTv = findViewById(R.id.tuan_pay_name);
        phoneTv = findViewById(R.id.tuan_pay_phone);
        addressTv = findViewById(R.id.tuan_pay_address);
        addTv = findViewById(R.id.tuan_pay_addaddress);
        adresslayout = findViewById(R.id.go_select_layout);
        findViewById(R.id.go_add_manage).setOnClickListener(this);
        findViewById(R.id.piao_h_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.piao_h_back:
                finish();
                break;
            case R.id.go_add_manage:
                startActivity(new Intent(mContext, AddressManageActivity.class));
                break;
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
                            currentAddress = addressModel;// 赋值当前收货地址
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
    protected void initData() {
        // 获取地址列表
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
