package cn.com.zwwl.bayuwen.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.LessonReportModel;

public class LatestReportAdapter extends BaseQuickAdapter<LessonReportModel, BaseViewHolder> {
    public LatestReportAdapter(@Nullable List<LessonReportModel> data) {
        super(R.layout.item_latest_report, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LessonReportModel item) {


        switch (item.getType()) {
            case 1:
                helper.setText(R.id.report, item.getReport_name());
                helper.setText(R.id.type, R.string.lesson_report);
                break;
            case 2:
                helper.setText(R.id.type, R.string.middle_report);
                break;
            case 3:
                helper.setText(R.id.type, R.string.final_report);
                break;
        }
    }
}
