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
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 首页tab1 已选课程adapter
 */
public class MainYixuanKeAdapter extends CheckScrollAdapter<AlbumModel> {
    protected Context mContext;
    protected List<AlbumModel> mItemList = new ArrayList<>();

    public MainYixuanKeAdapter(Context context) {
        super(context);
        mContext = context;
    }

    public void setData(List<AlbumModel> mItemList) {
        clearData();
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

    public void clearData() {
        mItemList.clear();
    }

    public boolean isScroll() {
        return isScroll;
    }
}