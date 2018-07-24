package cn.com.zwwl.bayuwen.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.LessonReportAdapter;
import cn.com.zwwl.bayuwen.api.StudyingCourseApi;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.dialog.FinalEvalDialog;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.LessonModel;
import cn.com.zwwl.bayuwen.model.StudyingModel;
import cn.com.zwwl.bayuwen.util.TimeUtil;
import cn.com.zwwl.bayuwen.util.ToastUtil;
import cn.com.zwwl.bayuwen.util.UmengLogUtil;
import cn.com.zwwl.bayuwen.view.OvalImageview;
import cn.com.zwwl.bayuwen.widget.decoration.DividerItemDecoration;

import static cn.com.zwwl.bayuwen.base.MenuCode.REPLAY;

/**
 * 课程跟踪点击“往次”，或已完成课程二级页面点击条目进入该页面
 * Created by zhumangmang at 2018/6/27 14:28
 */
public class ReportIndexActivity extends BaseActivity {
    @BindView(R.id.logo)
    OvalImageview logo;
    @BindView(R.id.course_name)
    AppCompatTextView courseName;
    @BindView(R.id.course_code)
    AppCompatTextView courseCode;
    @BindView(R.id.teacher_name)
    AppCompatTextView teacherName;
    @BindView(R.id.school_name)
    AppCompatTextView schoolName;
    @BindView(R.id.date)
    AppCompatTextView date;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    LessonReportAdapter adapter;
    List<LessonModel> reports;
    StudyingModel model;
    @BindView(R.id.id_back)
    ImageView idBack;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.right_title)
    TextView rightTitle;
    @BindView(R.id.report_view)
    View mainView;
    private FinalEvalDialog evalDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_index);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        initView1();
        initData1();
        setListener1();
    }

    @Override
    protected void initData() {

    }


    protected void initView1() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(getResources(), R.color.white, R
                .dimen.dp_5, OrientationHelper.VERTICAL));
        reports = new ArrayList<>();
        adapter = new LessonReportAdapter(reports);
        adapter.setEmptyView(R.layout.empty_view, (ViewGroup) recyclerView.getParent());
        recyclerView.setAdapter(adapter);

        mainView = findViewById(R.id.main_view);
    }


    protected void initData1() {
        titleName.setText(getIntent().getStringExtra("title"));
        if ("1".equals(getIntent().getStringExtra("online"))) {
//            showMenu(REPLAY);
            rightTitle.setVisibility(View.VISIBLE);
            rightTitle.setText("查看回放");

        } else {
            rightTitle.setVisibility(View.GONE);
        }
        new StudyingCourseApi(this, getIntent().getStringExtra("kid"), new
                ResponseCallBack<StudyingModel>() {
                    @Override
                    public void result(StudyingModel studyingModel, ErrorMsg errorMsg) {
                        if (studyingModel != null) {
                            model = studyingModel;
                            bindData();
                        }
                    }
                });
    }


    protected void setListener1() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                goWeb(0, reports.get(position).getReport_url(),reports.get(position).getKid(),reports.get(position).getId());
            }
        });
    }

    private void bindData() {
        if (model.getCourse() != null) {
            KeModel keModel = model.getCourse();
            courseName.setText(keModel.getTitle());
            courseCode.setText(String.format("班级编码: %s", keModel.getModel()));
            teacherName.setText(String.format("%s", keModel.getTname()));
            schoolName.setText(String.format("%s", keModel.getSchool()));
            date.setText(String.format(" %s-%s", TimeUtil.parseTime(keModel.getStartPtime() *
                            1000, "yyyy年MM月dd日"),
                    TimeUtil.parseTime(keModel.getEndPtime() * 1000, "yyyy年MM月dd日")) + " " +
                    String.format("%s%s-%s", keModel.getWeekday(),
                            TimeUtil.parseToHm(keModel.getClass_start_at()), TimeUtil.parseToHm
                                    (keModel.getClass_end_at())));
            ImageLoader.display(this, logo, keModel.getPic());
        }
        if (model.getCompleteClass() != null) {
            reports = new ArrayList<>();
            for (LessonModel lessonModel : model.getCompleteClass()) {
                if (!TextUtils.isEmpty(lessonModel.getReport_url())) reports.add(lessonModel);
            }
            adapter.setNewData(reports);
        }
    }


    @OnClick({R.id.middle_report, R.id.final_report, R.id.welcome, R.id.right_title, R.id.id_back})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_back:
                finish();
                break;
            case R.id.right_title:
                if (model != null) {
                    Intent intent = new Intent(this, ReplayListActivity.class);
                    intent.putExtra("kid", model.getCourse().getKid());
                    intent.putExtra("title", model.getCourse().getTitle());
                    intent.putExtra("type", 1);
                    startActivity(intent);
                }
                break;
            case R.id.middle_report:
                if (TextUtils.isEmpty(model.getMidterm_report())) {
                    ToastUtil.showShortToast(R.string.warning_no_mid_report);
                } else {
                    UmengLogUtil.QiZhongReportClick(mContext);
                    goWeb(1, model.getMidterm_report(), null, null);
                }
                break;
            case R.id.final_report:
                if (TextUtils.isEmpty(model.getEnd_term_report())) {
                    ToastUtil.showShortToast(R.string.warning_no_mid_report);
                } else {
                    UmengLogUtil.QiMoReportClick(mContext);
                    goWeb(2, model.getEnd_term_report(), null, null);
                }
                break;
            case R.id.welcome:

                if (TextUtils.isEmpty(model.getWelcome_speech())) {
                    ToastUtil.showShortToast(R.string.warning_no_mid_report);
                } else {
                    UmengLogUtil.WelReportClick(mContext);
                    goWeb(3, model.getWelcome_speech(), null, null);
                }
                break;


        }
    }

    private void goWeb(int type, String url, String kid, String lessonid) {
        Intent intent = new Intent(mContext, WebReportActivity.class);
        intent.putExtra("WebActivity_url", url);
        intent.putExtra("WebActivity_kid", kid);
        intent.putExtra("WebActivity_lessonid", lessonid);
        intent.putExtra("WebActivity_type", type);
        startActivity(intent);
    }

//    @Override
//    public void onMenuClick(int menuCode) {
//        if (model != null) {
//            Intent intent = new Intent(this, ReplayListActivity.class);
//            intent.putExtra("kid", model.getCourse().getKid());
//            intent.putExtra("title", model.getCourse().getTitle());
//            intent.putExtra("type", 1);
//            startActivity(intent);
//        }
//    }
}
