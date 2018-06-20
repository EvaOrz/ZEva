package cn.com.zwwl.bayuwen.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.fm.AlbumModel;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 首页tab1 已选课程adapter
 */
public class MainYixuanKeAdapter extends CheckScrollAdapter<KeModel> {
    protected Context mContext;

    public MainYixuanKeAdapter(Context context) {
        super(context);
        mContext = context;
    }

    public void setData(List<KeModel> mItemList) {
        clear();
        isScroll = false;
        synchronized (mItemList) {
            for (KeModel item : mItemList) {
                add(item);
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        KeModel keModel = getItem(position);
        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout.item_yixuankecheng);
        ImageView tag = viewHolder.getView(R.id.yixuan_tag);
        TextView title = viewHolder.getView(R.id.yixuan_title);
        TextView jindu = viewHolder.getView(R.id.yixuan_jindu);
        ProgressBar progressBar = viewHolder.getView(R.id.progressBarHorizontal);

        tag.setImageResource(keModel.getTagImg());
        title.setText(keModel.getTitle());
//        jindu.setText(String.format("课程进度（%s/%s）", keModel.getPlan().getCurrent(), keModel.getPlan()
//                .getCount()));
        return viewHolder.getConvertView();
    }


    public boolean isScroll() {
        return isScroll;
    }
}