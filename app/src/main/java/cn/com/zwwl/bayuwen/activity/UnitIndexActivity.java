package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.PicAdapter;
import cn.com.zwwl.bayuwen.api.UnitDetailApi;
import cn.com.zwwl.bayuwen.api.VoteApi;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.dialog.FinalEvalDialog;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.UnitDetailModel;

/**
 * 课程单元详情首页
 * Created by zhumangmang at 2018/5/30 11:11
 */
public class UnitIndexActivity extends BasicActivityWithTitle {
    @BindView(R.id.ppt_list)
    RecyclerView pptList;
    @BindView(R.id.homework)
    RecyclerView homework;
    @BindView(R.id.tutor_eval)
    AppCompatTextView tutorEval;
    @BindView(R.id.teacher)
    AppCompatTextView teacher;
    @BindView(R.id.tutor)
    AppCompatTextView tutor;
    @BindView(R.id.adviser)
    AppCompatTextView adviser;
    PicAdapter pptAdapter, jobAdapter;
    UnitDetailModel model;
    @BindView(R.id.teacher_vote)
    AppCompatCheckBox teacherVote;
    @BindView(R.id.tutor_vote)
    AppCompatCheckBox tutorVote;
    @BindView(R.id.adviser_vote)
    AppCompatCheckBox adviserVote;
    private String kId, cId;
    FinalEvalDialog evalDialog;

    @Override
    protected int setContentView() {
        return R.layout.activity_unit_index;
    }

    @Override
    protected void initView() {
        evalDialog = new FinalEvalDialog(this);
        pptList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        pptList.setItemAnimator(new DefaultItemAnimator());
        homework.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        homework.setItemAnimator(new DefaultItemAnimator());
        pptAdapter = new PicAdapter(null);
        pptList.setAdapter(pptAdapter);
        jobAdapter = new PicAdapter(null);
        homework.setAdapter(jobAdapter);

    }

    @Override
    protected void initData() {
        setCustomTitle(getIntent().getStringExtra("title"));
        kId = getIntent().getStringExtra("kId");
        cId = getIntent().getStringExtra("cId");
        getData();
    }

    private void getData() {
        new UnitDetailApi(this, kId, cId, new ResponseCallBack<UnitDetailModel>() {
            @Override
            public void result(UnitDetailModel unitDetailModel, ErrorMsg errorMsg) {
                if (unitDetailModel != null) {
                    model = unitDetailModel;
                    tutorEval.setText(unitDetailModel.getTaSummary().getContent());
                    pptAdapter.setNewData(unitDetailModel.getAccessory().getData());
                    jobAdapter.setNewData(unitDetailModel.getJob().getData());

                    teacher.setText(String.format("授课老师: %s", unitDetailModel.getTeachers().getTeacher().getName()));
                    teacherVote.setChecked(unitDetailModel.getTeachers().getTeacher().getState() == 1);
                    if (unitDetailModel.getTeachers().getAssistant() != null) {
                        tutor.setText(String.format("助教老师: %s", unitDetailModel.getTeachers().getAssistant().getName()));
                        tutorVote.setChecked(unitDetailModel.getTeachers().getAssistant().getState() == 1);
                    } else {
                        tutor.setText("助教老师: 无");
                        tutorVote.setVisibility(View.GONE);
                    }
                    if (unitDetailModel.getTeachers().getCounselor() != null) {
                        adviser.setText(String.format("学业顾问: %s", unitDetailModel.getTeachers().getCounselor().getName()));
                        adviserVote.setChecked(unitDetailModel.getTeachers().getCounselor().getState() == 1);
                    } else {
                        adviser.setText("学业顾问: 无");
                        adviserVote.setVisibility(View.GONE);
                    }

                }
            }
        });
    }

    @Override
    protected void setListener() {

    }

    @OnCheckedChanged({R.id.teacher_vote, R.id.tutor_vote, R.id.adviser_vote})
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.teacher_vote:
                addVote("1", model.getTeachers().getTeacher().getId(), isChecked);
                break;
            case R.id.tutor_vote:
                addVote("3", model.getTeachers().getAssistant().getId(), isChecked);
                break;
            default:
                addVote("2", model.getTeachers().getCounselor().getId(), isChecked);
                break;
        }
    }

    private void addVote(String type, String id, boolean isChecked) {
        map.clear();
        map.put("to_uid", id);
        map.put("status", isChecked ? "1" : "0");
        map.put("theme", type);
        map.put("kid", kId);
        map.put("lecture_id", cId);
        new VoteApi(this, map, new ResponseCallBack() {
            @Override
            public void result(Object o, ErrorMsg errorMsg) {

            }
        });
    }

    @OnClick({R.id.submit_work, R.id.ppt, R.id.month_report})
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.month_report:
                evalDialog.showAtLocation(teacher, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.submit_work:
                intent.setClass(this, UploadPicActivity.class);
                intent.putExtra("cId", "");
                startActivity(intent);
                break;
            case R.id.ppt:
                if (model.getAccessory().getState() != 0) {
                    String[] urls = new String[model.getAccessory().getData().size()];
                    for (int i = 0; i < model.getAccessory().getData().size(); i++)
                        urls[i] = model.getAccessory().getData().get(i).getUrl();
                    intent.setClass(this, LookPPTActivity.class);
                    intent.putExtra("urls", urls);
                    startActivity(intent);
                }
                break;
        }

    }

    @Override
    public boolean setParentScrollable() {
        return true;
    }

    @Override
    public void close() {
        finish();
    }

}
