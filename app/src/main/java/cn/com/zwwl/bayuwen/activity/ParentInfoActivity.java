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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.GiftAdapter;
import cn.com.zwwl.bayuwen.api.HonorListApi;
import cn.com.zwwl.bayuwen.api.UploadPicApi;
import cn.com.zwwl.bayuwen.api.UserInfoApi;
import cn.com.zwwl.bayuwen.db.TempDataHelper;
import cn.com.zwwl.bayuwen.db.UserDataHelper;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ChildModel;
import cn.com.zwwl.bayuwen.model.CommonModel;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.GiftAndJiangModel;
import cn.com.zwwl.bayuwen.model.UserModel;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.view.GenderPopWindow;
import cn.com.zwwl.bayuwen.widget.FetchPhotoManager;
import cn.com.zwwl.bayuwen.widget.MostGridView;

/**
 * 编辑家长信息页面
 */
public class ParentInfoActivity extends BaseActivity {
    private String picturePath;// 头像
    private static final String KEY_IMAGE = "data";
    private static final String AVATAR_PIC = "avatar.jpg";
    private File photoFile;
    private ImageView aImg;
    private EditText nameEv;
    private TextView genderTv, phoneTv;
    private boolean isNeedChangePic = false;
    private MostGridView giftGridView;
    private GiftAdapter giftAdapter;

    private List<GiftAndJiangModel> giftModels = new ArrayList<>();// 礼物数据

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_parent);
        picturePath = Environment.getExternalStorageDirectory().getPath() + "/" + AVATAR_PIC;
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {
        aImg = findViewById(R.id.info_p_aimg);
        nameEv = findViewById(R.id.info_p_nametv);
        genderTv = findViewById(R.id.info_p_sextv);
        phoneTv = findViewById(R.id.info_p_phoneTv);
        giftGridView = findViewById(R.id.gift_grid);

        findViewById(R.id.info_p_back).setOnClickListener(this);
        findViewById(R.id.info_p_avatar).setOnClickListener(this);
        findViewById(R.id.info_p_sex).setOnClickListener(this);
        findViewById(R.id.info_p_manage).setOnClickListener(this);
        findViewById(R.id.info_p_save).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.info_p_back:
                finish();
                break;
            case R.id.info_p_avatar:
                doFecthPicture();
                break;
            case R.id.info_p_sex:
                new GenderPopWindow(this, new GenderPopWindow.ChooseGenderListener() {
                    @Override
                    public void choose(int gender) {
                        userModel.setSex(gender);
                        handler.sendEmptyMessage(1);
                    }
                });
                break;
            case R.id.info_p_manage:
                startActivity(new Intent(mContext, AddressManageActivity.class));
                break;

            case R.id.info_p_save:// 保存
                String na = nameEv.getText().toString();
                if (TextUtils.isEmpty(na)) {
                    showToast("姓名不能为空");
                } else {
                    userModel.setName(na);
                    showLoadingDialog(true);
                    if (isNeedChangePic) {
                        uploadPic(photoFile);
                    } else
                        commit();
                }


                break;
        }

    }

    private void uploadPic(File file) {
        new UploadPicApi(this, file, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                if (entry != null && entry instanceof CommonModel) {
                    userModel.setPic(((CommonModel) entry).getUrl());
                    commit();

                }
            }

            @Override
            public void setError(ErrorMsg error) {
                if (error != null)
                    showToast(TextUtils.isEmpty(error.getDesc()) ? mContext.getResources()
                            .getString(R.string.upload_faild) : error
                            .getDesc());
            }
        });
    }

    /**
     * 提交
     */
    private void commit() {
        new UserInfoApi(this, userModel, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                showLoadingDialog(false);
                if (entry != null && entry instanceof UserModel) {
                    MyApplication.userStatusChange = true;
                    finish();
                }
            }

            @Override
            public void setError(ErrorMsg error) {
                showLoadingDialog(false);
                if (error != null)
                    showToast(TextUtils.isEmpty(error.getDesc()) ? mContext.getResources()
                            .getString(R.string.upload_faild) : error
                            .getDesc());
            }
        });
    }

    protected void doFecthPicture() {
        FetchPhotoManager fetchPhotoManager = new FetchPhotoManager(this, picturePath);
        fetchPhotoManager.doFecthPicture();
    }

    @Override
    protected void initData() {
        if (!TextUtils.isEmpty(userModel.getPic()))
            Glide.with(this).load(userModel.getPic()).into(aImg);
        nameEv.setText(userModel.getName());
        genderTv.setText(userModel.getSexTxt(userModel.getSex()));
        phoneTv.setText(userModel.getTel());

        new HonorListApi(mContext, 2, TempDataHelper.getCurrentChildNo(mContext), new
                FetchEntryListListener() {
                    @Override
                    public void setData(List list) {
                        if (Tools.listNotNull(list)) {
                            giftModels.clear();
                            giftModels.addAll(list);
                            handler.sendEmptyMessage(2);
                        }
                    }

                    @Override
                    public void setError(ErrorMsg error) {

                    }
                });

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:// 重新加载用户信息
                    initData();
                    break;

                case 2:// 更新礼物数据
                    giftAdapter = new GiftAdapter(mContext, giftModels);
                    giftGridView.setAdapter(giftAdapter);
                    break;
                case 3:
                    isNeedChangePic = true;
                    Glide.with(mContext).load(photoFile).into(aImg);
                    break;

            }
        }
    };

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
