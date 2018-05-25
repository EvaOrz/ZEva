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
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.GuwenModel;
import cn.com.zwwl.bayuwen.model.TeacherModel;
import cn.com.zwwl.bayuwen.model.fm.AlbumModel;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 历史记录adapter
 */
public class DianzanAdapter extends CheckScrollAdapter<Entry> {
    protected Context mContext;
    protected List<Entry> mItemList = new ArrayList<>();
    private int type;// 0：教师 1：助教、顾问

    public DianzanAdapter(Context context, int type) {
        super(context);
        mContext = context;
        this.type = type;
    }

    public void setData(List<Entry> mItemList) {
        clearData();
        isScroll = false;
        synchronized (mItemList) {
            for (Entry item : mItemList) {
                add(item);
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout.item_praise);

        TextView name = viewHolder.getView(R.id.tcherNameTv);
        TextView desc = viewHolder.getView(R.id.tcherDesTv);
        ImageView img = viewHolder.getView(R.id.course_cover);

        if (type == 0) {
            final TeacherModel item = (TeacherModel) getItem(position);
            name.setText(item.getTo_name());
            desc.setText(item.getT_desc());
            if (!TextUtils.isEmpty(item.getPic()))
                Glide.with(mContext)
                        .load(item.getPic())
                        .into(img);
        } else {
            final GuwenModel item = (GuwenModel) getItem(position);
            name.setText(item.getTo_name());
            desc.setText(item.getT_desc());
            if (!TextUtils.isEmpty(item.getPic()))
                Glide.with(mContext)
                        .load(item.getPic())
                        .into(img);
        }
        return viewHolder.getConvertView();
    }

    public void clearData() {
        mItemList.clear();
    }

    public boolean isScroll() {
        return isScroll;
    }

}

