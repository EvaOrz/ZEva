package cn.com.zwwl.bayuwen.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;

import java.io.File;
import java.io.IOException;

import cn.com.zwwl.bayuwen.R;


/**
 * 获取图片
 */
public class FetchPhotoManager implements OnClickListener {
    public static final int REQUEST_CAMERA = 100;
    public static final int REQUEST_GALLERY = 101;

    private Context mContext;
    private PopupWindow window;
    private String picturePath;

    public FetchPhotoManager(Context context, String picturePath) {
        mContext = context;
        this.picturePath = picturePath;
    }

    /**
     * 获取图片作为头像
     */
    public void doFecthPicture() {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.pop_camera, null);
        window = new PopupWindow(view, LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT);
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.setAnimationStyle(R.style.fetch_image_popup_anim);
        window.update();
        window.setBackgroundDrawable(new BitmapDrawable());
        window.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        view.findViewById(R.id.fetch_image_from_camear)
                .setOnClickListener(this);
        view.findViewById(R.id.fetch_image_from_gallery).setOnClickListener(
                this);
        view.findViewById(R.id.fetch_image_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.fetch_image_from_camear) {

            File outputImage = new File(picturePath);
            try {
                if (outputImage.exists()) {
                    outputImage.delete();
                }
                outputImage.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Uri photoUri = null;
            if (Build.VERSION.SDK_INT >= 24) {
                photoUri = FileProvider.getUriForFile(mContext,mContext.getPackageName() + ".fileprovider",outputImage);
            }else {
                photoUri = Uri.fromFile(outputImage);
            }
            //启动相机程序
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
            ((Activity) mContext).startActivityForResult(intent, REQUEST_CAMERA);
            window.dismiss();

        } else if (id == R.id.fetch_image_from_gallery) {
            window.dismiss();
            Intent intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            ((Activity) mContext).startActivityForResult(intent,
                    REQUEST_GALLERY);
        } else if (id == R.id.fetch_image_cancel) {
            window.dismiss();
        }
    }

}
