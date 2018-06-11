package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.icu.text.UFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.beecloud.BCPay;
import cn.beecloud.async.BCCallback;
import cn.beecloud.async.BCResult;
import cn.beecloud.entity.BCPayResult;
import cn.beecloud.entity.BCReqParams;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.api.order.OrderDetailApi;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.AddressModel;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.OrderForMyListModel;
import cn.com.zwwl.bayuwen.util.CalendarTools;

/**
 * 订单详情页面
 */
public class OrderDetailActivity extends BaseActivity {
    @BindView(R.id.order_d_back)
    ImageView orderDBack;
    @BindView(R.id.left_time)
    TextView leftTime;
    @BindView(R.id.need_price)
    TextView needPrice;
    @BindView(R.id.order_d_wait1)
    LinearLayout orderDWait1;
    @BindView(R.id.address_name)
    TextView addressName;
    @BindView(R.id.address_detail)
    TextView addressDetail;
    @BindView(R.id.ke_layout)
    LinearLayout keLayout;
    @BindView(R.id.order_no)
    TextView orderNo;
    @BindView(R.id.order_no_copy)
    TextView orderNoCopy;
    @BindView(R.id.order_time)
    TextView orderTime;
    @BindView(R.id.pay_style)
    TextView payStyle;
    @BindView(R.id.order_pay_time)
    TextView orderPayTime;
    @BindView(R.id.order_d_complate1)
    LinearLayout orderDComplate1;
    @BindView(R.id.zhifubao_pay)
    ImageView zhifubaoPay;
    @BindView(R.id.weixin_pay)
    ImageView weixinPay;
    @BindView(R.id.order_d_wait2)
    LinearLayout orderDWait2;
    @BindView(R.id.need_price_2)
    TextView needPrice2;
    @BindView(R.id.order_d_bt1)
    TextView orderDBt1;
    @BindView(R.id.order_d_bt2)
    TextView orderDBt2;


    private Timer timer = new Timer();// 初始化定时器
    private int type;// 1：未完成、2：已完成
    private String oid;
    private OrderForMyListModel orderForMyListModel;
    private long leftTimeString;// 待支付剩余时间
    private int payType = 1;// 1：支付宝 2：微信

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);

        oid = getIntent().getStringExtra("OrderDetailActivity_data");
        type = getIntent().getIntExtra("OrderDetailActivity_type", 0);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {
        orderDBack.setOnClickListener(this);
        orderNoCopy.setOnClickListener(this);
        zhifubaoPay.setOnClickListener(this);
        weixinPay.setOnClickListener(this);
        orderDBt1.setOnClickListener(this);
        orderDBt2.setOnClickListener(this);

        if (type == 1) {
            orderDWait1.setVisibility(View.VISIBLE);
            orderDWait2.setVisibility(View.VISIBLE);
            orderDComplate1.setVisibility(View.GONE);
            orderDBt1.setText(R.string.order_cancle);
            orderDBt2.setVisibility(View.VISIBLE);

        } else if (type == 2) {
            orderDWait1.setVisibility(View.GONE);
            orderDWait2.setVisibility(View.GONE);
            orderDComplate1.setVisibility(View.VISIBLE);
            orderDBt1.setText(R.string.tuifei);
            orderDBt2.setVisibility(View.GONE);

        }

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    leftTimeString = CalendarTools.fromStringTotime(orderForMyListModel
                            .getExpire_at()) - System.currentTimeMillis();

                    handler.sendEmptyMessage(1);
                    needPrice.setText("需付款：￥" + Double.valueOf(orderForMyListModel.getTotal_fee()
                    ) / 100);
                    orderNo.setText(orderForMyListModel.getOid());
                    orderTime.setText(orderForMyListModel.getCreate_at());
                    payStyle.setText(orderForMyListModel.getPay_channel().equals("1") ? "支付宝" :
                            "微信");
                    orderPayTime.setText(orderForMyListModel.getPay_at());
                    if (type == 1) {
                        needPrice2.setText("需付款：￥" + Double.valueOf(orderForMyListModel
                                .getTotal_fee()
                        ) / 100);
                        // 待支付启动倒计时器
                        timer.schedule(new TimerTask() {

                            @Override
                            public void run() {
                                leftTimeString -= 1000;
                                handler.sendEmptyMessage(1);
                            }
                        }, 0, 1000);
                    } else {
                        needPrice2.setText("实付款：￥" + Double.valueOf(orderForMyListModel
                                .getTotal_fee()
                        ) / 100);
                    }
                    AddressModel addressModel = orderForMyListModel.getAddressModel();
                    addressName.setText(addressModel.getTo_user());
                    addressDetail.setText(addressModel.getProvince() + addressModel.getCity()
                            + addressModel.getDistrict() + addressModel.getAddress());

                    keLayout.removeAllViews();
                    for (KeModel keModel : orderForMyListModel.getKeModels()) {
                        keLayout.addView(getKeView(keModel));
                    }

                    break;
                case 1:// 更新倒计时
                    if ((int) (leftTimeString / 1000) == 0)
                        stopTimer();

                    leftTime.setText("剩余：" + CalendarTools.getTimeLeft(leftTimeString / 1000));
                    break;
            }
        }
    };

    // 停止定时器
    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            // 一定设置为null，否则定时器不会被回收
            timer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
        //使用微信的，在initWechatPay的activity结束时detach
        BCPay.detachWechat();

        //清理当前的activity引用
        BCPay.clear();
    }

    @Override
    protected void initData() {
        new OrderDetailApi(mContext, oid, false, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                if (entry != null && entry instanceof OrderForMyListModel) {
                    orderForMyListModel = (OrderForMyListModel) entry;
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });
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
        ImageLoader.display(mContext, pic, model.getPic(), R.drawable.avatar_placeholder, R
                .drawable.avatar_placeholder);

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
            case R.id.order_d_back:
                finish();
                break;
            case R.id.order_no_copy:// 复制订单号码
                ClipboardManager cm = (ClipboardManager) getSystemService(Context
                        .CLIPBOARD_SERVICE);
                cm.setText(orderForMyListModel.getOid());
                showToast("已复制到剪切板");
                break;
            case R.id.zhifubao_pay:// 支付宝
                zhifubaoPay.setImageResource(R.drawable.radio_checked);
                weixinPay.setImageResource(R.drawable.radio_default);
                payType = 1;
                break;
            case R.id.weixin_pay:// 微信
                zhifubaoPay.setImageResource(R.drawable.radio_default);
                weixinPay.setImageResource(R.drawable.radio_checked);
                payType = 2;
                break;
            case R.id.order_d_bt1:// 取消订单|申请退款
                if (type == 1) {
                    doCancleOrder();
                } else if (type == 2) {
                    for (KeModel k : orderForMyListModel.getKeModels()) {
                        if (k.getRefund().equals("3")) {// 有正在退费的课程
                            showToast("订单中有正在退费的课程，请完成退费后重试");
                            return;
                        }
                    }
                    Intent i = new Intent(mContext, OrderTuifeeListActivity.class);
                    i.putExtra("OrderTuifeeActivity_data", orderForMyListModel);
                    startActivity(i);
                }
                break;
            case R.id.order_d_bt2:// 直接调起支付
                if ((int) (leftTimeString / 1000) == 0) {
                    showToast("请重新下单");
                } else {
                    if (payType == 2) {
                        doWeixinPay();
                    } else {
                        doAliPay();
                    }

                }

                break;
        }
    }

    /**
     * 取消订单
     */
    private void doCancleOrder() {
        showLoadingDialog(true);
        new OrderDetailApi(mContext, oid, true, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
            }

            @Override
            public void setError(ErrorMsg error) {
                if (error != null) {
                    showToast(error.getDesc());
                } else {
                    showToast("取消订单成功");
                    finish();
                }

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
        i.putExtra("TuanPayResultActivity_desc", desc);
        startActivity(i);
        finish();
    }

    private void doWeixinPay() {
        Map<String, String> mapOptional = new HashMap<String, String>();

        if (BCPay.isWXAppInstalledAndSupported() &&
                BCPay.isWXPaySupported()) {

            BCPay.PayParams payParams = new BCPay.PayParams();
            payParams.channelType = BCReqParams.BCChannelTypes.WX_APP;
            payParams.billTitle = orderForMyListModel.getTitle();   //订单标题
            payParams.billTotalFee = Integer.valueOf(orderForMyListModel.getReal_fee());
            //订单金额(分)
            payParams.billNum = orderForMyListModel.getOid();  //订单流水号
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
        aliParam.billTitle = orderForMyListModel.getTitle();
        aliParam.billTotalFee = Integer.valueOf(orderForMyListModel.getReal_fee());
        aliParam.billNum = orderForMyListModel.getOid();
        aliParam.optional = mapOptional;

        BCPay.getInstance(mContext).reqPaymentAsync(
                aliParam, bcCallback);

    }

}
