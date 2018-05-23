package cn.com.zwwl.bayuwen.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CourseListAdapter;
import cn.com.zwwl.bayuwen.model.CourseModel;

/**
 * Created by lousx on 2018/5/22.
 */

public class FgEvaluate extends Fragment {
    private TextView evaluateTv;
    private String evaluate;

    public static FgEvaluate newInstance(String evaluate) {
        FgEvaluate fg = new FgEvaluate();
        Bundle bundle = new Bundle();
        bundle.putString("evaluate", evaluate);
        fg.setArguments(bundle);
        return fg;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        evaluate = getArguments().getString("evaluate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_evaluate, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        evaluateTv = view.findViewById(R.id.evaluate);
        evaluateTv.setText(evaluate);
    }

}
