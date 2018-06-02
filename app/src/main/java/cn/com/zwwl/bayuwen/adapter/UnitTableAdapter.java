package cn.com.zwwl.bayuwen.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.UnitModel;

public class UnitTableAdapter extends BaseQuickAdapter<UnitModel, BaseViewHolder> {
    public UnitTableAdapter(@Nullable List<UnitModel> data) {
        super(R.layout.item_unit_table, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UnitModel item) {
        helper.setText(R.id.unit_name, item.getTitle());
    }
}
