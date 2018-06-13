package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.PicAdapter;
import cn.com.zwwl.bayuwen.api.HaveReportApi;
import cn.com.zwwl.bayuwen.api.SignApi;
import cn.com.zwwl.bayuwen.api.UnitDetailApi;
import cn.com.zwwl.bayuwen.api.VoteApi;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.base.MenuCode;
import cn.com.zwwl.bayuwen.dialog.FinalEvalDialog;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.CommonModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.ReportModel;
import cn.com.zwwl.bayuwen.model.UnitDetailModel;
import cn.com.zwwl.bayuwen.util.TimeUtil;
import cn.com.zwwl.bayuwen.util.ToastUtil;

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
    AppCompatImageView teacherVote;
    @BindView(R.id.tutor_vote)
    AppCompatImageView tutorVote;
    @BindView(R.id.adviser_vote)
    AppCompatImageView adviserVote;
    @BindView(R.id.month_report)
    AppCompatTextView monthReport;
    @BindView(R.id.look_video)
    AppCompatTextView lookVideo;
    @BindView(R.id.video_layout)
    RelativeLayout videoLayout;
    @BindView(R.id.class_status)
    AppCompatTextView classStatus;
    @BindView(R.id.seat_table)
    AppCompatTextView seatTable;
    private String kId, cId;
    FinalEvalDialog evalDialog;
    private int eval_type = 1;
    ReportModel.ReportBean reportBean;

    @Override
    protected int setContentView() {
        return R.layout.activity_unit_index;
    }

    @Override
    protected void initView() {
        if (getIntent().getIntExtra("online", -1) == 0) {
            classStatus.setVisibility(View.VISIBLE);
            seatTable.setVisibility(View.VISIBLE);
            videoLayout.setVisibility(View.GONE);
            showMenu(MenuCode.SIGN);
        } else {
            classStatus.setVisibility(View.GONE);
            seatTable.setVisibility(View.GONE);
            videoLayout.setVisibility(View.VISIBLE);
            if (getIntent().getIntExtra("video", -1) == 0) {
                lookVideo.setText(R.string.look_video);
            } else {
                lookVideo.setText(R.string.look_replay);
            }
        }

        evalDialog = new FinalEvalDialog(this);
        pptList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        pptList.setItemAnimator(new DefaultItemAnimator());
        homework.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        homework.setItemAnimator(new DefaultItemAnimator());
        pptAdapter = new PicAdapter(null);
        pptList.setAdapter(pptAdapter);
        jobAdapter = new PicAdapter(null);
        homework.setAdapter(jobAdapter);
        haveCourseReport();
    }

    private void haveCourseReport() {
        map.clear();
        map.put("kid", kId);
        map.put("type", "1");
        new HaveReportApi(this, map, new ResponseCallBack<ReportModel.ReportBean>() {
            @Override
            public void result(ReportModel.ReportBean bean, ErrorMsg errorMsg) {
                reportBean = bean;
                if (reportBean == null) {
                    haveMonthReport();
                } else {
                    monthReport.setText(R.string.course_report);
                }
            }
        });
    }

    private void haveMonthReport() {
        map.clear();
        map.put("type", "2");
        map.put("year", TimeUtil.getCurrentY());
        map.put("month", TimeUtil.getCurrentY());
        new HaveReportApi(this, map, new ResponseCallBack<ReportModel.ReportBean>() {
            @Override
            public void result(ReportModel.ReportBean bean, ErrorMsg errorMsg) {
                reportBean = bean;
                eval_type = 2;
                if (reportBean != null) monthReport.setText(R.string.month_report);
            }
        });
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
                    if (!TextUtils.isEmpty(unitDetailModel.getTaSummary().getContent()))
                    tutorEval.setText(unitDetailModel.getTaSummary().getContent());
                    else
                        tutorEval.setText("暂无");
                    pptAdapter.setNewData(unitDetailModel.getAccessory().getData());
                    if (unitDetailModel.getHomework() != null)
                        jobAdapter.setNewData(unitDetailModel.getHomework().getC_img());
                    teacher.setText(String.format("授课老师: %s", unitDetailModel.getTeachers().getTeacher().getName()));
                    teacherVote.setImageResource(unitDetailModel.getTeachers().getTeacher().getState() == 1 ?
                            R.mipmap.icon_vote_checked : R.mipmap.icon_vote_default);
                    if (unitDetailModel.getTeachers().getAssistant() != null) {
                        tutor.setText(String.format("助教老师: %s", unitDetailModel.getTeachers().getAssistant().getName()));

                        tutorVote.setImageResource(unitDetailModel.getTeachers().getTeacher().getState() == 1 ?
                                R.mipmap.icon_vote_checked : R.mipmap.icon_vote_default);
                    } else {
                        tutor.setText("助教老师: 无");
                        tutorVote.setVisibility(View.GONE);
                    }
                    if (unitDetailModel.getTeachers().getCounselor() != null) {
                        adviser.setText(String.format("学业顾问: %s", unitDetailModel.getTeachers().getCounselor().getName()));
                        adviserVote.setImageResource(unitDetailModel.getTeachers().getTeacher().getState() == 1 ?
                                R.mipmap.icon_vote_checked : R.mipmap.icon_vote_default);
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
        evalDialog.setSubmitListener(new FinalEvalDialog.SubmitListener() {
            @Override
            public void show() {
                evalDialog.showAtLocation(tutorEval, Gravity.BOTTOM, 0, 0);
            }

            @Override
            public void ok() {
                Intent intent = new Intent(mActivity, WebActivity.class);
                intent.putExtra("WebActivity_data", reportBean.getUrl());
                startActivity(intent);
            }

            @Override
            public void error(int code) {

            }
        });
    }

    private void addVote(final String type, String id) {
        map.clear();
        map.put("type", "1");
        map.put("to_uid", id);
        map.put("theme", type);
        map.put("kid", kId);
        map.put("lecture_id", cId);
        new VoteApi(this, map, new ResponseCallBack<CommonModel>() {
            @Override
            public void result(CommonModel o, ErrorMsg errorMsg) {
                if (o != null)
                    switch (type) {
                        case "1":
                            teacherVote.setImageResource(o.getStatus() == 1 ?
                                    R.mipmap.icon_vote_checked : R.mipmap.icon_vote_default);
                            break;
                        case "2":
                            adviserVote.setImageResource(o.getStatus() == 1 ?
                                    R.mipmap.icon_vote_checked : R.mipmap.icon_vote_default);
                            break;
                        case "3":
                            tutorVote.setImageResource(o.getStatus() == 1 ?
                                    R.mipmap.icon_vote_checked : R.mipmap.icon_vote_default);
                            break;
                    }
            }
        });
    }

    @OnClick({R.id.submit_work, R.id.ppt, R.id.month_report, R.id.teacher_vote, R.id.tutor_vote, R.id.adviser_vote})
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.teacher_vote:
                addVote("1", model.getTeachers().getTeacher().getId());
                break;
            case R.id.tutor_vote:
                addVote("3", model.getTeachers().getAssistant().getId());
                break;
            default:
                addVote("2", model.getTeachers().getCounselor().getId());
                break;
            case R.id.month_report:
                gotoWeb();
                break;
            case R.id.submit_work:
                intent.setClass(this, WorkDetailsActivity.class);
                intent.putExtra("model", model.getHomework());
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

    private void gotoWeb() {
        if (reportBean != null) {
            if (reportBean.getComment_id() != null) {
                Intent intent = new Intent(this, WebActivity.class);
                intent.putExtra("WebActivity_data", reportBean.getUrl());
                startActivity(intent);
            } else {
                switch (eval_type) {
                    case 1:
                        evalDialog.setData(eval_type, kId);
                        break;
                    case 2:
                        evalDialog.setData(eval_type, reportBean.getYear(), reportBean.getMonth());
                        break;
                }
            }
        }
    }

    @Override
    public void onMenuClick(int menuCode) {
        map.clear();
        map.put("kid", kId);
        map.put("lecture_id", cId);
        map.put("status", "1");
        new SignApi(this, map, new ResponseCallBack<CommonModel>() {
            @Override
            public void result(CommonModel commonModel, ErrorMsg errorMsg) {
                if (TextUtils.isEmpty(errorMsg.getDesc())) {
                    ToastUtil.showShortToast("签到成功");
                } else {
                    ToastUtil.showShortToast(errorMsg.getDesc());
                }
            }
        });
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
