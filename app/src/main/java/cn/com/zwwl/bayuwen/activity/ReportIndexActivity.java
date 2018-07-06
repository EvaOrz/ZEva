package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.LessonReportAdapter;
import cn.com.zwwl.bayuwen.api.StudyingCourseApi;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.LessonModel;
import cn.com.zwwl.bayuwen.model.StudyingModel;
import cn.com.zwwl.bayuwen.util.TimeUtil;
import cn.com.zwwl.bayuwen.util.ToastUtil;
import cn.com.zwwl.bayuwen.widget.CircleImageView;
import cn.com.zwwl.bayuwen.widget.decoration.DividerItemDecoration;

import static cn.com.zwwl.bayuwen.base.MenuCode.REPLAY;

/**
 * 课程跟踪点击“往次”，或已完成课程二级页面点击条目进入该页面
 * Created by zhumangmang at 2018/6/27 14:28
 */
public class ReportIndexActivity extends BasicActivityWithTitle {
    @BindView(R.id.logo)
    CircleImageView logo;
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
    @BindView(R.id.time)
    AppCompatTextView time;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    LessonReportAdapter adapter;
    List<LessonModel> reports;
    StudyingModel model;

    @Override
    protected int setContentView() {
        return R.layout.activity_report_index;
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(getResources(), R.color.white, R
                .dimen.dp_5, OrientationHelper.VERTICAL));
        reports = new ArrayList<>();
        adapter = new LessonReportAdapter(reports);
        adapter.setEmptyView(R.layout.empty_view, (ViewGroup) recyclerView.getParent());
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        setCustomTitle(getIntent().getStringExtra("title"));
        if ("1".equals(getIntent().getStringExtra("online"))) {
            showMenu(REPLAY);
        }
        new StudyingCourseApi(this, getIntent().getStringExtra("kid"), new ResponseCallBack<StudyingModel>() {
            @Override
            public void result(StudyingModel studyingModel, ErrorMsg errorMsg) {
                if (studyingModel != null) {
                    model = studyingModel;
                    bindData();
                }
            }
        });
    }

    @Override
    protected void setListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mActivity, WebActivity.class);
                intent.putExtra("WebActivity_title", reports.get(position).getTitle());
                intent.putExtra("WebActivity_data", reports.get(position).getReport_url());
                startActivity(intent);
            }
        });
    }

    private void bindData() {
        if (model.getCourse() != null) {
            KeModel keModel = model.getCourse();
            courseName.setText(keModel.getTitle());
            courseCode.setText(String.format("班级编码: %s", keModel.getModel()));
            teacherName.setText(String.format("授课老师: %s", keModel.getTname()));
            schoolName.setText(String.format("上课地点: %s", keModel.getSchool()));
            date.setText(String.format("上课日期: %s-%s", TimeUtil.parseTime(keModel.getStartPtime() * 1000, "yyyy年MM月dd日"), TimeUtil.parseTime(keModel.getEndPtime() * 1000, "yyyy年MM月dd日")));
            time.setText(String.format("上课时间: %s%s-%s", keModel.getWeekday(),
                    TimeUtil.parseToHm(keModel.getClass_start_at()), TimeUtil.parseToHm(keModel.getClass_end_at())));
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

    @Override
    public boolean setParentScrollable() {
        return true;
    }

    @OnClick({R.id.middle_report, R.id.final_report, R.id.welcome})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.middle_report:
                if (TextUtils.isEmpty(model.getMidterm_report())) {
                    ToastUtil.showShortToast(R.string.warning_no_mid_report);
                } else {
                    Intent intent = new Intent(mActivity, WebActivity.class);
                    intent.putExtra("WebActivity_title", model.getCourse().getTitle());
                    intent.putExtra("WebActivity_data", model.getMidterm_report());
                    startActivity(intent);
                }
                break;
            case R.id.final_report:
                if (TextUtils.isEmpty(model.getMidterm_report())) {
                    ToastUtil.showShortToast(R.string.warning_no_mid_report);
                } else {
                    Intent intent = new Intent(mActivity, WebActivity.class);
                    intent.putExtra("WebActivity_title", model.getCourse().getTitle());
                    intent.putExtra("WebActivity_data", model.getEnd_term_report());
                    startActivity(intent);
                }
                break;
            default:
                if (TextUtils.isEmpty(model.getWelcome_speech())) {
                    ToastUtil.showShortToast(R.string.warning_no_mid_report);
                } else {
                    Intent intent = new Intent(mActivity, WebActivity.class);
                    intent.putExtra("WebActivity_title", model.getCourse().getTitle());
                    intent.putExtra("WebActivity_data", model.getWelcome_speech());
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void onMenuClick(int menuCode) {
        if (model!=null) {
            Intent intent = new Intent(this, ReplayListActivity.class);
            intent.putExtra("kid", model.getCourse().getKid());
            intent.putExtra("title", model.getCourse().getTitle());
            intent.putExtra("type", 1);
            startActivity(intent);
        }
    }
}
