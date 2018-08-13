package cn.com.zwwl.bayuwen.adapter;


import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.model.MyCourseModel;
import cn.com.zwwl.bayuwen.util.TimeUtil;
import cn.com.zwwl.bayuwen.util.Tools;

public class CourseIndexAdapter extends BaseMultiItemQuickAdapter<MyCourseModel.UnfinishedBean, BaseViewHolder> {
    public CourseIndexAdapter(List<MyCourseModel.UnfinishedBean> data) {
        super(data);
        addItemType(0, R.layout.item_course_preparation_offline);
        addItemType(1, R.layout.item_course_preparation);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MyCourseModel.UnfinishedBean item) {
        helper.addOnClickListener(R.id.trace_title);
        helper.addOnClickListener(R.id.linear_bg);
        helper.addOnClickListener(R.id.work_title);
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
        ImageLoader.display(mContext, (ImageView) helper.getView(R.id.course_cover), item.getProducts().getPic());
        helper.setText(R.id.course_name, item.getProducts().getTitle());
        helper.setText(R.id.course_chapter, String.valueOf(item.getProducts().getDesc()));
        helper.setText(R.id.class_time, String.format("下次上课时间:%s", TimeUtil.parseToHm(item.getPlan().getNextTime())));
        if (item.getPlan().getIs_submit_job() == 1 && item.getPlan().getJob() != null) {
            helper.setText(R.id.work_title, R.string.look_work);
        } else {
            helper.setText(R.id.work_title, R.string.submit_homework);
        }
        if (helper.getItemViewType() == 1) {
            if ((item.getProducts().getType() == "9001") || (item.getProducts().getType() == "22")) {
                helper.getView(R.id.playback_title).setVisibility(View.INVISIBLE);
                helper.getView(R.id.work_title).setVisibility(View.INVISIBLE);
                helper.getView(R.id.trace_title).setVisibility(View.INVISIBLE);
            } else {
                helper.getView(R.id.playback_title).setVisibility(View.VISIBLE);
                helper.getView(R.id.work_title).setVisibility(View.VISIBLE);
                helper.getView(R.id.trace_title).setVisibility(View.VISIBLE);
            }
            helper.addOnClickListener(R.id.course_cover);
            helper.addOnClickListener(R.id.playback_title);
        }
    }
}
