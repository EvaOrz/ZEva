package cn.com.zwwl.bayuwen.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.CourseModel;

public class StudyingCourseAdapter extends BaseQuickAdapter<CourseModel,BaseViewHolder> {
    public StudyingCourseAdapter(@Nullable List<CourseModel> data) {
        super(R.layout.item_studying_course,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseModel item) {

    }
}
