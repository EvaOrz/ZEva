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

public class FgAnswerResult extends BasicFragment {
    @BindView(R.id.content)
    AppCompatTextView content;

    public static Fragment newInstance(String content) {
        FgAnswerResult fragment = new FgAnswerResult();
        Bundle bundle = new Bundle();
        bundle.putString("content", content);
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
          content.setText(bundle.getString("content"));
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }
}
