package cn.com.zwwl.bayuwen.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.zwwl.bayuwen.R;


public class OptionsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private int choose = -1, correct;

    public OptionsAdapter(@Nullable List<String> data) {
        super(R.layout.item_question_option, data);
    }

    public void setChoose(int choose) {
        this.choose = choose;
        notifyDataSetChanged();
    }

    public int getChoose() {
        return choose;
    }

    public boolean isRight() {
        return choose == correct;
    }

    public void setOptions(List<String> options, int correct) {
        choose = -1;
        this.correct = correct;
        this.setNewData(options);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.itemView.setEnabled(choose == -1);
        helper.setBackgroundRes(R.id.index, R.drawable.corner_index_normal);
        helper.setBackgroundRes(R.id.option_layout, R.drawable.corner_option_normal);
        helper.setVisible(R.id.logo, false);
        helper.setTextColor(R.id.index, ContextCompat.getColor(mContext, R.color.text_color_default));
        if (choose != -1) {
            if (helper.getLayoutPosition() == correct) {
                helper.setTextColor(R.id.index, ContextCompat.getColor(mContext, R.color.white));
                helper.setVisible(R.id.logo, true);
                helper.setBackgroundRes(R.id.index, R.drawable.corner_index_right);
                helper.setBackgroundRes(R.id.option_layout, R.drawable.corner_option_right);
                helper.setImageResource(R.id.logo, R.mipmap.icon_vote_default);
            }
            if (helper.getLayoutPosition() == choose) {
                helper.setTextColor(R.id.index, ContextCompat.getColor(mContext, R.color.white));
                helper.setVisible(R.id.logo, true);
                if (choose == correct) {
                    helper.setBackgroundRes(R.id.index, R.drawable.corner_index_right);
                    helper.setBackgroundRes(R.id.option_layout, R.drawable.corner_option_right);
                    helper.setImageResource(R.id.logo, R.mipmap.icon_vote_default);
                } else {
                    helper.setBackgroundRes(R.id.index, R.drawable.corner_index_wrong);
                    helper.setBackgroundRes(R.id.option_layout, R.drawable.corner_option_wrong);
                    helper.setImageResource(R.id.logo, R.mipmap.icon_vote_checked);
                }
            }
        }
    }
}
