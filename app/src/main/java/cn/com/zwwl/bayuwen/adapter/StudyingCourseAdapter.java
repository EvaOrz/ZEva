package cn.com.zwwl.bayuwen.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.UnitModel;

public class StudyingCourseAdapter extends BaseQuickAdapter<UnitModel, BaseViewHolder> {
    public StudyingCourseAdapter(@Nullable List<UnitModel> data) {
        super(R.layout.item_studying_course, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UnitModel item) {
        helper.setText(R.id.course_name, item.getTitle());
    }
}
