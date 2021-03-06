package cn.com.zwwl.bayuwen.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.com.zwwl.bayuwen.R;

/**
 * 询问确定、取消dialog
 */
public class AskDialog implements View.OnClickListener {
    private Context mContext;
    private Dialog mDialog;
    private Window window;
    private String sureTxt, cancleTxt;
    private OnSurePickListener onSurePickListener;

    public AskDialog(Context context, String content, OnSurePickListener
            onSurePickListener) {
        this.mContext = context;
        this.onSurePickListener = onSurePickListener;
        init(content);
    }

    public AskDialog(Context context, String sureText, String cacleText, String content,
                     OnSurePickListener
                             onSurePickListener) {
        this.mContext = context;
        this.onSurePickListener = onSurePickListener;
        sureTxt = sureText;
        cancleTxt = cacleText;
        init(content);
    }

    private void init(String cString) {
        mDialog = new Dialog(mContext, R.style.CustomDialog);
        mDialog.show();
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(false);// 屏蔽dialog外焦点，弹出键盘
        window = mDialog.getWindow();
        window.setContentView(R.layout.dialog_ask_sure);

        mDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout
                .LayoutParams.MATCH_PARENT);

        TextView title = window.findViewById(R.id.motify_sign_title);
        TextView sureBt = window.findViewById(R.id.motify_sign_sure);
        TextView cancleBt = window.findViewById(R.id.motify_sign_cancle);
        if (!TextUtils.isEmpty(sureTxt) && !TextUtils.isEmpty(cancleTxt)) {
            sureBt.setText(sureTxt);
            cancleBt.setText(cancleTxt);
        }
        TextView content = window.findViewById(R.id.motify_sign_content);
        content.setText(cString);

        window.findViewById(R.id.motify_sign_sure).setOnClickListener(this);
        window.findViewById(R.id.motify_sign_cancle).setOnClickListener(this);

    }

    public interface OnSurePickListener {
        public void onSure();

        public void onCancle();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.motify_sign_sure:
                if (onSurePickListener != null) {
                    onSurePickListener.onSure();
                }
                break;
            case R.id.motify_sign_cancle:
                if (onSurePickListener != null) {
                    onSurePickListener.onCancle();
                }
                break;
        }
        mDialog.cancel();
    }


}
