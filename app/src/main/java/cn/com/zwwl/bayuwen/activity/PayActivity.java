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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
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
import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.api.order.CountPriceApi;
import cn.com.zwwl.bayuwen.api.order.CouponApi;
import cn.com.zwwl.bayuwen.api.order.GetYueApi;
import cn.com.zwwl.bayuwen.api.order.MakeOrderApi;
import cn.com.zwwl.bayuwen.db.TempDataHelper;
import cn.com.zwwl.bayuwen.dialog.AskDialog;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.AddressModel;
import cn.com.zwwl.bayuwen.model.CouponModel;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.OrderModel;
import cn.com.zwwl.bayuwen.model.PromotionModel;
import cn.com.zwwl.bayuwen.util.AppValue;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.dialog.PayDetailDialog;
import cn.com.zwwl.bayuwen.view.YouHuiJuanPopWindow;

/**
 * 报名付费页面
 */
public class PayActivity extends BaseActivity {

    private LinearLayout adresslayout;
    private int type; // 0：单独参团 1：垫付参团 2：单独购买 3:购课单多选购买 4:组合课购买 5:fm购买
    private LinearLayout pinLayout, dianLayout, youhuiLayout;
    private TextView nameTv, phoneTv, addressTv, addTv;
    private LinearLayout keLayout;
    private TextView dianNumTv, tuanCodeTv, yueTv, priceTv, couponTv;
    private EditText tuijianEv;
    private ImageView zhifubaoBt, weixinBt;
    private CheckBox tuikuanCheckbox;

    private List<KeModel> keDatas = new ArrayList<>();
    private PromotionModel promotionModel; // 组合课购买场合、需要传入组合课model
    // 优惠券数据
    private List<CouponModel> couponModels = new ArrayList<>();
    private String tuanCode;// 拼团码
    private CouponModel currentCoupon;// 优惠码
    private String itemCode = "";// id组合码，生成订单用
    private String yueTxt = "0.00";// 账户余额
    private boolean isUseYue = true;// 是否使用余额
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
            keDatas.add((KeModel) getIntent().getSerializableExtra("TuanPayActivity_data"));
        }
        if (getIntent().getSerializableExtra("TuanPayActivity_datas") != null) {
            keDatas.addAll((List<KeModel>) getIntent().getSerializableExtra
                    ("TuanPayActivity_datas"));
        }
        if (getIntent().getSerializableExtra("TuanPayActivity_promo") != null) {
            promotionModel = (PromotionModel) getIntent().getSerializableExtra
                    ("TuanPayActivity_promo");
            keDatas.addAll(promotionModel.getKeModels());
        }
        type = getIntent().getIntExtra("TuanPayActivity_type", 0);
        tuanCode = getIntent().getStringExtra("TuanPayActivity_code");

        initView();
        initItemString();
        setGoodsInfo();
        if (type != 0 && type != 1)
            checkYouhui();
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
     * <p>
     * 组合课购买不需要item
     */
    private void initItemString() {
        if (type == 1) {// 垫付需要循环垫付数量

            KeModel keModel = keDatas.get(0);
            if (keModel.getGroupbuy() == null || keModel.getGroupbuy().getDiscount() == null ||
                    keModel.getGroupbuy().getDiscount().getLimit_num() < 1)
                return;
            itemCode = keModel.getKid() + "_1_" + TempDataHelper.getCurrentChildNo(mContext);
            for (int i = 0; i < keModel.getGroupbuy().getDiscount().getLimit_num() - 1; i++) {
                itemCode += "," + keModel.getKid() + "_1_" + "0";
            }
        } else if (type == 0 || type == 2 || type == 5) { //单独购买（包括fm购买）||单独参团
            KeModel keModel = keDatas.get(0);
            itemCode = keModel.getKid() + "_1_" + TempDataHelper.getCurrentChildNo(mContext);
        } else if (type == 3) {// 购课单购买
            for (KeModel keModel : keDatas) {
                itemCode += keModel.getKid() + "_1_" + TempDataHelper.getCurrentChildNo(mContext)
                        + ",";
            }
        }
        if (itemCode.length() > 0 && itemCode.endsWith(","))
            itemCode = itemCode.substring(0, itemCode.length() - 1);
    }

    private void setGoodsInfo() {
        keLayout.removeAllViews();
        for (KeModel keModel : keDatas) {
            keLayout.addView(getKeView(keModel));
        }
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
        tuikuanCheckbox = findViewById(R.id.tuikuan_info_check);
        priceTv = findViewById(R.id.order_d_price);
        zhifubaoBt = findViewById(R.id.zhifubao_pay);
        weixinBt = findViewById(R.id.weixin_pay);
        keLayout = findViewById(R.id.ke_layout);
        yueTv = findViewById(R.id.yue_tv);
        couponTv = findViewById(R.id.youhuiquan_tv);
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
        findViewById(R.id.pay_yue).setOnClickListener(this);
        youhuiLayout.setOnClickListener(this);
        if (type == 0) {
            pinLayout.setVisibility(View.VISIBLE);
            dianLayout.setVisibility(View.GONE);
            youhuiLayout.setVisibility(View.GONE);
        } else if (type == 1) {
            pinLayout.setVisibility(View.GONE);
            dianLayout.setVisibility(View.VISIBLE);
            youhuiLayout.setVisibility(View.GONE);
            dianNumTv.setText(keDatas.get(0).getGroupbuy().getDiscount().getLimit_num() + "");//
            // 垫付只有一个kemodel
        } else if (type == 2 || type == 3 || type == 4 || type == 5) {
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
                    if (currentAddress != null) {
                        adresslayout.setVisibility(View.VISIBLE);
                        addressTv.setVisibility(View.VISIBLE);
                        addTv.setVisibility(View.GONE);
                        nameTv.setText(currentAddress.getTo_user());
                        phoneTv.setText(currentAddress.getPhone());
                        addressTv.setText(currentAddress.getProvince() + currentAddress.getCity()
                                + currentAddress.getDistrict() + currentAddress.getAddress());
                    }
                    break;
                case 1:// 默认不选择地址
                    adresslayout.setVisibility(View.GONE);
                    addressTv.setVisibility(View.GONE);
                    addTv.setVisibility(View.VISIBLE);
                    break;
                case 2:// 更新账户余额
                    if (isUseYue) {
                        double aa = Double.valueOf(yueTxt) / 100;
                        yueTv.setText(Tools.getTwoDecimal(aa));
                    } else
                        yueTv.setText("不使用");
                    break;
                case 3:// 实时计算价格，之后更新最新价格
                    priceTv.setText("实付款：￥" + Tools.getTwoDecimal(detailModel.getAmount() / 100));
                    if (!TextUtils.isEmpty(detailModel.getWarn())) {
                        new AskDialog(mContext, "使用", "不使用", detailModel.getWarn(), new AskDialog
                                .OnSurePickListener() {

                            @Override
                            public void onCancle() {
                                currentCoupon = null;
                                handler.sendEmptyMessage(5);
                            }

                            @Override
                            public void onSure() {

                            }
                        });
                    }
                    break;

                case 4:// 显示可以使用的优惠券
                    if (currentCoupon != null) {
                        couponTv.setText(getYouhuiPrice());
                    } else couponTv.setText("无可用");
                    countPrice();
                    break;
                case 5:// 显示选择使用的优惠券
                    couponTv.setText(getYouhuiPrice());
                    countPrice();// 选择优惠券之后需要重新计算明细
                    break;
            }
        }
    };

    private String getYouhuiPrice() {
        if (currentCoupon == null) return "不使用";
        String desc = "";
        if (currentCoupon.getReduce().getDiscount() > 0) {// 折扣券
            desc = (int) (currentCoupon.getReduce().getDiscount() * 10) + "折";
        } else {
            desc = "-" + (int) currentCoupon.getReduce().getDecrease();
        }
        return desc;
    }

    /**
     * 获取课程卡片
     *
     * @param model
     * @return
     */
    private View getKeView(KeModel model) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_course_for_order, null);
        ImageView tag = view.findViewById(R.id.item_order_tag);
        TextView title = view.findViewById(R.id.item_order_title);
        TextView teacher = view.findViewById(R.id.item_order_teacher);
        TextView date = view.findViewById(R.id.item_order_date);
        TextView time = view.findViewById(R.id.item_order_time);
        TextView xiaoqu = view.findViewById(R.id.item_order_xiaoqu);
        ImageView pic = view.findViewById(R.id.item_order_pic);
        ImageLoader.display(mContext, pic, model.getPic());

        tag.setImageResource(model.getTagImg());
        title.setText(model.getTitle());
        teacher.setText(model.getTname());
        date.setText(CalendarTools.format(Long.valueOf(model.getStartPtime()),
                "yyyy-MM-dd") + " 至 " + CalendarTools.format(Long.valueOf(model
                        .getEndPtime()),
                "yyyy-MM-dd"));
        time.setText(model.getClass_start_at() + " - " + model.getClass_end_at
                ());
        xiaoqu.setText(model.getSchool());
        return view;
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tuan_pay_back:
                finish();
                break;
            case R.id.go_add_manage:
                Intent i = new Intent(mContext, AddressManageActivity.class);
                i.putExtra("AddressManageActivity_type", true);
                startActivityForResult(i, 1001);
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
                if (Tools.listNotNull(couponModels)) {
                    new YouHuiJuanPopWindow(mContext, couponModels, new YouHuiJuanPopWindow
                            .OnPickYouhuiListener() {

                        @Override
                        public void onPick(CouponModel couponModel) {
                            currentCoupon = couponModel;
                            handler.sendEmptyMessage(5);
                        }
                    });
                } else {
                    showToast("当前您没有可使用的优惠券");
                }
                break;
            case R.id.order_d_commit:// 提交订单
                if (!tuikuanCheckbox.isChecked()) {
                    showToast("请阅读并同意退款方式须知");
                    return;
                }
                if (promotionModel != null) {
                    commit(promotionModel.getId());
                } else commit(null);
                break;
            case R.id.pay_detail:// 支付明细
                if (detailModel != null) {
                    new PayDetailDialog(mContext, detailModel);
                }
                break;
            case R.id.zhifubao_pay:// 支付宝
                zhifubaoBt.setImageResource(R.drawable.radio_checked);
                weixinBt.setImageResource(R.drawable.radio_default);
                payType = 1;
                break;
            case R.id.weixin_pay:// 微信
                zhifubaoBt.setImageResource(R.drawable.radio_default);
                weixinBt.setImageResource(R.drawable.radio_checked);
                payType = 2;
                break;
            case R.id.pay_yue:// 使用余额
                double aa = Double.valueOf(yueTxt) / 100;
                if (aa > 0) {
                    new AskDialog(mContext, "使用余额", "不使用", "您当前余额为" + aa + "，是否使用余额", new
                            AskDialog.OnSurePickListener() {

                                @Override
                                public void onSure() {
                                    isUseYue = true;
                                    handler.sendEmptyMessage(2);
                                    countPrice();
                                }

                                @Override
                                public void onCancle() {
                                    isUseYue = false;
                                    handler.sendEmptyMessage(2);
                                    countPrice();
                                }
                            });
                } else showToast("您当前余额为0");
                break;
        }

    }

    /**
     * 生成订单
     *
     * @param promoId
     */
    private void commit(String promoId) {
        String tuijianCode = tuijianEv.getText().toString();
        String couponCode = currentCoupon == null ? "" : currentCoupon.getCoupon_code();
        String addressId = currentAddress == null ? "" : currentAddress.getId();
        String yue = isUseYue ? yueTxt : null;
        new MakeOrderApi(mContext, payType + "", couponCode, addressId, tuijianCode,
                yue, itemCode, tuanCode, promoId, new FetchEntryListener() {
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
        i.putExtra("WebActivity_data", AppValue.refundUrl);
        startActivity(i);
    }

    @Override
    protected void initData() {
        handler.sendEmptyMessage(1);
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
        String promoId = promotionModel == null ? "" : promotionModel.getId();
        String couponCode = currentCoupon == null ? "" : currentCoupon.getCoupon_code();
        String yue = isUseYue ? yueTxt : "0";
        new CountPriceApi(mContext, itemCode, couponCode, promoId, yue, tuanCode, new
                FetchEntryListener() {
                    @Override
                    public void setData(Entry entry) {
                        if (entry != null && entry instanceof OrderModel.OrderDetailModel) {
                            detailModel = (OrderModel.OrderDetailModel) entry;
                            handler.sendEmptyMessage(3);
                        }
                    }

                    @Override
                    public void setError(ErrorMsg error) {
                        if (error != null) showToast(error.getDesc());
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
            goPayResult(tt, desc);
        }
    };

    private void goPayResult(int t, String desc) {
        Intent i = new Intent(mContext, TuanPayResultActivity.class);
        i.putExtra("TuanPayResultActivity_data", t);
        i.putExtra("TuanPayResultActivity_oid", orderModel.getBill_no());
        i.putExtra("TuanPayResultActivity_desc", desc);
        if (type == 1) {
            i.putExtra("is_dianfu", true);
            i.putExtra("TuanPayResultActivity_kid", keDatas.get(0).getKid());// 垫付只有一个课，不存在多个课垫付的情况
            i.putExtra("TuanPayResultActivity_code", tuanCode);
        }
        if (type == 5) {
            i.putExtra("is_fm_pay", true);
        }
        startActivity(i);
        finish();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == 1000 && data.getSerializableExtra
                ("address_pick") != null) {
            currentAddress = (AddressModel) data.getSerializableExtra("address_pick");
            handler.sendEmptyMessage(0);
        }
    }

    /**
     * 检查是否有可用优惠券
     */
    private void checkYouhui() {
        String kids = "";
        for (KeModel k : keDatas) {
            kids += k.getKid() + ",";
        }
        if (TextUtils.isEmpty(kids))
            return;
        new CouponApi(mContext, 0, kids, new FetchEntryListListener() {
            @Override
            public void setData(List list) {
                if (Tools.listNotNull(list)) {
                    couponModels.clear();
                    couponModels.addAll(list);
                    currentCoupon = couponModels.get(0);
                    handler.sendEmptyMessage(4);
                }
            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });
    }
}