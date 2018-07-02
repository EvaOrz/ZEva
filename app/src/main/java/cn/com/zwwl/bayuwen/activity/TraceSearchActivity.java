package cn.com.zwwl.bayuwen.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.LessonReportAdapter;
import cn.com.zwwl.bayuwen.api.TraceSearchApi;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.LessonModel;
import cn.com.zwwl.bayuwen.util.AppValue;
import cn.com.zwwl.bayuwen.widget.decoration.DividerItemDecoration;

/**
 * 课程跟踪点击搜索
 */
public class TraceSearchActivity extends BaseActivity {
    @BindView(R.id.search_view)
    AppCompatEditText searchView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    LessonReportAdapter adapter;
    List<LessonModel> reports;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trace_search);
        ButterKnife.bind(this);
        initView();
        setListener();
        initData();
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(getResources(), R.color.white, R
                .dimen.dp_5, OrientationHelper.VERTICAL));
        reports = new ArrayList<>();
        adapter = new LessonReportAdapter(reports);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }

    private void setListener() {

        searchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search();
                    return true;
                }
                return false;
            }
        });
    }

    private void search() {
        new TraceSearchApi(this, AppValue.getText(searchView), new ResponseCallBack<List<LessonModel>>() {
            @Override
            public void result(List<LessonModel> lessonModels, ErrorMsg errorMsg) {
                reports=lessonModels;
                adapter.setEmptyView(R.layout.empty_view, (ViewGroup) recyclerView.getParent());
                adapter.setNewData(lessonModels);
            }
        });
    }

    @OnClick(R.id.back)
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

}
