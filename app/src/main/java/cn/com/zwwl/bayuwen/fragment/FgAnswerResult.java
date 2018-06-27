package cn.com.zwwl.bayuwen.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.base.BasicFragment;
import cn.com.zwwl.bayuwen.model.OptionModel;

public class FgAnswerResult extends BasicFragment {
    @BindView(R.id.title)
    AppCompatTextView title;
    @BindView(R.id.answer)
    AppCompatTextView answer;
    @BindView(R.id.description)
    AppCompatTextView description;

    public static Fragment newInstance(int position, OptionModel content) {
        FgAnswerResult fragment = new FgAnswerResult();
        Bundle bundle = new Bundle();
        bundle.putString("question", position + "、" + content.getTitle());
        bundle.putString("des", content.getRemark());
        bundle.putString("answer", content.getAnswer());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_answer_result, container, false);
    }

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            title.setText(bundle.getString("question"));
            answer.setText(String.format("正确答案: %s", bundle.getString("answer")));
            description.setText(String.format("答案解析: %s", bundle.getString("des")));
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }
}
