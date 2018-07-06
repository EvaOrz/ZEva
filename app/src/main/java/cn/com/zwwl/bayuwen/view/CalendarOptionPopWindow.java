package cn.com.zwwl.bayuwen.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.maning.calendarlibrary.MNCalendarVertical;
import com.maning.calendarlibrary.listeners.OnCalendarRangeChooseListener;
import com.maning.calendarlibrary.model.MNCalendarVerticalConfig;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.CalendarJigouModel;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 添加课程页面option window
 */
public class CalendarOptionPopWindow implements View.OnClickListener {

    private int type = 1;
    private Context mContext;
    private PopupWindow window;
    private TextView title;
    private TimePicker timePicker;
    private MNCalendarVertical mnCalendarVertical;// 垂直日历选择器
    private MyTimePickListener myTimePickListener;
    private MyJigouChooseListener myJigouChooseListener;
    private MyPeriodPickListener myPeriodPickListener;
    private MyWeekChooseListener myWeekChooseListener;
    private GridView gridView;
    private EditText jigouEv;
    private CalendarGridAdapter gridAdapter;
    private List<CheckStatusModel> jigouDatas = new ArrayList<>();
    private List<CheckStatusModel> weekDatas = new ArrayList<>();
    private List<Calendar> months = new ArrayList<>();
    private Date startDate, endDate;// 课程开始日期和结束日期

    /**
     * @param context
     * @param wtype   1:上课时间 2:下课时间 3:课程机构 4:日期选择 5:周次选择
     */
    public CalendarOptionPopWindow(Context context, List<CalendarJigouModel> calendarJigouModels,
                                   MyJigouChooseListener myJigouChooseListener, String
                                           defaultJigou, int wtype) {
        mContext = context;
        this.myJigouChooseListener = myJigouChooseListener;
        initJigou(calendarJigouModels, defaultJigou);
        this.type = wtype;
        init();
    }

    public CalendarOptionPopWindow(Context context, MyTimePickListener myTimePickListener, int
            wtype) {
        mContext = context;
        this.type = wtype;
        this.myTimePickListener = myTimePickListener;
        init();
    }

    public CalendarOptionPopWindow(Context context, MyWeekChooseListener myWeekChooseListener,
                                   int wtype) {
        mContext = context;
        this.type = wtype;
        this.myWeekChooseListener = myWeekChooseListener;
        init();
    }

    /**
     * 时间段选择器构造
     *
     * @param context
     * @param myPeriodPickListener
     * @param wtype
     */
    public CalendarOptionPopWindow(Context context, MyPeriodPickListener myPeriodPickListener,
                                   int wtype) {
        mContext = context;
        this.type = wtype;
        this.myPeriodPickListener = myPeriodPickListener;
        init();
    }


    private void initWeek() {
        weekDatas.add(new CheckStatusModel("周一", 2));
        weekDatas.add(new CheckStatusModel("周二", 3));
        weekDatas.add(new CheckStatusModel("周三", 4));
        weekDatas.add(new CheckStatusModel("周四", 5));
        weekDatas.add(new CheckStatusModel("周五", 6));
        weekDatas.add(new CheckStatusModel("周六", 7));
        weekDatas.add(new CheckStatusModel("周日", 1));
    }


    private void initJigou(List<CalendarJigouModel> calendarJigouModels, String defaultJigou) {
        for (CalendarJigouModel c : calendarJigouModels) {
            CheckStatusModel checkStatusModel = new CheckStatusModel(c);
            if (!TextUtils.isEmpty(defaultJigou) && c.getName().equals(defaultJigou))
                checkStatusModel.setCheckStatus(true);
            jigouDatas.add(checkStatusModel);
        }
    }

    public void init() {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.pop_calendar_option, null);
        view.findViewById(R.id.pop_calendar_cancle)
                .setOnClickListener(this);
        view.findViewById(R.id.pop_calendar_sure)
                .setOnClickListener(this);
        title = view.findViewById(R.id.pop_calendar_title);
        timePicker = view.findViewById(R.id.pop_calendar_timepicker);
        gridView = view.findViewById(R.id.pop_calendar_grid);
        mnCalendarVertical = view.findViewById(R.id.mnCalendarVertical);
        jigouEv = view.findViewById(R.id.jigou_input);

        timePicker.setVisibility(View.GONE);
        gridView.setVisibility(View.GONE);
        mnCalendarVertical.setVisibility(View.GONE);
        if (type == 1) {
            title.setText("选择上课时间");
            timePicker.setVisibility(View.VISIBLE);
        }
        if (type == 2) {
            title.setText("选择下课时间");
            timePicker.setVisibility(View.VISIBLE);
        }
        if (type == 3) {
            title.setText("选择课程机构");
            gridAdapter = new CalendarGridAdapter(mContext, jigouDatas);
            gridView.setAdapter(gridAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    boolean ss = jigouDatas.get(position).isCheckStatus();
                    for (int i = 0; i < jigouDatas.size(); i++) {
                        if (i == position) {
                            jigouDatas.get(position).setCheckStatus(!ss);
                        } else {
                            jigouDatas.get(i).setCheckStatus(false);
                        }
                    }
                    gridAdapter.notifyDataSetChanged();

                }
            });
            jigouEv.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!TextUtils.isEmpty(s.toString())) {
                        for (int i = 0; i < jigouDatas.size(); i++) {
                            jigouDatas.get(i).setCheckStatus(false);
                        }
                        gridAdapter.notifyDataSetChanged();
                    }
                }
            });
            gridView.setNumColumns(2);
            gridView.setVisibility(View.VISIBLE);
            jigouEv.setVisibility(View.VISIBLE);
        }
        if (type == 4) {
            title.setText("选择日期");
            months.addAll(CalendarTools.initCalendarForMonthView());
            mnCalendarVertical.setVisibility(View.VISIBLE);
            initMNCalendarVertical();
            /**
             * 区间选取完成监听
             */
            mnCalendarVertical.setOnCalendarRangeChooseListener(new OnCalendarRangeChooseListener
                    () {
                @Override
                public void onRangeDate(Date date, Date date1) {
                    startDate = date;
                    endDate = date1;
                }
            });
        }
        if (type == 5) {
            title.setText("选择周次");
            initWeek();
            gridAdapter = new CalendarGridAdapter(mContext, weekDatas);
            gridView.setAdapter(gridAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    boolean ss = weekDatas.get(position).isCheckStatus();
                    weekDatas.get(position).setCheckStatus(!ss);
                    gridAdapter.notifyDataSetChanged();

                }
            });
            gridView.setNumColumns(3);
            gridView.setVisibility(View.VISIBLE);
        }
        window = new PopupWindow(view, RelativeLayout.LayoutParams.FILL_PARENT,
                RelativeLayout.LayoutParams.FILL_PARENT);
        window.setFocusable(true);

        window.setOutsideTouchable(true);
        window.update();
        window.setBackgroundDrawable(new BitmapDrawable());
        window.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    private void initMNCalendarVertical() {
        String graydark = "#" + Integer.toHexString(mContext.getResources().getColor(R.color
                .gray_dark));
        String graylight = "#" + Integer.toHexString(mContext.getResources().getColor(R.color
                .gray_light));
        String gold = "#" + Integer.toHexString(mContext.getResources().getColor(R.color.gold));
        String white = "#" + Integer.toHexString(mContext.getResources().getColor(R.color.white));
        /**
         *  自定义设置相关
         */
        MNCalendarVerticalConfig mnCalendarVerticalConfig = new MNCalendarVerticalConfig.Builder()
                .setMnCalendar_showWeek(true)                   //是否显示星期栏
                .setMnCalendar_showLunar(true)                  //是否显示阴历
                .setMnCalendar_colorWeek(graydark)             //星期栏的颜色
                .setMnCalendar_titleFormat("yyyy年MM月")           //每个月的标题样式
                .setMnCalendar_colorTitle(graydark)            //每个月标题的颜色
                .setMnCalendar_colorSolar(graydark)            //阳历的颜色
                .setMnCalendar_colorLunar(graydark)            //阴历的颜色
                .setMnCalendar_colorBeforeToday(graylight)      //今天之前的日期的颜色
                .setMnCalendar_colorRangeBg(gold)        //区间中间的背景颜色
                .setMnCalendar_colorRangeText(white)        //区间文字的颜色
                .setMnCalendar_colorStartAndEndBg(gold)    //开始结束的背景颜色
                .setMnCalendar_countMonth(12)                    //显示多少月(默认6个月)
                .build();
        mnCalendarVertical.setConfig(mnCalendarVerticalConfig);
    }


    /**
     * 时间选择监听
     */
    public interface MyTimePickListener {
        public void onTimePick(int hour, int minute);
    }

    /**
     * 机构选择监听
     */
    public interface MyJigouChooseListener {
        public void onJigouChoose(CalendarJigouModel calendarJigouModel);
    }

    /**
     * 星期选择监听
     */
    public interface MyPeriodPickListener {
        public void onPeriodPick(Date start, Date end);
    }

    /**
     * 星期选择监听
     */
    public interface MyWeekChooseListener {
        public void onWeekChoose(List<Integer> weeks);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pop_calendar_cancle:
                break;
            case R.id.pop_calendar_sure:
                if (type == 1) {
                    myTimePickListener.onTimePick(timePicker.getHour(), timePicker.getMinute());
                } else if (type == 2) {
                    myTimePickListener.onTimePick(timePicker.getHour(), timePicker.getMinute());
                } else if (type == 3) {
                    String jigou = jigouEv.getText().toString();
                    if (!TextUtils.isEmpty(jigou)) {
                        CalendarJigouModel jigouModel = new CalendarJigouModel();
                        jigouModel.setName(jigou);
                        myJigouChooseListener.onJigouChoose(jigouModel);
                    } else {
                        for (CheckStatusModel checkStatusModel : jigouDatas) {
                            if (checkStatusModel.isCheckStatus()) {
                                myJigouChooseListener.onJigouChoose(checkStatusModel
                                        .getCalendarJigouModel());
                            }
                        }
                    }

                } else if (type == 4) {
                    myPeriodPickListener.onPeriodPick(startDate, endDate);
                } else if (type == 5) {
                    List<Integer> ds = new ArrayList<>();
                    for (CheckStatusModel cc : weekDatas) {
                        if (cc.isCheckStatus()) {
                            ds.add(cc.getWeekId());
                        }
                    }
                    myWeekChooseListener.onWeekChoose(ds);
                }
                break;
        }
        window.dismiss();
    }

    /**
     *
     */
    public class CalendarGridAdapter extends BaseAdapter {

        private Context mContext;
        private List<CheckStatusModel> datas = new ArrayList<>();

        public CalendarGridAdapter(Context context, List<CheckStatusModel> datas) {
            mContext = context;
            this.datas = datas;
        }

        public int getCount() {
            return datas.size();
        }

        public Object getItem(int position) {
            return datas.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout
                    .item_calendar_jigou);
            TextView textView = viewHolder.getView(R.id.calendar_option_text);
            if (datas.get(position).isCheckStatus()) {
                textView.setTextColor(mContext.getResources().getColor(R.color.white));
                textView.setBackgroundResource(R.drawable.gold_circle);
            } else {
                textView.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
                textView.setBackgroundResource(R.drawable.gray_white_xiankuang);
            }
            textView.setText(datas.get(position).getName());
            return viewHolder.getConvertView();
        }

    }

    /**
     * 周次、机构选择model
     */
    public class CheckStatusModel extends Entry {
        private String name;
        private boolean checkStatus = false;
        private CalendarJigouModel calendarJigouModel;
        private int weekId;

        public int getWeekId() {
            return weekId;
        }

        public void setWeekId(int weekId) {
            this.weekId = weekId;
        }

        public CheckStatusModel(CalendarJigouModel model) {
            this.calendarJigouModel = model;
            this.name = model.getName();
        }

        public CheckStatusModel(String name, int weekId) {
            this.name = name;
            this.weekId = weekId;
        }

        public CalendarJigouModel getCalendarJigouModel() {
            return calendarJigouModel;
        }

        public void setCalendarJigouModel(CalendarJigouModel calendarJigouModel) {
            this.calendarJigouModel = calendarJigouModel;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isCheckStatus() {
            return checkStatus;
        }

        public void setCheckStatus(boolean checkStatus) {
            this.checkStatus = checkStatus;
        }
    }

}
