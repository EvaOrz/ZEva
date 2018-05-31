package cn.com.zwwl.bayuwen.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.view.CalendarOptionPopWindow;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 礼物适配器
 */
public class GiftAdapter extends BaseAdapter {

    private Context mContext;
    private List<CalendarOptionPopWindow.CheckStatusModel> datas = new ArrayList<>();

    public GiftAdapter(Context context, List<CalendarOptionPopWindow.CheckStatusModel> datas) {
        mContext = context;
        this.datas = datas;
    }

    public int getCount() {
//        return datas.size();
        return 13;
    }


    public Object getItem(int position) {
        return datas.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout
                .item_cdetail_teacher);
        ImageView imageView = viewHolder.getView(R.id.cdetail_t_avatar);
        TextView textView = viewHolder.getView(R.id.cdetail_t_name);
        return viewHolder.getConvertView();
    }

}

