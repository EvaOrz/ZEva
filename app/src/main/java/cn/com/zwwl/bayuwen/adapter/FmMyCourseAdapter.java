package cn.com.zwwl.bayuwen.adapter;


import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.FmListhiistoryModel;
import cn.com.zwwl.bayuwen.model.FmMyCourceListModel;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * lmj  on 2018/6/26
 */
public class FmMyCourseAdapter extends CheckScrollAdapter<FmMyCourceListModel> {
        protected Context mContext;

        public FmMyCourseAdapter(Context context) {
            super(context);
            mContext = context;
        }

        public void setData(List<FmMyCourceListModel> mItemList) {
            clear();
            isScroll = false;
            synchronized (mItemList) {
                for (FmMyCourceListModel item : mItemList) {
                    add(item);
                }
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final FmMyCourceListModel item = getItem(position);
            ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout.item_fmcourse);

            TextView title = viewHolder.getView(R.id.his_title);
            TextView playnum=viewHolder.getView(R.id.play_num_id);
            TextView desc = viewHolder.getView(R.id.his_desc);
            ImageView img = viewHolder.getView(R.id.his_img);
            TextView time = viewHolder.getView(R.id.his_time);
//            if (item.getKeInfo().getTitle()!=null&&item.getKeInfo().getTitle()!="") {
                title.setText(item.getTitle());
            playnum.setText(item.getPlayNum()+"人气");
//            }
          //  if (item.getLectureInfo().getTitle()!=null) {
                desc.setText(item.getTname());
          //  }
            time.setText(item.getStart_at());
            if (!TextUtils.isEmpty(item.getPic()))
                Glide.with(mContext)
                        .load(item.getPic())
                        .into(img);

            return viewHolder.getConvertView();
        }


        public boolean isScroll() {
            return isScroll;
        }

    }