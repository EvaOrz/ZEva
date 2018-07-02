package cn.com.zwwl.bayuwen.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.model.Index2Model.TagCourseModel.ClassifyBean.*;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.widget.RoundAngleImageView;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * frag2 的三个item中grid的adapter
 */
public class KeTagGridAdapter extends BaseAdapter {
    protected Context mContext;
    protected List<DetailsBean> mItemList = new ArrayList<>();
    private int partNum = 0;
    private int wid = (MyApplication.width - 60) / 3;


    public KeTagGridAdapter(Context mContext, int partNum, List<DetailsBean> mItemList) {
        this.mContext = mContext;
        this.partNum = partNum;
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
        TextView title = viewHolder.getView(R.id.tag_title);
        TextView desc = viewHolder.getView(R.id.tag_desc);

        if (partNum == 0) {
            img.setLayoutParams(new RelativeLayout.LayoutParams(wid, wid * 195 / 224));
            if (position == 0) {
                desc.setVisibility(View.GONE);
                img.setImageResource(R.mipmap.xiezuo);
            } else if (position == 1) {
                desc.setVisibility(View.VISIBLE);
                desc.setText(item.getDesc());
                img.setImageResource(R.mipmap.dayuwen);
            } else if (position == 2) {
                desc.setVisibility(View.GONE);
                img.setImageResource(R.mipmap.yuedu);
            } else if (position == 3) {
                desc.setVisibility(View.GONE);
                img.setImageResource(R.mipmap.xianshangke);
            } else if (position == 4) {
                desc.setVisibility(View.VISIBLE);
                desc.setText(item.getDesc());
                img.setImageResource(R.mipmap.wangzheban);
            } else if (position == 5) {
                desc.setVisibility(View.GONE);
                img.setImageResource(R.mipmap.youxue);
            }
        } else if (partNum == 1) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(wid *  2 / 3, wid
                    * 2 / 3);
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            params.setMargins(0, 20, 0, 20);
            img.setLayoutParams(params);
            desc.setVisibility(View.GONE);
            if (position == 0) {
                img.setImageResource(R.mipmap.xiaoshengchu);
            } else if (position == 1) {
                desc.setText(item.getDesc());
                img.setImageResource(R.mipmap.zhongkao);
            } else if (position == 2) {
                img.setImageResource(R.mipmap.gaokao);
            }
        } else if (partNum == 2) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(wid *2 / 3, wid
                    * 2 / 3);
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            params.setMargins(0, 20, 0, 20);
            img.setLayoutParams(params);
            desc.setVisibility(View.GONE);
            if (position == 0) {
                img.setImageResource(R.mipmap.shuxue);
            } else if (position == 1) {
                desc.setText(item.getDesc());
                img.setImageResource(R.mipmap.yingyu);
            } else if (position == 2) {
                img.setImageResource(R.mipmap.lihua);
            }
        }


        title.setText(item.getName());
        return viewHolder.getConvertView();
    }

}
