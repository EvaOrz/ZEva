package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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
    private boolean isNeedResult = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_manage);
        isNeedResult = getIntent().getBooleanExtra("AddressManageActivity_type", false);
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
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
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void setError(ErrorMsg error) {
                showLoadingDialog(false);
                if (error != null) showToast(error.getDesc());

            }
        });
    }

    /**
     * 删除
     *
     * @param aid
     */
    private void doDelete(String aid) {
        showLoadingDialog(true);
        new AddressApi(mContext, aid, 0, new AddressApi.FetchAddressListListener() {
            @Override
            public void setData(List<AddressModel> list) {
                showLoadingDialog(false);
                handler.sendEmptyMessage(1);
            }

            @Override
            public void setError(ErrorMsg error) {
                showLoadingDialog(false);
                if (error != null) showToast(error.getDesc());
            }
        });
    }

    /**
     * 设置默认
     *
     * @param aid
     */
    private void doSetDefualt(String aid) {
        showLoadingDialog(true);
        new AddressApi(mContext, aid, 1, new AddressApi.FetchAddressListListener() {
            @Override
            public void setData(List<AddressModel> list) {

            }

            @Override
            public void setError(ErrorMsg error) {
                showLoadingDialog(false);
                if (error != null) showToast(error.getDesc());
                else {
                    handler.sendEmptyMessage(1);
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
                    addressAdapter.setData(datas);
                    addressAdapter.notifyDataSetChanged();
                    break;
                case 1:
                    initData();
                    break;
            }
        }
    };


    private void initView() {
        findViewById(R.id.add_manage_back).setOnClickListener(this);
        findViewById(R.id.add_manage_bt).setOnClickListener(this);
        listView = findViewById(R.id.add_manage_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!isNeedResult) return;
                Intent i = new Intent();
                i.putExtra("address_pick", datas.get(position));
                setResult(1000, i);
                finish();
            }
        });
        addressAdapter = new AddressAdapter(mContext);
        listView.setAdapter(addressAdapter);
    }

    public class AddressAdapter extends CheckScrollAdapter<AddressModel> {
        protected Context mContext;

        public AddressAdapter(Context context) {
            super(context);
            mContext = context;
        }

        public void setData(List<AddressModel> mItemList) {
            clear();
            isScroll = false;
            synchronized (mItemList) {
                for (AddressModel item : mItemList) {
                    add(item);
                }
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final AddressModel item = getItem(position);
            ViewHolder viewHolder = ViewHolder.get(mContext, convertView, R.layout.item_address);
            TextView name = viewHolder.getView(R.id.item_a_name);
            TextView phone = viewHolder.getView(R.id.item_a_phone);
            TextView tag = viewHolder.getView(R.id.item_a_tag);
            TextView area1 = viewHolder.getView(R.id.item_a_area1);
            TextView area2 = viewHolder.getView(R.id.item_a_area2);
            CheckBox checkBox = viewHolder.getView(R.id.address_checkbox);
            TextView defaul = viewHolder.getView(R.id.address_default);
            LinearLayout undefault = viewHolder.getView(R.id.address_default1);

            name.setText(item.getTo_user());
            phone.setText(item.getPhone());
            if (TextUtils.isEmpty(item.getAddress_alias())) {
                tag.setVisibility(View.GONE);
            } else {
                tag.setVisibility(View.VISIBLE);
                tag.setText(item.getAddress_alias());
            }
            area1.setText(item.getProvince() + item.getCity() + item.getDistrict());
            area2.setText(item.getAddress());
            if (item.getIs_default().equals("1")) {
                defaul.setVisibility(View.VISIBLE);
                undefault.setVisibility(View.INVISIBLE);
            } else {
                defaul.setVisibility(View.INVISIBLE);
                undefault.setVisibility(View.VISIBLE);
                checkBox.setChecked(false);
            }
            viewHolder.getView(R.id.address_edit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, AddressAddActivity.class);
                    i.putExtra("AddressAddActivity_data", item);
                    mContext.startActivity(i);
                }
            });
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        doSetDefualt(item.getId());
                    }
                }
            });
            viewHolder.getView(R.id.address_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doDelete(item.getId());
                }
            });
            return viewHolder.getConvertView();
        }


        public boolean isScroll() {
            return isScroll;
        }

    }

}
