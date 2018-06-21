package cn.com.zwwl.bayuwen.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.CommonModel;


public class OptionsAdapter extends BaseQuickAdapter<CommonModel, BaseViewHolder> {
    private String choose = null, correct;

    public OptionsAdapter(@Nullable List<CommonModel> data) {
        super(R.layout.item_question_option, data);
    }

    public void setChoose(String choose) {
        this.choose = choose;
        notifyDataSetChanged();
    }

    public String getChoose() {
        return choose;
    }

    public boolean isRight() {
        return choose == correct;
    }

    public void setOptions(List<CommonModel> options, String correct) {
        choose = null;
        this.correct = correct;
        this.setNewData(options);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommonModel item) {
        helper.itemView.setEnabled(choose == null);
        helper.setText(R.id.index, item.getOption());
        helper.setText(R.id.content, item.getContent());
        helper.setBackgroundRes(R.id.index, R.drawable.corner_index_normal);
        helper.setBackgroundRes(R.id.option_layout, R.drawable.corner_option_normal);
        helper.setVisible(R.id.logo, false);
        helper.setTextColor(R.id.index, ContextCompat.getColor(mContext, R.color.text_color_default));
        if (choose != null) {
            if (correct.equals(item.getOption())) {
                helper.setTextColor(R.id.index, ContextCompat.getColor(mContext, R.color.white));
                helper.setVisible(R.id.logo, true);
                helper.setBackgroundRes(R.id.index, R.drawable.corner_index_right);
                helper.setBackgroundRes(R.id.option_layout, R.drawable.corner_option_right);
                helper.setImageResource(R.id.logo, R.mipmap.icon_right);
            }
            if (item.getOption().equals(choose)) {
                helper.setTextColor(R.id.index, ContextCompat.getColor(mContext, R.color.white));
                helper.setVisible(R.id.logo, true);
                if (correct.equals(choose)) {
                    helper.setBackgroundRes(R.id.index, R.drawable.corner_index_right);
                    helper.setBackgroundRes(R.id.option_layout, R.drawable.corner_option_right);
                    helper.setImageResource(R.id.logo, R.mipmap.icon_right);
                } else {
                    helper.setBackgroundRes(R.id.index, R.drawable.corner_index_wrong);
                    helper.setBackgroundRes(R.id.option_layout, R.drawable.corner_option_wrong);
                    helper.setImageResource(R.id.logo, R.mipmap.icon_wrong);
                }
            }
        }
    }
}
