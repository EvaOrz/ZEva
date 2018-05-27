package cn.com.zwwl.bayuwen.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
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
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 领取优惠券option window
 */
public class YouHuiJuanPopWindow implements View.OnClickListener {

    private Context mContext;
    private PopupWindow window;
    private ListView listView;
    private List<String> datas = new ArrayList<>();
    private YouhuiAdapter adapter;

    /**
     * @param context
     */
    public YouHuiJuanPopWindow(Context context) {
        mContext = context;
        init();
    }


    public void init() {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.pop_youhuijuan, null);
        view.findViewById(R.id.youhuijuan_close)
                .setOnClickListener(this);
        listView = view.findViewById(R.id.youhuijuan_listview);
        datas.add("");
        datas.add("");
        datas.add("");
        adapter = new YouhuiAdapter(mContext, datas);
        listView.setAdapter(adapter);
        window = new PopupWindow(view, RelativeLayout.LayoutParams.FILL_PARENT,
                RelativeLayout.LayoutParams.FILL_PARENT);
        window.setFocusable(true);

        window.setOutsideTouchable(true);
        window.update();
        window.setBackgroundDrawable(new BitmapDrawable());
        window.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.youhuijuan_close:
                break;
        }
        window.dismiss();
    }

    /**
     *
     */
    public class YouhuiAdapter extends BaseAdapter {

        private Context mContext;
        private List<String> datas = new ArrayList<>();

        public YouhuiAdapter(Context context, List<String> datas) {
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
            ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout.item_youhuijuan);

            return viewHolder.getConvertView();
        }

    }


}
