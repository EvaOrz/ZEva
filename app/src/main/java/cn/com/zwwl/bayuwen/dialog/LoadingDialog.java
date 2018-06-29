package cn.com.zwwl.bayuwen.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import cn.com.zwwl.bayuwen.R;


/**
 *  进度对话框
 *  Created by zhumangmang at 2018/6/28 19:08
 */
public class LoadingDialog extends Dialog {
    private TextView content;

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.LoadingDialogStyle);
        setContentView(R.layout.dialog_loading);
        content = findViewById(R.id.content);
    }

    public void setContent(String text) {
        content.setText(text);
    }
    public void setContent(int resId) {
       setContent(getContext().getString(resId));
    }
}
