package cn.com.zwwl.bayuwen.activity;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.UploadPicAdapter;
import cn.com.zwwl.bayuwen.api.UploadPicApi;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.base.MenuCode;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.CommonModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 上传照片
 * Created by zhumangmang at 2018/5/30 13:52
 */
public class UploadPicActivity extends BasicActivityWithTitle {
    @BindView(R.id.content)
    AppCompatEditText content;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    ArrayList<AlbumFile> albumFiles;
    UploadPicAdapter uploadPicAdapter;
    AlbumFile file;

    @Override
    protected int setContentView() {
        return R.layout.activity_upload_pic;
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
    }

    @Override
    protected void initData() {
        albumFiles = new ArrayList<>();
        file = new AlbumFile();
        file.setPath("");
        albumFiles.add(file);
        uploadPicAdapter = new UploadPicAdapter(albumFiles);
        recyclerView.setAdapter(uploadPicAdapter);
        showMenu(MenuCode.SUBMIT);
    }

    @Override
    protected void setListener() {

        uploadPicAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.delete:
                        albumFiles.remove(position);
                        uploadPicAdapter.setNewData(albumFiles);
                        break;
                    default:
                        selectImage();
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
                .selectCount(6)
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

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onMenuClick(int menuCode) {
        List<File> files = new ArrayList<>();
        for (AlbumFile albumFile : albumFiles) {
            File f = new File(albumFile.getPath());
                files.add(f);
        }
        new UploadPicApi(this, files, new ResponseCallBack<List<CommonModel>>() {
            @Override
            public void success(List<CommonModel> o) {

            }

            @Override
            public void error(ErrorMsg error) {

            }
        });
    }

    @Override
    public void close() {
        finish();
    }
}
