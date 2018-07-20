package cn.com.zwwl.bayuwen.adapter;

import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.TuanDianModel;
import cn.com.zwwl.bayuwen.util.AppValue;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 显示垫付之后团购码的adapter
 */
public class TuanmaAdapter extends CheckScrollAdapter<TuanDianModel> {
    protected Context mContext;
    private int type = 0;// 0:支付结果页面 1:我的团购页面

    public TuanmaAdapter(Context context, int type) {
        super(context);
        mContext = context;
        this.type = type;
    }

    public void setData(List<TuanDianModel> mItemList) {
        clear();
        isScroll = false;
        synchronized (mItemList) {
            for (TuanDianModel item : mItemList) {
                add(item);
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final TuanDianModel item = getItem(position);
        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout
                .item_tuan_dianma);

        TextView title = viewHolder.getView(R.id.tuanma_code);
        TextView desc = viewHolder.getView(R.id.tuanma_copy);
        TextView nouse = viewHolder.getView(R.id.tuanma_nouser);

        title.setText(item.getCourse_code());
        if (type == 0) {
            desc.setVisibility(View.VISIBLE);
            nouse.setVisibility(View.GONE);
        } else {
            desc.setVisibility(View.GONE);
            if (item.getIs_use() == 0) {
                nouse.setText("未使用");
            } else nouse.setText("已使用");

            nouse.setVisibility(View.VISIBLE);
            viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doCopy(item.getPurchase_code());
                }
            });

        }
        desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCopy(item.getPurchase_code());
            }
        });

        return viewHolder.getConvertView();
    }

    private void doCopy(String text) {
        ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context
                .CLIPBOARD_SERVICE);
        cm.setText(text);
        AppValue.showToast(mContext, "已复制到剪切板");
    }

    public boolean isScroll() {
        return isScroll;
    }

}
