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
import cn.com.zwwl.bayuwen.model.fm.AlbumModel;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 专辑列表adapter
 */
public class AlbumAdapter extends CheckScrollAdapter<AlbumModel> {
    protected Context mContext;

    public AlbumAdapter(Context context) {
        super(context);
        mContext = context;
    }

    public void setData(List<AlbumModel> mItemList) {
        clear();
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
        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout.item_album);

        TextView title = viewHolder.getView(R.id.album_title);
        TextView desc = viewHolder.getView(R.id.album_desc);
        ImageView img = viewHolder.getView(R.id.album_img);
        TextView learn = viewHolder.getView(R.id.album_learn_count);
        TextView per = viewHolder.getView(R.id.album_period);

        title.setText(item.getTitle());
        desc.setText(item.getDesc());
        learn.setText(item.getPlayNum() + "");
        per.setText(item.getNum() + "");
        if (!TextUtils.isEmpty(item.getPic()))
            Glide.with(mContext)
                    .load(item.getPic())
                    .into(img);

        return viewHolder.getConvertView();
    }

    public boolean isScroll() {
        return isScroll;
    }

}
