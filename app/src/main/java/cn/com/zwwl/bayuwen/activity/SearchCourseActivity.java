package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;


import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.KeSelectAdapter;
import cn.com.zwwl.bayuwen.api.CourseListApi;
import cn.com.zwwl.bayuwen.api.KeSelectTypeApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.listener.OnItemClickListener;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.KeTypeModel;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.widget.selectmenu.SelectMenuView;

/**
 * 筛选课程页面
 */

public class SearchCourseActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private EditText search_view;
    private KeSelectAdapter keSelectAdapter;
    private List<KeModel> keModels = new ArrayList<>();
    private String tagId;
    private KeTypeModel keTypeModel;// 选课类型
    private SelectMenuView selectMenuView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_search_course);
        tagId = getIntent().getStringExtra("SearchCourseActivity_id");
        initView();
        initData();
    }

    private void initView() {
        selectMenuView = findViewById(R.id.select_ke_menu);
        search_view = findViewById(R.id.search_view);
        recyclerView = findViewById(R.id.course_recyclerVie);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, OrientationHelper
                .VERTICAL, false));
        keSelectAdapter = new KeSelectAdapter(mContext, keModels);
        recyclerView.setAdapter(keSelectAdapter);
        keSelectAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void setOnItemClickListener(View view, int position) {

                Intent intent = new Intent();
                intent.putExtra("CourseDetailActivity_id", keModels.get(position).getKid());
                intent.setClass(mContext, CourseDetailActivity.class);
                startActivity(intent);
            }

            @Override
            public void setOnLongItemClickListener(View view, int position) {

            }
        });
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
    protected void initData() {
        new KeSelectTypeApi(mContext, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                if (entry != null) {
                    keTypeModel = (KeTypeModel) entry;
                    handler.sendEmptyMessage(1);
                }
            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });
        if (TextUtils.isEmpty(tagId)) {
            new CourseListApi(mContext, new FetchEntryListListener() {
                @Override
                public void setData(List list) {
                    if (Tools.listNotNull(list)) {
                        keModels.clear();
                        keModels.addAll(list);
                        handler.sendEmptyMessage(0);
                    }

                }

                @Override
                public void setError(ErrorMsg error) {

                }
            });
        } else {
            new CourseListApi(mContext, tagId, 1, new FetchEntryListListener() {
                @Override
                public void setData(List list) {
                    if (Tools.listNotNull(list)) {
                        keModels.clear();
                        keModels.addAll(list);
                        handler.sendEmptyMessage(0);
                    }
                }

                @Override
                public void setError(ErrorMsg error) {

                }
            });
        }


    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    keSelectAdapter.notifyDataSetChanged();
                    break;
                case 1:
                    selectMenuView.setData();
                    break;
            }

        }
    };


}
