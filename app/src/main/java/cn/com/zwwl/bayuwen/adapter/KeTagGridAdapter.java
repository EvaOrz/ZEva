package cn.com.zwwl.bayuwen.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.model.Index2Model.TagCourseModel.ClassifyBean.*;
import cn.com.zwwl.bayuwen.widget.RoundAngleImageView;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * frag2 的三个item中grid的adapter
 */
public class KeTagGridAdapter extends BaseAdapter {
    protected Context mContext;
    protected List<DetailsBean> mItemList = new ArrayList<>();

    public KeTagGridAdapter(Context mContext, List<DetailsBean> mItemList) {
        this.mContext = mContext;
        this.mItemList = mItemList;
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public DetailsBean getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final DetailsBean item = getItem(position);
        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout.item_main_grid);
        ImageView img = viewHolder.getView(R.id.index2_item_image);

        ImageLoader.display(mContext, img, item.getImg(), R
                .drawable.avatar_placeholder, R.drawable.avatar_placeholder);
        return viewHolder.getConvertView();
    }

}
