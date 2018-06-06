package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
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


    private int type;// 1：未完成、2：已完成
    private String oid;
    private OrderForMyListModel orderForMyListModel;
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
                    leftTime.setText("剩余：");
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
                case 1:
                    break;
            }
        }
    };


    @Override
    protected void initData() {
        new OrderDetailApi(mContext, oid, new FetchEntryListener() {
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
        TextView tag = view.findViewById(R.id.item_order_tag);
        TextView title = view.findViewById(R.id.item_order_title);
        TextView teacher = view.findViewById(R.id.item_order_teacher);
        TextView date = view.findViewById(R.id.item_order_date);
        TextView time = view.findViewById(R.id.item_order_time);
        TextView xiaoqu = view.findViewById(R.id.item_order_xiaoqu);
        ImageView pic = view.findViewById(R.id.item_order_pic);
        ImageLoader.display(mContext, pic, model.getPic(), R.drawable.avatar_placeholder, R
                .drawable.avatar_placeholder);

        tag.setText(model.getTagTxt());
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
                zhifubaoPay.setBackgroundColor(getResources().getColor(R.color.gold));
                weixinPay.setBackgroundColor(getResources().getColor(R.color.gray_dark));
                payType = 1;
                break;
            case R.id.weixin_pay:// 微信
                zhifubaoPay.setBackgroundColor(getResources().getColor(R.color.gray_dark));
                weixinPay.setBackgroundColor(getResources().getColor(R.color.gold));
                payType = 2;
                break;
            case R.id.order_d_bt1:// 取消订单|申请退款
                if (type == 1) {

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
            case R.id.order_d_bt2:// 去支付
                break;
        }
    }


}
