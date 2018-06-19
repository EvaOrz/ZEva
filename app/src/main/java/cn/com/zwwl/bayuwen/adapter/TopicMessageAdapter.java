package cn.com.zwwl.bayuwen.adapter;


import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.TopicMessageModel;

public class TopicMessageAdapter extends BaseQuickAdapter<TopicMessageModel, BaseViewHolder> {


    public TopicMessageAdapter(@Nullable List<TopicMessageModel> data) {
        super(R.layout.item_top_layout, data);
    }



    @Override
    protected void convert(BaseViewHolder helper, TopicMessageModel item) {
        if (item.getCourse()!=null||item.getCourse().getName()!=null) {
            helper.setText(R.id.tv_topic_title, item.getCourse().getName());
        }
        helper.setText(R.id.topic_name, item.getName());
        helper.setText(R.id.topic_content, item.getContent());
        helper.setText(R.id.tv_dianzan_id, item.getVote_num()+" 赞");
        helper.setText(R.id.tv_comment_id, item.getComment_num()+" 评论");
//        if ((helper.getAdapterPosition())%4==0){
//            helper.setImageResource(R.id.layout_topic_title, R.color.black);
//        }

    }

}
