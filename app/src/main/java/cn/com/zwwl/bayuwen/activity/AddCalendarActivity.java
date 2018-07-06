package cn.com.zwwl.bayuwen.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.CalendarContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.api.CalendarDetailApi;
import cn.com.zwwl.bayuwen.api.CalendarEActionApi;
import cn.com.zwwl.bayuwen.api.CalendarJigouListApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.CalendarEventModel;
import cn.com.zwwl.bayuwen.model.CalendarJigouModel;
import cn.com.zwwl.bayuwen.model.CourseModel;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.view.CalendarOptionPopWindow;

/**
 * 添加课程页面
 */
public class AddCalendarActivity extends BaseActivity {
    private TextView shangkeTv, xiangkeTv, jigouTv, kaiTv1, kaiTv2, jieTv1, jieTv2, weekCountTv;
    private EditText nameEv, cishuEv, addressEv, teacherEv, codeEv;
    private TextView add_calendar_title;

    private CalendarEventModel calendarEventModel;// 当前日历事件
    private boolean isCanSave = true;// 日历事件不可以修改，只可以增减日期
    private List<Date> periods = new ArrayList<>();// 课程日期集合
    private List<CalendarJigouModel> jigouModels = new ArrayList<>();// 第三方机构列表

    private boolean alarmStatus = true;
    private static String CALANDER_URL = "content://com.android.calendar/calendars";
    private static String CALANDER_EVENT_URL = "content://com.android.calendar/events";
    private static String CALANDER_REMIDER_URL = "content://com.android.calendar/reminders";

    private String[] needPermissions = new String[]{Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR, Manifest.permission.SET_ALARM};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_add);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            askPermission(needPermissions, 101);
        }
        if (!TextUtils.isEmpty(getIntent().getStringExtra("AddCalendarActivity_data"))) {
            isCanSave = false;
            getDetail(getIntent().getStringExtra("AddCalendarActivity_data"));
        } else calendarEventModel = new CalendarEventModel();
        initView();
        initData();
    }

    /**
     * 获取第三方日历事件的详情
     *
     * @param id
     */
    private void getDetail(final String id) {
        new CalendarDetailApi(mContext, id, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                if (entry != null && entry instanceof CalendarEventModel) {
                    calendarEventModel = (CalendarEventModel) entry;
                    handler.sendEmptyMessage(5);
                }
            }

            @Override
            public void setError(ErrorMsg error) {
                if (error != null) {
                    showToast(error.getDesc());
                    calendarEventModel = new CalendarEventModel();
                }
            }
        });
    }

    private void initView() {
        findViewById(R.id.add_calendar_back).setOnClickListener(this);
        findViewById(R.id.add_calendar_save).setOnClickListener(this);
        findViewById(R.id.add_zengshan).setOnClickListener(this);
        findViewById(R.id.add_shangkeshijian).setOnClickListener(this);
        findViewById(R.id.add_xiakeshijian).setOnClickListener(this);
        findViewById(R.id.add_kechengjigou).setOnClickListener(this);
        findViewById(R.id.add_period).setOnClickListener(this);

        add_calendar_title = findViewById(R.id.add_calendar_title);
        shangkeTv = findViewById(R.id.content_shangkeshijian);
        xiangkeTv = findViewById(R.id.content_xiakeshijian);
        jigouTv = findViewById(R.id.content_jigou);
        kaiTv1 = findViewById(R.id.kaike_date);
        kaiTv2 = findViewById(R.id.kaike_week);
        jieTv1 = findViewById(R.id.jieke_date);
        jieTv2 = findViewById(R.id.jieke_week);
        weekCountTv = findViewById(R.id.week_count);
        nameEv = findViewById(R.id.content_name);
        cishuEv = findViewById(R.id.content_cishu);
        addressEv = findViewById(R.id.content_address);
        teacherEv = findViewById(R.id.content_teacher);
        codeEv = findViewById(R.id.content_code);
        if (!isCanSave) {
            add_calendar_title.setText("修改课程");
            nameEv.setFocusable(false);
            cishuEv.setFocusable(false);
            addressEv.setFocusable(false);
            teacherEv.setFocusable(false);
            codeEv.setFocusable(false);
            nameEv.setEnabled(false);
            cishuEv.setEnabled(false);
            addressEv.setEnabled(false);
            teacherEv.setEnabled(false);
            codeEv.setEnabled(false);
            nameEv.setHint("");
            cishuEv.setHint("");
            addressEv.setHint("");
            teacherEv.setHint("");
            codeEv.setHint("");
        }
    }

    @Override
    protected void initData() {
        new CalendarJigouListApi(mContext, new FetchEntryListListener() {
            @Override
            public void setData(List list) {
                if (Tools.listNotNull(list)) {
                    jigouModels.clear();
                    jigouModels.addAll(list);
                }
            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:// 更新上课时间
                    shangkeTv.setText(calendarEventModel.getStartTime());
                    break;
                case 1:// 更新下课时间
                    xiangkeTv.setText(calendarEventModel.getEndTime());
                    break;
                case 2:// 更新课程机构
                    jigouTv.setText(calendarEventModel.getOrgName());
                    break;

                case 3:// 更新课程日期
                    new CalendarOptionPopWindow(mContext, new CalendarOptionPopWindow
                            .MyWeekChooseListener() {
                        @Override
                        public void onWeekChoose(List<Integer> weeks) {
                            List<Date> pickDays = new ArrayList<>();
                            for (Date date : periods) {
                                Calendar c = Calendar.getInstance();
                                c.setTime(date);
                                for (int i : weeks)
                                    if (c.get(Calendar.DAY_OF_WEEK) == i) {
                                        pickDays.add(date);
                                    }
                            }
                            periods.clear();
                            periods.addAll(pickDays);
                        }

                    }, 5);
                    setStartAndEndDate();
                    weekCountTv.setText(calendarEventModel.getTotalWeeks() + "周");
                    break;
                case 4:// 同步到系统日历
                    Toast.makeText(mContext, "正在同步到系统日历", Toast.LENGTH_LONG).show();

                    for (Date date : periods) {
                        long sss = CalendarTools.fromStringToLongtime(calendarEventModel
                                .getStartTime());
                        long xxx = CalendarTools.fromStringToLongtime(calendarEventModel
                                .getEndTime());
                        addCalendarEvent(mContext, nameEv.getText().toString(),
                                calendarEventModel.getOrgName(), date.getTime() + sss, date.getTime
                                        () + xxx);
                    }
                    Toast.makeText(mContext, "已同步课程到系统日历", Toast.LENGTH_SHORT).show();
                    finish();

                    break;
                case 5:// 修改事件初始化
                    nameEv.setText(calendarEventModel.getName() + "");

                    weekCountTv.setText(calendarEventModel.getTotalWeeks() + "周");
                    shangkeTv.setText(calendarEventModel.getStartTime() + "");
                    xiangkeTv.setText(calendarEventModel.getEndTime() + "");
                    cishuEv.setText(calendarEventModel.getTotalNumber() + "");
                    teacherEv.setText(calendarEventModel.getTeacherName() + "");
                    addressEv.setText(calendarEventModel.getAddress() + "");
                    jigouTv.setText(calendarEventModel.getOrgName() + "");
                    codeEv.setText(calendarEventModel.getCode() + "");
                    periods.clear();
                    for (int i = 0; i < calendarEventModel.getCourseDates().size(); i++) {
                        periods.add(CalendarTools.fromStringToca(calendarEventModel
                                .getCourseDates().get(i).getCourseDate()).getTime());

                    }
                    setStartAndEndDate();
                    break;
                case 6:
                    setStartAndEndDate();
                    weekCountTv.setText(calendarEventModel.getTotalWeeks() + "周");
                    break;
            }
        }
    };

    private void setStartAndEndDate() {
        if (Tools.listNotNull(periods)) {
            Date start = periods.get(0);
            Date end = periods.get(periods.size() - 1);
            calendarEventModel.setTotalWeeks(CalendarTools.countTwoDayWeek(start, end));
            DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
            kaiTv1.setText(df.format(start));
            kaiTv2.setText(new SimpleDateFormat("EEE").format(start));

            jieTv1.setText(df.format(end));
            jieTv2.setText(new SimpleDateFormat("EEE").format(end));

        }
    }

    private void doSave() {
        String name = nameEv.getText().toString();
        String totalNumber = cishuEv.getText().toString();
        String teacher = teacherEv.getText().toString();
        String address = addressEv.getText().toString();
        String code = codeEv.getText().toString();
        if (!Tools.listNotNull(periods)) {
            showToast("请选择课程开始和结束日期");
        } else if (TextUtils.isEmpty(name)) {
            showToast("请填写课程名称");
        } else if (TextUtils.isEmpty(totalNumber)) {
            showToast("请填写课程次数");
        } else if (TextUtils.isEmpty(calendarEventModel.getOrgName())) {
            showToast("请填写机构名称");
        } else if (TextUtils.isEmpty(calendarEventModel.getStartTime())) {
            showToast("请填写上课时间");
        } else if (TextUtils.isEmpty(calendarEventModel.getEndTime())) {
            showToast("请填写下课时间");
        } else {
            calendarEventModel.setName(name);
            calendarEventModel.setTotalNumber(Integer.valueOf(totalNumber));
            calendarEventModel.setTeacherName(teacher);
            calendarEventModel.setAddress(address);
            calendarEventModel.setCode(code);
            showLoadingDialog(true);
            new CalendarEActionApi(mContext, calendarEventModel,
                    transPeriod(), new FetchEntryListener() {


                @Override
                public void setData(Entry entry) {
                    showLoadingDialog(false);
                    if (entry != null) {
                        handler.sendEmptyMessage(4);

                    }
                    finish();
                }

                @Override
                public void setError(ErrorMsg error) {
                    showLoadingDialog(false);
                    if (error != null) {
                        showToast(error.getDesc());
                    }
                }
            });
        }
    }

    private void doModify() {
        showLoadingDialog(true);
        new CalendarEActionApi(mContext, calendarEventModel.getId(), transPeriod(), 0, new
                FetchEntryListener() {

                    @Override
                    public void setData(Entry entry) {

                    }

                    @Override
                    public void setError(ErrorMsg error) {
                        showLoadingDialog(false);
                        if (error != null) {
                            showToast(error.getDesc());
                        }
                    }
                });
    }

    private String transPeriod() {
        String strs = "";
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < periods.size(); i++) {
            strs += fm.format(periods.get(i)) + ",";
        }
        return strs;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.add_calendar_save:// 保存
                if (isCanSave) {
                    doSave();
                } else
                    doModify();
                break;
            case R.id.add_calendar_back:
                finish();
                break;
            case R.id.add_zengshan:// 增删课程
                Intent intent = new Intent(mContext, SelectCalendarActivity.class);
                if (isCanSave) {
                    intent.putExtra("SelectCalendarActivity_data", (Serializable) periods);
                } else {
                    intent.putExtra("SelectCalendarActivity_data", calendarEventModel);
                }
                startActivityForResult(intent, 100);
                break;
            case R.id.add_xiakeshijian:// 下课时间
                if (!isCanSave) return;
                hideJianpan();
                new CalendarOptionPopWindow(mContext, new CalendarOptionPopWindow
                        .MyTimePickListener() {
                    @Override
                    public void onTimePick(int hour, int minute) {
                        calendarEventModel.setEndTime(hour + ":" + minute);
                        handler.sendEmptyMessage(1);
                    }
                }, 2);
                break;
            case R.id.add_shangkeshijian:// 上课时间
                if (!isCanSave) return;
                hideJianpan();
                new CalendarOptionPopWindow(mContext, new CalendarOptionPopWindow
                        .MyTimePickListener() {
                    @Override
                    public void onTimePick(int hour, int minute) {
                        calendarEventModel.setStartTime(hour + ":" + minute);
                        handler.sendEmptyMessage(0);
                    }
                }, 1);
                break;

            case R.id.add_kechengjigou:// 课程机构
                if (!isCanSave) return;
                hideJianpan();
                String defuJigou = calendarEventModel.getOrgName();
                new CalendarOptionPopWindow(mContext, jigouModels, new CalendarOptionPopWindow
                        .MyJigouChooseListener() {
                    @Override
                    public void onJigouChoose(CalendarJigouModel model) {
                        calendarEventModel.setOrgName(model.getName());
                        calendarEventModel.setOutOrgId(model.getId());
                        handler.sendEmptyMessage(2);
                    }
                }, defuJigou, 3);
                break;

            case R.id.add_period:// 课程时间段选择器
                if (!isCanSave) return;
                hideJianpan();
                new CalendarOptionPopWindow(mContext, new CalendarOptionPopWindow
                        .MyPeriodPickListener() {

                    @Override
                    public void onPeriodPick(Date start, Date end) {
                        periods.clear();
                        periods.addAll(CalendarTools.betweenDays(start, end));
                        handler.sendEmptyMessage(3);
                    }
                }, 4);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Serializable o = getIntent().getSerializableExtra("SelectCalendar_result_data");
            if (o != null) {
                periods.clear();
                periods.addAll((ArrayList<Date>) o);
                handler.sendEmptyMessage(6);
            }
        }
    }

    /**
     * 检查是否有日历账户
     *
     * @param context
     * @return
     */
    private int checkCalendarAccount(Context context) {
        Cursor userCursor = context.getContentResolver().query(Uri.parse(CALANDER_URL), null,
                null, null, null);
        try {
            if (userCursor == null)//查询返回空值
                return -1;
            int count = userCursor.getCount();
            if (count > 0) {//存在现有账户，取第一个账户的id返回
                userCursor.moveToFirst();
                return userCursor.getInt(userCursor.getColumnIndex(CalendarContract.Calendars._ID));
            } else {
                return -1;
            }
        } finally {
            if (userCursor != null) {
                userCursor.close();
            }
        }
    }


    private static String CALENDARS_NAME = "test";
    private static String CALENDARS_ACCOUNT_NAME = "test@gmail.com";
    private static String CALENDARS_ACCOUNT_TYPE = "com.android.exchange";
    private static String CALENDARS_DISPLAY_NAME = "大语文账户";

    /**
     * 添加日历账户
     *
     * @param context
     * @return
     */
    private long addCalendarAccount(Context context) {
        TimeZone timeZone = TimeZone.getDefault();
        ContentValues value = new ContentValues();
        value.put(CalendarContract.Calendars.NAME, CALENDARS_NAME);

        value.put(CalendarContract.Calendars.ACCOUNT_NAME, CALENDARS_ACCOUNT_NAME);
        value.put(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDARS_ACCOUNT_TYPE);
        value.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, CALENDARS_DISPLAY_NAME);
        value.put(CalendarContract.Calendars.VISIBLE, 1);
        value.put(CalendarContract.Calendars.CALENDAR_COLOR, Color.BLUE);
        value.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars
                .CAL_ACCESS_OWNER);
        value.put(CalendarContract.Calendars.SYNC_EVENTS, 1);
        value.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, timeZone.getID());
        value.put(CalendarContract.Calendars.OWNER_ACCOUNT, CALENDARS_ACCOUNT_NAME);
        value.put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, 0);

        Uri calendarUri = Uri.parse(CALANDER_URL);
        calendarUri = calendarUri.buildUpon().appendQueryParameter(CalendarContract
                .CALLER_IS_SYNCADAPTER, "true").appendQueryParameter(CalendarContract.Calendars
                .ACCOUNT_NAME, CALENDARS_ACCOUNT_NAME).appendQueryParameter(CalendarContract
                .Calendars.ACCOUNT_TYPE, CALENDARS_ACCOUNT_TYPE).build();

        Uri result = context.getContentResolver().insert(calendarUri, value);
        long id = result == null ? -1 : ContentUris.parseId(result);
        return id;
    }

    //检查是否已经添加了日历账户，如果没有添加先添加一个日历账户再查询
    private int checkAndAddCalendarAccount(Context context) {
        int oldId = checkCalendarAccount(context);
        if (oldId >= 0) {
            return oldId;
        } else {
            long addId = addCalendarAccount(context);
            if (addId >= 0) {
                return checkCalendarAccount(context);
            } else {
                return -1;
            }
        }
    }

    /**
     * 添加系统日历事件
     *
     * @param context
     * @param title
     * @param description
     * @param beginTime   毫秒单位
     * @param endTime     毫秒单位
     */

    public void addCalendarEvent(Context context, String title, String description, long
            beginTime, long endTime) {
        // 获取日历账户的id
        int calId = checkAndAddCalendarAccount(context);
        Log.e("日历账户id：", calId + "");
        if (calId < 0) {

            // 获取账户id失败直接返回，添加日历事件失败
            return;
        }

        ContentValues event = new ContentValues();
        event.put("title", title);
        event.put("description", description);
        // 插入账户的id
        event.put("calendar_id", calId);

        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(beginTime);//设置开始时间
        long start = mCalendar.getTime().getTime();
        mCalendar.setTimeInMillis(endTime);//设置终止时间
        long end = mCalendar.getTime().getTime();

        Log.e("start & end ", CalendarTools.format(start / 1000, "yyyy-MM-dd HH:mm") + " ________" +
                CalendarTools.format(end / 1000, "yyyy-MM-dd HH:mm"));

        event.put(CalendarContract.Events.DTSTART, start);
        event.put(CalendarContract.Events.DTEND, start);

        if (alarmStatus) {
            event.put(CalendarContract.Events.HAS_ALARM, 1);//设置有闹钟提醒
        } else {
            event.put(CalendarContract.Events.HAS_ALARM, 0);//设置有闹钟提醒
        }
        event.put(CalendarContract.Events.EVENT_TIMEZONE, "Asia/Beijing");  //这个是时区，必须有，
        //添加事件
        Uri newEvent = context.getContentResolver().insert(Uri.parse(CALANDER_EVENT_URL), event);
        if (newEvent == null) {
            Log.e("添加日历失败", "添加日历失败");
            // 添加日历事件失败直接返回
            return;
        }

        if (alarmStatus) {
            //事件提醒的设定
            ContentValues values = new ContentValues();
            values.put(CalendarContract.Reminders.EVENT_ID, ContentUris.parseId(newEvent));
            // 提前一个小时提醒
            values.put(CalendarContract.Reminders.MINUTES, 60);

            values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
            Uri uri = context.getContentResolver().insert(Uri.parse(CALANDER_REMIDER_URL), values);
            if (uri == null) {
                // 添加闹钟提醒失败直接返回
                Log.e("闹钟添加失败", "闹钟添加失败");
                return;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 101:
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Log.e("ppppppppphas", permissions[i]);
                    } else {
                        Log.e("pppppppppno", permissions[i]);
                    }
                }
                break;
        }
    }
}
