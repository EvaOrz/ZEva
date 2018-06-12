package cn.com.zwwl.bayuwen.adapter;

import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.EvalContentModel;

public class FinalEvalVoteAdapter extends BaseQuickAdapter<EvalContentModel.DataBean, BaseViewHolder> {


    public FinalEvalVoteAdapter(List<EvalContentModel.DataBean> data) {
        super(R.layout.item_final_vote, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, EvalContentModel.DataBean item) {
        helper.setText(R.id.name, item.getName());
        if (item.getIsPraised() == 1) {
            helper.setBackgroundRes(R.id.content, R.drawable.drawable_checked__corner);
            helper.setImageResource(R.id.logo, R.mipmap.icon_vote_checked);
            helper.setTextColor(R.id.name, ContextCompat.getColor(mContext, R.color.gold));
        } else {
            helper.setBackgroundRes(R.id.content, R.drawable.drawable_uncheck__corner);
            helper.setImageResource(R.id.logo, R.mipmap.icon_vote_default);
            helper.setTextColor(R.id.name, ContextCompat.getColor(mContext, R.color.gray_line));
        }
    }
}
