package cn.com.zwwl.bayuwen.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.LessonModel;

public class LessonReportAdapter extends BaseQuickAdapter<LessonModel, BaseViewHolder> {
    public LessonReportAdapter(@Nullable List<LessonModel> data) {
        super(R.layout.item_lesson_report, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LessonModel item) {
        helper.setText(R.id.course_name, item.getTitle());
        helper.setText(R.id.time, String.format("上课时间: %s", item.getStart_at()));
    }
}
