package cn.com.zwwl.bayuwen.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.KeModel;

/**
 * 课程详情页面 教学大纲
 */
public class CDetailTabFrag2 extends Fragment {

    private KeModel keModel;
    @BindView(R.id.introduction_tv)
    TextView introductionTv;
    @BindView(R.id.grade_tv)
    TextView gradeTv;
    @BindView(R.id.teaching_goal_tv)
    TextView teachingGoalTv;
    @BindView(R.id.teatures_tv)
    TextView teaturesTv;
    Unbinder unbinder;

    public static CDetailTabFrag2 newInstance() {
        CDetailTabFrag2 fg = new CDetailTabFrag2();
        Bundle bundle = new Bundle();
        fg.setArguments(bundle);
        return fg;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kedetail_2, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public void setData(KeModel keModel) {
        this.keModel = keModel;
        introductionTv.setText(keModel.getDesc());
        gradeTv.setText(keModel.getUsers());
        teachingGoalTv.setText(keModel.getC_target());
        teaturesTv.setText(keModel.getC_trait());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
