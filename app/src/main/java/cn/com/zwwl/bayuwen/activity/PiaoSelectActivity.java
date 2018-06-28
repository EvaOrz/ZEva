package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CartAdapter;
import cn.com.zwwl.bayuwen.api.CourseListApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.util.Tools;

/**
 * 开票选择页面
 */
public class PiaoSelectActivity extends BaseActivity {


    private ListView listView;
    private CheckBox checkBox;
    private List<KeModel> datas = new ArrayList<>();
    private CartAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piao_select);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {
        listView = findViewById(R.id.piao_s_listview);
        checkBox = findViewById(R.id.piao_s_check);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 重置
                for (int i = 0; i < datas.size(); i++) {
                    datas.get(i).setHasSelect(isChecked);
                }
                handler.sendEmptyMessage(0);
            }
        });
        adapter = new CartAdapter(this, true, new CartAdapter.OnItemCheckChangeListener() {
            @Override
            public void onCheckChange(int position, boolean cStatus) {
                if (datas.size() > position)
                    datas.get(position).setHasSelect(cStatus);
            }

            @Override
            public void onDelete(int position) {

            }
        });
        listView.setAdapter(adapter);
        findViewById(R.id.piao_s_back).setOnClickListener(this);
        findViewById(R.id.piao_s_next).setOnClickListener(this);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    adapter.setData(datas);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.piao_s_back:
                finish();
                break;
            case R.id.piao_s_next:// 下一步
                String oid_item = "";
                for (int i = 0; i < datas.size(); i++) {
                    if (datas.get(i).isHasSelect()) {// 选中
                        oid_item += datas.get(i).getKid() + "_" + datas.get(i)
                                .getOrderDetailModel().getOid() + ",";
                    }
                }
                if (TextUtils.isEmpty(oid_item)) {
                    showToast("请选择需要开发票的课程");

                } else {
                    Intent i = new Intent(mContext, PiaoKaiActivity.class);
                    i.putExtra("PiaoKaiActivity_list", oid_item);
                    startActivity(i);
                }

                break;
        }
    }

    @Override
    protected void initData() {
        showLoadingDialog(true);
        new CourseListApi(mContext, new FetchEntryListListener() {
            @Override
            public void setData(List list) {
                showLoadingDialog(false);
                if (Tools.listNotNull(list)) {
                    datas.clear();
                    datas.addAll(list);

                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void setError(ErrorMsg error) {
                showLoadingDialog(false);
                if (error != null)
                    showToast(error.getDesc());

            }
        });

    }
}
