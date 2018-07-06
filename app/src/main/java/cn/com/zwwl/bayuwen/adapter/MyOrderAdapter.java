package cn.com.zwwl.bayuwen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.OrderForMyListModel;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 我的订单页面
 * <p>
 * 待支付、已支付、退款
 */
public class MyOrderAdapter extends CheckScrollAdapter<OrderForMyListModel> {
    protected Context mContext;
    private int type;

    public MyOrderAdapter(Context context, int type) {
        super(context);
        this.type = type;
        mContext = context;
    }


    public void setData(List<OrderForMyListModel> mItemList) {
        clear();
        isScroll = false;
        synchronized (mItemList) {
            for (OrderForMyListModel item : mItemList) {
                add(item);
            }
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout.item_order_my);
        OrderForMyListModel model = getItem(position);

        TextView orderNo = viewHolder.getView(R.id.item_oder_no);
        TextView waitPay = viewHolder.getView(R.id.waiting_pay_status);
        LinearLayout keLayout = viewHolder.getView(R.id.item_oder_ke_layout);
        TextView price = viewHolder.getView(R.id.item_order_price);
        TextView bt = viewHolder.getView(R.id.item_order_bt);
        ImageView wancheng_status = viewHolder.getView(R.id.wancheng_status);

        orderNo.setText("订单编号：" + model.getOid());
        keLayout.removeAllViews();
        if (Tools.listNotNull(model.getKeModels())) {
            for (KeModel mm : model.getKeModels()) {
                keLayout.addView(getItemView(mm));
            }
        }
        if (type == 2) {// 待付款
            waitPay.setVisibility(View.VISIBLE);
            wancheng_status.setVisibility(View.GONE);
            price.setText("需付款：" + Tools.getTwoDecimal(model.getTotal_fee() / 100));
            bt.setBackground(mContext.getResources().getDrawable(R.drawable
                    .gold_white_xiangkuang));
            bt.setText(R.string.go_pay);
            bt.setTextColor(mContext.getResources().getColor(R.color.gold));
        } else if (type == 3) {// 已付款
            waitPay.setVisibility(View.GONE);
            wancheng_status.setVisibility(View.VISIBLE);
            price.setText("实付款：" + Tools.getTwoDecimal(model.getReal_fee() / 100));
            bt.setBackground(mContext.getResources().getDrawable(R.drawable
                    .gray_white_xiankuang));
            bt.setTextColor(mContext.getResources().getColor(R.color.gray_light));
            bt.setText(R.string.tuifei);
        } else if (type == 4) {// 退款/售后
            waitPay.setVisibility(View.GONE);
            wancheng_status.setVisibility(View.GONE);
            price.setText("实付款：" + Tools.getTwoDecimal(model.getReal_fee() / 100));
            bt.setBackground(mContext.getResources().getDrawable(R.drawable
                    .gold_white_xiangkuang));
            bt.setText(R.string.look_detail);
            bt.setTextColor(mContext.getResources().getColor(R.color.gold));
        }
        return viewHolder.getConvertView();
    }

    public boolean isScroll() {
        return isScroll;
    }

    private View getItemView(KeModel keModel) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_order_ke, null);
        ImageView tag = view.findViewById(R.id.item_oder_tag);
        TextView title = view.findViewById(R.id.item_oder_title);
        TextView teacher = view.findViewById(R.id.item_oder_teacher);
        TextView date = view.findViewById(R.id.item_oder_date);
        TextView time = view.findViewById(R.id.item_oder_time);
        TextView xiaoqu = view.findViewById(R.id.item_oder_xiaoqu);

        ImageView pic = view.findViewById(R.id.item_oder_pic);
        ImageLoader.display(mContext, pic, keModel.getPic());
        tag.setImageResource(keModel.getTagImg());
        title.setText(keModel.getTitle());
        teacher.setText(keModel.getTname());
        date.setText(CalendarTools.format(Long.valueOf(keModel.getStartPtime()),
                "yyyy-MM-dd") + " 至 " + CalendarTools.format(Long.valueOf(keModel
                        .getEndPtime()),
                "yyyy-MM-dd"));
        time.setText(keModel.getClass_start_at() + " - " + keModel.getClass_end_at
                ());
        xiaoqu.setText(keModel.getSchool());
        return view;
    }

}
