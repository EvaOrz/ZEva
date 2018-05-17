package cn.com.zwwl.bayuwen.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.CourseModel;
import cn.com.zwwl.bayuwen.model.Pevaluation;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * Created by lousx
 */
public class PevaluationListAdapter extends BaseAdapter {
    private List<Pevaluation> mList = new LinkedList<>();
    private Context mContext;

    public PevaluationListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Pevaluation getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        final CourseModel item = getItem(position);

        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout.item_pevaluation_comment);

        ImageView avatar = viewHolder.getView(R.id.avatar);
        TextView comment_tv = viewHolder.getView(R.id.comment_tv);
        TextView time = viewHolder.getView(R.id.time_tv);
        TextView user_name = viewHolder.getView(R.id.user_name);

        user_name.setText("张妈妈");
        time.setText("2018-02-09 19:15");
        comment_tv.setText("生动有趣生动有趣生动有趣生动有趣生动有趣生动有趣生动有趣生动有趣");
        avatar.setImageResource(R.drawable.avatar_placeholder);
        return viewHolder.getConvertView();
    }

    public void setData(List<Pevaluation> list) {
        if (list != null) {
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void addMoreData(List<Pevaluation> list) {
        if (list == null)
            return;
        for (Pevaluation model : list)
            ((LinkedList<Pevaluation>) mList).addLast(model);
        notifyDataSetChanged();

    }

    public void clear() {
        if (mList != null && mList.size() == 0)
            return;
        mList.clear();
        notifyDataSetChanged();
    }
}
