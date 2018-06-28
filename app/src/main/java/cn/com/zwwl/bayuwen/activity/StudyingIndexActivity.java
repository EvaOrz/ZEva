package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.api.StudyingClassInfoApi;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ClassModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.util.TimeUtil;
import cn.com.zwwl.bayuwen.util.ToastUtil;

/**
 * 课程跟踪正在进行课程点击箭头进入该界面
 * Created by zhumangmang at 2018/6/27 11:11
 */
public class StudyingIndexActivity extends BasicActivityWithTitle {
    @BindView(R.id.logo)
    AppCompatImageView logo;
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
    @BindView(R.id.sign_per)
    AppCompatTextView signPer;
    @BindView(R.id.no_sign)
    AppCompatTextView noSign;
    private String kid;
    private int online;
    private ClassModel classModel;

    @Override
    protected int setContentView() {
        return R.layout.activity_studying_index;
    }

    @Override
    protected void initView() {
    }

    @Override
    public boolean setParentScrollable() {
        return true;
    }

    @Override
    protected void initData() {
        kid = getIntent().getStringExtra("kid");
        setCustomTitle(getIntent().getStringExtra("title"));
        online = getIntent().getIntExtra("online", -1);
        new StudyingClassInfoApi(this, kid, new ResponseCallBack<ClassModel>() {
            @Override
            public void result(ClassModel model, ErrorMsg errorMsg) {
                if (model != null) {
                    classModel = model;
                    bindData();
                }
            }
        });
    }

    private void bindData() {
        if (classModel.getCourse() != null) {
            KeModel keModel = classModel.getCourse();
            courseName.setText(keModel.getTitle());
            courseCode.setText(String.format("班级编码: %s", keModel.getModel()));
            teacherName.setText(String.format("授课老师: %s", keModel.getTname()));
            schoolName.setText(String.format("上课地点: %s", keModel.getSchool()));
            date.setText(String.format("上课日期: %s-%s", TimeUtil.parseTime(keModel.getStartPtime() * 1000, "yyyy年MM月dd日"), TimeUtil.parseTime(keModel.getEndPtime() * 1000, "yyyy年MM月dd日")));
            time.setText(String.format("上课时间: %s%s-%s", keModel.getWeekday(), keModel.getClass_start_at(), keModel.getClass_end_at()));
            ImageLoader.display(this, logo, keModel.getPic());
        }
        signPer.setText(String.format("签到率: %s%s", classModel.getSignInRate(), "%"));
        noSign.setText(String.format("缺勤次数: %s次", classModel.getAbsenteeism()));
    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.course_change, R.id.class_covert, R.id.class_seat, R.id.middle_report, R.id.final_report, R.id.welcome})
    @Override
    public void onClick(View view) {
        if (classModel == null) return;
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.course_change:
                if (online == 1) {
                    ToastUtil.showShortToast("线上班级暂不支持调课");
                    return;
                }
                mApplication.operate_type = 0;
                intent.putExtra("kid", kid);
                intent.putExtra("course_type", 0);
                intent.setClass(this, UnitTableActivity.class);
                startActivity(intent);
                break;
            case R.id.class_covert:
                if (online == 1) {
                    ToastUtil.showShortToast("线上班级暂不支持换班");
                    return;
                }
                mApplication.operate_type = 1;
                intent.setClass(this, ConvertClassActivity.class);
                startActivity(intent);
                break;
            case R.id.class_seat:
                ToastUtil.showShortToast("敬请期待");
                break;
            case R.id.middle_report:
                if (TextUtils.isEmpty(classModel.getMidterm_report())) {
                    ToastUtil.showShortToast(R.string.warning_no_mid_report);
                } else {
                    intent.setClass(mActivity, WebActivity.class);
                    intent.putExtra("WebActivity_title", classModel.getCourse().getTitle());
                    intent.putExtra("WebActivity_data", classModel.getMidterm_report());
                    startActivity(intent);
                }
                break;
            case R.id.final_report:
                if (TextUtils.isEmpty(classModel.getMidterm_report())) {
                    ToastUtil.showShortToast(R.string.warning_no_mid_report);
                } else {
                    intent.setClass(mActivity, WebActivity.class);
                    intent.putExtra("WebActivity_title", classModel.getCourse().getTitle());
                    intent.putExtra("WebActivity_data", classModel.getEnd_term_report());
                    startActivity(intent);
                }
                break;
            default:
                if (TextUtils.isEmpty(classModel.getWelcome_speech())) {
                    ToastUtil.showShortToast(R.string.warning_no_mid_report);
                } else {
                    intent.setClass(mActivity, WebActivity.class);
                    intent.putExtra("WebActivity_title", classModel.getCourse().getTitle());
                    intent.putExtra("WebActivity_data", classModel.getWelcome_speech());
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void close() {
        finish();
    }
}
