package cn.com.zwwl.bayuwen.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.model.AchievementModel;
import cn.com.zwwl.bayuwen.model.Index2Model.TagCourseModel.ClassifyBean.DetailsBean;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 成就adapter
 */
public class AchievementAdapter extends BaseAdapter {
    protected Context mContext;
    protected List<AchievementModel> mItemList = new ArrayList<>();
    private RelativeLayout.LayoutParams params;

    public AchievementAdapter(Context mContext, List<AchievementModel> mItemList) {
        this.mContext = mContext;
        this.mItemList = mItemList;
        params = new RelativeLayout.LayoutParams(MyApplication.width / 3 - 12, (MyApplication
                .width / 3 - 12) * 154 / 110);
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

        bg.setLayoutParams(params);
        bg1.setLayoutParams(params);
        if (item.getIs_get() == 0) {
            bg1.setVisibility(View.VISIBLE);
        } else bg1.setVisibility(View.GONE);
//        ImageLoader.display(mContext, img, item.getPic());
        title.setText(item.getTitle());
        time.setText(CalendarTools.format(CalendarTools.fromStringTotime(item.getCreated_at()) /
                        1000,
                "yyyy.MM.dd") + "获得");
        return viewHolder.getConvertView();
    }

}
