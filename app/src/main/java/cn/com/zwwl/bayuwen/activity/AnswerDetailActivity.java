package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.AnswerDetailAdapter;
import cn.com.zwwl.bayuwen.api.AnswerDetailApi;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.OptionModel;
import cn.com.zwwl.bayuwen.util.Tools;

/**
 * 答题详情页面
 */
public class AnswerDetailActivity extends BaseActivity {
    private ListView listView;
    private AnswerDetailAdapter adapter;
    private List<OptionModel> all = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_detail);
        initView();
        initData();
    }

    private void initView() {
        findViewById(R.id.answer_d_back).setOnClickListener(this);
        listView = findViewById(R.id.answer_listview);
        adapter = new AnswerDetailAdapter(mContext);
        listView.setAdapter(adapter);
    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    adapter.setData(all);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.answer_d_back:
                finish();
                break;
        }
    }

    @Override
    protected void initData() {
        new AnswerDetailApi(this, getIntent().getStringExtra("AnswerDetailActivity_data"), new
                ResponseCallBack<List<OptionModel>>() {
                    @Override
                    public void result(List<OptionModel> optionModels, ErrorMsg errorMsg) {
                        if (errorMsg != null) showToast(errorMsg.getDesc());
                        if (Tools.listNotNull(optionModels)) {
                            all.addAll(optionModels);
                            handler.sendEmptyMessage(0);
                        }
                    }
                });
    }
}
