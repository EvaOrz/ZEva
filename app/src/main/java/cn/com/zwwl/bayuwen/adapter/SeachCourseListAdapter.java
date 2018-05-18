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
import cn.com.zwwl.bayuwen.model.CourseVideoModel;

/**
 * Created by lousx
 */
public class SeachCourseListAdapter extends BaseRecylcerViewAdapter<CourseVideoModel> {
    private List<CourseVideoModel> mList = new LinkedList<>();
    private Context mContext;

    public SeachCourseListAdapter(Context mContext, List<CourseVideoModel> list) {
        super(mContext, list);
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public SeachCourseListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SeachCourseListAdapter.ViewHolder(inflater.inflate(R.layout.item_course_video, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final SeachCourseListAdapter.ViewHolder viewHolder = (SeachCourseListAdapter.ViewHolder) holder;
//        viewHolder.stateTv.setText(position + 1 + "  中国神话传说及文化寓意");
//        viewHolder.tNameTv.setText("2018-02-09 19:15-20:15 ( 60分钟 )");
//        viewHolder.addressTv.setText(position + 1 + "  中国神话传说及文化寓意");
//        viewHolder.dateTv.setText("2018-02-09 19:15-20:15 ( 60分钟 )");
//        viewHolder.timeTv.setText(position + 1 + "  中国神话传说及文化寓意");
//        viewHolder.surplus_quotaTv.setText("2018-02-09 19:15-20:15 ( 60分钟 )");
//        viewHolder.priceTv.setText(position + 1 + "  中国神话传说及文化寓意");
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView stateTv;
        private TextView tNameTv;
        private TextView addressTv;
        private TextView dateTv;
        private TextView timeTv;
        private TextView surplus_quotaTv;
        private TextView priceTv;

        public ViewHolder(View itemView) {
            super(itemView);
            initView();
        }

        private void initView() {
            stateTv =  itemView.findViewById(R.id.stateTv);
            tNameTv =  itemView.findViewById(R.id.tNameTv);
            addressTv =  itemView.findViewById(R.id.addressTv);
            dateTv =  itemView.findViewById(R.id.dateTv);
            timeTv =  itemView.findViewById(R.id.timeTv);
            surplus_quotaTv =  itemView.findViewById(R.id.surplus_quotaTv);
            priceTv = itemView.findViewById(R.id.priceTv);
        }
    }
}
