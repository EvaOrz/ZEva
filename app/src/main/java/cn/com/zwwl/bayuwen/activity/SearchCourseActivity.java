package cn.com.zwwl.bayuwen.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.SeachCourseListAdapter;
import cn.com.zwwl.bayuwen.model.CourseVideoModel;
import cn.com.zwwl.bayuwen.view.IconCenterEditText;
import cn.com.zwwl.bayuwen.widget.decoration.DividerItemDecoration;
import cn.com.zwwl.bayuwen.widget.decoration.SpacesItemDecoration;

/**
 * Created by lousx on 2018/5/17.
 */

public class SearchCourseActivity extends BaseActivity implements IconCenterEditText.OnSearchClickListener{
    private RecyclerView recyclerView;
    private SeachCourseListAdapter adapter;
    private IconCenterEditText search_view;

    private List<CourseVideoModel> courseVideoModelList = new ArrayList<>();

    @Override
    protected void initData() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_search_course);
        initView();
    }

    private void initView(){

        for (int i = 0; i< 6;i++){
            CourseVideoModel model = new CourseVideoModel();
            courseVideoModelList.add(model);
        }

        search_view = findViewById(R.id.search_view);
        recyclerView = findViewById(R.id.course_recyclerVie);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, OrientationHelper.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getResources(),R.color.app_bgColor,R.dimen.dp_10,OrientationHelper.VERTICAL));
        adapter = new SeachCourseListAdapter(mContext,courseVideoModelList);
        recyclerView.setAdapter(adapter);

        search_view.setOnSearchClickListener(this);
        findViewById(R.id.back_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.back_btn:
                finish();
                break;
        }
    }


    @Override
    public void onSearchClick(View view) {
    }
}
