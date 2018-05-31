package cn.com.zwwl.bayuwen.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.model.CommonModel;


public class PicAdapter extends BaseQuickAdapter<CommonModel, BaseViewHolder> {
    public PicAdapter(@Nullable List<CommonModel> data) {
        super(R.layout.item_pic, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommonModel item) {
        ImageLoader.displayBorderCircle(mContext, (AppCompatImageView) helper.getView(R.id.pic), item.getUrl());
    }
}
