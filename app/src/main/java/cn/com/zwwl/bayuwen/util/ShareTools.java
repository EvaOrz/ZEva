package cn.com.zwwl.bayuwen.util;

import android.content.Context;

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
     *
     * @param title
     * @param desc
     * @param pic
     * @param webUrl
     */
    public static void doShareWeb(final BaseActivity context, String title, String desc, String pic, String webUrl) {

        UMWeb web = new UMWeb(webUrl);
        web.setTitle(title);//标题
        UMImage image = new UMImage(context, pic);//网络图片
        UMImage thumb = new UMImage(context, R.mipmap.app_icon);
        image.setThumb(thumb);
        web.setThumb(image);  //缩略图
        web.setDescription(desc);//描述

        new ShareAction(context).withMedia(web).setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
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
