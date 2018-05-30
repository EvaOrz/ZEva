package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;

/**
 * 调课申请提交
 * Created by zhumangmang at 2018/5/28 17:30
 */
public class CourseApplyActivity extends BasicActivityWithTitle {
    @BindView(R.id.unit_name)
    AppCompatTextView unitName;
    @BindView(R.id.description)
    AppCompatTextView description;
    @BindView(R.id.pic)
    AppCompatImageView pic;
    @BindView(R.id.course_name)
    AppCompatTextView courseName;
    @BindView(R.id.teacher_name)
    AppCompatTextView teacherName;
    @BindView(R.id.school_name)
    AppCompatTextView schoolName;
    @BindView(R.id.date)
    AppCompatTextView date;
    @BindView(R.id.time)
    AppCompatTextView time;

    @Override
    protected int setContentView() {
        return R.layout.activity_course_apply;
    }

    @Override
    protected void initView() {
        setDisplayShowTitleEnabled(false);
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
        startActivity(new Intent(this, ChangeResultActivity.class));
    }

    @Override
    public void close() {
        finish();
    }
}
