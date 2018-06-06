package cn.com.zwwl.bayuwen.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.fm.AlbumModel;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 首页tab1 已选课程adapter
 */
public class MainYixuanKeAdapter extends CheckScrollAdapter<AlbumModel> {
    protected Context mContext;

    public MainYixuanKeAdapter(Context context) {
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
        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout.item_yixuankecheng);


        return viewHolder.getConvertView();
    }


    public boolean isScroll() {
        return isScroll;
    }
}