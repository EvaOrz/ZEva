package cn.com.zwwl.bayuwen.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.model.CommonModel;
import cn.com.zwwl.bayuwen.model.WorkListModel;
import cn.com.zwwl.bayuwen.widget.CircleImageView;


public class Pic1Adapter extends BaseQuickAdapter<WorkListModel.ChildClassInfoBeanX.JobBean.JobImgBean, BaseViewHolder> {
    public Pic1Adapter(@Nullable List<WorkListModel.ChildClassInfoBeanX.JobBean.JobImgBean> data) {
        super(R.layout.item_pic, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkListModel.ChildClassInfoBeanX.JobBean.JobImgBean item) {
        ImageLoader.display(mContext, (CircleImageView) helper.getView(R.id.pic), item.getUrl());
    }
}
