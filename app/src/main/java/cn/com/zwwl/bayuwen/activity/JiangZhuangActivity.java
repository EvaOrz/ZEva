package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.Context;
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

import java.io.File;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.api.HonorActionApi;
import cn.com.zwwl.bayuwen.api.UploadPicApi;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.CommonModel;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.GiftAndJiangModel;
import cn.com.zwwl.bayuwen.view.DatePopWindow;
import cn.com.zwwl.bayuwen.widget.FetchPhotoManager;

/**
 * 奖状编辑页面
 */
public class JiangZhuangActivity extends BaseActivity {

    private String picturePath;// 头像
    private static final String AVATAR_PIC = "jiangzhuang.jpg";

    private File photoFile;
    private GiftAndJiangModel giftAndJiangModel;

    private ImageView pic, takeBt, deleteBt;
    private EditText name;
    private TextView time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        picturePath = Environment.getExternalStorageDirectory().getPath() + "/" + AVATAR_PIC;
        setContentView(R.layout.activity_jiangzhuang);
        if (getIntent().getSerializableExtra("JiangZhuangActivity_data") != null) {
            giftAndJiangModel = (GiftAndJiangModel) getIntent().getSerializableExtra
                    ("JiangZhuangActivity_data");
        }
        initView();

    }

    private void initView() {
        pic = findViewById(R.id.jiang_pic);
        takeBt = findViewById(R.id.jiang_bt1);
        deleteBt = findViewById(R.id.jiang_bt2);
        name = findViewById(R.id.jiang_name);
        time = findViewById(R.id.jiang_time);
        findViewById(R.id.jiangzhuang_back).setOnClickListener(this);
        findViewById(R.id.jiangzhuang_save).setOnClickListener(this);
        takeBt.setOnClickListener(this);
        deleteBt.setOnClickListener(this);
        time.setOnClickListener(this);

        if (giftAndJiangModel.getId() != -1) {
            deleteBt.setVisibility(View.GONE);
            name.setText(giftAndJiangModel.getTitle());
            time.setText(giftAndJiangModel.getDate());
            ImageLoader.display(mContext, pic, giftAndJiangModel.getPic(), null, null);
        }
    }

    private void doDelete() {
        showLoadingDialog(true);
        new HonorActionApi(mContext, giftAndJiangModel.getId() + "", new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {

            }

            @Override
            public void setError(ErrorMsg error) {
                showLoadingDialog(false);
                if (error == null) {
                    finish();
                } else {
                    showToast(error.getDesc());
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.jiangzhuang_back:
                finish();
                break;
            case R.id.jiangzhuang_save:
                String title = name.getText().toString();
                if (TextUtils.isEmpty(giftAndJiangModel.getPic())) {
                    showToast("请上传奖状图片");
                } else if (TextUtils.isEmpty(title)) {
                    showToast("请上传奖状名称");
                } else if (TextUtils.isEmpty(giftAndJiangModel.getDate())) {
                    showToast("请上传获奖时间");
                } else
                    giftAndJiangModel.setTitle(title);
                doSave();
                break;
            case R.id.jiang_time:
                hideJianpan();
                new DatePopWindow(mContext, new DatePopWindow.MyDatePickListener() {
                    @Override
                    public void onDatePick(int year, int month, int day) {
                        giftAndJiangModel.setDate(year + "-" + month + "-" + day);
                        handler.sendEmptyMessage(1);
                    }
                });
                break;
            case R.id.jiang_bt1:
                doFecthPicture();
                break;
            case R.id.jiang_bt2:
                doDelete();
                break;
        }
    }

    private void doSave() {
        showLoadingDialog(true);
        if (giftAndJiangModel.getId() == -1) {
            new HonorActionApi(mContext, 1, giftAndJiangModel, new FetchEntryListener() {
                @Override
                public void setData(Entry entry) {

                }

                @Override
                public void setError(ErrorMsg error) {
                    showLoadingDialog(false);
                    if (error == null) {
                        finish();
                    } else {
                        showToast(error.getDesc());
                    }
                }
            });
        } else {
            new HonorActionApi(mContext, giftAndJiangModel, new FetchEntryListener() {
                @Override
                public void setData(Entry entry) {

                }

                @Override
                public void setError(ErrorMsg error) {
                    showLoadingDialog(false);
                    if (error == null) {
                        finish();
                    } else {
                        showToast(error.getDesc());
                    }
                }
            });
        }
    }

    protected void doFecthPicture() {
        FetchPhotoManager fetchPhotoManager = new FetchPhotoManager(this, picturePath);
        fetchPhotoManager.doFecthPicture();
    }

    @Override
    protected void initData() {

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
                if (entry != null && entry instanceof CommonModel) {
                    String pic = ((CommonModel) entry).getUrl();
                    giftAndJiangModel.setPic(pic);
                    handler.sendEmptyMessage(0);
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

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    ImageLoader.display(mContext, pic, giftAndJiangModel.getPic(), null, null);
                    break;
                case 1:// 更新时间
                    time.setText(giftAndJiangModel.getDate());
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == FetchPhotoManager.REQUEST_CAMERA) {
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
            uploadPic(photoFile);

        } else if (resultCode == RESULT_CANCELED) {
            showToast(R.string.cancle);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}