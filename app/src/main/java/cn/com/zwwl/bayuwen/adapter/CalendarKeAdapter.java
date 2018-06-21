package cn.com.zwwl.bayuwen.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.AddCalendarActivity;
import cn.com.zwwl.bayuwen.activity.ChildInfoActivity;
import cn.com.zwwl.bayuwen.activity.TeacherDetailActivity;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.model.CalendarEventModel;
import cn.com.zwwl.bayuwen.model.ChildModel;
import cn.com.zwwl.bayuwen.model.fm.AlbumModel;
import cn.com.zwwl.bayuwen.util.Tools;

/**
 * 日历页面课程卡片
 */
public class CalendarKeAdapter extends RecyclerView.Adapter<CalendarKeAdapter.ViewHolder> {

    private List<CalendarEventModel> datas = new ArrayList<>();
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tag;
        TextView title;
        TextView xiaoqu;
        TextView time;
        LinearLayout teacherLayout;
        LinearLayout calendar_item_bg;


        public ViewHolder(View view) {
            super(view);
            tag = view.findViewById(R.id.calendar_item_tag);
            title = view.findViewById(R.id.calendar_item_name);
            xiaoqu = view.findViewById(R.id.calendar_item_xiaoqu);
            time = view.findViewById(R.id.calendar_item_time);
            teacherLayout = view.findViewById(R.id.calendar_item_child_layout);
            calendar_item_bg = view.findViewById(R.id.calendar_item_bg);
        }
    }

    public CalendarKeAdapter(Context context, List<CalendarEventModel> mBookList) {
        this.context = context;
        this.datas = mBookList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calender_ke,
                parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (datas.size() > position) {
            final CalendarEventModel item = datas.get(position);
            holder.tag.setText(item.getOrgName());
            holder.title.setText(item.getName());
            holder.time.setText("时间：" + item.getCourseDate() + " " + item.getStartTime() + "-" +
                    item.getEndTime());

            if (item.getIs_thirdorg() == 0) {
                holder.calendar_item_bg.setBackgroundResource(R.drawable.gold_white_xiangkuang);
                holder.xiaoqu.setText("校区：" + item.getSchool());
            } else {
                holder.calendar_item_bg.setBackgroundResource(R.drawable.gray_white_xiankuang);
                holder.xiaoqu.setText("地址：" + item.getAddress());
            }
            if (Tools.listNotNull(item.getTeacher())) {
                for (CalendarEventModel.TeacherBean teacherBean : item.getTeacher()) {
                    holder.teacherLayout.addView(getChildView(teacherBean), LinearLayout
                            .LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                }
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.getIs_thirdorg() == 0) {
                        return;
                    }
                    Intent i = new Intent(context, AddCalendarActivity.class);
                    i.putExtra("AddCalendarActivity_data", item.getId());
                    context.startActivity(i);
                }
            });
        }
    }


    private View getChildView(final CalendarEventModel.TeacherBean teacherBean) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_teacher_calendar_list,
                null);
        TextView name = view.findViewById(R.id.item_t_c_name);
        ImageView avat = view.findViewById(R.id.item_t_c_avatar);
        name.setText(teacherBean.getName());
        if (!TextUtils.isEmpty(teacherBean.getPic()))
            ImageLoader.display(context, avat, teacherBean.getPic(), R.drawable
                    .avatar_placeholder, R.drawable.avatar_placeholder);

        view.setOnClickListener(new View
                .OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, TeacherDetailActivity.class);
                i.putExtra("tid", teacherBean.getTid());
                context.startActivity(i);
            }
        });
        return view;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

}
