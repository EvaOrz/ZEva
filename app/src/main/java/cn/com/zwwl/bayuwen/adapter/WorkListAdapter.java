package cn.com.zwwl.bayuwen.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.WorkListModel;

/**
 * lmj  on 2018/7/13
 */
public class WorkListAdapter  extends BaseQuickAdapter<WorkListModel.ChildClassInfoBeanX, BaseViewHolder> {

    public WorkListAdapter(@Nullable List<WorkListModel.ChildClassInfoBeanX> data) {
        super(R.layout.item_work_list, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, WorkListModel.ChildClassInfoBeanX item) {

        helper.setText(R.id.course_name, item.getChildClassInfo().getTitle());
        if (item.getChildClassInfo().getIslook() == 0) {
            helper.setText(R.id.submit_tv, "上传作业");
            helper.setBackgroundRes(R.id.submit_tv, R.drawable.complete_course_see_bg);
            helper.setTextColor(R.id.submit_tv,mContext.getResources().getColor(R.color.lisichen));

        } else if (item.getChildClassInfo().getIslook() == 1) {
            helper.setText(R.id.submit_tv, "待批阅");
            helper.setTextColor(R.id.submit_tv,mContext.getResources().getColor(R.color.workListNoAppro));
            helper.setText(R.id.date_time, item.getChildClassInfo().getSubmitTime());
        } else if (item.getChildClassInfo().getIslook() == 2) {
            helper.setText(R.id.date_time, item.getChildClassInfo().getSubmitTime());
            helper.setTextColor(R.id.submit_tv, mContext.getResources().getColor(R.color.workListScore));
            if (item.getChildClassInfo().getScore() == -1) {
                helper.setText(R.id.submit_tv, "已批阅");
            } else {
                helper.setText(R.id.submit_tv, item.getChildClassInfo().getScore() + "");
            }
        }
    }
}
