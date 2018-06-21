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
import cn.com.zwwl.bayuwen.model.TopicDetailModel;

public class TeacherAnswerAdapter extends BaseAdapter {

    protected Activity mContext;
    protected List<TopicDetailModel.TeacherCommentBean> teacherCommentBeans = new ArrayList<>();
    private String titlename;

    public TeacherAnswerAdapter(Activity mContext, List<TopicDetailModel.TeacherCommentBean> mItemList,String titlename) {
        this.mContext = mContext;
        this.teacherCommentBeans = mItemList;
        this.titlename=titlename;
    }

    @Override
    public int getCount() {
        return teacherCommentBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return teacherCommentBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
//        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_teacher_answer_layout, null);
            mViewHolder = new ViewHolder(convertView);

//        } else {
//            mViewHolder = (ViewHolder) convertView.getTag();
//        }

      mViewHolder.teacherTitlename.setText(titlename);
        mViewHolder.teacherName.setText(teacherCommentBeans.get(position).getUser_name());
        mViewHolder.teacherDateId.setText(teacherCommentBeans.get(position).getCreate_at());
        mViewHolder.teacherAnswerContent.setText(teacherCommentBeans.get(position).getContent());


        return convertView;
    }



    public static class ViewHolder {

        @BindView(R.id.teacher_titlename)
        TextView teacherTitlename;
        @BindView(R.id.teacher_name)
        TextView teacherName;
        @BindView(R.id.teacher_dateId)
        TextView teacherDateId;
        @BindView(R.id.teacher_answer_content)
        TextView teacherAnswerContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}




