package cn.com.zwwl.bayuwen.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.CommonModel;


public class RadarAdapter extends BaseQuickAdapter<CommonModel, BaseViewHolder> {
    public RadarAdapter(@Nullable List<CommonModel> data) {
        super(R.layout.item_radar, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommonModel item) {
    }
}
