package cn.com.zwwl.bayuwen.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.widget.CircleImageView;

public class InClassStatusAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public InClassStatusAdapter(List<String> data) {
        super(R.layout.item_in_class_status, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageLoader.display(mContext, (CircleImageView) helper.getView(R.id.pic), item);
    }
}
