package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
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

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.KeSelectAdapter;
import cn.com.zwwl.bayuwen.api.CourseListApi;
import cn.com.zwwl.bayuwen.api.KeSelectTypeApi;
import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.db.TempDataHelper;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.listener.OnItemClickListener;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.KeTypeModel;
import cn.com.zwwl.bayuwen.model.SearchModel;
import cn.com.zwwl.bayuwen.util.AppValue;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.view.selectmenu.SelectMenuView;
import cn.com.zwwl.bayuwen.view.selectmenu.SelectTempModel;

/**
 * 选课中心页面
 */
public class CourseCenterActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private EditText search_view;
    private KeSelectAdapter keSelectAdapter;
    private List<KeModel> keModels = new ArrayList<>();
    private KeTypeModel keTypeModel;// 选课类型
    private SelectMenuView selectMenuView;
    private SmartRefreshLayout refresh;

    private String tagId;// 首页传来的tag_id
    private String searchKey; // 搜索页面传来的关键词
    private int gradeTxt = -1;// 初始化 学员年级
    private boolean isNeedDefaultGrade = true;// 是否需要选中默认年级

    protected HashMap<String, String> para;
    private int page = 1;
    private String baseUrl = UrlUtil.getCDetailUrl(null) + "/search";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_course_center);
        tagId = getIntent().getStringExtra("SearchCourseActivity_id");
        searchKey = getIntent().getStringExtra("SearchCourseActivity_key");
        isNeedDefaultGrade = getIntent().getBooleanExtra("SearchCourseActivity_grade", true);
        initView();
        initData();
        para = new HashMap<>();
        gradeTxt = TempDataHelper.getCurrentChildGrade(mContext);
        if (gradeTxt > 0) {
            para.put("users", gradeTxt + "");
        }
        if (!TextUtils.isEmpty(tagId)) {
            if (tagId.equals("100")) {// 去除线上课
                para.put("online", "1");
            } else
                para.put("type", tagId);
        }

        if (!TextUtils.isEmpty(searchKey)) {
            para.put("keyword", searchKey);
        }
    }

    private void initView() {
        refresh = findViewById(R.id.refresh);
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                page = 1;
                refresh.setNoMoreData(false);
                initListData();
            }
        });
        refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ++page;
                initListData();
            }
        });

        selectMenuView = findViewById(R.id.select_ke_menu);
        selectMenuView.setOnMenuSelectDataChangedListener(new SelectMenuView
                .OnMenuSelectDataChangedListener() {

            @Override
            public void onSortChanged(SelectTempModel sortType, int type) {
                if (type == 1) {
                    para.put("users", sortType.getId());
                } else if (type == 2) {
                    para.put("type", sortType.getId());
                } else if (type == 3) {
                    para.put("online", sortType.getId());
                } else if (type == 4) {
                    para.put("school", sortType.getId());
                } else if (type == 5) {
                    para.put("time", sortType.getId());
                }
                page = 1;
                initListData();
            }

            @Override
            public void onViewClicked(View view) {

            }
        });
        search_view = findViewById(R.id.search_view);
        if (!TextUtils.isEmpty(searchKey)) {
            search_view.setText(searchKey);
        }
        search_view.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideJianpan();
                    para.put("keyword", Tools.getText(v));
                    initListData();
                    return true;
                }
                return false;
            }
        });
        recyclerView = findViewById(R.id.course_recyclerVie);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, OrientationHelper
                .VERTICAL, false));
        refresh.setRefreshContent(recyclerView);
        refresh.autoRefresh();
        keSelectAdapter = new KeSelectAdapter(mContext, 0, keModels);
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


    /**
     * 获取筛选之后的数据
     */
    private void initListData() {
        StringBuilder builder = new StringBuilder(baseUrl);
        para.put("page", String.valueOf(page));
        if (para.size() > 0) {
            builder.append("?");
            for (String key : para.keySet()) {
                if (!TextUtils.isEmpty(para.get(key))) {
                    String and = builder.toString().endsWith("?") ? "" : "&";
                    builder.append(and).append(key).append("=").append(para.get(key));
                }
            }
        }
        new CourseListApi(this, builder.toString(), new ResponseCallBack<SearchModel>() {
            @Override
            public void result(SearchModel searchModel, ErrorMsg errorMsg) {
                refresh.finishRefresh();
                refresh.finishLoadMore();
                if (searchModel != null) {
                    if (searchModel.get_meta().getCurrentPage() == searchModel.get_meta()
                            .getPageCount())
                        refresh.finishLoadMoreWithNoMoreData();
                    if (page == 1) {
                        keModels.clear();
                        keModels.addAll(searchModel.getData());
                    } else {
                        keModels.addAll(searchModel.getData());
                    }
                }
                keSelectAdapter.refresh(keModels);
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
                case 1:
                    String gra = "";
                    if (gradeTxt > 0) gra = AppValue.getGradeStrings().get(gradeTxt - 1);
                    boolean isWangke = false;
                    if (!TextUtils.isEmpty(tagId) && tagId.equals("100")) {
                        isWangke = true;
                    }
                    selectMenuView.setData(keTypeModel, gra, tagId, isWangke, isNeedDefaultGrade);
                    int height = getViewHeight(selectMenuView);
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams
                            (RelativeLayout
                                    .LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams
                                    .WRAP_CONTENT);
                    layoutParams.setMargins(0, height, 0, 0);
                    layoutParams.addRule(RelativeLayout.BELOW, R.id.search_head_layout);
                    refresh.setLayoutParams(layoutParams);
                    break;
            }

        }
    };
}
