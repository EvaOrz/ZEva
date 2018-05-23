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
import java.util.List;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.api.ChildApi;
import cn.com.zwwl.bayuwen.api.UploadPicApi;
import cn.com.zwwl.bayuwen.api.UserInfoApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ChildModel;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.UserModel;
import cn.com.zwwl.bayuwen.view.DatePopWindow;
import cn.com.zwwl.bayuwen.view.GenderPopWindow;
import cn.com.zwwl.bayuwen.view.NianjiPopWindow;
import cn.com.zwwl.bayuwen.widget.FetchPhotoManager;

/**
 * 编辑学员信息页面
 */
public class ChildInfoActivity extends BaseActivity {
    private String picturePath;// 头像
    private static final String KEY_IMAGE = "data";
    private static final String AVATAR_PIC = "avatar.jpg";
    private File photoFile;
    private ImageView aImg;
    private EditText nameEv, phoneEv;
    private TextView genderTv, birthTv, ruxueTv, nianjiTv;
    private boolean isNeedChangePic = false;
    private boolean isModify = false;// 是否是修改信息

    private ChildModel childModel = new ChildModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_child);
        picturePath = Environment.getExternalStorageDirectory().getPath() + "/" + AVATAR_PIC;
        initView();
        if (getIntent().getSerializableExtra("ChildInfoActivity_data") != null && getIntent()
                .getSerializableExtra("ChildInfoActivity_data") instanceof ChildModel) {
            childModel = (ChildModel) getIntent().getSerializableExtra("ChildInfoActivity_data");
            initData();
            isModify = true;
        }

    }

    private void initView() {
        aImg = findViewById(R.id.info_c_aimg);
        nameEv = findViewById(R.id.info_c_nametv);
        genderTv = findViewById(R.id.info_c_sextv);
        phoneEv = findViewById(R.id.info_c_phoneev);
        birthTv = findViewById(R.id.info_p_birthtv);
        ruxueTv = findViewById(R.id.info_p_ruxuetv);
        nianjiTv = findViewById(R.id.info_c_nianjitv);

        findViewById(R.id.info_c_back).setOnClickListener(this);
        findViewById(R.id.info_c_avatar).setOnClickListener(this);
        findViewById(R.id.info_c_sex).setOnClickListener(this);
        findViewById(R.id.info_c_ruxue).setOnClickListener(this);
        findViewById(R.id.info_c_birth).setOnClickListener(this);
        findViewById(R.id.info_c_nianji).setOnClickListener(this);
        findViewById(R.id.info_c_save).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.info_c_back:
                finish();
                break;
            case R.id.info_c_avatar:
                doFecthPicture();
                break;
            case R.id.info_c_sex:
                new GenderPopWindow(this, new GenderPopWindow.ChooseGenderListener() {
                    @Override
                    public void choose(int gender) {// 1男2女0保密
                        if (gender == 0) {
                            childModel.setGender(2);
                        } else if (gender == 2) {
                            childModel.setGender(0);
                        }
                        handler.sendEmptyMessage(5);
                    }
                });
                break;
            case R.id.info_c_nianji:// 年级
                new NianjiPopWindow(mContext, new NianjiPopWindow.MyNianjiPickListener() {
                    @Override
                    public void onNianjiPick(String string) {
                        childModel.setGrade(string);
                        handler.sendEmptyMessage(4);
                    }
                });
                break;
            case R.id.info_c_birth:// 出生年月
                new DatePopWindow(mContext, new DatePopWindow.MyDatePickListener() {
                    @Override
                    public void onDatePick(int year, int month, int day) {
                        childModel.setBirthday(year + "-" + month + "-" + day);
                        handler.sendEmptyMessage(0);
                    }
                });
                break;

            case R.id.info_c_ruxue:// 入学年月
                new DatePopWindow(mContext, new DatePopWindow.MyDatePickListener() {
                    @Override
                    public void onDatePick(int year, int month, int day) {
                        childModel.setAdmission_time(year + "-" + month + "-" + day);
                        handler.sendEmptyMessage(2);
                    }
                });
                break;
            case R.id.info_c_save:
                String na = nameEv.getText().toString();
                String phone = phoneEv.getText().toString();
                String grade = nianjiTv.getText().toString();

                if (TextUtils.isEmpty(na)) {
                    showToast("姓名不能为空");
                } else if (TextUtils.isEmpty(phone)) {
                    showToast("紧急联系人不能为空");
                } else if (TextUtils.isEmpty(grade)) {
                    showToast("年级不能为空");
                } else {
                    childModel.setName(na);

                    showLoadingDialog(true);
                    if (isNeedChangePic) {
                        uploadPic(photoFile);
                    } else
                        commit();
                }
                break;
        }

    }

    /**
     * 上传头像
     *
     * @param file
     */
    private void uploadPic(File file) {
        new UploadPicApi(this, file, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                if (entry != null && entry instanceof UserModel) {
                    childModel.setPic(((UserModel) entry).getPic());
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
        new ChildApi(this, childModel, isModify, new FetchEntryListListener() {
            @Override
            public void setData(List list) {
                showLoadingDialog(false);
//                if (entry != null && entry instanceof UserModel) {
//                    MyApplication.loginStatusChange = true;
//                    finish();
//                }
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
        if (!TextUtils.isEmpty(childModel.getPic()))
            Glide.with(this).load(childModel.getPic()).into(aImg);
        nameEv.setText(childModel.getName());
        genderTv.setText(childModel.getSexTxt(childModel.getGender()));
        phoneEv.setText(childModel.getTel());
        birthTv.setText(childModel.getBirthday());
        ruxueTv.setText(childModel.getAdmission_time());
        nianjiTv.setText(childModel.getGrade());
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:// 出生年月日
                    birthTv.setText(childModel.getBirthday());
                    break;
                case 1:// 重新加载用户信息
                    initData();
                    break;
                case 2:// 入学年月日
                    ruxueTv.setText(childModel.getAdmission_time());
                    break;
                case 3:
                    isNeedChangePic = true;
                    Glide.with(mContext).load(photoFile).into(aImg);
                    break;
                case 4:// 年级选择
                    nianjiTv.setText(childModel.getGrade());
                    break;
                case 5:// 性别选择
                    genderTv.setText(childModel.getSexTxt(childModel.getGender()));
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
