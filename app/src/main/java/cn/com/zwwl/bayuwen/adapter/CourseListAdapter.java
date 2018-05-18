package cn.com.zwwl.bayuwen.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.CourseModel;

/**
 * Created by lousx
 */
public class CourseListAdapter extends BaseRecylcerViewAdapter<CourseModel> {
    private List<CourseModel> mList = new LinkedList<>();
    private Context mContext;

    public CourseListAdapter(Context mContext,List<CourseModel> list) {
        super(mContext, list);
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public CourseListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CourseListAdapter.ViewHolder(inflater.inflate(R.layout.item_c_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final CourseListAdapter.ViewHolder viewHolder = (CourseListAdapter.ViewHolder) holder;
        viewHolder.video_title.setText(position + 1 + "  中国神话传说及文化寓意");
        viewHolder.video_time.setText("2018-02-09 19:15-20:15 ( 60分钟 )");
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView video_title;
        private TextView video_time;

        public ViewHolder(View itemView) {
            super(itemView);
            initView();
        }

        private void initView() {
            video_title =  itemView.findViewById(R.id.video_title);
            video_time =  itemView.findViewById(R.id.video_time);
        }
    }
}
