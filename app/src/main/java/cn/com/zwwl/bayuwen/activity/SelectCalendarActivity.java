package cn.com.zwwl.bayuwen.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CheckScrollAdapter;
import cn.com.zwwl.bayuwen.model.AlbumModel;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.view.CalendarOptionPopWindow;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 增减课程
 */
public class SelectCalendarActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_calendar);
        initView();
    }

    private void initView() {
        findViewById(R.id.select_calendar_back).setOnClickListener(this);
        findViewById(R.id.calendar_add).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.calendar_add://
                new CalendarOptionPopWindow(mContext, new CalendarOptionPopWindow.MyPeriodPickListener() {

                    @Override
                    public void onPeriodPick(Date start, Date end) {

                    }
                }, 4);
                break;
            case R.id.select_calendar_back:
                finish();
                break;
        }
    }

    public class SelectDateAdapter extends CheckScrollAdapter<Date> {
        protected Context mContext;
        protected List<Date> mItemList = new ArrayList<>();

        public SelectDateAdapter(Context context) {
            super(context);
            mContext = context;
        }

        public void setData(List<Date> mItemList) {
            clearData();
            clear();
            isScroll = false;
            synchronized (mItemList) {
                for (Date item : mItemList) {
                    add(item);
                }
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Date item = getItem(position);
            ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout.item_album);

            TextView title = viewHolder.getView(R.id.album_title);
            TextView desc = viewHolder.getView(R.id.album_desc);
            ImageView img = viewHolder.getView(R.id.album_img);
            TextView learn = viewHolder.getView(R.id.album_learn_count);
            TextView per = viewHolder.getView(R.id.album_period);



            return viewHolder.getConvertView();
        }

        public void clearData() {
            mItemList.clear();
        }

        public boolean isScroll() {
            return isScroll;
        }

    }
}
