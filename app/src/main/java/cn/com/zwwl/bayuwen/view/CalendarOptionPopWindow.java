package cn.com.zwwl.bayuwen.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import cn.com.zwwl.bayuwen.R;

/**
 * 添加课程页面option window
 */
public class CalendarOptionPopWindow implements View.OnClickListener {

    private int type = 0;
    private Context mContext;
    private PopupWindow window;
    private TextView title;
    private TimePicker timePicker;
    private MyTimePickListener myTimePickListener;
    private MyJigouChooseListener myJigouChooseListener;
    private GridView gridView;

    /**
     * @param context
     * @param type    1:上课时间 2:下课时间 3:课程机构 4:日期选择 5:周次选择
     */
    public CalendarOptionPopWindow(Context context, MyJigouChooseListener myJigouChooseListener, int type) {
        mContext = context;
        this.myJigouChooseListener = myJigouChooseListener;
        this.type = type;
        init();
    }

    public CalendarOptionPopWindow(Context context, MyTimePickListener myTimePickListener, int type) {
        mContext = context;
        this.type = type;
        this.myTimePickListener = myTimePickListener;
        init();
    }


    public void init() {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.pop_calendar_option, null);
        window = new PopupWindow(view, RelativeLayout.LayoutParams.FILL_PARENT,
                RelativeLayout.LayoutParams.FILL_PARENT);
        window.setFocusable(true);

        window.setOutsideTouchable(true);
        window.setAnimationStyle(R.style.fetch_image_popup_anim);
        window.update();
        window.setBackgroundDrawable(new BitmapDrawable());
        window.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        view.findViewById(R.id.pop_calendar_cancle)
                .setOnClickListener(this);
        view.findViewById(R.id.pop_calendar_sure)
                .setOnClickListener(this);
        title = view.findViewById(R.id.pop_calendar_title);
        timePicker = view.findViewById(R.id.pop_calendar_timepicker);
        gridView = view.findViewById(R.id.pop_calendar_grid);

        if (type == 1) {
            title.setText("选择上课时间");
            timePicker.setVisibility(View.VISIBLE);
        } else {
            timePicker.setVisibility(View.GONE);
        }
        if (type == 2) {
            title.setText("选择下课时间");
            timePicker.setVisibility(View.VISIBLE);
        } else {
            timePicker.setVisibility(View.GONE);
        }
        if (type == 3) {
            title.setText("选择课程机构");
            gridView.setVisibility(View.VISIBLE);
        } else {
            gridView.setVisibility(View.GONE);
        }
        if (type == 4) {
            title.setText("选择日期");
        } else {
        }
        if (type == 5) {
            title.setText("选择周次");
            gridView.setVisibility(View.VISIBLE);
        } else {
            gridView.setVisibility(View.GONE);
        }
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
        public void onJigouChoose(String name);
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
                } else if (type == 4) {
                }
                break;
        }
        window.dismiss();
    }

}
