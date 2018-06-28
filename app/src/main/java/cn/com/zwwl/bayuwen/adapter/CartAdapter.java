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
import cn.com.zwwl.bayuwen.dialog.AskDialog;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 购课单列表\开发票列表adapter
 */
public class CartAdapter extends CheckScrollAdapter<KeModel> {
    protected Context mContext;
    private OnItemCheckChangeListener onItemCheckChangeListener;
    private boolean isPiao = false;// 是否是开发票页面

    public interface OnItemCheckChangeListener {
        public void onCheckChange(int position, boolean cStatus);

        public void onDelete(int position);
    }

    public CartAdapter(Context context, boolean isPiao, OnItemCheckChangeListener
            onItemCheckChangeListener) {
        super(context);
        mContext = context;
        this.isPiao = isPiao;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout.item_order_cart);
        final KeModel model = getItem(position);

        final CheckBox checkBox = viewHolder.getView(R.id.item_order_check);
        ImageView tag = viewHolder.getView(R.id.item_order_tag);
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
                if (!isPiao && isChecked && Integer.valueOf(model.getNum()) == 0) {
                    new AskDialog(mContext, "该课程已满班，是否从购课单移除？", new AskDialog
                            .OnSurePickListener() {
                        @Override
                        public void onSure() {
                            checkBox.setChecked(false);
                            onItemCheckChangeListener.onDelete(position);
                        }
                    });
                } else
                    onItemCheckChangeListener.onCheckChange(position, isChecked);
            }
        });
        if (model.isHasSelect()) {
            checkBox.setChecked(true);
        } else checkBox.setChecked(false);

        tag.setImageResource(model.getTagImg());
        title.setText(model.getTitle());
        teacher.setText(model.getTname());
        date.setText(CalendarTools.format(Long.valueOf(model.getStartPtime()),
                "yyyy-MM-dd") + " 至 " + CalendarTools.format(Long.valueOf(model
                        .getEndPtime()),
                "yyyy-MM-dd"));
        time.setText(model.getClass_start_at() + " - " + model.getClass_end_at
                ());
        xiaoqu.setText(model.getSchool());
        price.setText("￥" + model.getBuyPrice());

        return viewHolder.getConvertView();
    }

    public boolean isScroll() {
        return isScroll;
    }

}