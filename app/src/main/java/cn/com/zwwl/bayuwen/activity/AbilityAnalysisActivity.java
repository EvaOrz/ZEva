package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.model.PintuModel;

public class AbilityAnalysisActivity extends BasicActivityWithTitle implements View.OnClickListener {

    @BindView(R.id.tab_course_name)
    TabLayout tabCourseName;
    @BindView(R.id.course_content)
    RadioButton courseContent;
    @BindView(R.id.student_condition)
    RadioButton studentCondition;
    @BindView(R.id.system_introduction)
    RadioButton systemIntroduction;
    @BindView(R.id.course_d_group)
    RadioGroup courseDGroup;
    @BindView(R.id.course_d_line1)
    View courseDLine1;
    @BindView(R.id.course_d_line2)
    View courseDLine2;
    @BindView(R.id.course_d_line3)
    View courseDLine3;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.questionNum)
    TextView questionNum;
    @BindView(R.id.rightNum)
    TextView rightNum;
    @BindView(R.id.errorNum)
    TextView errorNum;
    @BindView(R.id.student_content)
    LinearLayout studentContent;
    private ArrayList<PintuModel> pintuModels = new ArrayList<>();// 拼图数据
    private PintuModel pintuModel;

    @Override
    protected int setContentView() {
        return R.layout.activity_ability_analysis;
    }

    @Override
    protected void initView() {
        setCustomTitle("能力分析拼图");

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        pintuModels = (ArrayList<PintuModel>) intent.getSerializableExtra("pintuModels");
        for (int i = 0; i < pintuModels.size(); i++) {
            tabCourseName.addTab(tabCourseName.newTab().setText(pintuModels.get(i).getName()));
        }

        pintuModel = pintuModels.get(0);
        content.setText(pintuModel.getContent().getContent());
        initAddData();

    }

    @Override
    protected void setListener() {
        courseContent.setOnClickListener(this);
        systemIntroduction.setOnClickListener(this);
        studentCondition.setOnClickListener(this);
        tabCourseName.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pintuModel = pintuModels.get(tab.getPosition());
                initAddData();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initAddData() {
        courseContent.setText(pintuModel.getContent().getTitle());
        studentCondition.setText(pintuModel.getStudent_info().getTitle());
        systemIntroduction.setText(pintuModel.getCurricula().getTitle());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.course_content:
                courseDLine1.setVisibility(View.VISIBLE);
                courseDLine2.setVisibility(View.INVISIBLE);
                courseDLine3.setVisibility(View.INVISIBLE);
                content.setVisibility(View.VISIBLE);
                studentContent.setVisibility(View.GONE);
                content.setText(pintuModel.getContent().getContent());
                break;
            case R.id.student_condition:
                courseDLine1.setVisibility(View.INVISIBLE);
                courseDLine2.setVisibility(View.VISIBLE);
                courseDLine3.setVisibility(View.INVISIBLE);
                content.setVisibility(View.GONE);
                studentContent.setVisibility(View.VISIBLE);
                if (pintuModel.getLectureinfo()!=null&&pintuModel.getLectureinfo().size()!=0) {
                    questionNum.setText("总题目数：" + pintuModel.getLectureinfo().get(0).getQuestionNum());
                    rightNum.setText("答对题数：" + pintuModel.getLectureinfo().get(0).getRightNum());
                    errorNum.setText("答错题数：" + pintuModel.getLectureinfo().get(0).getErrorNum());
                }
                break;
            case R.id.system_introduction:
                courseDLine1.setVisibility(View.INVISIBLE);
                courseDLine2.setVisibility(View.INVISIBLE);
                courseDLine3.setVisibility(View.VISIBLE);
                content.setVisibility(View.VISIBLE);
                studentContent.setVisibility(View.GONE);
                content.setText(pintuModel.getCurricula().getContent());
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void close() {
        super.close();
        finish();
    }
}
