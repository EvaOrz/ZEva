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
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.model.GiftAndJiangModel;
import cn.com.zwwl.bayuwen.view.CalendarOptionPopWindow;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 礼物适配器
 */
public class GiftAdapter extends BaseAdapter {

    private Context mContext;
    private List<GiftAndJiangModel> datas = new ArrayList<>();

    public GiftAdapter(Context context, List<GiftAndJiangModel> datas) {
        mContext = context;
        this.datas = datas;
    }

    public int getCount() {
        return datas.size();
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
        GiftAndJiangModel g = datas.get(position);
        ImageView imageView = viewHolder.getView(R.id.cdetail_t_avatar);
        TextView textView = viewHolder.getView(R.id.cdetail_t_name);

        ImageLoader.display(mContext, imageView, g.getPic());
        textView.setText(g.getTitle());
        return viewHolder.getConvertView();
    }

}

