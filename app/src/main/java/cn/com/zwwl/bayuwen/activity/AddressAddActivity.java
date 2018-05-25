package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.api.AddressApi;
import cn.com.zwwl.bayuwen.model.AddressModel;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.util.AddressTools.*;
import cn.com.zwwl.bayuwen.view.AddressPopWindow;
import cn.com.zwwl.bayuwen.widget.AutoTextGroupView;

/**
 * 添加地址页面
 */
public class AddressAddActivity extends BaseActivity {

    private AutoTextGroupView tagView;
    private List<AddressTag> tagDatas = new ArrayList<>();
    private LinearLayout addTag, addLayout;
    private TextView provinceTv;
    private EditText nameEv, phoneEv, addressEv;
    private String username, usernumber;
    private ProvinceModel provinceModel;
    private CityModel cityModel;
    private DistModel distModel;
    private AddressModel addressModel;
    private boolean isModify = false;// 是否是修改页面

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_add);
        initView();
        if (getIntent().getSerializableExtra("AddressAddActivity_data") != null && getIntent()
                .getSerializableExtra("AddressAddActivity_data") instanceof AddressModel) {
            addressModel = (AddressModel) getIntent().getSerializableExtra
                    ("AddressAddActivity_data");
            isModify = true;
            initData();
        } else addressModel = new AddressModel();

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.add_address_back:
                finish();
                break;
            case R.id.add_address_save:
                String name = nameEv.getText().toString();
                String phone = phoneEv.getText().toString();
                String area = provinceTv.getText().toString();
                String addre = addressEv.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    showToast("请填写收货人姓名");
                } else if (TextUtils.isEmpty(phone)) {
                    showToast("请填写收货人联系方式");
                } else if (TextUtils.isEmpty(area)) {
                    showToast("请选择收货地址");
                } else if (TextUtils.isEmpty(addre)) {
                    showToast("请填写详细地址");
                } else {
                    addressModel.setTo_user(name);
                    addressModel.setPhone(phone);
                    addressModel.setProvince(provinceModel.getPtxt());
                    addressModel.setProvince_id(provinceModel.getPid());
                    addressModel.setCity(cityModel.getCtxt());
                    addressModel.setCity_id(cityModel.getCid());
                    addressModel.setDistrict(distModel.getDtxt());
                    addressModel.setDistrict_id(distModel.getDid());
                    addressModel.setAddress(addre);
                    for (AddressTag tag : tagDatas) {
                        if (tag.isCheck) {
                            addressModel.setAddress_alias(tag.getTagTxt());
                        }
                    }
                    doAdd();
                }

                break;
            case R.id.tongxunlu:// 访问通讯录
                startActivityForResult(new Intent(Intent.ACTION_PICK,
                        ContactsContract.Contacts.CONTENT_URI), 0);
                break;

            case R.id.a_a_address:// 选择地址
                new AddressPopWindow(mContext, 0, new AddressPopWindow.OnAddressCListener() {

                    @Override
                    public void onClick(ProvinceModel province,
                                        CityModel city, DistModel dist) {
                        provinceModel = province;
                        cityModel = city;
                        distModel = dist;
                        handler.sendEmptyMessage(3);
                    }

                });
                break;
        }
    }

    private void doAdd() {
        showLoadingDialog(true);
        new AddressApi(mContext, addressModel, isModify, new AddressApi.FetchAddressListListener() {
            @Override
            public void setData(List<AddressModel> list) {
                showLoadingDialog(false);
            }

            @Override
            public void setError(ErrorMsg error) {
                showLoadingDialog(false);
                if (error != null)
                    showToast(error.getDesc());
            }
        });
    }

    @Override
    protected void initData() {
        nameEv.setText(addressModel.getTo_user());
        phoneEv.setText(addressModel.getPhone());
        provinceTv.setText(addressModel.getProvince() + " " + addressModel.getCity() + " " +
                addressModel.getDistrict());
        addressEv.setText(addressModel.getAddress());

    }

    private void initTag() {
        if (tagDatas.size() == 0) {
            tagDatas.add(new AddressTag("家", false));
            tagDatas.add(new AddressTag("公司", false));
            tagDatas.add(new AddressTag("学校", false));
        }
        tagView.removeAllViews();
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup
                .LayoutParams
                .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 40, 20);
        for (AddressTag addressTag : tagDatas) {
            tagView.addView(getTextView(addressTag, false), lp);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    initTag();
                    break;

                case 1:
                    addTag.setVisibility(View.GONE);
                    addLayout.setVisibility(View.VISIBLE);
                    break;
                case 2:// 通讯录选择
                    nameEv.setText(username);
                    phoneEv.setText(usernumber);
                    break;

                case 3:
                    provinceTv.setText(provinceModel.getPtxt() + " " + cityModel.getCtxt() + " "
                            + distModel.getDtxt());
                    break;
            }
        }
    };


    private void initView() {
        findViewById(R.id.add_address_back).setOnClickListener(this);
        findViewById(R.id.add_address_save).setOnClickListener(this);
        findViewById(R.id.tongxunlu).setOnClickListener(this);
        findViewById(R.id.a_a_address).setOnClickListener(this);

        provinceTv = findViewById(R.id.a_a_addresstv);
        addressEv = findViewById(R.id.a_a_addressev);
        nameEv = findViewById(R.id.a_a_name);
        phoneEv = findViewById(R.id.a_a_phone);
        tagView = findViewById(R.id.tag_view);
        addTag = findViewById(R.id.add_tag);
        addTag.addView(getTextView(null, true), new LinearLayout.LayoutParams(LinearLayout
                .LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        addLayout = findViewById(R.id.add_layout);
        initTag();

    }


    /**
     * 初始化标签textview
     *
     * @return
     */
    private TextView getTextView(AddressTag addressTag, final boolean isAdd) {
        TextView view = new TextView(this);

        if (isAdd) {
            view.setText("+");
            view.setTextSize(14);
            view.setGravity(Gravity.CENTER);
            view.setTextColor(getResources().getColor(R.color.gray_dark));
            view.setBackgroundResource(R.drawable.gray_white_xiankuang);
        } else {
            view.setTag(addressTag);
            view.setText(addressTag.getTagTxt());
            view.setTextSize(12);
            view.setGravity(Gravity.CENTER);
            if (!addressTag.isCheck()) {
                view.setTextColor(getResources().getColor(R.color.gray_dark));
                view.setBackgroundResource(R.drawable.gray_white_xiankuang);
            } else {
                view.setTextColor(getResources().getColor(R.color.white));
                view.setBackgroundResource(R.drawable.gold_circle);
            }
        }
        view.setPadding(40, 10, 40, 10);

        // 点击跳转
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isAdd) {
                    handler.sendEmptyMessage(1);
                } else {
                    AddressTag o = (AddressTag) v.getTag();
                    for (int i = 0; i < tagDatas.size(); i++) {
                        if (tagDatas.get(i).getTagTxt().equals(o.getTagTxt())) {
                            boolean origin = tagDatas.get(i).isCheck;
                            tagDatas.get(i).setCheck(!origin);
                        } else
                            tagDatas.get(i).setCheck(false);
                    }
                    handler.sendEmptyMessage(0);
                }

            }
        });
        return view;
    }

    public class AddressTag extends Entry {
        private String tagTxt = "";
        private boolean isCheck = false;

        public AddressTag(String tagTxt, boolean isCheck) {
            this.tagTxt = tagTxt;
            this.isCheck = isCheck;
        }

        public String getTagTxt() {
            return tagTxt;
        }

        public void setTagTxt(String tagTxt) {
            this.tagTxt = tagTxt;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            // ContentProvider展示数据类似一个单个数据库表
            // ContentResolver实例带的方法可实现找到指定的ContentProvider并获取到ContentProvider的数据
            ContentResolver reContentResolverol = getContentResolver();
            // URI,每个ContentProvider定义一个唯一的公开的URI,用于指定到它的数据集
            Uri contactData = data.getData();
            // 查询就是输入URI等参数,其中URI是必须的,其他是可选的,如果系统能找到URI对应的ContentProvider将返回一个Cursor对象.
            Cursor cursor = managedQuery(contactData, null, null, null, null);
            cursor.moveToFirst();
            // 获得DATA表中的名字
            username = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            // 条件为联系人ID
            String contactId = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts._ID));
            // 获得DATA表中的电话号码，条件为联系人ID,因为手机号码可能会有多个
            Cursor phone = reContentResolverol.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
                            + contactId, null, null);
            while (phone.moveToNext()) {
                usernumber = phone
                        .getString(phone
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                handler.sendEmptyMessage(2);
            }

        }
    }

}
