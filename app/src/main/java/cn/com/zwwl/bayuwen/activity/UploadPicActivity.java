package cn.com.zwwl.bayuwen.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.UploadPicAdapter;
import cn.com.zwwl.bayuwen.api.UploadPicApi;
import cn.com.zwwl.bayuwen.api.UploadWorkApi;
import cn.com.zwwl.bayuwen.base.MenuCode;
import cn.com.zwwl.bayuwen.dialog.LoadingDialog;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.CommonModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.util.ToastUtil;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.widget.decoration.GridItemDecoration;

/**
 * 上传照片
 * Created by zhumangmang at 2018/5/30 13:52
 */
public class UploadPicActivity extends BaseActivity {
    @BindView(R.id.content)
    AppCompatEditText content;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    ArrayList<AlbumFile> albumFiles;
    UploadPicAdapter uploadPicAdapter;
    AlbumFile file;
    String kid, cid;
    StringBuilder urls;
    LoadingDialog loadingDialog;
    @BindView(R.id.id_back)
    ImageView idBack;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.right_title)
    TextView rightTitle;
    private HashMap<String, String> map;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pic);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        map = new HashMap<>();
        mActivity = this;
        initView1();
        initData1();
        setListener1();
    }

    @Override
    protected void initData() {

    }


    protected void initView1() {
        titleName.setText("作业上传");
        rightTitle.setVisibility(View.VISIBLE);
        rightTitle.setText("提交");
        ConstraintLayout.LayoutParams linearParams = (ConstraintLayout.LayoutParams) content.getLayoutParams();
        linearParams.height = MyApplication.height / 2;
        content.setLayoutParams(linearParams);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.addItemDecoration(new GridItemDecoration(this));
    }

    protected void initData1() {
        loadingDialog = new LoadingDialog(this);
        loadingDialog.setContent("正在上传");
        urls = new StringBuilder();
        kid = getIntent().getStringExtra("kid");
        cid = getIntent().getStringExtra("cid");
        albumFiles = new ArrayList<>();
        file = new AlbumFile();
        file.setPath("");
        albumFiles.add(file);
        uploadPicAdapter = new UploadPicAdapter(albumFiles);
        recyclerView.setAdapter(uploadPicAdapter);
//        showMenu(MenuCode.SUBMIT);
    }

    protected void setListener1() {

        uploadPicAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.delete:
                        albumFiles.remove(position);
                        uploadPicAdapter.setNewData(albumFiles);
                        break;
                    default:
                        if (albumFiles.size() == 6) {
                            ToastUtil.showShortToast("最多允许上传五张图片");
                        } else {
                            selectImage();
                        }
                        break;
                }
            }
        });
    }

    private void selectImage() {
        albumFiles.remove(file);
        Album.image(this)
                .multipleChoice()
                .camera(true)
                .columnCount(2)
                .selectCount(5)
                .checkedList(albumFiles)
                .widget(
                        Widget.newDarkBuilder(this)
                                .title("选择图片").toolBarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                                .build()
                )
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        albumFiles = result;
                        albumFiles.add(file);
                        uploadPicAdapter.setNewData(albumFiles);
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                        albumFiles.add(file);
                        uploadPicAdapter.setNewData(albumFiles);
                    }
                })
                .start();
    }

    @OnClick({R.id.right_title, R.id.id_back})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.right_title:
                if ((albumFiles == null || albumFiles.size() == 1) && TextUtils.isEmpty(Tools.getText(content))) {
                    ToastUtil.showShortToast("请先输入文字作业或上传作业图片");
                    return;
                }
                if (albumFiles != null && albumFiles.size() > 0) {
                    List<File> files = new ArrayList<>();
                    for (AlbumFile albumFile : albumFiles) {
                        File f = new File(albumFile.getPath());
                        files.add(f);
                    }
                    loadingDialog.show();
                    new UploadPicApi(this, files, new ResponseCallBack<List<CommonModel>>() {
                        @Override
                        public void result(List<CommonModel> commonModels, ErrorMsg errorMsg) {
                            if (commonModels != null && commonModels.size() > 0) {
                                urls.setLength(0);
                                for (CommonModel c : commonModels) {
                                    urls.append(c.getUrl()).append(",");
                                }
                                uploadWork();
                            } else {
                                loadingDialog.dismiss();
                                ToastUtil.showShortToast("上传失败");
                            }
                        }
                    });
                } else {
                    urls.setLength(0);
                    loadingDialog.show();
                    uploadWork();
                }
                break;
            case R.id.id_back:
                finish();
                break;
        }

    }

//    @Override
//    public void onMenuClick(int menuCode) {
//
//
//    }

    private void uploadWork() {
        map.clear();
        if (urls.length() > 0)
            map.put("url", urls.toString());
        map.put("kid", kid);
        map.put("cid", cid);
        map.put("state", "1");
        map.put("status", "1");
        map.put("id", "");
        map.put("content", Tools.getText(content));
        new UploadWorkApi(this, map, new ResponseCallBack<CommonModel>() {
            @Override
            public void result(CommonModel commonModel, ErrorMsg errorMsg) {
                loadingDialog.dismiss();
                if (errorMsg != null) {
                    ToastUtil.showShortToast(errorMsg.getDesc());
                } else {
                    finish();
                }
            }
        });
    }


//
//    @Override
//    public boolean setParentScrollable() {
//        return true;
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
}
