package cn.com.zwwl.bayuwen.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.AlbumModel;

/**
 * 日历页面课程卡片
 */
public class CalendarMonthAdapter extends RecyclerView.Adapter<CalendarMonthAdapter.ViewHolder> {

    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView month;

        public ViewHolder(View view) {
            super(view);
            month = view.findViewById(R.id.calendar_text);
        }
    }

    public CalendarMonthAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calender_month, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.month.setText("");
    }

    @Override
    public int getItemCount() {
//        return datas.size();
        return 12;
    }

}
