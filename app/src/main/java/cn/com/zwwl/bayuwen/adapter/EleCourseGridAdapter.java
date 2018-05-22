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
import cn.com.zwwl.bayuwen.model.EleCourseModel;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * Created by lousx on 2018/5/11.
 */

public class EleCourseGridAdapter extends BaseAdapter {
    protected Context mContext;
    protected List<EleCourseModel> mItemList = new ArrayList<>();

    public EleCourseGridAdapter(Context mContext,List<EleCourseModel> mItemList) {
        this.mContext = mContext;
        this.mItemList = mItemList;
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public EleCourseModel getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final EleCourseModel item = getItem(position);

        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout.item_main_grid);

        TextView title = viewHolder.getView(R.id.item_tv);
        ImageView img = viewHolder.getView(R.id.item_img);

        title.setText(item.getName());
        if (!TextUtils.isEmpty(String.valueOf(item.getUrl())))
            img.setImageResource(item.getUrl());
        return viewHolder.getConvertView();
    }
}
