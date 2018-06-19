package cn.com.zwwl.bayuwen.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.model.TopicDetailModel;
import cn.com.zwwl.bayuwen.widget.CircleImageView;

public class TopicUserAnswerAdapter extends BaseAdapter {
    protected Activity mContext;
    protected List<TopicDetailModel.UserCommentBean> userCommentBeans = new ArrayList<>();


    public TopicUserAnswerAdapter(Activity mContext, List<TopicDetailModel.UserCommentBean> mItemList) {
        this.mContext = mContext;
        this.userCommentBeans = mItemList;
    }

    @Override
    public int getCount() {
        return userCommentBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return userCommentBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_user_topicdetail_layout, null);
            mViewHolder = new ViewHolder(convertView);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
      if (userCommentBeans.get(position).getUser_pic()!=null) {
          ImageLoader.display(mContext, mViewHolder.userIcon, userCommentBeans.get(position).getUser_pic());
      }else{
          ImageLoader.display(mContext, mViewHolder.userIcon, R.mipmap.ic_launcher);
      }
        mViewHolder.userName.setText(userCommentBeans.get(position).getUser_name());
        mViewHolder.userContent.setText( userCommentBeans.get(position).getContent());


        return convertView;
    }


    public static class ViewHolder {

        @BindView(R.id.user_icon)
        CircleImageView userIcon;
        @BindView(R.id.user_name)
        TextView userName;
        @BindView(R.id.user_content)
        TextView userContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

