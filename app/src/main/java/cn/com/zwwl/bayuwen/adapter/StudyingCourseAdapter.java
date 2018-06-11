package cn.com.zwwl.bayuwen.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.LessonModel;

public class StudyingCourseAdapter extends BaseQuickAdapter<LessonModel, BaseViewHolder> {
    private int type;

    public StudyingCourseAdapter(@Nullable List<LessonModel> data) {
        super(R.layout.item_studying_course, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LessonModel item) {
        switch (type) {
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
        helper.setText(R.id.course_name, item.getTitle());
        helper.setText(R.id.description, item.getDesc());
    }

    public void setType(int course_type) {
        this.type = course_type;
    }
}
