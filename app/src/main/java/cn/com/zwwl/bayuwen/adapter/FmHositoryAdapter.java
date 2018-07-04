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
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * lmj  on 2018/6/26
 */
public class FmHositoryAdapter extends CheckScrollAdapter<FmListhiistoryModel> {
        protected Context mContext;

        public FmHositoryAdapter(Context context) {
            super(context);
            mContext = context;
        }

        public void setData(List<FmListhiistoryModel> mItemList) {
            clear();
            isScroll = false;
            synchronized (mItemList) {
                for (FmListhiistoryModel item : mItemList) {
                    add(item);
                }
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final FmListhiistoryModel item = getItem(position);
            ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout.item_history);

            TextView title = viewHolder.getView(R.id.his_title);
            TextView desc = viewHolder.getView(R.id.his_desc);
            ImageView img = viewHolder.getView(R.id.his_img);
            TextView time = viewHolder.getView(R.id.his_time);
            if (item.getKeInfo().getTitle()!=null&&item.getKeInfo().getTitle()!="") {
                title.setText(item.getKeInfo().getTitle());
            }
            if (item.getLectureInfo().getTitle()!=null) {
                desc.setText(item.getLectureInfo().getTitle());
            }
            time.setText(CalendarTools.getTime(Long.valueOf(item.getLectureInfo().getDuration())));
            if (!TextUtils.isEmpty(item.getKeInfo().getPic()))
                Glide.with(mContext)
                        .load(item.getKeInfo().getPic())
                        .into(img);

            return viewHolder.getConvertView();
        }


        public boolean isScroll() {
            return isScroll;
        }

    }