package cn.com.zwwl.bayuwen.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.LessonModel;

public class TestListAdapter extends BaseQuickAdapter<LessonModel,BaseViewHolder> {
    public TestListAdapter(@Nullable List<LessonModel> data) {
        super(R.layout.item_test_end,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LessonModel item) {

    }
}
