package cn.com.zwwl.bayuwen.adapter;

import android.support.v7.widget.AppCompatImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.glide.ImageLoader;

public class InClassStatusAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public InClassStatusAdapter(List<String> data) {
        super(R.layout.item_in_class_status, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageLoader.display(mContext, (AppCompatImageView) helper.getView(R.id.pic), item);
    }
}
