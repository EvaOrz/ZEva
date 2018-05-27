package cn.com.zwwl.bayuwen.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.api.KeTagListApi.*;
import cn.com.zwwl.bayuwen.glide.CircleTransform;
import cn.com.zwwl.bayuwen.glide.GlideApp;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * Created by lousx on 2018/5/11.
 */

public class KeTagGridAdapter extends BaseAdapter {
    protected Context mContext;
    protected List<TagCourseModel> mItemList = new ArrayList<>();

    public KeTagGridAdapter(Context mContext, List<TagCourseModel> mItemList) {
        this.mContext = mContext;
        this.mItemList = mItemList;
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public TagCourseModel getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final TagCourseModel item = getItem(position);

        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout.item_main_grid);

        TextView title = viewHolder.getView(R.id.item_tv);
        ImageView img = viewHolder.getView(R.id.item_img);

        title.setText(item.getName());
        if (!TextUtils.isEmpty(item.getImg()))
            GlideApp.with(mContext)
                    .load(item.getImg())
                    .placeholder(R.drawable.avatar_placeholder)
                    .error(R.drawable.avatar_placeholder)
                    .transform(new CircleTransform(mContext))
                    .into(img);
        return viewHolder.getConvertView();
    }

}
