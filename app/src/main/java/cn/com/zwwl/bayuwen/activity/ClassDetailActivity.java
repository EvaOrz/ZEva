package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;

public class ClassDetailActivity extends BasicActivityWithTitle {
    @BindView(R.id.class_name)
    AppCompatTextView className;
    @BindView(R.id.current_stu)
    AppCompatTextView currentStu;
    @BindView(R.id.total_stu)
    AppCompatTextView totalStu;
    @BindView(R.id.course_progress)
    ProgressBar courseProgress;
    @BindView(R.id.total_course)
    AppCompatTextView totalCourse;
    @BindView(R.id.class_introduce)
    AppCompatTextView classIntroduce;
    @BindView(R.id.teacher_introduce)
    AppCompatTextView teacherIntroduce;
    @BindView(R.id.submit)
    AppCompatTextView submit;

    @Override
    protected int setContentView() {
        return R.layout.activity_class_detail;
    }

    @Override
    protected void initView() {
        setCustomTitle("课程详情");
        if (mApplication.operate_type == 0) {
            submit.setText("选择调课");
        } else {
            submit.setText("选择转班");
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @OnClick(R.id.submit)
    @Override
    public void onClick(View view) {
        if (mApplication.operate_type == 0) {
            startActivity(new Intent(this, CourseApplyActivity.class));
        } else {
            startActivity(new Intent(this, ClassApplyActivity.class));
        }
    }

    @Override
    public void close() {
        finish();
    }
}
