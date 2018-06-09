package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.KeSelectAdapter;
import cn.com.zwwl.bayuwen.api.FollowApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.listener.OnItemClickListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.util.Tools;

/**
 * 我关注的班级页面
 */

public class MyCollectionActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private KeSelectAdapter keSelectAdapter;
    private List<KeModel> keModels = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        initView();
        initData();

    }

    private void initView() {
        findViewById(R.id.back_btn).setOnClickListener(this);
        recyclerView = findViewById(R.id.course_recyclerVie);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    keSelectAdapter = new KeSelectAdapter(mContext, 0, keModels);
                    recyclerView.setAdapter(keSelectAdapter);
                    keSelectAdapter.notifyDataSetChanged();
                    keSelectAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void setOnItemClickListener(View view, int position) {

                            Intent intent = new Intent();
                            intent.putExtra("CourseDetailActivity_id", keModels.get(position)
                                    .getKid());
                            intent.setClass(mContext, CourseDetailActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void setOnLongItemClickListener(View view, int position) {

                        }
                    });
                    break;
            }

        }
    };

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
        showLoadingDialog(true);
        new FollowApi(mContext, 1, new FetchEntryListListener() {
            @Override
            public void setData(List list) {
                showLoadingDialog(false);
                if (Tools.listNotNull(list)) {
                    keModels.clear();
                    keModels.addAll(list);
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void setError(ErrorMsg error) {
                showLoadingDialog(false);
            }
        });
    }
}
