package cn.com.zwwl.bayuwen.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.CourseModel;
import cn.com.zwwl.bayuwen.model.EleCourseModel;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * Created by lousx
 */
public class CourseListAdapter extends BaseAdapter {
    private List<CourseModel> mList = new LinkedList<>();
    private Context mContext;

    public CourseListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        final CourseModel item = getItem(position);

        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout.item_c_list);

        TextView title = viewHolder.getView(R.id.video_title);
        TextView time = viewHolder.getView(R.id.video_time);
        title.setText(position + 1 + "  中国神话传说及文化寓意");
        time.setText("2018-02-09 19:15-20:15 ( 60分钟 )");
        return viewHolder.getConvertView();
    }

    public void setData(List<CourseModel> list) {
        if (list != null) {
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void addMoreData(List<CourseModel> list) {
        if (list == null)
            return;
        for (CourseModel model : list)
            ((LinkedList<CourseModel>) mList).addLast(model);
        notifyDataSetChanged();

    }

    public void clear() {
        if (mList != null && mList.size() == 0)
            return;
        mList.clear();
        notifyDataSetChanged();
    }
}
