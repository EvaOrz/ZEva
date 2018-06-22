package cn.com.zwwl.bayuwen.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.util.AppValue;
import cn.com.zwwl.bayuwen.widget.wheel.OnWheelChangedListener;
import cn.com.zwwl.bayuwen.widget.wheel.OnWheelScrollListener;
import cn.com.zwwl.bayuwen.widget.wheel.WheelView;
import cn.com.zwwl.bayuwen.widget.wheel.adapters.AbstractWheelTextAdapter;

/**
 * 年级选择器
 */
public class NianjiPopWindow implements View.OnClickListener {
    private Context mContext;
    private PopupWindow window;
    private MyNianjiPickListener listener;

    private WheelView wvYear;
    private WheelView wvMonth;
    private WheelView wvDay;

    private ArrayList<String> arry_years = new ArrayList<>();
    private CalendarTextAdapter mYearAdapter;
    private int maxTextSize = 24;
    private int minTextSize = 14;

    private String currentText = "";

    /**
     * @param context
     */
    public NianjiPopWindow(Context context, MyNianjiPickListener listener) {
        mContext = context;
        this.listener = listener;
        init();
    }

    public void init() {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.pop_datepicker, null);
        view.findViewById(R.id.btn_myinfo_sure)
                .setOnClickListener(this);
        view.findViewById(R.id.btn_myinfo_cancel)
                .setOnClickListener(this);
        window = new PopupWindow(view, RelativeLayout.LayoutParams.FILL_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.setAnimationStyle(R.style.fetch_image_popup_anim);
        window.update();
        window.setBackgroundDrawable(new BitmapDrawable());
        window.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        wvYear = (WheelView) view.findViewById(R.id.wv_birth_year);
        wvMonth = (WheelView) view.findViewById(R.id.wv_birth_month);
        wvDay = (WheelView) view.findViewById(R.id.wv_birth_day);
        wvMonth.setVisibility(View.GONE);
        wvDay.setVisibility(View.GONE);

        initYears();
        mYearAdapter = new CalendarTextAdapter(mContext, arry_years, 0, maxTextSize, minTextSize);
        currentText = arry_years.get(0);
        wvYear.setVisibleItems(5);
        wvYear.setViewAdapter(mYearAdapter);
//        wvYear.setCurrentItem(1);

        wvYear.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                currentText = (String) mYearAdapter.getItemText(wheel.getCurrentItem());
            }
        });

        wvYear.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentText = (String) mYearAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, mYearAdapter);
            }
        });


    }

    public void initYears() {
        arry_years.addAll(AppValue.getGradeStrings());
    }


    /**
     * 年级选择监听
     */
    public interface MyNianjiPickListener {
        public void onNianjiPick(String nainji);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_myinfo_sure:

                listener.onNianjiPick(currentText);
                break;
            case R.id.btn_myinfo_cancel:

                break;
        }
        window.dismiss();
    }

    private class CalendarTextAdapter extends AbstractWheelTextAdapter {
        ArrayList<String> list;

        protected CalendarTextAdapter(Context context, ArrayList<String> list, int currentItem,
                                      int maxsize, int minsize) {
            super(context, R.layout.item_birth_year, NO_RESOURCE, currentItem, maxsize, minsize);
            this.list = list;
            setItemTextResource(R.id.tempValue);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index) + "";
        }
    }

    /**
     * 设置字体大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setTextviewSize(String curriteItemText, CalendarTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textvew.setTextSize(maxTextSize);
            } else {
                textvew.setTextSize(minTextSize);
            }
        }
    }


}
