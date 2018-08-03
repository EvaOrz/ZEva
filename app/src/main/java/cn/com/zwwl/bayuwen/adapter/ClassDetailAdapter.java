package cn.com.zwwl.bayuwen.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.model.TeacherModel;

public class ClassDetailAdapter extends BaseQuickAdapter<TeacherModel, BaseViewHolder> {
    public ClassDetailAdapter(@Nullable List<TeacherModel> data) {
        super(R.layout.item_class_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TeacherModel item) {
        helper.setText(R.id.teacher, item.getName());
        helper.setText(R.id.description, item.getT_desc());
        ImageLoader.display(mContext, (AppCompatImageView) helper.getView(R.id.pic), item.getPic());
        if (helper.getLayoutPosition() == mData.size() - 1) {
            helper.setVisible(R.id.divider, false);
        }
    }
}
