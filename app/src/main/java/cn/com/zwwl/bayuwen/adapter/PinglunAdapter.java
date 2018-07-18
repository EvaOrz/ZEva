package cn.com.zwwl.bayuwen.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.model.fm.PinglunModel;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 评论adapter
 */
public class PinglunAdapter extends CheckScrollAdapter<PinglunModel> {
    protected Context mContext;

    public PinglunAdapter(Context context) {
        super(context);
        mContext = context;
    }

    public void setData(List<PinglunModel> mItemList) {
        clear();
        isScroll = false;
        synchronized (mItemList) {
            for (PinglunModel item : mItemList) {
                add(item);
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final PinglunModel item = getItem(position);
        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout.item_pinglun);
        TextView name = viewHolder.getView(R.id.ping_name);
        TextView time = viewHolder.getView(R.id.ping_time);
        TextView content = viewHolder.getView(R.id.ping_content);
        ImageView avatar = viewHolder.getView(R.id.ping_avatar);

        name.setText(item.getUserModel().getName());
        content.setText(item.getContent());
        if (item.getUserModel() != null && !TextUtils.isEmpty(item.getUserModel().getPic()))
            ImageLoader.display(mContext, avatar, item.getUserModel().getPic(), R.drawable
                    .avatar_placeholder, R.drawable.avatar_placeholder);
        time.setText(CalendarTools.format(Long.valueOf(item.getCtime()), "yyyy-MM-dd " +
                "HH:mm:ss"));
        return viewHolder.getConvertView();
    }


    public boolean isScroll() {
        return isScroll;
    }

}
