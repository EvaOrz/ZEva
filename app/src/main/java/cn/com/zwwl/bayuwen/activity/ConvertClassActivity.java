package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CourseTableAdapter;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.model.CourseModel;
import cn.com.zwwl.bayuwen.view.selectmenu.SelectMenuView;

/**
 *  选择可调课（可转）班级
 *  Created by zhumangmang at 2018/5/29 13:57
 */
public class ConvertClassActivity extends BasicActivityWithTitle {
    @BindView(R.id.search)
    AppCompatEditText search;
    @BindView(R.id.operate)
    SelectMenuView operate;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    CourseTableAdapter adapter;

    @Override
    protected int setContentView() {
        return R.layout.activity_convert_class;
    }

    @Override
    protected void initView() {
        if (mApplication.operate_type == 0) {
            setCustomTitle(res.getString(R.string.chose_chanable_class));
        } else {
            setCustomTitle(res.getString(R.string.chose_convertable_class));
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
                startActivity(new Intent(mActivity, ClassDetailActivity.class));
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

}
