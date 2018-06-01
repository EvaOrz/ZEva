package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.UnitTableAdapter;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.model.LessonModel;

/**
 * 课程单元列表
 * Create by zhumangmang at 2018/5/28 10:50
 */
public class UnitTableActivity extends BasicActivityWithTitle {
    @BindView(R.id.type)
    AppCompatTextView type;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    UnitTableAdapter adapter;

    @Override
    protected int setContentView() {
        return R.layout.activity_course_table;
    }

    @Override
    protected void initView() {
        setCustomTitle(res.getString(R.string.no_study_unit));
        type.setText(res.getString(R.string.chose_change_course_unit_by_need));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initData() {
        List<LessonModel> courseModels = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            LessonModel model = new LessonModel();
            model.setTitle("XXX");
            courseModels.add(model);
        }
        adapter = new UnitTableAdapter(courseModels);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void setListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
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
}
