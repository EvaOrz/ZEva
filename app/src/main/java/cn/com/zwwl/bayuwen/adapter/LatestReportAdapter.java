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
                helper.setBackgroundRes(R.id.report_layout, R.mipmap.kejiebaogao_bg);
                break;
            case 2:
                helper.setText(R.id.type, "期中报告");
                helper.setBackgroundRes(R.id.report_layout, R.mipmap.qizhongbaogao_bg);
                break;
            case 3:
                helper.setText(R.id.type, "期末报告");
                helper.setBackgroundRes(R.id.report_layout, R.mipmap.qimobaogao_bg);
                break;
        }
    }
}
