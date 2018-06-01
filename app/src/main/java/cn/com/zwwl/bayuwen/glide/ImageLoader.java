package cn.com.zwwl.bayuwen.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import cn.com.zwwl.bayuwen.R;


/**
* I图片加载工具类 使用glide框架封装
* Create by zhumangmang at 2018/5/26 10:44
*/
public class ImageLoader {
    /**
     * 加载图片
     *
     * @param context     当前上下文
     * @param imageView   图片容器控件
     * @param url         图片地址
     * @param placeholder 占位图
     * @param error       加载失败时的图片
     */
    public static void display(Context context, ImageView imageView, String url, int placeholder, int error) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        GlideApp.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(placeholder)
                .error(error)
                .centerCrop()
                .into(imageView);
    }

    /**
     * 加载图片
     *
     * @param context     当前上下文
     * @param imageView   图片容器控件
     * @param url         图片地址
     * @param placeholder 占位图
     * @param error       加载失败时的图片
     */
    public static void display(Context context, ImageView imageView, String url, Drawable placeholder, Drawable error) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        GlideApp.with(context)
                .load(url)
                .placeholder(placeholder)
                .error(error)
                .into(imageView);
    }

    /**
     * 显示图片
     *
     * @param context   上下文
     * @param imageView 容器控件
     * @param url       图片地址
     */
    public static void display(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        GlideApp.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
    }


    /**
     * 缩小图片
     *
     * @param context        当前上下文
     * @param imageView      图片容器
     * @param url            图片地址
     * @param sizeMultiplier 比例(0-1)
     */
    public static void display(Context context, ImageView imageView, String url, float sizeMultiplier) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        GlideApp.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .centerCrop()
                .thumbnail(sizeMultiplier)
                .into(imageView);
    }

    /**
     * 根据资源ID显示图片
     *
     * @param context   当前上下文
     * @param imageView 容器控件
     * @param resId     资源ID
     */
    public static void display(Context context, ImageView imageView, int resId) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        GlideApp.with(context).load(resId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop().into(imageView);

    }
    /**
     * 显示图片
     *
     * @param context   上下文
     * @param imageView 容器控件
     * @param url       图片地址
     */
    public static void displayBorderCircle(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        GlideApp.with(context).asBitmap()
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideRoundTransform(context))
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
    }

}
