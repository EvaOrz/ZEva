package cn.com.zwwl.bayuwen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.model.ParentCommentModel;
import cn.com.zwwl.bayuwen.widget.CircleImageView;

public class CommentAdapter extends BaseAdapter {
    private Context mContext;
    private List<ParentCommentModel> mList;
    private LayoutInflater mInflater;

    public CommentAdapter(Context context, List<ParentCommentModel> mList) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mList = mList;
    }

    public void setData(List<ParentCommentModel> mList) {
        this.mList = mList;
    }

    public int getCount() {
        return mList.size();
    }

    public Object getItem(int position) {
        return mList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder;
        if (convertView != null) {
            holder = (Holder) convertView.getTag();
        } else {
            holder = new Holder();
            convertView = mInflater.inflate(R.layout.comment_item, null);
            // 建立对应
            holder.comment_item_top_line = (View) convertView.findViewById(R.id.comment_item_top_line);
            holder.comment_item_avatar = (CircleImageView) convertView.findViewById(R.id.comment_item_avatar);
            holder.comment_item_name = (TextView) convertView.findViewById(R.id.comment_item_name);
            holder.comment_item_date = (TextView) convertView.findViewById(R.id.comment_item_date);
            holder.comment_item_content = (TextView) convertView.findViewById(R.id.comment_item_content);
            convertView.setTag(holder);
        }
        ImageLoader.display(mContext, holder.comment_item_avatar, mList.get(position).getImg(), R
                .drawable.avatar_placeholder, R.drawable.avatar_placeholder);
        holder.comment_item_name.setText(mList.get(position).getAuthor());
        holder.comment_item_date.setText(mList.get(position).getCreated_at());
        holder.comment_item_content.setText(mList.get(position).getContent());
        if (position == 0) {
            holder.comment_item_top_line.setVisibility(View.INVISIBLE);
        } else {
            holder.comment_item_top_line.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    static class Holder {
        private View comment_item_top_line;
        public CircleImageView comment_item_avatar;
        private TextView comment_item_name;
        private TextView comment_item_date;
        private TextView comment_item_content;
    }

}
