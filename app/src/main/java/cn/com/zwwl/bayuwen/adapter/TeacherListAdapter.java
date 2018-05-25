package cn.com.zwwl.bayuwen.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.glide.BorderCircleTransform;
import cn.com.zwwl.bayuwen.glide.CircleTransform;
import cn.com.zwwl.bayuwen.glide.GlideApp;
import cn.com.zwwl.bayuwen.model.CourseDetailModel;
import cn.com.zwwl.bayuwen.model.TeacherModel;

/**
 * Created by lousx on 2016/8/9.
 */
public class TeacherListAdapter extends BaseRecylcerViewAdapter<CourseDetailModel.TeacherEntity> {
    public TeacherListAdapter(Context mContext, List<CourseDetailModel.TeacherEntity> list) {
        super(mContext, list);
    }

    @Override
    public TeacherListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_teacher_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final TeacherListAdapter.ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.name.setText(list.get(position).getName());
        if (list.get(position).getPic()!=null) {
            GlideApp.with(mContext).load(list.get(position).getPic())
                    .placeholder(R.drawable.avatar_placeholder)
                    .error(R.drawable.avatar_placeholder)
                    .transform(new CircleTransform(mContext))
                    .into(viewHolder.avatar);
        }else
            viewHolder.avatar.setImageResource(R.drawable.avatar_placeholder);
        setItemClickView(holder.itemView, position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView avatar;

        public ViewHolder(View itemView) {
            super(itemView);
            initView();
        }

        private void initView() {
            name =  itemView.findViewById(R.id.name);
            avatar =  itemView.findViewById(R.id.avatar);
        }
    }

}
