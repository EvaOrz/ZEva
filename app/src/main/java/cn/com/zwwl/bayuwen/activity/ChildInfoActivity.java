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

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.model.UserModel;
import cn.com.zwwl.bayuwen.view.DatePopWindow;
import cn.com.zwwl.bayuwen.view.GenderPopWindow;
import cn.com.zwwl.bayuwen.view.NianjiPopWindow;
import cn.com.zwwl.bayuwen.widget.FetchPhotoManager;

/**
 * 编辑家长信息页面
 */
public class ChildInfoActivity extends BaseActivity {
    private UserModel userModel;
    private String picturePath;// 头像
    private static final String KEY_IMAGE = "data";
    private static final String AVATAR_PIC = "avatar.jpg";
    private File photoFile;
    private ImageView aImg;
    private EditText nameEv, phoneEv;
    private TextView genderTv, birthTv, ruxueTv, nianjiTv;
    private boolean isNeedChangePic = false;
    private String birthTxt, ruxueTxt, nianjiTxt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_child);
        picturePath = Environment.getExternalStorageDirectory().getPath() + "/" + AVATAR_PIC;
        initView();
//        userModel = UserDataHelper.getUserLoginInfo(this);
        initData();
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
                    public void choose(int gender) {
                        userModel.setSex(gender);
                        handler.sendEmptyMessage(1);
                    }
                });
                break;
            case R.id.info_c_nianji:// 年级
                new NianjiPopWindow(mContext, new NianjiPopWindow.MyNianjiPickListener() {
                    @Override
                    public void onNianjiPick(int nianji, String string) {
                        nianjiTxt = string;
                        handler.sendEmptyMessage(4);
                    }
                });
                break;
            case R.id.info_c_birth:// 出生年月
                new DatePopWindow(mContext, new DatePopWindow.MyDatePickListener() {
                    @Override
                    public void onDatePick(int year, int month, int day) {
                        birthTxt = year + "年" + month + "月" + day + "日";
                        handler.sendEmptyMessage(0);
                    }
                });
                break;

            case R.id.info_c_ruxue:// 入学年月
                new DatePopWindow(mContext, new DatePopWindow.MyDatePickListener() {
                    @Override
                    public void onDatePick(int year, int month, int day) {
                        ruxueTxt = year + "年" + month + "月" + day + "日";
                        handler.sendEmptyMessage(2);
                    }
                });
                break;

            case R.id.info_c_save:
                break;
        }

    }

    protected void doFecthPicture() {
        FetchPhotoManager fetchPhotoManager = new FetchPhotoManager(this, picturePath);
        fetchPhotoManager.doFecthPicture();
    }

    @Override
    protected void initData() {
        if (userModel == null) return;
        if (!TextUtils.isEmpty(userModel.getPic()))
            Glide.with(this).load(userModel.getPic()).into(aImg);
        nameEv.setText(userModel.getName());
        genderTv.setText(userModel.getSexTxt(userModel.getSex()));
        phoneEv.setText(userModel.getTel());
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:// 出生年月日
                    birthTv.setText(birthTxt);
                    break;
                case 1:// 重新加载用户信息
                    initData();
                    break;
                case 2:// 入学年月日
                    ruxueTv.setText(ruxueTxt);
                    break;
                case 3:
                    isNeedChangePic = true;
                    Glide.with(mContext).load(photoFile).into(aImg);
                    break;
                case 4:// 年级选择
                    nianjiTv.setText(nianjiTxt);
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
