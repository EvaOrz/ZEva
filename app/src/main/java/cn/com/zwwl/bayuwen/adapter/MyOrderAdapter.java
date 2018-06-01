package cn.com.zwwl.bayuwen.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 我的订单页面
 */
public class MyOrderAdapter extends CheckScrollAdapter<String> {
    protected Context mContext;
    protected List<String> mItemList = new ArrayList<>();
    private int type;

    public MyOrderAdapter(Context context, int type) {
        super(context);
        this.type = type;
        mContext = context;
    }

    public void setData(List<String> mItemList) {
        clearData();
        clear();
        isScroll = false;
        synchronized (mItemList) {
            for (String item : mItemList) {
                add(item);
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout.item_order);
        TextView goPay = viewHolder.getView(R.id.go_pay);
        TextView pingjia = viewHolder.getView(R.id.wait_ping);
        TextView tuifei = viewHolder.getView(R.id.tuifei);
        TextView status = viewHolder.getView(R.id.waiting_pay_status);
        TextView price = viewHolder.getView(R.id.item_order_price);
        CheckBox checkBox = viewHolder.getView(R.id.item_order_check);
        LinearLayout linearLayout = viewHolder.getView(R.id.item_order_layout);

        if (type == 1) {// 购课单
            checkBox.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);
            price.setTextColor(mContext.getResources().getColor(R.color.gold));
            status.setVisibility(View.GONE);

        } else if (type == 2) {// 待付款
            checkBox.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            goPay.setVisibility(View.GONE);
            pingjia.setVisibility(View.VISIBLE);
            tuifei.setVisibility(View.VISIBLE);
            price.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
            status.setVisibility(View.VISIBLE);
            status.setText(R.string.waiting_pay);

        } else if (type == 3) {// 已付款
            checkBox.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            goPay.setVisibility(View.GONE);
            pingjia.setVisibility(View.VISIBLE);
            tuifei.setVisibility(View.VISIBLE);
            price.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
            status.setVisibility(View.GONE);
        } else if (type == 4) {// 退款/售后
            checkBox.setVisibility(View.GONE);
            linearLayout.setVisibility(View.GONE);
            price.setTextColor(mContext.getResources().getColor(R.color.gold));
            status.setVisibility(View.GONE);

        }
        return viewHolder.getConvertView();
    }

    public void clearData() {
        mItemList.clear();
    }

    public boolean isScroll() {
        return isScroll;
    }

}
