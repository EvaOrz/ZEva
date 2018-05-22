package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CheckScrollAdapter;
import cn.com.zwwl.bayuwen.api.AddressApi;
import cn.com.zwwl.bayuwen.model.AddressModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.fm.AlbumModel;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 地址管理页面
 */
public class AddressManageActivity extends BaseActivity {
    private ListView listView;
    private AddressAdapter addressAdapter;
    private List<AddressModel> datas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_manage);
        initView();
        initData();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.add_manage_back:
                finish();
                break;
            case R.id.add_manage_bt:
                startActivity(new Intent(mContext, AddressAddActivity.class));
                break;
        }
    }


    @Override
    protected void initData() {
        showLoadingDialog(true);
        new AddressApi(mContext, new AddressApi.FetchAddressListListener() {
            @Override
            public void setData(List<AddressModel> list) {
                showLoadingDialog(false);
                if (Tools.listNotNull(list)) {
                    datas.clear();
                    datas.addAll(list);
                    addressAdapter.setData(datas);
                }
            }

            @Override
            public void setError(ErrorMsg error) {
                showLoadingDialog(false);
                if (error != null) showToast(error.getDesc());

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
                    addressAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };


    private void initView() {
        findViewById(R.id.add_manage_back).setOnClickListener(this);
        findViewById(R.id.add_manage_bt).setOnClickListener(this);
        listView = findViewById(R.id.add_manage_list);
        addressAdapter = new AddressAdapter(mContext);
        listView.setAdapter(addressAdapter);
    }

    public class AddressAdapter extends CheckScrollAdapter<Entry> {
        protected Context mContext;
        protected List<AlbumModel> mItemList = new ArrayList<>();

        public AddressAdapter(Context context) {
            super(context);
            mContext = context;
        }

        public void setData(List<AddressModel> mItemList) {
            clearData();
            clear();
            isScroll = false;
            synchronized (mItemList) {
                for (Entry item : mItemList) {
                    add(item);
                }
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Entry item = getItem(position);
            ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout.item_address);
            viewHolder.getView(R.id.address_edit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, AddressAddActivity.class));
                }
            });
            return viewHolder.getConvertView();
        }

        public void clearData() {
            mItemList.clear();
        }

        public boolean isScroll() {
            return isScroll;
        }

    }

}
