package cn.com.zwwl.bayuwen.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.LessonModel;

public class UnitTableAdapter extends BaseQuickAdapter<LessonModel, BaseViewHolder> {
    public UnitTableAdapter(@Nullable List<LessonModel> data) {
        super(R.layout.item_unit_table, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LessonModel item) {
        helper.setText(R.id.course_name, item.getTitle());
        helper.setText(R.id.description,item.getDesc());
    }
}
