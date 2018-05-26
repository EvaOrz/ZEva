package cn.com.zwwl.bayuwen.activity;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.base.BasicActivity;

public class CourseChangeActivity extends BasicActivity {
    @BindView(R.id.title)
    AppCompatTextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.type)
    AppCompatTextView type;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected int setContentView() {
        return R.layout.activity_course_change;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        title.setText("选择需要调课的课程");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View view) {

    }

}
