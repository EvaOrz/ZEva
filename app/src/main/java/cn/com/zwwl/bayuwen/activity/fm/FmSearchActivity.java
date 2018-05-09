package cn.com.zwwl.bayuwen.activity.fm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.BaseActivity;
import cn.com.zwwl.bayuwen.adapter.AlbumAdapter;
import cn.com.zwwl.bayuwen.api.fm.AlbumListApi;
import cn.com.zwwl.bayuwen.model.AlbumModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 搜索页面
 */
public class FmSearchActivity extends BaseActivity {
    private EditText sEdit;
    private TextView sButton;
    private ImageView clear;
    private boolean ifcanSearch = true;
    private List<AlbumModel> data = new ArrayList<>();
    private AlbumAdapter albumAdapter;
    private ListView listView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initErrorLayout();
    }

    @Override
    protected void initData() {

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    sButton.setText(R.string.search);
                    disProcess();
                    albumAdapter.setData(data);
                    albumAdapter.notifyDataSetChanged();
                    break;

                case 1:
                    clear.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    clear.setVisibility(View.GONE);
                    break;
                case 3:
                    sButton.setText(R.string.search);
                    showError(R.mipmap.blank_search, R.string.no_search);
                    break;

            }
        }
    };

    private void doSearch(String key) {
        showLoading();
        sButton.setText(R.string.cancle);
        new AlbumListApi(this, key, new AlbumListApi.FetchAlbumListListener() {
            @Override
            public void setData(List<AlbumModel> list) {
                ifcanSearch = true;
                if (list != null && list.size() > 0) {
                    data.clear();
                    data.addAll(list);
                    handler.sendEmptyMessage(0);

                } else {
                    handler.sendEmptyMessage(3);
                }
            }

            @Override
            public void setError(ErrorMsg error) {
                ifcanSearch = true;
                handler.sendEmptyMessage(3);
            }
        });
    }

    private void initView() {
        findViewById(R.id.search_back).setOnClickListener(this);
        sEdit = findViewById(R.id.search_edit);
        sEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    handler.sendEmptyMessage(1);
                } else {
                    handler.sendEmptyMessage(2);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        sButton = findViewById(R.id.search_button);
        listView = findViewById(R.id.search_listview);
        clear = findViewById(R.id.search_clear);
        albumAdapter = new AlbumAdapter(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FmSearchActivity.this, AlbumDetailActivity.class);
                intent.putExtra("AlbumDetailActivity_data", data.get(position).getKid());
                startActivity(intent);
            }
        });
        listView.setAdapter(albumAdapter);
        sButton.setOnClickListener(this);
        clear.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.search_back:
                finish();
                break;

            case R.id.search_button:
                String key = sEdit.getText().toString();
                if (!TextUtils.isEmpty(key))
                    if (ifcanSearch) {
                        ifcanSearch = false;
                        doSearch(key);
                    } else {
                        ifcanSearch = true;
                        disProcess();
                        sButton.setText(R.string.search);
                    }

                break;

            case R.id.search_clear:
                sEdit.setText("");
                break;
        }
    }
}
