package cn.com.zwwl.bayuwen.adapter;


import android.support.design.widget.TabLayout;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.MyCourseModel;
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
        switch (helper.getItemViewType()) {
            case 1://线上
                helper.setText(R.id.course_name, item.getProducts().getTitle());
                helper.setText(R.id.progress_value, String.format("课程进度（%s/%s）", item.getPlan().getCurrent(), item.getPlan().getCount()));
                helper.setMax(R.id.progress, item.getPlan().getCount());
                helper.setProgress(R.id.progress, item.getPlan().getCurrent());
                helper.setText(R.id.per, Tools.parseDecimal((double) item.getPlan().getCurrent() / item.getPlan().getCount()));
                helper.setText(R.id.evaluate, item.getComments().getJob());
                helper.setText(R.id.status, String.format("下次上课时间:%s", item.getPlan().getNextTime()));
                helper.addOnClickListener(R.id.look_video);
                break;
            default://线下
                helper.setText(R.id.course_name, item.getProducts().getTitle());
                helper.setText(R.id.progress_value, String.format("课程进度（%s/%s）", item.getPlan().getCurrent(), item.getPlan().getCount()));
                helper.setMax(R.id.progress, item.getPlan().getCount());
                helper.setProgress(R.id.progress, item.getPlan().getCurrent());
                helper.setText(R.id.per, Tools.parseDecimal((double) item.getPlan().getCurrent() / item.getPlan().getCount()));
                helper.setText(R.id.evaluate, item.getComments().getStudent());
                ((TabLayout) helper.getView(R.id.tab)).addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        if (("顾问对学生的评价").equals(tab.getText().toString())) {
                            helper.setText(R.id.evaluate, item.getComments().getStudent());
                        } else {
                            helper.setText(R.id.evaluate, item.getComments().getParent());
                        }
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
                break;
        }
    }
}
