package cn.com.zwwl.bayuwen.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.KeModel;

/**
 * 教师详情页面课程adapter
 * Created by lousx on 2018/5/24.
 */

public class TCourseListAdapter extends BaseRecylcerViewAdapter<KeModel> {
    public TCourseListAdapter(Context mContext, List<KeModel> list) {
        super(mContext, list);
    }

    @NonNull
    @Override
    public TCourseListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int
            viewType) {
        return new TCourseListAdapter.ViewHolder(inflater.inflate(R.layout.item_course_list,
                parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final TCourseListAdapter.ViewHolder viewHolder = (TCourseListAdapter.ViewHolder) holder;
//        viewHolder.fcNameTv.setText(list.get(position).getTitle());
//        viewHolder.fcnoTv.setText("班级编码：" + list.get(position).getModel());
//        viewHolder.fcplaceTv.setText(list.get(position).gets());
//        viewHolder.fctimeTv.setText(list.get(position).getStart_at() + "至" + list.get(position)
// .getEnd_at());
//        viewHolder.fcpriceTv.setText("¥ " + list.get(position).getBuyPrice());
        setItemClickView(viewHolder.itemView, position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView fcNameTv;
        private TextView fcnoTv;
        private TextView fcplaceTv;
        private TextView fctimeTv;
        private TextView fcpriceTv;

        public ViewHolder(View itemView) {
            super(itemView);
            initView();
        }

        private void initView() {
            fcNameTv = itemView.findViewById(R.id.course_tv);
            fcnoTv = itemView.findViewById(R.id.classno_tv);
            fcplaceTv = itemView.findViewById(R.id.place_tv);
            fctimeTv = itemView.findViewById(R.id.time_tv);
            fcpriceTv = itemView.findViewById(R.id.price_tv);
        }
    }
}
