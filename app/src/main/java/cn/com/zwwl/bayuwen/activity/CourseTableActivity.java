package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CourseTableAdapter;
import cn.com.zwwl.bayuwen.base.BasicActivity;
import cn.com.zwwl.bayuwen.model.CourseModel;

/**
 * 可操作课程列表
 * Create by zhumangmang at 2018/5/28 10:03
 */
public class CourseTableActivity extends BasicActivity {
    @BindView(R.id.title)
    AppCompatTextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.type)
    AppCompatTextView type;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    int flag;
    CourseTableAdapter adapter;

    @Override
    protected int setContentView() {
        return R.layout.activity_course_table;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        flag = getIntent().getIntExtra("type", 0);
        if (flag == 0) {
            title.setText(res.getString(R.string.change_course));
            type.setText(res.getString(R.string.chose_course_by_need));
        } else {
            title.setText(res.getString(R.string.covert_class_course));
            type.setText(res.getString(R.string.chose_covert_class_course_by_need));
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initData() {
        List<CourseModel> courseModels = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CourseModel model = new CourseModel();
            model.setPage("XXX");
            courseModels.add(model);
        }
        adapter = new CourseTableAdapter(courseModels);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void setListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mActivity, UnitTableActivity.class));
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

}
