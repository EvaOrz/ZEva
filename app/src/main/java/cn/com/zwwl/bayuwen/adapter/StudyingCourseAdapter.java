package cn.com.zwwl.bayuwen.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.LessonModel;

public class StudyingCourseAdapter extends BaseQuickAdapter<LessonModel, BaseViewHolder> {
    public StudyingCourseAdapter(@Nullable List<LessonModel> data) {
        super(R.layout.item_studying_course, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LessonModel item) {
        helper.setText(R.id.course_name, item.getTitle());
    }
}
