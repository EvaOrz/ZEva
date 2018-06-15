package cn.com.zwwl.bayuwen.adapter;



import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.model.MessageModel;

public class NotifyMessageAdapter extends BaseQuickAdapter<MessageModel.ListBean,BaseViewHolder> {
    public NotifyMessageAdapter( @Nullable List<MessageModel.ListBean> data) {
        super(R.layout.item_notifytopic_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageModel.ListBean item) {
        helper.setText(R.id.time_tv, item.getCreatedTime());
        helper.setText(R.id.item_title,item.getTitle());
        ImageLoader.display(mContext,(ImageView) helper.getView(R.id.image_viewId),item.getUrl());

    }

}
