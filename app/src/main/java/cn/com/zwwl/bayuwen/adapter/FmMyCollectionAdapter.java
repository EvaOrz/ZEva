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
import cn.com.zwwl.bayuwen.fragment.FmMyCollectFragment;
import cn.com.zwwl.bayuwen.model.FmListCollectModel;
import cn.com.zwwl.bayuwen.model.FmMyCourceListModel;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * lmj  on 2018/6/26
 */
public class FmMyCollectionAdapter extends CheckScrollAdapter<FmListCollectModel.DataBean> {
        protected Context mContext;
        private List<FmListCollectModel.DataBean> dataBeans=new ArrayList<>();

        public FmMyCollectionAdapter(Context context) {
            super(context);
            mContext = context;
        }

        public void setData(List<FmListCollectModel.DataBean> mItemList) {
            clear();
            isScroll = false;
            synchronized (mItemList) {
                for (FmListCollectModel.DataBean item : mItemList) {
                    add(item);
                }
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final FmListCollectModel.DataBean item = getItem(position);
            ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout.item_fmcollect);

            TextView title = viewHolder.getView(R.id.his_title);
            TextView playnum =viewHolder.getView(R.id.play_num_id);
            TextView desc = viewHolder.getView(R.id.his_desc);
            ImageView img = viewHolder.getView(R.id.his_img);
            TextView time = viewHolder.getView(R.id.his_time);
//            if (item.getKeInfo().getTitle()!=null&&item.getKeInfo().getTitle()!="") {
                title.setText(item.getCourse().getTitle());
//            }
            playnum.setText(item.getCourse().getPlayNum()+"人气");
//            if (item.getLectureInfo().getTitle()!=null) {
                desc.setText(item.getCourse().getTname());
//            }
            time.setText(item.getCreated_at());
            if (!TextUtils.isEmpty(item.getCourse().getPic()))
                Glide.with(mContext)
                        .load(item.getCourse().getPic())
                        .into(img);

            return viewHolder.getConvertView();
        }


        public boolean isScroll() {
            return isScroll;
        }

    }