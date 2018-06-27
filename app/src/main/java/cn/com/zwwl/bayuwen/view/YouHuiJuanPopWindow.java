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
import android.widget.Toast;

import com.maning.calendarlibrary.MNCalendarVertical;
import com.maning.calendarlibrary.listeners.OnCalendarRangeChooseListener;
import com.maning.calendarlibrary.model.MNCalendarVerticalConfig;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.api.order.CouponApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.CouponModel;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.util.AppValue;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 领取优惠券option window
 */
public class YouHuiJuanPopWindow implements View.OnClickListener {

    private Context mContext;
    private PopupWindow window;
    private ListView listView;
    private YouhuiAdapter adapter;
    private List<CouponModel> couponModels = new ArrayList<>();
    private int type = 1;
    private OnPickYouhuiListener listener;

    public interface OnPickYouhuiListener {
        public void onPick(CouponModel couponModel);
    }

    /**
     * 可领取的优惠券
     *
     * @param context
     */
    public YouHuiJuanPopWindow(Context context, List<CouponModel> cs) {
        mContext = context;
        this.type = 1;
        couponModels.clear();
        couponModels.addAll(cs);
        init();
    }

    /**
     * 可使用的优惠券
     *
     * @param context
     * @param cs
     * @param listener
     */
    public YouHuiJuanPopWindow(Context context, List<CouponModel> cs, OnPickYouhuiListener
            listener) {
        mContext = context;
        this.type = 2;
        this.listener = listener;
        couponModels.clear();
        couponModels.addAll(cs);
        init();
    }


    public void init() {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.pop_youhuijuan, null);
        view.findViewById(R.id.youhuijuan_close)
                .setOnClickListener(this);
        TextView youNo = view.findViewById(R.id.youhuijuan_no);
        youNo.setOnClickListener(this);
        if (type == 1) {
            youNo.setVisibility(View.GONE);
        }else  youNo.setVisibility(View.VISIBLE);
        listView = view.findViewById(R.id.youhuijuan_listview);
        adapter = new YouhuiAdapter(mContext, couponModels);
        listView.setAdapter(adapter);

        window = new PopupWindow(view, RelativeLayout.LayoutParams.FILL_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.setAnimationStyle(R.style.fetch_image_popup_anim);
        window.update();
        window.setBackgroundDrawable(new BitmapDrawable());
        window.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.youhuijuan_close:
                break;
            case R.id.youhuijuan_no:
                listener.onPick(null);
                break;
        }
        window.dismiss();
    }

    /**
     *
     */
    public class YouhuiAdapter extends BaseAdapter {

        private Context mContext;
        private List<CouponModel> datas = new ArrayList<>();

        public YouhuiAdapter(Context context, List<CouponModel> datas) {
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
            final CouponModel c = datas.get(position);

            TextView price = viewHolder.getView(R.id.youhui_img);
            TextView typeTv = viewHolder.getView(R.id.youhui_type);
            TextView name = viewHolder.getView(R.id.youhui_name);
            TextView time = viewHolder.getView(R.id.youhui_time);
            TextView bt = viewHolder.getView(R.id.youhui_bt);

            String desc = "";
            if (c.getReduce().getDiscount() > 0) {// 折扣券
                desc = (int) (c.getReduce().getDiscount() * 10) + "折";
                typeTv.setText("折扣券");
            } else {
                desc = "￥" + (int) c.getReduce().getDecrease();
                typeTv.setText("优惠券");
            }
            price.setText(desc);
            name.setText(c.getName());
            time.setText(c.getStart_use_time().substring(0, 10) + " 至 " + c.getEnd_use_time()
                    .substring(0, 10));
            if (type == 1) {
                bt.setText("领取");
            } else if (type == 2) {
                bt.setText("使用");
            }

            viewHolder.getView(R.id.click_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (type == 1) {
                        lingqu(c.getId());
                    } else if (type == 2) {
                        listener.onPick(c);
                        window.dismiss();
                    }
                }
            });

            return viewHolder.getConvertView();
        }

    }

    private void lingqu(String id) {
        new CouponApi(mContext, id, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {

            }

            @Override
            public void setError(ErrorMsg error) {
                if (error == null) AppValue.showToast(mContext, "领取成功");
                else AppValue.showToast(mContext, error.getDesc());
            }
        });
    }


}
