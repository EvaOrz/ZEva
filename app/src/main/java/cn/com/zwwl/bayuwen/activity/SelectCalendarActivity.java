package cn.com.zwwl.bayuwen.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CheckScrollAdapter;
import cn.com.zwwl.bayuwen.view.CalendarOptionPopWindow;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 增减课程
 */
public class SelectCalendarActivity extends BaseActivity {

    private ListView listView;
    private SelectDateAdapter selectDateAdapter;
    private List<Date> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_calendar);
        Serializable o = getIntent().getSerializableExtra("SelectCalendarActivity_data");
        if (o != null) {
            datas.addAll((ArrayList<Date>) o);
        }
        initView();
    }

    private void initView() {
        findViewById(R.id.select_calendar_back).setOnClickListener(this);
        findViewById(R.id.calendar_add).setOnClickListener(this);
        listView = findViewById(R.id.calender_list);
        selectDateAdapter = new SelectDateAdapter(this);
        selectDateAdapter.setData(datas);
        listView.setAdapter(selectDateAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.calendar_add://
                new CalendarOptionPopWindow(mContext, new CalendarOptionPopWindow
                        .MyPeriodPickListener() {

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

        public SelectDateAdapter(Context context) {
            super(context);
            mContext = context;
        }

        public void setData(List<Date> mItemList) {
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
            ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout
                    .item_calender_select);

            ImageView delete = viewHolder.getView(R.id.calendar_select_delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    remove(item);
                }
            });
            TextView textView = viewHolder.getView(R.id.calendar_select_text);
            SimpleDateFormat fm = new SimpleDateFormat("yyyy年MM月dd日（EEEE）");
            textView.setText(fm.format(item));
            return viewHolder.getConvertView();
        }

        public boolean isScroll() {
            return isScroll;
        }

    }
}
