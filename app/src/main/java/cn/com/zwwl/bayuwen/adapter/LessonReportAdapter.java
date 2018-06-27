package cn.com.zwwl.bayuwen.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.util.TimeUtil;

public class LessonReportAdapter extends BaseQuickAdapter<KeModel, BaseViewHolder> {
    public LessonReportAdapter(@Nullable List<KeModel> data) {
        super(R.layout.item_lesson_report, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, KeModel item) {
        helper.setText(R.id.course_name, item.getTitle());
        helper.setText(R.id.time, String.format("上课时间: %s", TimeUtil.parseTime(item.getStartPtime()*1000,"yyyy年MM月dd日 hh:MM")));
    }
}
