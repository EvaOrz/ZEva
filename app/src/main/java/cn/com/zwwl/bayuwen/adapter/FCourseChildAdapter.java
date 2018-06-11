package cn.com.zwwl.bayuwen.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.MyCourseModel;
import cn.com.zwwl.bayuwen.util.Tools;

public class FCourseChildAdapter extends BaseQuickAdapter<MyCourseModel.UnfinishedBean, BaseViewHolder> {
    public FCourseChildAdapter(@Nullable List<MyCourseModel.UnfinishedBean> data) {
        super(R.layout.item_unit_table, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyCourseModel.UnfinishedBean item) {
        switch (Tools.getCourseType(Integer.parseInt(item.getProducts().getOnline()), Integer.parseInt(item.getProducts().getSource()), item.getProducts().getEnd_at())) {
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
        helper.setText(R.id.description, item.getProducts().getDesc());
    }
}
