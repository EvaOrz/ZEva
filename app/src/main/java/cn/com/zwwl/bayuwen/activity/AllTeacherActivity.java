package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.api.KeSelectTypeApi;
import cn.com.zwwl.bayuwen.api.TeacherApi;
import cn.com.zwwl.bayuwen.api.TeacherListApi;
import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.SearchTModel;
import cn.com.zwwl.bayuwen.model.TeacherModel;
import cn.com.zwwl.bayuwen.model.TeacherTypeModel;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.view.selectmenu.SelectTempModel;
import cn.com.zwwl.bayuwen.view.selectmenu.TeacherMenuView;
import cn.com.zwwl.bayuwen.widget.CircleImageView;

/**
 * 全部教师页面
 */
public class AllTeacherActivity extends BaseActivity {
    private TeacherMenuView menuView;
    private EditText searchEv;
    private RecyclerView gridView;
    private static String PAMAS_SEARCHKEY = "keyword";//关键字
    private static String PAMAS_USERS = "users";//年级
    private static String PAMAS_TYPES = "type";//课程类型

    private TeacherAdapter teacherAdapter;
    private List<TeacherModel> teacherModels = new ArrayList<>();
    private TeacherTypeModel teacherTypeModel;
    private SmartRefreshLayout refresh;

    private int page = 1;
    private int type = 1;// 1:老师 2:班主任
    protected HashMap<String, String> para = new HashMap<>();


    @Override
    protected void initData() {
        // 获取分类列表
        new KeSelectTypeApi(mContext, 2, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                if (entry != null) {
                    teacherTypeModel = (TeacherTypeModel) entry;
                    handler.sendEmptyMessage(1);
                }
            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });
    }

    private void getListData() {
        String url = "";
        if (para.containsKey(PAMAS_SEARCHKEY) || para.containsKey(PAMAS_USERS) || para
                .containsKey(PAMAS_TYPES)) {//教师/班主任搜索
            url += UrlUtil.getTeacherUrl(null) + "/search?flag=" + type;
        } else {//获取全部教师/全部班主任
            url += UrlUtil.getTeacherUrl(null) + "?flag=" + type;
        }
        StringBuilder builder = new StringBuilder(url);
        if (!para.containsKey("page")) {
            para.put("page", String.valueOf(page));
        }
        if (!para.containsKey("pagesize")) {
            para.put("pagesize", "32");
        }
        for (String key : para.keySet()) {
            if (!TextUtils.isEmpty(para.get(key))) {
                builder.append("&").append(key).append("=").append(para.get(key));
            }
        }
        // 获取全部教师
        new TeacherListApi(this, builder.toString(), new ResponseCallBack<SearchTModel>() {
            @Override
            public void result(SearchTModel searchTModel, ErrorMsg errorMsg) {
                refresh.finishRefresh();
                refresh.finishLoadMore();
                if (searchTModel != null) {
                    if (page == 1)
                        teacherModels.clear();
                    if (Tools.listNotNull(searchTModel.getData())) {
                        teacherModels.addAll(searchTModel.getData());
                    } else
                        refresh.finishLoadMoreWithNoMoreData();
                    handler.sendEmptyMessage(0);

                }
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
                    teacherAdapter = new TeacherAdapter(teacherModels);
                    gridView.setAdapter(teacherAdapter);
                    teacherAdapter.setOnItemClickListener(new BaseQuickAdapter
                            .OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            Intent i = new Intent(mContext, TeacherDetailActivity.class);
                            i.putExtra("tid", teacherModels.get(position).getTid());
                            startActivity(i);
                        }
                    });
                    break;
                case 1:
                    menuView.setData(teacherTypeModel);
                    break;
                case 2:

                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_teacher);
        type = getIntent().getIntExtra("AllTeacherActivity_data", 1);

        initView();
        initData();
    }

    private void initView() {
        findViewById(R.id.all_t_back).setOnClickListener(this);
        refresh = findViewById(R.id.refresh);
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                page = 1;
                refresh.setNoMoreData(false);
                getListData();
            }
        });
        refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ++page;
                getListData();
            }
        });
        searchEv = findViewById(R.id.search_view);
        searchEv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String search = Tools.getText(v);
                    if (!TextUtils.isEmpty(search)) {
                        para.put(PAMAS_SEARCHKEY, search);
                        page = 1;
                        getListData();
                    }
                    hideJianpan();
                    return true;
                }
                return false;
            }
        });

        menuView = findViewById(R.id.all_t_menu);
        menuView.setOnSureClickListener(new TeacherMenuView.OnSureClickListener() {
            @Override
            public void onClick(List<SelectTempModel> checkedList, int type) {
                String stxt = searchEv.getText().toString();
                if (!TextUtils.isEmpty(stxt)) {
                    para.put(PAMAS_SEARCHKEY, stxt);
                }
                if (Tools.listNotNull(checkedList)) {
                    if (type == 1) {//年级
                        String gtxt = "";
                        for (SelectTempModel selectTempModel : checkedList) {
                            if (selectTempModel.isCheck())
                                gtxt += selectTempModel.getText() + ",";
                        }
                        if (!TextUtils.isEmpty(gtxt))
                            para.put(PAMAS_USERS, gtxt.substring(0, gtxt.length() - 1));
                    } else if (type == 2) {//项目
                        String xtxt = "";
                        for (SelectTempModel selectTempModel : checkedList) {
                            if (selectTempModel.isCheck())
                                xtxt += selectTempModel.getText() + ",";
                        }
                        if (!TextUtils.isEmpty(xtxt))
                            para.put(PAMAS_TYPES, xtxt.substring(0, xtxt.length() - 1));
                    }
                } else {//全部
                    switch (type) {
                        case 1://年级
                            if (para.containsKey(PAMAS_USERS)) {
                                para.remove(PAMAS_USERS);
                            }
                            break;
                        case 2://项目
                            if (para.containsKey(PAMAS_TYPES)) {
                                para.remove(PAMAS_TYPES);
                            }
                            break;
                    }
                }
                page = 1;
                getListData();

            }
        });

        int height = getViewHeight(menuView);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout
                .LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, height, 0, 0);
        layoutParams.addRule(RelativeLayout.BELOW, R.id.search_head_layout);
        gridView = findViewById(R.id.all_t_grid);
        refresh.setLayoutParams(layoutParams);
        refresh.setRefreshContent(gridView);
        refresh.autoRefresh();

        GridLayoutManager mgr = new GridLayoutManager(mContext, 4);
        gridView.setLayoutManager(mgr);


    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.all_t_back:
                finish();
                break;
        }
    }

    public class TeacherAdapter extends BaseQuickAdapter<TeacherModel, BaseViewHolder> {
        public TeacherAdapter(@Nullable List<TeacherModel> data) {
            super(R.layout.item_cdetail_teacher, data);
        }


        @Override
        protected void convert(BaseViewHolder helper, TeacherModel item) {
            TextView title = helper.getView(R.id.cdetail_t_name);
            CircleImageView img = helper.getView(R.id.cdetail_t_avatar);

            title.setText(item.getName());
            if (!TextUtils.isEmpty(item.getPic()))
                ImageLoader.display(mContext, img, item.getPic(), R
                        .drawable.avatar_placeholder, R.drawable.avatar_placeholder);

        }
    }
}
