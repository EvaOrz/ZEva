package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.KeSelectAdapter;
import cn.com.zwwl.bayuwen.api.CourseListApi;
import cn.com.zwwl.bayuwen.api.KeSelectTypeApi;
import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.base.BasicActivity;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.listener.OnItemClickListener;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.KeTypeModel;
import cn.com.zwwl.bayuwen.model.SearchModel;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.view.selectmenu.SelectMenuView;
import cn.com.zwwl.bayuwen.view.selectmenu.SelectTempModel;

/**
 * 筛选课程页面
 */

public class SearchCourseActivity extends BasicActivity {
    @BindView(R.id.search_view)
    EditText searchView;
    @BindView(R.id.select_ke_menu)
    SelectMenuView selectKeMenu;
    @BindView(R.id.course_recyclerVie)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private KeSelectAdapter keSelectAdapter;
    private KeTypeModel keTypeModel;
    private List<KeModel> keModels = new ArrayList<>();
    private String baseUrl = UrlUtil.getCDetailUrl(null) + "/search";
    private int page = 1;

    @Override
    protected int setContentView() {
        return R.layout.activity_search_course;
    }

    @Override
    protected void initView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        keSelectAdapter = new KeSelectAdapter(mContext, 0, keModels);
        recyclerView.setAdapter(keSelectAdapter);
        refresh.setRefreshContent(recyclerView);
        refresh.autoRefresh();
    }

    @Override
    protected void initData() {
        String tagId = getIntent().getStringExtra("SearchCourseActivity_id");
        para = new HashMap<>();
        if (!TextUtils.isEmpty(tagId)) {
            para.put("type", tagId);
        }
        getSearchType();
    }

    @Override
    protected void setListener() {
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
        selectKeMenu.setOnMenuSelectDataChangedListener(new SelectMenuView
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
                    para.put("time", sortType.getText());
                }
                page=1;
                initListData();
            }

            @Override
            public void onViewClicked(View view) {

            }
        });
        searchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideInput();
                    para.put("keyword", Tools.getText(v));
                    return true;
                }
                return false;
            }
        });
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

    @OnClick(R.id.back_btn)
    @Override
    public void onClick(View view) {
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
                    if (searchModel.get_meta().getCurrentPage() == searchModel.get_meta().getPageCount())
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


    /**
     * 获取筛选类型数据
     */
    void getSearchType() {

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
                    selectKeMenu.setData(keTypeModel);
                    break;
            }
        }
    };
}
