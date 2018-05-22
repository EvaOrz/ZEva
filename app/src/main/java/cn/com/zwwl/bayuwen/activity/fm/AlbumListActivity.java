package cn.com.zwwl.bayuwen.activity.fm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.BaseActivity;
import cn.com.zwwl.bayuwen.adapter.AlbumAdapter;
import cn.com.zwwl.bayuwen.api.fm.AlbumListApi;
import cn.com.zwwl.bayuwen.model.fm.AlbumModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 专辑列表页面
 */
public class AlbumListActivity extends BaseActivity {
    private int type = 0;// 0：带kid的专辑列表；1：我的收藏
    private ListView listView;
    private TextView title;
    private AlbumAdapter albumAdapter;
    private List<AlbumModel> albumModels = new ArrayList<>();
    private String kId;
    private int page = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_list);
        kId = getIntent().getStringExtra("AlbumListActivity_data");
        type = getIntent().getIntExtra("AlbumListActivity_type", 0);
        initView();
        initErrorLayout();
        initData();
    }

    private void initView() {
        title = findViewById(R.id.album_list_title);
        if (type == 0) {
            title.setText(R.string.album_list);
        } else if (type == 1) {
            title.setText(R.string.gerenshoucang);
        }
        listView = findViewById(R.id.album_list_listview);
        findViewById(R.id.album_list_back).setOnClickListener(this);
        albumAdapter = new AlbumAdapter(this);
        albumAdapter.setData(albumModels);
        listView.setAdapter(albumAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AlbumListActivity.this, AlbumDetailActivity.class);
                intent.putExtra("AlbumDetailActivity_data", albumModels.get(position).getKid());
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        showLoading();
        if (type == 0)
            new AlbumListApi(this, kId, page, new AlbumListApi.FetchAlbumListListener() {
                @Override
                public void setData(List<AlbumModel> data) {
                    if (data != null && data.size() > 0) {

                        albumModels.clear();
                        albumModels.addAll(data);
                        handler.sendEmptyMessage(0);
                    } else
                        handler.sendEmptyMessage(1);

                }


                @Override
                public void setError(ErrorMsg error) {
                    handler.sendEmptyMessage(1);
                }
            });
        else if (type == 1) {
            new AlbumListApi(this, 1, new AlbumListApi.FetchAlbumListListener() {
                @Override
                public void setData(List<AlbumModel> data) {
                    if (data != null && data.size() > 0) {
                        albumModels.clear();
                        albumModels.addAll(data);
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
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    disProcess();
                    albumAdapter.setData(albumModels);
                    albumAdapter.notifyDataSetChanged();
                    break;

                case 1:
                    showError(R.mipmap.blank_no_fm, R.string.no_content);
                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.album_list_back:
                finish();
                break;

        }
    }
}
