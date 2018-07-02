package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.api.AddressApi;
import cn.com.zwwl.bayuwen.api.order.FaPiaoApi;
import cn.com.zwwl.bayuwen.api.order.FaPiaoCountPriceApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.AddressModel;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.FaPiaoModel;
import cn.com.zwwl.bayuwen.util.Tools;

/**
 * 开发票页面
 */
public class PiaoKaiActivity extends BaseActivity {

    private List<AddressModel> addressDatas = new ArrayList<>();
    private AddressModel currentAddress;// 当前收货地址
    private String oid_item;

    private LinearLayout adresslayout;
    private LinearLayout taxNoLayout;
    private ImageView arrow;
    private ImageView qiyeRadio, gerenRadio;
    private TextView sureBu;
    private TextView nameTv, phoneTv, addressTv, addTv, amountTv;
    private EditText taxnoEv, titleEv, markEv;

    private FaPiaoModel faPiaoModel;
    private boolean isShowDetail = false;// 是否是展示详情

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piao_kai);
        oid_item = getIntent().getStringExtra("PiaoKaiActivity_list");
        if (getIntent().getSerializableExtra("PiaoKaiActivity_data") != null && getIntent()
                .getSerializableExtra("PiaoKaiActivity_data") instanceof FaPiaoModel) {
            faPiaoModel = (FaPiaoModel) getIntent().getSerializableExtra("PiaoKaiActivity_data");
            isShowDetail = true;
        } else {
            faPiaoModel = new FaPiaoModel();

            countPiaoPrice();
        }
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isShowDetail) initData();
    }

    private void initView() {
        nameTv = findViewById(R.id.tuan_pay_name);
        phoneTv = findViewById(R.id.tuan_pay_phone);
        addressTv = findViewById(R.id.tuan_pay_address);
        addTv = findViewById(R.id.tuan_pay_addaddress);
        amountTv = findViewById(R.id.piao_option5);
        adresslayout = findViewById(R.id.go_select_layout);
        arrow = findViewById(R.id.go_select_a);
        qiyeRadio = findViewById(R.id.piao_qiye);
        gerenRadio = findViewById(R.id.piao_geren);
        taxNoLayout = findViewById(R.id.tax_no_layout);
        taxnoEv = findViewById(R.id.piao_tax_no);
        titleEv = findViewById(R.id.piao_title_ev);
        markEv = findViewById(R.id.piao_remark);

        qiyeRadio.setOnClickListener(this);
        gerenRadio.setOnClickListener(this);
        findViewById(R.id.go_add_manage).setOnClickListener(this);
        findViewById(R.id.piao_h_back).setOnClickListener(this);
        sureBu = findViewById(R.id.piao_sure);
        sureBu.setOnClickListener(this);

        if (isShowDetail) {
            arrow.setVisibility(View.GONE);
            nameTv.setText(faPiaoModel.getRece_name());
            phoneTv.setText(faPiaoModel.getRece_phone());
            addressTv.setText(faPiaoModel.getRece_address());
            if (faPiaoModel.getUtype().equals("1")) {
                setradioStatus(true);
            } else {
                setradioStatus(false);
            }
            titleEv.setText(faPiaoModel.getInvo_title());
            titleEv.setEnabled(false);
            taxnoEv.setText(faPiaoModel.getInvo_tax_no());
            taxnoEv.setEnabled(false);
            markEv.setEnabled(false);
            amountTv.setText("￥" + faPiaoModel.getInvo_amount());
            sureBu.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.piao_h_back:
                finish();
                break;
            case R.id.go_add_manage:
                if (isShowDetail) return;
                startActivity(new Intent(mContext, AddressManageActivity.class));
                break;
            case R.id.piao_qiye:
                if (isShowDetail) return;
                setradioStatus(false);
                faPiaoModel.setUtype("2");
                break;
            case R.id.piao_geren:
                if (isShowDetail) return;
                setradioStatus(true);
                faPiaoModel.setUtype("1");
                break;
            case R.id.piao_sure:
                String taxNo = taxnoEv.getText().toString();
                String title = titleEv.getText().toString();
                if (currentAddress == null) {
                    showToast("请选择收货地址");
                } else if (faPiaoModel.getUtype().equals("2") && TextUtils.isEmpty(taxNo)) {
                    showToast("请填写企业税号");
                } else if (TextUtils.isEmpty(title)) {
                    showToast("请填写发票抬头");
                } else {
                    faPiaoModel.setRece_name(currentAddress.getTo_user());
                    faPiaoModel.setRece_phone(currentAddress.getPhone());
                    faPiaoModel.setRece_address(currentAddress.getProvince() + currentAddress
                            .getCity() + currentAddress.getDistrict() + currentAddress.getAddress
                            ());
                    faPiaoModel.setInvo_tax_no(taxNo);
                    faPiaoModel.setInvo_title(title);
                    commit();
                }

                break;
        }
    }

    private void commit() {
        showLoadingDialog(true);
        new FaPiaoApi(mContext, faPiaoModel, oid_item, markEv.getText().toString(), new
                FetchEntryListener() {


                    @Override
                    public void setData(Entry entry) {
                    }

                    @Override
                    public void setError(ErrorMsg error) {
                        showLoadingDialog(false);
                        if (error != null) {
                            showToast(error.getDesc());
                        } else {
                            showToast("申请开票成功");
                            finish();
                        }

                    }
                });

    }

    private void setradioStatus(boolean isGeren) {
        if (isGeren) {
            qiyeRadio.setImageResource(R.mipmap.radio_default);
            gerenRadio.setImageResource(R.mipmap.radio_checked);
            taxNoLayout.setVisibility(View.GONE);
        } else {
            qiyeRadio.setImageResource(R.mipmap.radio_checked);
            gerenRadio.setImageResource(R.mipmap.radio_default);
            taxNoLayout.setVisibility(View.VISIBLE);
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

                case 2:// 显示发票类型
                    amountTv.setText("￥" + faPiaoModel.getPiaoType().getMoney());
                    break;

            }
        }
    };

    private void countPiaoPrice() {
        new FaPiaoCountPriceApi(mContext, oid_item, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                if (entry != null && entry instanceof FaPiaoModel.PiaoType) {
                    faPiaoModel.setPiaoType((FaPiaoModel.PiaoType) entry);
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


    @Override
    protected void initData() {
        // 获取地址列表
        new AddressApi(mContext, new AddressApi.FetchAddressListListener() {
            @Override
            public void setData(List<AddressModel> list) {
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
                if (error != null) {
                    showToast(error.getDesc());
                }

            }
        });
    }
}
