package cn.com.zwwl.bayuwen.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.YueModel;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 我的余额adapter
 */
public class YueAdapter extends CheckScrollAdapter<YueModel> {
    protected Context mContext;
    private int type;

    public YueAdapter(Context context, int type) {
        super(context);
        this.type = type;
        mContext = context;
    }


    public void setData(List<YueModel> mItemList) {
        clear();
        isScroll = false;
        synchronized (mItemList) {
            for (YueModel item : mItemList) {
                add(item);
            }
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout
                .item_my_yue);
        final YueModel model = getItem(position);

        TextView title = viewHolder.getView(R.id.yue_title);
        TextView time = viewHolder.getView(R.id.yue_time);
        TextView price = viewHolder.getView(R.id.yue_price);
        TextView oid = viewHolder.getView(R.id.yue_oid);
        if (type == 0) {
            title.setText("退款");
            price.setTextColor(mContext.getResources().getColor(R.color.lisichen));
            price.setText("+" + Tools.getTwoDecimal(model.getAmount() / 100));
        } else {
            title.setText("支付");
            price.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
            price.setText("-" + Tools.getTwoDecimal(model.getAmount() / 100));
        }
        time.setText(model.getCreated_at());
        oid.setText("订单编号：" + model.getOid());
        return viewHolder.getConvertView();
    }

    public boolean isScroll() {
        return isScroll;
    }
}