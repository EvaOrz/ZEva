package cn.com.zwwl.bayuwen.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.BaseActivity;
import cn.com.zwwl.bayuwen.util.ShareTools;


/**
 * Created by Eva. on 2017/9/7.
 */

public class SharePopWindow extends PopupWindow {
    private Context mContext;
    private View conentView;
    private PopupWindow window;
    private int type = 0;// 1：邮件；2：微信；3:朋友圈；4：微博；5：qq
    private String content;

    private void init() {
        conentView = LayoutInflater.from(mContext).inflate(R.layout.view_share_dialog, null);
        conentView.findViewById(R.id.share_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                window.dismiss();
            }
        });
        window = new PopupWindow(conentView, RelativeLayout.LayoutParams.FILL_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.setAnimationStyle(R.style.fetch_image_popup_anim);
        window.update();
        window.setBackgroundDrawable(new BitmapDrawable());
        window.showAtLocation(conentView, Gravity.BOTTOM, 0, 0);
        window.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                switch (type) {
                    case 1:

                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:

                        break;
                    case 5:


                        break;
                }
            }
        });
    }


    /**
     * 分享文章
     *
     * @param context
     */
    public SharePopWindow(final Context context, final String content, String url) {
        mContext = context;
        this.content = content;
        init();

        // 微信分享
        conentView.findViewById(R.id.share_weixin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 2;
                window.dismiss();
            }
        });
        conentView.findViewById(R.id.share_pengyouquan).setOnClickListener(new View
                .OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 3;
                window.dismiss();
            }
        });
        // 微博分享
        conentView.findViewById(R.id.share_sina).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 4;
                window.dismiss();
            }
        });
        //qq分享
        conentView.findViewById(R.id.share_qq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 5;
                window.dismiss();
            }
        });
    }

    public void showViewFromBo(View v) {
        if (this.isShowing()) {
            dismiss();
        } else {

            this.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        }
    }

    /**
     * 显示 隐藏
     */
    public void showView(View v) {
        if (this.isShowing()) {
            dismiss();
        } else {

            this.showAsDropDown(v);
        }
    }
}