package cn.com.zwwl.bayuwen.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.TeacherDetailActivity;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ZanTeacherModel;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 历史记录adapter
 */
public class DianzanAdapter extends CheckScrollAdapter<ZanTeacherModel> {
    protected Context mContext;
    protected List<Entry> mItemList = new ArrayList<>();

    public DianzanAdapter(Context context) {
        super(context);
        mContext = context;
    }

    public void setData(List<ZanTeacherModel> mItemList) {
        clearData();
        isScroll = false;
        synchronized (mItemList) {
            for (ZanTeacherModel item : mItemList) {
                add(item);
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout.item_praise);

        TextView name = viewHolder.getView(R.id.course_name);
        TextView desc = viewHolder.getView(R.id.description);
        ImageView img = viewHolder.getView(R.id.course_cover);

        final ZanTeacherModel item = getItem(position);
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, TeacherDetailActivity.class);
                i.putExtra("tid", item.getTo_uid());
                mContext.startActivity(i);
            }
        });
        name.setText(item.getTo_name());
        desc.setText(item.getT_desc());
        if (!TextUtils.isEmpty(item.getPic()))
            ImageLoader.display(mContext, img, item.getPic(), R
                    .drawable.avatar_placeholder, R.drawable.avatar_placeholder);

        return viewHolder.getConvertView();
    }

    public void clearData() {
        mItemList.clear();
    }

    public boolean isScroll() {
        return isScroll;
    }

}

