package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CheckScrollAdapter;
import cn.com.zwwl.bayuwen.api.UploadPicApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.GiftAndJiangModel;
import cn.com.zwwl.bayuwen.model.UserModel;
import cn.com.zwwl.bayuwen.widget.FetchPhotoManager;
import cn.com.zwwl.bayuwen.widget.ViewHolder;

/**
 * 奖状编辑页面
 */
public class JiangZhuangActivity extends BaseActivity {

    private String picturePath;// 头像
    private static final String KEY_IMAGE = "data";
    private static final String AVATAR_PIC = "jiangzhuang.jpg";

    private File photoFile;
    private GiftAndJiangModel giftAndJiangModel;
    private ImageView pic, takeBt, deleteBt;
    private EditText name, time;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiangzhuang);
        if (getIntent().getSerializableExtra("JiangZhuangActivity_data") != null) {
            giftAndJiangModel = (GiftAndJiangModel) getIntent().getSerializableExtra
                    ("JiangZhuangActivity_data");
        }
        initView();

    }

    private void initView() {
        findViewById(R.id.jiangzhuang_back).setOnClickListener(this);
        pic = findViewById(R.id.jiang_img);
        takeBt = findViewById(R.id.jiang_bt1);
        deleteBt = findViewById(R.id.jiang_bt2);
        name = findViewById(R.id.jiang_name);
        time = findViewById(R.id.jiang_time);

        if (giftAndJiangModel != null) {

        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.jiangzhuang_back:
                finish();
                break;

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
                if (entry != null && entry instanceof UserModel) {
                    String pic = ((UserModel) entry).getPic();
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