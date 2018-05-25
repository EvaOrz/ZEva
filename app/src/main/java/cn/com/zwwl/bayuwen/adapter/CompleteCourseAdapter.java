package cn.com.zwwl.bayuwen.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.KeModel;

/**
 * Created by lousx
 */
public class CompleteCourseAdapter extends BaseRecylcerViewAdapter<KeModel> {
    private List<KeModel> mList = new LinkedList<>();
    private Context mContext;

    public CompleteCourseAdapter(Context mContext, List<KeModel> list) {
        super(mContext, list);
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public CompleteCourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CompleteCourseAdapter.ViewHolder(inflater.inflate(R.layout.item_praise, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final CompleteCourseAdapter.ViewHolder viewHolder = (CompleteCourseAdapter.ViewHolder) holder;
        viewHolder.tcherNameTv.setTextColor(mContext.getResources().getColor(R.color.body_gray));
        viewHolder.tcherDesTv.setTextColor(mContext.getResources().getColor(R.color.body_gray));
        viewHolder.tcherNameTv.setText("大语文");
        viewHolder.tcherDesTv.setText("语文介绍语文介绍语文介绍语文介绍语文介绍语文介绍语文介绍语文介绍语文介绍");
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView course_cover;
        private TextView tcherNameTv;
        private TextView tcherDesTv;

        public ViewHolder(View itemView) {
            super(itemView);
            initView();
        }

        private void initView() {
            course_cover =  itemView.findViewById(R.id.course_cover);
            tcherNameTv =  itemView.findViewById(R.id.tcherNameTv);
            tcherDesTv =  itemView.findViewById(R.id.tcherDesTv);
        }
    }
}
