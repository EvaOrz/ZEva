package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.ClassDetailAdapter;
import cn.com.zwwl.bayuwen.api.ClassDetailApi;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ClassModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.util.ToastUtil;

/**
 * 班级详情
 * Created by zhumangmang at 2018/6/7 17:32
 */
public class ClassDetailActivity extends BasicActivityWithTitle {
    @BindView(R.id.class_name)
    AppCompatTextView className;
    @BindView(R.id.current_stu)
    AppCompatTextView currentStu;
    @BindView(R.id.total_stu)
    AppCompatTextView totalStu;
    @BindView(R.id.course_progress)
    ProgressBar courseProgress;
    @BindView(R.id.current_course)
    AppCompatTextView currentCourse;
    @BindView(R.id.total_course)
    AppCompatTextView totalCourse;
    @BindView(R.id.class_introduce)
    AppCompatTextView classIntroduce;
    @BindView(R.id.teacher_introduce)
    RecyclerView teacherIntroduce;
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
        teacherIntroduce.setLayoutManager(new LinearLayoutManager(this));
        teacherIntroduce.setNestedScrollingEnabled(false);
        teacherIntroduce.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initData() {
        new ClassDetailApi(this, getIntent().getStringExtra("kid"), new ResponseCallBack<ClassModel>() {
            @Override
            public void result(ClassModel classModel, ErrorMsg errorMsg) {
                if (classModel != null) {
                    className.setText(classModel.getCourse().getTitle());
                    currentStu.setText(classModel.getNum().getNow_num() == null ? "0" : classModel.getNum().getNow_num());
                    totalStu.setText(classModel.getNum().getFull_num());
                    courseProgress.setMax(classModel.getPlan().getCount());
                    courseProgress.setProgress(classModel.getPlan().getCurrent());
                    currentCourse.setText(String.valueOf(classModel.getPlan().getCurrent()));
                    totalCourse.setText(String.valueOf(classModel.getPlan().getCount()));
                    if (TextUtils.isEmpty(classModel.getCourse().getDesc())) {
                        classIntroduce.setText("暂无描述");
                    } else
                        classIntroduce.setText(classModel.getCourse().getDesc());
                    ClassDetailAdapter adapter = new ClassDetailAdapter(classModel.getTeacher());
                    teacherIntroduce.setAdapter(adapter);
                } else {
                    ToastUtil.showShortToast(errorMsg.getDesc());
                }
            }
        });
    }

    @Override
    protected void setListener() {

    }

    @Override
    public boolean setParentScrollable() {
        return false;
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
