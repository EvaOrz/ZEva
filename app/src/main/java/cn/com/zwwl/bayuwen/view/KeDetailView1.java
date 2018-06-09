package cn.com.zwwl.bayuwen.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.BaseRecylcerViewAdapter;
import cn.com.zwwl.bayuwen.adapter.CheckScrollAdapter;
import cn.com.zwwl.bayuwen.adapter.KeSelectAdapter;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.listener.OnItemClickListener;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.LessonModel;
import cn.com.zwwl.bayuwen.model.fm.AlbumModel;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.widget.NoScrollListView;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 课程详情页 课程表
 */
public class KeDetailView1 extends LinearLayout {

    private Context context;
    private NoScrollListView listView;
    private TextView lookAll;
    private KeLectruesAdapter adapter;
    private List<LessonModel> data = new ArrayList<>();
    private List<LessonModel> allData = new ArrayList<>();

    public KeDetailView1(Context context) {
        super(context);
        this.context = context;

        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_kedetail_1, null, false);
        addView(view);
        listView = view.findViewById(R.id.frag_kedetail1_list);

        lookAll = view.findViewById(R.id.frag_kedetail1_all);
        lookAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int record = data.size();
                if (allData.size() > data.size()) {
                    data.clear();
                    data.addAll(allData);
                    adapter.setData(data);
                }
            }
        });
        adapter = new KeLectruesAdapter(context);
        listView.setAdapter(adapter);
    }

    public void setData(List<LessonModel> dataList) {
        data.clear();
        allData.clear();
        allData.addAll(dataList);

        if (!Tools.listNotNull(dataList)) {
            lookAll.setVisibility(View.GONE);
        } else if (dataList.size() > 2) {
            data.addAll(dataList.subList(0, 3));
        } else data.addAll(dataList);

        adapter.setData(data);
    }


    public class KeLectruesAdapter extends CheckScrollAdapter<LessonModel> {
        protected Context mContext;

        public KeLectruesAdapter(Context context) {
            super(context);
            mContext = context;
        }

        public void setData(List<LessonModel> mItemList) {
            clear();
            isScroll = false;
            synchronized (mItemList) {
                for (LessonModel item : mItemList) {
                    add(item);
                }
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final LessonModel item = getItem(position);
            ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout.item_c_list);

            TextView video_id = viewHolder.getView(R.id.course_d_id);
            TextView video_title = viewHolder.getView(R.id.course_d_title);
            TextView video_time = viewHolder.getView(R.id.course_d_time);

            video_id.setText(position + 1 + "");
            video_title.setText(item.getTitle());
            video_time.setText(item.getStart_at()
                    + "  " + item.getClass_start_at()
                    + "-" + item.getClass_end_at()
                    + "(" + item.getHours() + "小时)");
            return viewHolder.getConvertView();
        }

        public boolean isScroll() {
            return isScroll;
        }

    }
}
