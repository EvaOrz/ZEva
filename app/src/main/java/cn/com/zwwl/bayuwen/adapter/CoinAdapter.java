package cn.com.zwwl.bayuwen.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.CoinModel;
import cn.com.zwwl.bayuwen.model.YueModel;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 我的余额adapter
 */
public class CoinAdapter extends CheckScrollAdapter<CoinModel> {
    protected Context mContext;

    public CoinAdapter(Context context) {
        super(context);
        mContext = context;
    }


    public void setData(List<CoinModel> mItemList) {
        clear();
        isScroll = false;
        synchronized (mItemList) {
            for (CoinModel item : mItemList) {
                add(item);
            }
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout
                .item_my_yue);
        final CoinModel model = getItem(position);

        TextView title = viewHolder.getView(R.id.yue_title);
        TextView time = viewHolder.getView(R.id.yue_time);
        TextView price = viewHolder.getView(R.id.yue_price);
        TextView oid = viewHolder.getView(R.id.yue_oid);

        title.setText(model.getSummary());
        if (model.getEffect().equals("1")) {//赚取
            price.setTextColor(mContext.getResources().getColor(R.color.lisichen));
            price.setText("+" + model.getIntegral());
        } else if (model.getEffect().equals("2")) {//消耗
            price.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
            price.setText("-" + model.getIntegral());
        }
        time.setText(model.getCreated_at());
        oid.setText("订单编号：" + model.getOid());
        return viewHolder.getConvertView();
    }

    public boolean isScroll() {
        return isScroll;
    }
}