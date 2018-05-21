package cn.com.zwwl.bayuwen.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.CalendarActivity;
import cn.com.zwwl.bayuwen.util.CalendarTools;

/**
 * 日历页面月份筛选控件
 */
public class CalendarMonthSelectView extends HorizontalScrollView {

    private Context mContext;
    private LinearLayout layout;
    private List<View> checkSelectView = new ArrayList<>();//

    /**
     * 由于可能是独立栏目，并且绑定在列表上，导致每次切换的时候都还原，所以设置成静态变量
     */
    public static int selectPosition = -1;

    public CalendarMonthSelectView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public void setCorruntPosition(int p) {
        selectPosition = p;
    }

    public CalendarMonthSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    private void init() {


    }


    public void setData(List<Calendar> calendars) {
        if (calendars == null || calendars.size() < 1) return;
        removeAllViews();
        checkSelectView.clear();
        layout = new LinearLayout(mContext);
        for (int i = 0; i < calendars.size(); i++) {
            final int position = i;
            TextView textView = new TextView(mContext);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(16);
            textView.setPadding(10, 10, 10, 10);
            textView.setTag(calendars.get(i));
            textView.setText(CalendarTools.format(calendars.get(i).getTimeInMillis(), "yyyy年MM月"));
            textView.setLayoutParams(new ViewGroup.LayoutParams(MyApplication.width / 3, ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickItem((Calendar) v.getTag(), position);
                }
            });

            layout.addView(textView);
            checkSelectView.add(textView);
        }
        addView(layout);
        setSelectedItemForChild(calendars.get(0));

    }

    protected void clickItem(final Calendar calendar, final int position) {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                click(calendar, position);
            }
        }, 250);
    }

    private void click(Calendar calendar, int position) {
        if (mContext instanceof CalendarActivity) {

            // 如果点击的是当前显示的cat,不重新 获取数据
            if (selectPosition == position) {
                return;
            }
            selectPosition = position;
            ((CalendarActivity) mContext).doMonthSelect(calendar);// 换内容
            setSelectedItemForChild(calendar);
        }
    }

    /**
     * 设置子栏目选中状态
     *
     * @param current
     */
    @SuppressLint("NewApi")
    public void setSelectedItemForChild(Calendar current) {
        if (layout == null || layout.getChildCount() == 0) return;
        int screenHalf = MyApplication.width / 2;// 屏幕宽度的一半

        for (int i = 0; i < layout.getChildCount(); i++) {

            TextView child = (TextView) layout.getChildAt(i);

            // 选中状态
            if (current.get(Calendar.YEAR) == ((Calendar) child.getTag()).get(Calendar.YEAR) && current.get(Calendar.MONTH) == ((Calendar) child.getTag()).get(Calendar.MONTH)) {
                selectPosition = i;
                child.setTextColor(mContext.getResources().getColor(R.color.gold));
                int scrollX = this.getScrollX();
                int left = child.getLeft();
                int right = child.getRight();
                int leftScreen = left - scrollX + (right - left) / 2;
                smoothScrollBy((leftScreen - screenHalf), 0);
            } else {
                child.setTextColor(mContext.getResources().getColor(R.color.gray_dark));

            }
        }
    }
}
