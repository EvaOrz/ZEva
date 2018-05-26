package cn.com.zwwl.bayuwen.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.view.IconCenterEditText;
import cn.com.zwwl.bayuwen.widget.decoration.DividerItemDecoration;

/**
 * Created by lousx on 2018/5/17.
 */

public class SearchCourseActivity extends BaseActivity implements IconCenterEditText.OnSearchClickListener{
    private RecyclerView recyclerView;
    private EditText search_view;


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


        search_view = findViewById(R.id.search_view);
        recyclerView = findViewById(R.id.course_recyclerVie);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, OrientationHelper.VERTICAL, false));

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
