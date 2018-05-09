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
import cn.com.zwwl.bayuwen.model.AlbumModel;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 历史记录adapter
 */
public class HistroyAdapter extends CheckScrollAdapter<AlbumModel> {
    protected Context mContext;
    protected List<AlbumModel> mItemList = new ArrayList<>();

    public HistroyAdapter(Context context) {
        super(context);
        mContext = context;
    }

    public void setData(List<AlbumModel> mItemList) {
        clearData();
        isScroll = false;
        synchronized (mItemList) {
            for (AlbumModel item : mItemList) {
                add(item);
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final AlbumModel item = getItem(position);
        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout.item_history);

        TextView title = viewHolder.getView(R.id.his_title);
        TextView desc = viewHolder.getView(R.id.his_desc);
        ImageView img = viewHolder.getView(R.id.his_img);
        TextView time = viewHolder.getView(R.id.his_time);

        title.setText(item.getTitle());
        desc.setText(item.getContent());
        time.setText(Tools.getTime(Long.valueOf(item.getCreated_at())));
        if (!TextUtils.isEmpty(item.getPic()))
            Glide.with(mContext)
                    .load(item.getPic())
                    .into(img);

        return viewHolder.getConvertView();
    }

    public void clearData() {
        mItemList.clear();
    }

    public boolean isScroll() {
        return isScroll;
    }

}

