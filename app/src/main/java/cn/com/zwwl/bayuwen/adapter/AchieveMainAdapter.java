package cn.com.zwwl.bayuwen.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.AchievementModel;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 首页成就列表adapter
 */
public class AchieveMainAdapter extends BaseAdapter {
    protected Context mContext;
    protected List<AchievementModel> mItemList = new ArrayList<>();

    public AchieveMainAdapter(Context mContext, List<AchievementModel> mItemList) {
        this.mContext = mContext;
        this.mItemList = mItemList;
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public AchievementModel getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final AchievementModel item = getItem(position);
        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout.item_achievement);
        ImageView img = viewHolder.getView(R.id.achieve_img);
        LinearLayout bg = viewHolder.getView(R.id.achieve_bg);
        View bg1 = viewHolder.getView(R.id.achieve_above_bg);
        TextView title = viewHolder.getView(R.id.achieve_title);
        TextView time = viewHolder.getView(R.id.achieve_time);

        bg.setBackground(null);
        bg1.setVisibility(View.GONE);
        time.setVisibility(View.GONE);

//        ImageLoader.display(mContext, img, item.getPic());
        title.setText(item.getTitle());
        return viewHolder.getConvertView();
    }
}