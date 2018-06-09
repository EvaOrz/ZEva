package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.PicAdapter;
import cn.com.zwwl.bayuwen.api.FinalApi;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.FinalModel;

/**
 * 试卷详情
 * Created by zhumangmang at 2018/6/2 13:48
 */
public class ExamDetailsActivity extends BasicActivityWithTitle {
    @BindView(R.id.homework)
    RecyclerView homework;
    @BindView(R.id.teacher_eval)
    AppCompatTextView teacherEval;
    @BindView(R.id.tutor_eval)
    AppCompatTextView tutorEval;
    @BindView(R.id.adviser_eval)
    AppCompatTextView adviserEval;
    PicAdapter workAdapter;
    private FinalModel model;

    @Override
    protected int setContentView() {
        return R.layout.activity_exam_detail;
    }

    @Override
    protected void initView() {
        homework.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        homework.setItemAnimator(new DefaultItemAnimator());
        workAdapter = new PicAdapter(null);
        homework.setAdapter(workAdapter);
    }

    @Override
    protected void initData() {
        setCustomTitle(getIntent().getStringExtra("title"));
        String kId = getIntent().getStringExtra("kid");
        getData(kId);
    }

    private void getData(String kId) {
        new FinalApi(this, kId, new ResponseCallBack<FinalModel>() {
            @Override
            public void result(FinalModel finalModel, ErrorMsg errorMsg) {
                if (finalModel != null) {
                    model = finalModel;
                    workAdapter.setNewData(model.getExam());
                    if (model.getTeachers() == null) {
                        teacherEval.setText("无");
                    } else {
                        teacherEval.setText(model.getTeachers().getContent());
                    }

                    if (model.getTutors() == null) {
                        tutorEval.setText("无");
                    } else {
                        tutorEval.setText(model.getTutors().getContent());
                    }
                    if (model.getStu_advisors() == null) {
                        adviserEval.setText("无");
                    } else {
                        adviserEval.setText(model.getStu_advisors().getContent());
                    }
                    tutorEval.setText(model.getTutors().getContent());
                }
            }
        });
    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.paper, R.id.feedback, R.id.teacher, R.id.tutor, R.id.adviser})
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.paper:
                if (model.getExam() != null && model.getExam().size() > 0) {
                    intent.setClass(this, InClassStatusActivity.class);
                    String[] urls = new String[model.getExam().size()];
                    for (int i = 0; i < model.getExam().size(); i++)
                        urls[i] = model.getExam().get(i).getUrl();
                    intent.putExtra("urls", urls);

                }
                break;
            case R.id.feedback:
                intent.setClass(this, FeedBackActivity.class);
                break;
            case R.id.teacher:
                if (TextUtils.isEmpty(model.getTeachers().getContent())) return;
                intent.setClass(this, TeacherSummaryActivity.class);
                intent.putExtra("type", 0);
                intent.putExtra("content", model.getTeachers().getContent());
                break;
            case R.id.tutor:
                if (TextUtils.isEmpty(model.getTutors().getContent())) return;
                intent.setClass(this, TeacherSummaryActivity.class);
                intent.putExtra("type", 0);
                intent.putExtra("content", model.getTutors().getContent());
                intent.setClass(this, TeacherSummaryActivity.class);
                break;
            case R.id.adviser:
                if (TextUtils.isEmpty(model.getStu_advisors().getContent())) return;
                intent.setClass(this, TeacherSummaryActivity.class);
                intent.putExtra("type", 0);
                intent.putExtra("content", model.getStu_advisors().getContent());
                intent.setClass(this, TeacherSummaryActivity.class);
                break;
        }
        startActivity(intent);
    }

    @Override
    public void close() {
        finish();
    }

}
