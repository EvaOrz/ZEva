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
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.KeSelectAdapter;
import cn.com.zwwl.bayuwen.api.CourseListApi;
import cn.com.zwwl.bayuwen.api.KeSelectTypeApi;
import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.listener.OnItemClickListener;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.KeTypeModel;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.view.selectmenu.SelectMenuView;
import cn.com.zwwl.bayuwen.view.selectmenu.SelectTempModel;

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

    private String baseUrl = UrlUtil.getCDetailUrl(null) + "/search";
    // 筛选参数
    private String pama1 = "", pama2 = "", pama3 = "", pama4 = "", pama5 = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_search_course);
        tagId = getIntent().getStringExtra("SearchCourseActivity_id");
        initView();
        initData();
        if (!TextUtils.isEmpty(tagId)) {
            initListData(baseUrl + "?type=" + tagId);
        }
    }

    private void initView() {
        selectMenuView = findViewById(R.id.select_ke_menu);
        selectMenuView.setOnMenuSelectDataChangedListener(new SelectMenuView
                .OnMenuSelectDataChangedListener() {

            @Override
            public void onSortChanged(SelectTempModel sortType, int type) {
                if (type == 1) {
                    pama1 = sortType.getId();
                } else if (type == 2) {
                    pama2 = sortType.getId();
                } else if (type == 3) {
                    pama3 = sortType.getId();
                } else if (type == 4) {
                    pama4 = sortType.getId();
                } else if (type == 5) {
                    pama5 = sortType.getText();
                }
                baseUrl += "?";
                if (!TextUtils.isEmpty(pama1)) {
                    String and = baseUrl.endsWith("?") ? "" : "&";
                    baseUrl += and + "users=" + pama1;
                }
                if (!TextUtils.isEmpty(pama2)) {
                    String and = baseUrl.endsWith("?") ? "" : "&";
                    baseUrl += and + "type=" + pama2;
                }
                if (!TextUtils.isEmpty(pama3)) {
                    String and = baseUrl.endsWith("?") ? "" : "&";
                    baseUrl += and + "online=" + pama3;
                }
                if (!TextUtils.isEmpty(pama4)) {
                    String and = baseUrl.endsWith("?") ? "" : "&";
                    baseUrl += and + "school=" + pama4;
                }
                if (!TextUtils.isEmpty(pama5)) {
                    String and = baseUrl.endsWith("?") ? "" : "&";
                    baseUrl += and + "time=" + pama5;
                }
                initListData(baseUrl);
            }

            @Override
            public void onViewClicked(View view) {

            }
        });
        search_view = findViewById(R.id.search_view);
        search_view.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideJianpan();
                    initListData(baseUrl + "?keyword=" + v.getText().toString());
                    return true;
                }
                return false;
            }
        });
        recyclerView = findViewById(R.id.course_recyclerVie);
        int height = getViewHeight(selectMenuView);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout
                .LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, height, 0, 0);
        layoutParams.addRule(RelativeLayout.BELOW, R.id.search_head_layout);
        recyclerView.setLayoutParams(layoutParams);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, OrientationHelper
                .VERTICAL, false));

        findViewById(R.id.back_btn).setOnClickListener(this);
    }

    private void resetData() {
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

    /**
     * 获取筛选之后的数据
     */
    private void initListData(String url) {
        showLoadingDialog(true);
        new CourseListApi(mContext, url, new FetchEntryListListener() {
            @Override
            public void setData(List list) {
                showLoadingDialog(false);
                keModels.clear();
                if (Tools.listNotNull(list))
                    keModels.addAll(list);
                handler.sendEmptyMessage(0);
            }

            @Override
            public void setError(ErrorMsg error) {
                showLoadingDialog(false);
            }
        });
    }


    @Override
    protected void initData() {
        // 获取筛选类型数据
        new KeSelectTypeApi(mContext, 1, new FetchEntryListener() {
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
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    resetData();
                    break;
                case 1:
                    selectMenuView.setData(keTypeModel);
                    break;
            }

        }
    };
}
