package cn.com.zwwl.bayuwen.activity;

import android.content.Context;
import android.content.Intent;
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
import cn.com.zwwl.bayuwen.api.CalendarEActionApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.CalendarEventModel;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.view.DatePopWindow;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 增减课程
 */
public class SelectCalendarActivity extends BaseActivity {

    private ListView listView;
    private SelectDateAdapter selectDateAdapter;
    private List<Date> datas = new ArrayList<>();
    private CalendarEventModel calendarEventModel;
    private boolean isDelete = false;// 是否是编辑日历事件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_calendar);
        if (getIntent().getSerializableExtra("SelectCalendarActivity_data") != null) {
            if (getIntent().getSerializableExtra("SelectCalendarActivity_data") instanceof
                    CalendarEventModel) {
                calendarEventModel = (CalendarEventModel) getIntent().getSerializableExtra
                        ("SelectCalendarActivity_data");
                for (int i = 0; i < calendarEventModel.getCourseDates().size(); i++) {
                    datas.add(CalendarTools.fromStringToca(calendarEventModel.getCourseDates()
                            .get(i).getCourseDate()).getTime());
                }
                isDelete = true;
            } else {
                Serializable o = getIntent().getSerializableExtra("SelectCalendarActivity_data");
                if (o != null) {
                    datas.addAll((ArrayList<Date>) o);
                }
            }
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
    protected void onDestroy() {
        super.onDestroy();
        Intent i = new Intent();
        i.putExtra("SelectCalendar_result_data", (Serializable) datas);
        setResult(101, i);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.calendar_add://
                new DatePopWindow(mContext, false, new DatePopWindow.MyDatePickListener() {
                    @Override
                    public void onDatePick(int year, int month, int day) {
                        String dateString = year + "-" + month + "-" + day;
                        datas.add(CalendarTools.fromStringToca(dateString).getTime());
                        selectDateAdapter.setData(datas);
                    }
                });
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            final Date item = getItem(position);
            ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout
                    .item_calender_select);

            ImageView delete = viewHolder.getView(R.id.calendar_select_delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isDelete) {
                        doDelete(position);
                    } else {
                        datas.remove(position);

                    }
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

        private void doDelete(final int position) {
            showLoadingDialog(true);
            new CalendarEActionApi(mContext, calendarEventModel.getId(), calendarEventModel
                    .getCourseDates().get(position).getId(), new FetchEntryListener() {

                @Override
                public void setData(Entry entry) {

                }

                @Override
                public void setError(ErrorMsg error) {
                    showLoadingDialog(false);
                    if (error != null) {
                        showToast(error.getDesc());
                    } else {
                        datas.remove(position);
                    }

                }
            });
        }

    }
}
