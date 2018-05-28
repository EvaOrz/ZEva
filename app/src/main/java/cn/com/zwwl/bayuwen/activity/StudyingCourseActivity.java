package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.StudyingCourseAdapter;
import cn.com.zwwl.bayuwen.base.BasicActivity;
import cn.com.zwwl.bayuwen.model.CourseModel;

/**
 * 在学课程
 * Create by zhumangmang at 2018/5/26 13:22
 */
public class StudyingCourseActivity extends BasicActivity {
    @BindView(R.id.title)
    AppCompatTextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.course_progress)
    ProgressBar courseProgress;
    @BindView(R.id.total_course)
    AppCompatTextView totalCourse;
    @BindView(R.id.course_eval)
    AppCompatTextView courseEval;
    @BindView(R.id.course_change)
    AppCompatTextView courseChange;
    @BindView(R.id.class_covert)
    AppCompatTextView classCovert;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    StudyingCourseAdapter adapter;

    @Override
    protected int setContentView() {
        return R.layout.activity_studying_course;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        title.setText("四成写作三年级");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(true);
    }

    @Override
    protected void initData() {
        List<CourseModel> models = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            CourseModel model = new CourseModel();
            model.setPage("");
            models.add(model);
        }
        adapter = new StudyingCourseAdapter(models);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.course_eval, R.id.course_change, R.id.class_covert})
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.course_eval:
                intent.setClass(this, CourseEvalActivity.class);
                break;
            case R.id.course_change:
                intent.putExtra("type", 0);
                intent.setClass(this, CourseTableActivity.class);
                break;
            case R.id.class_covert:
                intent.putExtra("type", 1);
                intent.setClass(this, CourseTableActivity.class);
                break;
        }
        startActivity(intent);
    }

}
