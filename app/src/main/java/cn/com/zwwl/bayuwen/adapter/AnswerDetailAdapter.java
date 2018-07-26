package cn.com.zwwl.bayuwen.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.CommonModel;
import cn.com.zwwl.bayuwen.model.OptionModel;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 答题详情adapter
 */
public class AnswerDetailAdapter extends CheckScrollAdapter<OptionModel> {
    protected Context mContext;

    public AnswerDetailAdapter(Context context) {
        super(context);
        mContext = context;
    }

    public void setData(List<OptionModel> mItemList) {
        clear();
        isScroll = false;
        synchronized (mItemList) {
            for (OptionModel item : mItemList) {
                add(item);
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final OptionModel item = getItem(position);
        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout.item_answer_detail);

        TextView title = viewHolder.getView(R.id.a_d_title);
        LinearLayout tiLayout = viewHolder.getView(R.id.a_d_contain);
        TextView answer = viewHolder.getView(R.id.a_d_answer);
        TextView remark = viewHolder.getView(R.id.a_d_remark);

        title.setText(position + 1 + "、" + item.getQuestionTitle());
        tiLayout.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 30, 0, 0);
        for (CommonModel cc : item.getSelect()) {
            tiLayout.addView(getAnswerView(cc, item.getAnswer(), item.getStudentAnswer()), params);
        }
        remark.setText(item.getRemark());
        if (item.getAnswer().equals(item.getStudentAnswer())) {
            answer.setText("回答正确。");
        } else answer.setText("回答错误。");

        return viewHolder.getConvertView();
    }

    private View getAnswerView(CommonModel item, String op1, String my) {
        LinearLayout layout = new LinearLayout(mContext);
        layout.setGravity(Gravity.CENTER_VERTICAL);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        TextView option = new TextView(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(80, 80);
        params.setMargins(0, 0, 30, 0);
        option.setTextSize(18);
        option.setGravity(Gravity.CENTER);
        option.setLayoutParams(params);
        option.setText(item.getOption());
        if (op1.equals(item.getOption())) {// 正确
            option.setTextColor(mContext.getResources().getColor(R.color.white));
            option.setBackground(mContext.getResources().getDrawable(R.drawable.oval_answer_green));
        } else {
            if (my.equals(item.getOption())) {// 我的
                option.setTextColor(mContext.getResources().getColor(R.color.white));
                option.setBackground(mContext.getResources().getDrawable(R.drawable
                        .oval_answer_red));
            } else {
                option.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
                option.setBackground(mContext.getResources().getDrawable(R.drawable
                        .oval_answer_gray));
            }
        }

        TextView content = new TextView(mContext);
        content.setTextSize(14);
        content.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
        content.setText(item.getContent());
        layout.addView(option);
        layout.addView(content);
        return layout;
    }

    public boolean isScroll() {
        return isScroll;
    }

}

