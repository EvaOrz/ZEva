package cn.com.zwwl.bayuwen.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.LessonModel;
import cn.com.zwwl.bayuwen.util.TimeUtil;

public class LessonReplayAdapter extends BaseQuickAdapter<LessonModel, BaseViewHolder> {
    public LessonReplayAdapter(@Nullable List<LessonModel> data) {
        super(R.layout.item_lesson_replay11, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LessonModel item) {
        helper.setText(R.id.course_name, item.getTitle());
        if (!TextUtils.isEmpty(item.getStart_at()))
            helper.setText(R.id.time, String.format("上课时间: %s", item.getStart_at()));
        else if (item.getStartClassTime() != 0) {
            helper.setText(R.id.time, String.format("上课时间: %s",
                    TimeUtil.parseTime(item.getStartClassTime() * 1000, "yyyy年MM月dd日")));
        }
        if (helper.getLayoutPosition()%2==1){
            helper.setBackgroundRes(R.id.replay_linear, R.color.course_repaly);
        }else{
            helper.setBackgroundRes(R.id.replay_linear, R.color.white);
        }
    }
}
