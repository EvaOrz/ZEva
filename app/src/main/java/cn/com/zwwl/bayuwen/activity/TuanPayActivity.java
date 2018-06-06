package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.beecloud.BCPay;
import cn.beecloud.async.BCCallback;
import cn.beecloud.async.BCResult;
import cn.beecloud.entity.BCPayResult;
import cn.beecloud.entity.BCReqParams;
import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.api.AddressApi;
import cn.com.zwwl.bayuwen.api.order.CountPriceApi;
import cn.com.zwwl.bayuwen.api.order.GetYueApi;
import cn.com.zwwl.bayuwen.api.order.MakeOrderApi;
import cn.com.zwwl.bayuwen.db.TempDataHelper;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.AddressModel;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.OrderModel;
import cn.com.zwwl.bayuwen.util.AddressTools;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.view.PayDetailDialog;
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
    private TextView tagTv, titleTv, teacherTv, xiaoquTv, dateTv, timeTv, yueTv;
    private ImageView imgView;
    private TextView dianNumTv, tuanCodeTv, priceTv;
    private EditText tuijianEv;
    private ImageView zhifubaoBt, weixinBt;

    private KeModel keModel;
    private String tuanCode;// 拼团码
    private String itemCode;// id组合码
    private String yueTxt = "0.00";// 账户余额
    private AddressModel currentAddress;// 当前收货地址
    private OrderModel orderModel;// 订单model
    private OrderModel.OrderDetailModel detailModel; // 订单详情model
    private int payType = 1;// 1：支付宝 2：微信

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
        tuanCode = getIntent().getStringExtra("TuanPayActivity_code");

        initView();
        initItemString();
        setGoodsInfo();
        // 如果用到微信支付，在用到微信支付的Activity的onCreate函数里调用以下函数.
        String initInfo = BCPay.initWechatPay(mContext, MyApplication.WEIXIN_APP_ID);
        if (initInfo != null) {
            Toast.makeText(this, "微信初始化失败：" + initInfo, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    /**
     * 生成订单item串
     */
    private void initItemString() {
        if (type == 1) {// 垫付需要循环
            itemCode = keModel.getKid() + "_1_" + TempDataHelper.getCurrentChildNo(mContext);
            for (int i = 0; i < keModel.getGroupbuy().getLimit_num() - 1; i++) {
                itemCode += keModel.getKid() + "_1_" + "0";
            }
        } else {
            itemCode = keModel.getKid() + "_1_" + TempDataHelper.getCurrentChildNo(mContext);
        }
    }

    private void setGoodsInfo() {
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
        tuanCodeTv = findViewById(R.id.tuan_code_tv);
        tuijianEv = findViewById(R.id.tuijian_ev);
        tuijianEv.clearFocus();

        priceTv = findViewById(R.id.order_d_price);
        zhifubaoBt = findViewById(R.id.zhifubao_pay);
        weixinBt = findViewById(R.id.weixin_pay);

        tagTv = findViewById(R.id.ke_tag);
        titleTv = findViewById(R.id.ke_title);
        teacherTv = findViewById(R.id.ke_teacher);
        xiaoquTv = findViewById(R.id.ke_xiaoqu);
        dateTv = findViewById(R.id.ke_date);
        timeTv = findViewById(R.id.ke_time);
        imgView = findViewById(R.id.ke_avatar);
        yueTv = findViewById(R.id.yue_tv);

        dianLayout = findViewById(R.id.dianfu_layout);
        pinLayout = findViewById(R.id.pintuan_layout);
        youhuiLayout = findViewById(R.id.youhui_layout);

        zhifubaoBt.setOnClickListener(this);
        weixinBt.setOnClickListener(this);
        findViewById(R.id.order_d_commit).setOnClickListener(this);
        findViewById(R.id.go_add_manage).setOnClickListener(this);
        findViewById(R.id.tuan_pay_back).setOnClickListener(this);
        findViewById(R.id.tuikuan_info).setOnClickListener(this);
        findViewById(R.id.tuan_code_copy).setOnClickListener(this);
        findViewById(R.id.pay_detail).setOnClickListener(this);
        youhuiLayout.setOnClickListener(this);
        if (type == 0) {
            pinLayout.setVisibility(View.VISIBLE);
            dianLayout.setVisibility(View.GONE);
            youhuiLayout.setVisibility(View.GONE);
        } else if (type == 1) {
            pinLayout.setVisibility(View.GONE);
            dianLayout.setVisibility(View.VISIBLE);
            youhuiLayout.setVisibility(View.GONE);
            dianNumTv.setText(keModel.getGroupbuy().getLimit_num() + "");
        } else if (type == 2) {
            pinLayout.setVisibility(View.GONE);
            dianLayout.setVisibility(View.GONE);
            youhuiLayout.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(tuanCode)) tuanCodeTv.setText(tuanCode);

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
                case 2:// 更新账户余额
                    yueTv.setText(yueTxt);
                    break;
                case 3:// 实时计算价格，之后更新最新价格
                    priceTv.setText("实付款：￥" + detailModel.getAmount() / 100);
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
                cm.setText(tuanCode);
                showToast("已复制到剪切板");
                break;
            case R.id.youhui_layout:// 优惠券
                new YouHuiJuanPopWindow(mContext);
                break;
            case R.id.order_d_commit:// 提交订单
                if (currentAddress == null) {
                    showToast("请选择收货地址");
                } else {
                    commit();
                }

                break;
            case R.id.pay_detail:// 支付明细
                if (detailModel != null) {
                    new PayDetailDialog(mContext, detailModel);
                }

                break;

            case R.id.zhifubao_pay:// 支付宝
                zhifubaoBt.setBackgroundColor(getResources().getColor(R.color.gold));
                weixinBt.setBackgroundColor(getResources().getColor(R.color.gray_dark));
                payType = 1;
                break;
            case R.id.weixin_pay:// 微信
                zhifubaoBt.setBackgroundColor(getResources().getColor(R.color.gray_dark));
                weixinBt.setBackgroundColor(getResources().getColor(R.color.gold));
                payType = 2;
                break;
        }

    }

    // String channel, String coupon_code, String aid, String
//            saleno, String assets, String item, String groupbuy
    private void commit() {
        String tuijianCode = tuijianEv.getText().toString();
        new MakeOrderApi(mContext, payType + "", "", currentAddress.getId(), tuijianCode,
                yueTxt, itemCode, tuanCode, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                if (entry != null && entry instanceof OrderModel) {
                    orderModel = (OrderModel) entry;
                    if (orderModel.getTrade_fee() == 0) {// 如果支付价格是0元直接购买成功
                        goPayResult(TuanPayResultActivity.PAY_SUCCESS, "报名付费成功");
                    } else {
                        if (payType == 2) {
                            doWeixinPay();
                        } else {
                            doAliPay();
                        }
                    }

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

    public void goWeb() {
        Intent i = new Intent(mContext, WebActivity.class);
        i.putExtra("WebActivity_title", "退款须知");
        i.putExtra("WebActivity_data", "");
        startActivity(i);
    }

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
        // 获取账户余额
        new GetYueApi(mContext, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                if (entry != null && entry instanceof ErrorMsg) {
                    yueTxt = ((ErrorMsg) entry).getDesc();
                    countPrice();
                    handler.sendEmptyMessage(2);
                }
            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });
    }


    /**
     * 实时计算金额
     */
    private void countPrice() {
        new CountPriceApi(mContext, itemCode, "", "", yueTxt, tuanCode, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                if (entry != null && entry instanceof OrderModel.OrderDetailModel) {
                    detailModel = (OrderModel.OrderDetailModel) entry;
                    handler.sendEmptyMessage(3);
                }
            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });
    }

    //支付结果返回入口
    BCCallback bcCallback = new BCCallback() {
        @Override
        public void done(final BCResult bcResult) {
            final BCPayResult bcPayResult = (BCPayResult) bcResult;

            //根据你自己的需求处理支付结果
            String result = bcPayResult.getResult();
            int tt = 0;
            String desc = "";
            if (result.equals(BCPayResult.RESULT_SUCCESS)) {
                tt = TuanPayResultActivity.PAY_SUCCESS;
                desc = "报名付费成功";
            } else if (result.equals(BCPayResult.RESULT_CANCEL)) {
                tt = TuanPayResultActivity.PAY_CANCLE;
                desc = "取消支付";
            } else if (result.equals(BCPayResult.RESULT_FAIL)) {
                tt = TuanPayResultActivity.PAY_FAILD;
                desc = "支付失败, 原因: " + bcPayResult.getErrCode() +
                        " # " + bcPayResult.getErrMsg() +
                        " # " + bcPayResult.getDetailInfo();

            } else if (result.equals(BCPayResult.RESULT_UNKNOWN)) {
                tt = TuanPayResultActivity.PAY_UNKNOWN;
                //可能出现在支付宝8000返回状态
                desc = "订单状态未知";
            } else {
                tt = TuanPayResultActivity.PAY_UNKNOWN;
                desc = "invalid return";
            }
            Log.e("ssssssss", desc);

            goPayResult(tt, desc);
        }
    };

    private void goPayResult(int t, String desc) {
        Intent i = new Intent(mContext, TuanPayResultActivity.class);
        i.putExtra("TuanPayResultActivity_data", t);
        i.putExtra("TuanPayResultActivity_desc", desc);
        startActivity(i);
    }

    private void doWeixinPay() {
        Map<String, String> mapOptional = new HashMap<String, String>();

        if (BCPay.isWXAppInstalledAndSupported() &&
                BCPay.isWXPaySupported()) {

            BCPay.PayParams payParams = new BCPay.PayParams();
            payParams.channelType = BCReqParams.BCChannelTypes.WX_APP;
            payParams.billTitle = orderModel.getBill_title();   //订单标题
            payParams.billTotalFee = orderModel.getTrade_fee();    //订单金额(分)
            payParams.billNum = orderModel.getBill_no();  //订单流水号
//            payParams.couponId = "bbbf835d-f6b0-484f-bb6e-8e6082d4a35f";    // 优惠券ID
            payParams.optional = mapOptional;            //扩展参数(可以null)

            BCPay.getInstance(mContext).reqPaymentAsync(
                    payParams,
                    bcCallback);            //支付完成后回调入口

        } else {
            showToast("您尚未安装微信或者安装的微信版本不支持");
        }
    }

    private void doAliPay() {
        Map<String, String> mapOptional = new HashMap<>();
//                        mapOptional.put("disable_pay_channels", "balance,creditCard");

        BCPay.PayParams aliParam = new BCPay.PayParams();
        aliParam.channelType = BCReqParams.BCChannelTypes.ALI_APP;
        aliParam.billTitle = orderModel.getBill_title();
        aliParam.billTotalFee = orderModel.getTrade_fee();
        aliParam.billNum = orderModel.getBill_no();
        aliParam.optional = mapOptional;

        BCPay.getInstance(mContext).reqPaymentAsync(
                aliParam, bcCallback);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //使用微信的，在initWechatPay的activity结束时detach
        BCPay.detachWechat();

        //清理当前的activity引用
        BCPay.clear();
    }
}