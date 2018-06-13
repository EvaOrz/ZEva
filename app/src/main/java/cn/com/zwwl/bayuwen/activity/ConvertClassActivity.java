package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CourseTableAdapter;
import cn.com.zwwl.bayuwen.api.CourseListApi;
import cn.com.zwwl.bayuwen.api.KeSelectTypeApi;
import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.base.MenuCode;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
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
 * 选择可调课（可转）班级
 * Created by zhumangmang at 2018/5/29 13:57
 */
public class ConvertClassActivity extends BasicActivityWithTitle {
    @BindView(R.id.search)
    AppCompatEditText search;
    @BindView(R.id.operate)
    SelectMenuView operate;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    CourseTableAdapter adapter;
    KeTypeModel typeModel;
    private String url = UrlUtil.getCDetailUrl(null) + "/search";
    private List<KeModel> keModels;
    private List<KeModel> stockClass;
    private int page = 1;

    @Override
    protected int setContentView() {
        return R.layout.activity_convert_class;
    }

    @Override
    protected void initView() {
        showMenu(MenuCode.HIDE_CLASS);
        keModels = new ArrayList<>();
        stockClass = new ArrayList<>();
        if (mApplication.operate_type == 0) {
            setCustomTitle(res.getString(R.string.chose_chanable_class));
        } else {
            setCustomTitle(res.getString(R.string.chose_convertable_class));
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new CourseTableAdapter(null);
        recyclerView.setAdapter(adapter);
        refresh.setRefreshContent(recyclerView);
        refresh.autoRefresh();
    }

    @Override
    protected void initData() {
        getChoseType();
    }

    private void getChoseType() {
        new KeSelectTypeApi(mContext, 1, new FetchEntryListener() {
            @Override
            public void setData(final Entry entry) {
                if (entry != null) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            typeModel = (KeTypeModel) entry;
                            operate.setOfflineData(typeModel);
                        }
                    });

                }
            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });
    }

    @Override
    protected void setListener() {
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                page = 1;
                refresh.setNoMoreData(false);
                getCourseData();
            }
        });
        refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ++page;
                getCourseData();
            }
        });
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideInput();
                    map.put("keyword", Tools.getText(v));
                    return true;
                }
                return false;
            }
        });
        operate.setOnMenuSelectDataChangedListener(new SelectMenuView.OnMenuSelectDataChangedListener() {
            @Override
            public void onSortChanged(SelectTempModel sortType, int type) {
                switch (type) {
                    case 1:
                        map.put("users", sortType.getId());
                        break;
                    case 2:
                        map.put("type", sortType.getId());
                        break;
                    case 4:
                        map.put("school", sortType.getId());
                        break;
                    default:
                        map.put("time", sortType.getText());
                        break;
                }
                getCourseData();
            }

            @Override
            public void onViewClicked(View view) {

            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mApplication.newKe = keModels.get(position);
                if (mApplication.operate_type == 0) {
                    Intent intent = new Intent(mActivity, UnitTableActivity.class);
                    intent.putExtra("type", 1);
                    intent.putExtra("kid", keModels.get(position).getKid());
                    intent.putExtra("course_type", 1);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mActivity, ClassDetailActivity.class);
                    intent.putExtra("kid", keModels.get(position).getKid());
                    startActivity(intent);
                }
            }
        });
    }

    private void getCourseData() {
        map.put("online", "0");
        map.put("page", String.valueOf(page));
        StringBuilder temp = new StringBuilder(url);
        if (map.size() > 0) {
            temp.append("?");
            for (String key : map.keySet()) {
                if (!TextUtils.isEmpty(map.get(key))) {
                    String and = temp.toString().endsWith("?") ? "" : "&";
                    temp.append(and).append(key).append("=").append(map.get(key));
                }
            }
            new CourseListApi(this, temp.toString(), new ResponseCallBack<SearchModel>() {
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
                    stockClass.clear();
                    for (KeModel model : keModels) {
                        if (!"0".equals(model.getStock())) stockClass.add(model);
                    }
                    adapter.setNewData(keModels);
                }
            });
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void onMenuClick(int menuCode) {
        adapter.setNewData(stockClass);
    }
}
