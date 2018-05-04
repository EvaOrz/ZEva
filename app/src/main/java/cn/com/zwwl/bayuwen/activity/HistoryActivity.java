package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.HistroyAdapter;
import cn.com.zwwl.bayuwen.api.fm.AlbumListApi;
import cn.com.zwwl.bayuwen.model.AlbumModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.view.NoScrollListView;

/**
 * 收听历史页面
 */
public class HistoryActivity extends BaseActivity {


    private NoScrollListView listView1, listView2;
    private TextView today, yesterday;
    private HistroyAdapter adapter1, adapter2;
    private List<AlbumModel> albumModels1 = new ArrayList<>(), albumModels2 = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initView();
        initErrorLayout();
        initData();
    }


    private void initView() {
        findViewById(R.id.history_back).setOnClickListener(this);
        today = findViewById(R.id.history__1);
        yesterday = findViewById(R.id.history__2);
        listView1 = findViewById(R.id.history_listview1);
        listView2 = findViewById(R.id.history_listview2);
        adapter1 = new HistroyAdapter(this);
        adapter2 = new HistroyAdapter(this);
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.history_back:
                finish();
                break;
        }
    }

    @Override
    public void initData() {
        showLoading();
        new AlbumListApi(this, new AlbumListApi.FetchAlbumListListener() {
            @Override
            public void setData(List<AlbumModel> data) {
                if (data != null && data.size() > 0) {
                    albumModels1.clear();
                    albumModels2.clear();
                    for (AlbumModel aa : data) {
                        if (System.currentTimeMillis() - Tools.fromStringTotime(aa.getCreated_at()) > 1000 * 60 * 60 * 24) {
                            albumModels2.add(aa);
                        } else {
                            albumModels1.add(aa);
                        }
                    }
                    handler.sendEmptyMessage(0);
                } else
                    handler.sendEmptyMessage(1);

            }


            @Override
            public void setError(ErrorMsg error) {
                handler.sendEmptyMessage(1);
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
                    disProcess();
                    if (Tools.listNotNull(albumModels1)) {
                        today.setVisibility(View.VISIBLE);
                        listView1.setVisibility(View.VISIBLE);
                        adapter1.setData(albumModels1);
                        adapter1.notifyDataSetChanged();
                    } else {
                        today.setVisibility(View.GONE);
                        listView1.setVisibility(View.GONE);
                    }
                    if (Tools.listNotNull(albumModels2)) {
                        yesterday.setVisibility(View.VISIBLE);
                        listView2.setVisibility(View.VISIBLE);
                        adapter2.setData(albumModels2);
                        adapter2.notifyDataSetChanged();
                    } else {
                        yesterday.setVisibility(View.GONE);
                        listView2.setVisibility(View.GONE);

                    }

                    break;

                case 1:
                    showError(R.mipmap.blank_no_fm, R.string.no_content);
                    break;
            }
        }
    };


}
