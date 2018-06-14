package cn.com.zwwl.bayuwen.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.model.GiftAndJiangModel;
import cn.com.zwwl.bayuwen.model.TeacherModel;
import cn.com.zwwl.bayuwen.view.CalendarOptionPopWindow;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 奖状adapter
 */
public class JiangZhuangAdapter extends CheckScrollAdapter<GiftAndJiangModel> {

    private Context mContext;

    public JiangZhuangAdapter(Context context) {
        super(context);
        mContext = context;
    }


    public void setData(List<GiftAndJiangModel> mItemList) {
        clear();
        isScroll = false;
        synchronized (mItemList) {
            for (GiftAndJiangModel item : mItemList) {
                add(item);
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout
                .item_jiangzhuang);
        GiftAndJiangModel giftAndJiangModel = getItem(position);

        ImageView imageView = viewHolder.getView(R.id.jiang_img);
        ImageView delete = viewHolder.getView(R.id.jiang_delete);
        TextView textView = viewHolder.getView(R.id.jiang_name);
        LinearLayout layout1 = viewHolder.getView(R.id.jiang_layout1);
        TextView layout2 = viewHolder.getView(R.id.jiang_layout2);

        ImageLoader.display(mContext, imageView, giftAndJiangModel.getPic(), null, null);
        textView.setText(giftAndJiangModel.getTitle());
        if (giftAndJiangModel.isDeleteStatus()) {
            delete.setVisibility(View.VISIBLE);
        } else {
            delete.setVisibility(View.GONE);
        }
        if (giftAndJiangModel.getId() == -1) {
            layout1.setVisibility(View.INVISIBLE);
            layout2.setVisibility(View.VISIBLE);
        } else {
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.INVISIBLE);
        }
        return viewHolder.getConvertView();
    }

}

