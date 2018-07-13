package cn.com.zwwl.bayuwen.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.LessonModel;

/**
 * lmj  on 2018/7/13
 */
public class WorkListAdapter extends BaseQuickAdapter {

    public WorkListAdapter(@Nullable List<LessonModel> data) {
        super(R.layout.item_work_list, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, Object item) {

    }
}
