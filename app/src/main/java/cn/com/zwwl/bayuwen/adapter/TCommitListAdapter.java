package cn.com.zwwl.bayuwen.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.TeacherDetailStuentevaluateModel;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.widget.CircleImageView;

/**
 * 教师详情学员评价adapter
 * Created by lmj
 */

public class TCommitListAdapter extends BaseRecylcerViewAdapter<TeacherDetailStuentevaluateModel.CommentsBean> {
    public TCommitListAdapter(Context mContext, List<TeacherDetailStuentevaluateModel.CommentsBean> list) {
        super(mContext, list);
    }

    @NonNull
    @Override
    public TCommitListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int
            viewType) {
        return new TCommitListAdapter.ViewHolder(inflater.inflate(R.layout.teacher_detail_student_evaluate_item,
                parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final TCommitListAdapter.ViewHolder viewHolder = (TCommitListAdapter.ViewHolder) holder;
//        KeModel keModel = list.get(position);
//        viewHolder.student_circleimag.setImageResource(list.get(position).getUserinfo().getPic());
        if (list.get(position).getUserinfo().getPic()!=null){
            ImageLoader.display(mContext,(CircleImageView) viewHolder.student_circleimag,list.get(position).getUserinfo().getPic()+"");
        }
        viewHolder.student_evaluate_tv1.setText(list.get(position).getContent());

          SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = null;

               date = format.format(new Date(Long.parseLong(list.get(position).getCtime())));
               viewHolder.student_evaluate_createtime.setText(date+"");





        viewHolder.student_evaluate_name.setText(list.get(position).getUserinfo().getName());
        setItemClickView(viewHolder.itemView, position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView student_evaluate_tv1;
        private TextView student_evaluate_createtime;
        private TextView  student_evaluate_name;
        private CircleImageView student_circleimag;


        public ViewHolder(View itemView) {
            super(itemView);
            initView();
        }

        private void initView() {
            student_evaluate_tv1 = itemView.findViewById(R.id.student_evaluate_tv1);
            student_evaluate_createtime = itemView.findViewById(R.id.student_evaluate_createtime);
            student_evaluate_name = itemView.findViewById(R.id.student_evaluate_name);
            student_circleimag = itemView.findViewById(R.id.student_circleimag);

        }
    }
}
