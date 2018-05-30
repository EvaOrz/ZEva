package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.TestListAdapter;
import cn.com.zwwl.bayuwen.adapter.UnitTableAdapter;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.model.CourseModel;

/**
 * 课程跟踪列表点击后进入该处
 * Created by zhumangmang at 2018/5/29 15:55
 */
public class FCourseIndexActivity extends BasicActivityWithTitle {
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.percent)
    AppCompatTextView percent;
    @BindView(R.id.test)
    RecyclerView test;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    UnitTableAdapter unitTableAdapter;
    TestListAdapter testListAdapter;

    @Override
    protected int setContentView() {
        return R.layout.activity_finish_course_index;
    }

    @Override
    protected void initView() {
        setCustomTitle("大语文");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        test.setLayoutManager(new LinearLayoutManager(this));
        test.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initData() {
        List<CourseModel> courseModels = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CourseModel model = new CourseModel();
            model.setPage("XXX");
            courseModels.add(model);
        }
        unitTableAdapter = new UnitTableAdapter(courseModels);
        testListAdapter = new TestListAdapter(courseModels);
        test.setNestedScrollingEnabled(false);
        recyclerView.setNestedScrollingEnabled(false);
        test.setAdapter(testListAdapter);
        recyclerView.setAdapter(unitTableAdapter);
    }

    @Override
    protected void setListener() {
        testListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mActivity, ExamDetailsActivity.class));
            }
        });
        unitTableAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mActivity, ConvertClassActivity.class));
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public boolean setParentScrollable() {
        return true;
    }
}
