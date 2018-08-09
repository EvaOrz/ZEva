package cn.com.zwwl.bayuwen.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;

import java.util.List;

import cn.com.zwwl.bayuwen.R;

public class UploadPicAdapter extends BaseQuickAdapter<AlbumFile, BaseViewHolder> {
    public UploadPicAdapter(@Nullable List<AlbumFile> data) {
        super(R.layout.item_upload_pic, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AlbumFile item) {
        if (helper.getLayoutPosition() == mData.size() - 1) {
            helper.setVisible(R.id.delete, false);
            helper.setImageResource(R.id.pic, R.mipmap.icon_add_pic);
        } else {
            helper.setVisible(R.id.delete, true);
            Album.getAlbumConfig().
                    getAlbumLoader().
                    load((AppCompatImageView) helper.getView(R.id.pic), item);
        }
        helper.addOnClickListener(R.id.delete);
        helper.addOnClickListener(R.id.pic);
    }
}
