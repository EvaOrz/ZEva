package cn.com.zwwl.bayuwen.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.BaseActivity;

public class ShareTools {


    /**
     * 分享
     */
    public static void doSharePic(final BaseActivity context, Bitmap picPath) {

        UMImage image = new UMImage(context, picPath);
        image.compressStyle = UMImage.CompressStyle.QUALITY;

        new ShareAction(context).withMedia(image).setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ,
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    /**
                     * @descrption 分享成功的回调
                     * @param share_media 平台类型
                     */
                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        context.showToast(R.string.share_success);


                    }

                    /**
                     * @descrption 分享失败的回调
                     * @param share_media 平台类型
                     * @param throwable 错误原因
                     */
                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {

                        context.showToast(R.string.share_faile);


                    }

                    /**
                     * @descrption 分享取消的回调
                     * @param share_media 平台类型
                     */
                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        context.showToast(R.string.share_cancle);
                    }
                }).open();
    }

    /**
     * 分享不带图片的链接
     *
     * @param context
     * @param title
     * @param url
     * @param desc
     */
    public static void doShareWeb(final BaseActivity context, String title, String url, String
            desc) {
        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        UMImage image = new UMImage(context, R.mipmap.ic_launcher);//资源文件
        web.setThumb(image);  //缩略图
        web.setDescription(desc);//描述
        new ShareAction(context).withMedia(web).setDisplayList(SHARE_MEDIA
                        .SINA, SHARE_MEDIA.QQ,
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    /**
                     * @descrption 分享成功的回调
                     * @param share_media 平台类型
                     */
                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        context.showToast(R.string.share_success);


                    }

                    /**
                     * @descrption 分享失败的回调
                     * @param share_media 平台类型
                     * @param throwable 错误原因
                     */
                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {

                        context.showToast(R.string.share_faile);


                    }

                    /**
                     * @descrption 分享取消的回调
                     * @param share_media 平台类型
                     */
                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        context.showToast(R.string.share_cancle);
                    }
                }).open();
    }

    /**
     * 分享文本
     *
     * @param context
     * @param content
     */
    public static void doShareText(final BaseActivity context, String content) {
        UMImage image = new UMImage(context, R.mipmap.ic_launcher);//资源文件
        new ShareAction(context).withText(content).setDisplayList(SHARE_MEDIA
                        .SINA, SHARE_MEDIA.QQ,
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    /**
                     * @descrption 分享成功的回调
                     * @param share_media 平台类型
                     */
                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        context.showToast(R.string.share_success);


                    }

                    /**
                     * @descrption 分享失败的回调
                     * @param share_media 平台类型
                     * @param throwable 错误原因
                     */
                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {

                        context.showToast(R.string.share_faile);


                    }

                    /**
                     * @descrption 分享取消的回调
                     * @param share_media 平台类型
                     */
                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        context.showToast(R.string.share_cancle);
                    }
                }).open();
    }

    /**
     * 分享带图片的链接
     *
     * @param context
     * @param title
     * @param url
     * @param desc
     */
    public static void doShareWebWithPic(final BaseActivity context,String pic, String title, String url, String
            desc) {
        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        UMImage image = new UMImage(context, pic);//资源文件
        web.setThumb(image);  //缩略图
        web.setDescription(desc);//描述
        new ShareAction(context).withMedia(web).setDisplayList(SHARE_MEDIA
                        .SINA, SHARE_MEDIA.QQ,
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    /**
                     * @descrption 分享成功的回调
                     * @param share_media 平台类型
                     */
                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        context.showToast(R.string.share_success);


                    }

                    /**
                     * @descrption 分享失败的回调
                     * @param share_media 平台类型
                     * @param throwable 错误原因
                     */
                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {

                        context.showToast(R.string.share_faile);


                    }

                    /**
                     * @descrption 分享取消的回调
                     * @param share_media 平台类型
                     */
                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        context.showToast(R.string.share_cancle);
                    }
                }).open();
    }



}
