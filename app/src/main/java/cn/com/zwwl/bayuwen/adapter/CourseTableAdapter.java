package cn.com.zwwl.bayuwen.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.util.CalendarTools;

public class CourseTableAdapter extends BaseQuickAdapter<KeModel, BaseViewHolder> {
    public CourseTableAdapter(@Nullable List<KeModel> data) {
        super(R.layout.item_course_table, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, KeModel item) {
        helper.setText(R.id.course_name, item.getTitle());
        helper.setText(R.id.teacher_name,item.getTname());
        helper.setText(R.id.school_name,item.getSchool());
        helper.setText(R.id.stock,"剩余名额: " + item.getNum());
        helper.setText(R.id.price,"￥" +item.getBuyPrice());
        helper.setText(R.id.date, CalendarTools.format(Long.valueOf(item.getStartPtime()),
                "yyyy-MM-dd") + " 至 " + CalendarTools.format(Long.valueOf(item.getEndPtime()),
                "yyyy-MM-dd"));
        helper.setText(R.id.time, item.getClass_start_at() + "-" + item.getClass_end_at());
        ImageLoader.display(mContext,(AppCompatImageView)helper.getView(R.id.pic),item.getPic());
        helper.setVisible(R.id.hide_full,"0".equals(item.getStock()));
    }
}
