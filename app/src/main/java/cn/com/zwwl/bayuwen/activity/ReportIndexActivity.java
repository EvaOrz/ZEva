package cn.com.zwwl.bayuwen.activity;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.LessonReportAdapter;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.widget.decoration.DividerItemDecoration;

/**
 * 课程跟踪点击“往次”，或已完成课程二级页面点击条目进入该页面
 * Created by zhumangmang at 2018/6/27 14:28
 */
public class ReportIndexActivity extends BasicActivityWithTitle {
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
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    LessonReportAdapter adapter;
    List<KeModel> reports;

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
        for (int i = 0; i < 10; i++) {
            KeModel model = new KeModel();
            model.setTitle("第" + i + "课 课节报告");
            reports.add(model);
        }
        adapter = new LessonReportAdapter(reports);
        adapter.setEmptyView(R.layout.empty_view, (ViewGroup) recyclerView.getParent());
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        setCustomTitle(getIntent().getStringExtra("title"));
    }

    @Override
    protected void setListener() {

    }

    @Override
    public boolean setParentScrollable() {
        return true;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void close() {
        finish();
    }
}
