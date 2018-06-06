package cn.com.zwwl.bayuwen.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.MyCourseModel;

public class FCourseChildAdapter extends BaseQuickAdapter<MyCourseModel.UnfinishedBean, BaseViewHolder> {
    public FCourseChildAdapter(@Nullable List<MyCourseModel.UnfinishedBean> data) {
        super(R.layout.item_unit_table, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyCourseModel.UnfinishedBean item) {
        helper.setText(R.id.course_name, item.getProducts().getTitle());
        helper.setText(R.id.description, item.getProducts().getDesc());
    }
}
