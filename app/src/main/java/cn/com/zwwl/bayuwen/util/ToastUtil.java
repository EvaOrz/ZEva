package cn.com.zwwl.bayuwen.util;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;


public class ToastUtil {
    private static Toast toast;

    private static void longToast(String content) {
        initToast(content, Toast.LENGTH_LONG);
    }

    private static void shortToast(String content) {
        initToast(content, Toast.LENGTH_SHORT);
    }

    public static void setTextColor(int resId) {
        View view = toast.getView();
        if (view != null) {
            TextView message = view.findViewById(R.id.toast_content);
            message.setTextColor(resId);
        }
    }

    private static void initToast(String content, int type) {
        Context context = MyApplication.getInstance();
        if (toast == null) {
            toast = new Toast(context);
        }
        View view = LayoutInflater.from(context).inflate(R.layout.toast_item, null);
        TextView textView = view.findViewById(R.id.toast_content);
        textView.setTextColor(ContextCompat.getColor(context, R.color.white));
        textView.setText(content);
        toast.setView(view);
        toast.setDuration(type);
        toast.setGravity(Gravity.CENTER, Gravity.CENTER, Gravity.CENTER);
        toast.show();
    }

    public static void showShortToast(int resID) {
        shortToast(MyApplication.getInstance().getResources().getString(resID));
    }

    public static void showShortToast(String content) {
        shortToast(content);
    }

    public static void showLongToast(int resID) {
        longToast(MyApplication.getInstance().getResources().getString(resID));
    }

    public static void showLongToast(String content) {
        longToast(content);
    }
}
