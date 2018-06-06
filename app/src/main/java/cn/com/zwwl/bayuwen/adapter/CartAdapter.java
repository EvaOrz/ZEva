package cn.com.zwwl.bayuwen.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 购课单列表adapter
 */
public class CartAdapter extends CheckScrollAdapter<KeModel> {
    protected Context mContext;
    private OnItemCheckChangeListener onItemCheckChangeListener;

    public interface OnItemCheckChangeListener {
        public void onCheckChange(String cartId, boolean cStatus);
    }

    public CartAdapter(Context context, OnItemCheckChangeListener onItemCheckChangeListener) {
        super(context);
        mContext = context;
        this.onItemCheckChangeListener = onItemCheckChangeListener;
    }


    public void setData(List<KeModel> mItemList) {
        clear();
        isScroll = false;
        synchronized (mItemList) {
            for (KeModel item : mItemList) {
                add(item);
            }
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout.item_order_cart);
        final KeModel model = getItem(position);

        CheckBox checkBox = viewHolder.getView(R.id.item_order_check);
        TextView tag = viewHolder.getView(R.id.item_order_tag);
        TextView title = viewHolder.getView(R.id.item_order_title);
        TextView teacher = viewHolder.getView(R.id.item_order_teacher);
        TextView date = viewHolder.getView(R.id.item_order_date);
        TextView time = viewHolder.getView(R.id.item_order_time);
        TextView xiaoqu = viewHolder.getView(R.id.item_order_xiaoqu);
        TextView price = viewHolder.getView(R.id.item_order_price);
        ImageView pic = viewHolder.getView(R.id.item_order_pic);
        ImageLoader.display(mContext, pic, model.getPic(), R.drawable.avatar_placeholder, R
                .drawable.avatar_placeholder);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onItemCheckChangeListener.onCheckChange(model.getCartId(), isChecked);
            }
        });
        tag.setText(model.getTagTxt());
        title.setText(model.getTitle());
        teacher.setText(model.getTname());
        date.setText(CalendarTools.format(Long.valueOf(model.getStartPtime()),
                "yyyy-MM-dd") + " 至 " + CalendarTools.format(Long.valueOf(model
                        .getEndPtime()),
                "yyyy-MM-dd"));
        time.setText(model.getClass_start_at() + " - " + model.getClass_end_at
                ());
        xiaoqu.setText(model.getSchool());
        price.setText(Double.valueOf(model.getBuyPrice()) / 100 + "");

        return viewHolder.getConvertView();
    }

    public boolean isScroll() {
        return isScroll;
    }

}