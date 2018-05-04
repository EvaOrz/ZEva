package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.api.UploadApi;
import cn.com.zwwl.bayuwen.api.UserApi;
import cn.com.zwwl.bayuwen.db.DataHelper;
import cn.com.zwwl.bayuwen.model.UserModel;
import cn.com.zwwl.bayuwen.view.AddressPopWindow;
import cn.com.zwwl.bayuwen.view.ChangeInfoDialog;
import cn.com.zwwl.bayuwen.view.FetchPhotoManager;
import cn.com.zwwl.bayuwen.view.GenderPopWindow;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 修改用户信息页面
 */
public class ChangeUserInfoActivity extends BaseActivity {

    private UserModel userModel;
    private String picturePath;// 头像
    private static final String KEY_IMAGE = "data";
    private static final String AVATAR_PIC = "avatar.jpg";
    private File photoFile;
    private ImageView aImg;
    private TextView nameTxt, genderTxt, phoneTxt, addTxt;
    private boolean isNeedChangePic = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_change_userinfo);
        picturePath = Environment.getExternalStorageDirectory().getPath() + "/" + AVATAR_PIC;
        initView();
        userModel = DataHelper.getUserLoginInfo(this);
        initData();
    }

    @Override
    protected void initData() {
        if (userModel == null) return;
        if (!TextUtils.isEmpty(userModel.getPic()))
            Glide.with(this).load(userModel.getPic()).into(aImg);
        nameTxt.setText(userModel.getName());
        genderTxt.setText(userModel.getSexTxt(userModel.getSex()));
        phoneTxt.setText(userModel.getTel());
        addTxt.setText(userModel.getArea() + "");
    }

    private void initView() {
        nameTxt = findViewById(R.id.info_name_d);
        genderTxt = findViewById(R.id.info_gender_d);
        phoneTxt = findViewById(R.id.info_phone_d);
        addTxt = findViewById(R.id.info_address_d);
        aImg = findViewById(R.id.info_avatar_d);

        findViewById(R.id.info_back).setOnClickListener(this);
        findViewById(R.id.info_avatar).setOnClickListener(this);
        findViewById(R.id.info_name).setOnClickListener(this);
        findViewById(R.id.info_gender).setOnClickListener(this);
        findViewById(R.id.info_phone).setOnClickListener(this);
        findViewById(R.id.info_address).setOnClickListener(this);
        findViewById(R.id.info_commit).setOnClickListener(this);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                case 3:
                    isNeedChangePic = true;
                    Glide.with(ChangeUserInfoActivity.this).load(photoFile).
                            into(aImg);
                    break;


                case 1:// 重新加载用户信息
                    initData();
                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.info_back:
                finish();
                break;
            case R.id.info_avatar:
                doFecthPicture();
                break;
            case R.id.info_name:
                new ChangeInfoDialog(this, 1, new ChangeInfoDialog.SignChangeListener() {
                    @Override
                    public void setText(String text) {
                        userModel.setName(text);
                        handler.sendEmptyMessage(1);
                    }
                });
                break;
            case R.id.info_gender:
                new GenderPopWindow(this, new GenderPopWindow.ChooseGenderListener() {
                    @Override
                    public void choose(int gender) {
                        userModel.setSex(gender);
                        handler.sendEmptyMessage(1);
                    }
                });
                break;
            case R.id.info_phone:
                new ChangeInfoDialog(this, 2, new ChangeInfoDialog.SignChangeListener() {
                    @Override
                    public void setText(String text) {
                        userModel.setTel(text);
                        handler.sendEmptyMessage(1);
                    }
                });
                break;
            case R.id.info_address:
                new AddressPopWindow(mContext, new AddressPopWindow.OnAddressCListener() {
                    @Override
                    public void onClick(String province, String city) {

                    }
                });
                break;
            case R.id.info_commit:
                showLoadingDialog(true);
                if (isNeedChangePic) {
                    uploadPic(photoFile);
                } else
                    commit(userModel.getName(), userModel.getTel(), userModel.getSex(), userModel.getProvince(), userModel.getCity(), "");
                break;

        }
    }

    private void uploadPic(File file) {
        new UploadApi(this, file, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                if (entry != null && entry instanceof ErrorMsg) {
                    userModel.setPic(((ErrorMsg) entry).getDesc());
                    commit(userModel.getName(), userModel.getTel(), userModel.getSex(), userModel.getProvince(), userModel.getCity(), userModel.getPic());

                } else {
                    showToast(R.string.upload_faild);
                }
            }
        });
    }

    /**
     * 提交
     *
     * @param name
     * @param phone
     * @param gendar
     * @param province
     * @param city
     * @param pic
     */
    private void commit(String name, String phone, int gendar, int province, int city, String pic) {
        new UserApi(this, name, phone, gendar, province, city, pic, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                showLoadingDialog(false);
                if (entry != null && entry instanceof UserModel) {
                    MyApplication.loginStatusChange = true;
                    finish();
                } else {
                    showToast(R.string.change_info_faild);
                }

            }
        });
    }

    protected void doFecthPicture() {
        FetchPhotoManager fetchPhotoManager = new FetchPhotoManager(this, picturePath);
        fetchPhotoManager.doFecthPicture();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == FetchPhotoManager.REQUEST_CAMERA) {

//                avatarBit = data.getParcelableExtra("data");
                photoFile = new File(picturePath);
            } else if (requestCode == FetchPhotoManager.REQUEST_GALLERY) {
                if (data != null) {
                    String[] projection = {MediaStore.Video.Media.DATA};
                    Cursor cursor = managedQuery(data.getData(), projection, null, null, null);
                    int column_index = cursor
                            .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
                    cursor.moveToFirst();
                    photoFile = new File(cursor.getString(column_index));

                }
            }
            handler.sendEmptyMessage(3);

        } else if (resultCode == RESULT_CANCELED) {
            showToast(R.string.cancle);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}