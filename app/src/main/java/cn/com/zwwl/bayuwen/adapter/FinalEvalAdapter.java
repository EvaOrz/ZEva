package cn.com.zwwl.bayuwen.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.SparseBooleanArray;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.EvalContentModel;

public class FinalEvalAdapter extends BaseQuickAdapter<EvalContentModel.DataBean, BaseViewHolder> {
    private SparseBooleanArray mSelectedPositions = new SparseBooleanArray();

    private void setItemChecked(int position, boolean isChecked) {
        mSelectedPositions.put(position, isChecked);
    }

    private boolean isItemChecked(int position) {
        return mSelectedPositions.get(position);
    }
    public FinalEvalAdapter(int layoutResId, @Nullable List<EvalContentModel.DataBean> data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(final BaseViewHolder helper, EvalContentModel.DataBean item) {
        helper.setText(R.id.content, item.getName());
        ( (AppCompatCheckBox)helper.getView(R.id.content)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setItemChecked(helper.getLayoutPosition(),isChecked);
            }
        });
    }
    //获得选中条目的结果
    public ArrayList<EvalContentModel.DataBean> getSelectedItem() {
        ArrayList<EvalContentModel.DataBean> selectList = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            if (isItemChecked(i)) {
                selectList.add(mData.get(i));
            }
        }
        return selectList;
    }
}
