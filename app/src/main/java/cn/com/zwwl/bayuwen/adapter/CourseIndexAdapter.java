package cn.com.zwwl.bayuwen.adapter;


import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.MyCourseModel;
import cn.com.zwwl.bayuwen.util.TimeUtil;
import cn.com.zwwl.bayuwen.util.Tools;

public class CourseIndexAdapter extends BaseMultiItemQuickAdapter<MyCourseModel.UnfinishedBean, BaseViewHolder> {
    public CourseIndexAdapter(List<MyCourseModel.UnfinishedBean> data) {
        super(data);
        addItemType(0, R.layout.item_course_offline);
        addItemType(1, R.layout.item_course_online);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MyCourseModel.UnfinishedBean item) {
        helper.addOnClickListener(R.id.trace);
        helper.addOnClickListener(R.id.arrow);
        helper.addOnClickListener(R.id.work);
        switch (Tools.getCourseType(item.getPlan().getOnline(), item.getPlan().getSource(), item.getProducts().getEnd_at())) {
            case 1:
                helper.setImageResource(R.id.logo, R.mipmap.icon_face_teach);
                break;
            case 2:
                helper.setImageResource(R.id.logo, R.mipmap.icon_record);
                break;
            case 3:
                helper.setImageResource(R.id.logo, R.mipmap.icon_live);
                break;
            case 4:
                helper.setImageResource(R.id.logo, R.mipmap.icon_replay);
                break;
        }
        helper.setText(R.id.course_name, item.getProducts().getTitle());
        helper.setText(R.id.course_progress, item.getPlan().getTitle());
        helper.setMax(R.id.progress, item.getPlan().getCount());
        helper.setProgress(R.id.progress, item.getPlan().getCurrent());
        helper.setText(R.id.current, String.valueOf(item.getPlan().getCurrent()));
        helper.setText(R.id.total_course, String.valueOf(item.getPlan().getCount()));
        helper.setText(R.id.per, Tools.parseDecimal((double) item.getPlan().getCurrent() / item.getPlan().getCount()));
        helper.setText(R.id.time, String.format("下次上课时间:%s", TimeUtil.parseToDHm(item.getPlan().getNextTime())));
        if (helper.getItemViewType() == 1) {
            helper.addOnClickListener(R.id.look_video);
        }
    }
}
