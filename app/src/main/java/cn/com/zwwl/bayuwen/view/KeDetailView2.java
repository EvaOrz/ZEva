package cn.com.zwwl.bayuwen.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.KeModel;

/**
 * 课程详情页 课程表
 */
public class KeDetailView2 extends LinearLayout {

    private Context context;
    private KeModel keModel;
    private TextView introductionTv;
    private TextView gradeTv;
    private TextView teachingGoalTv;
    private TextView teaturesTv;

    public KeDetailView2(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_kedetail_2, null, false);
        addView(view);
        introductionTv = view.findViewById(R.id.introduction_tv);
        gradeTv = view.findViewById(R.id.grade_tv);
        teachingGoalTv = view.findViewById(R.id.teaching_goal_tv);
        teaturesTv = view.findViewById(R.id.teatures_tv);
    }

    public void setData(KeModel keModel) {
        this.keModel = keModel;
        introductionTv.setText(keModel.getDesc());
        gradeTv.setText(keModel.getUsers());
        teachingGoalTv.setText(keModel.getC_target());
        teaturesTv.setText(keModel.getC_trait());
    }


}
