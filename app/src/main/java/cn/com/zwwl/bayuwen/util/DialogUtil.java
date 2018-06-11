package cn.com.zwwl.bayuwen.util;


import android.app.Activity;
import android.content.DialogInterface.OnClickListener;
import android.support.v7.app.AlertDialog;

import cn.com.zwwl.bayuwen.R;


/**
 * 对话框工具类
 * Created by zhumangmang at 2018/6/11 17:45
 */
public class DialogUtil {
    public static AlertDialog showSingleDialog(Activity activity, int title, int message, OnClickListener positive) {
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.confirm, positive)
                .create();
        dialog.show();
        return dialog;
    }

    public static AlertDialog showSingleDialog(Activity activity, String title, String message, OnClickListener positive) {
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.confirm, positive)
                .create();
        dialog.show();
        return dialog;
    }

    public static AlertDialog showDoubleDialog(Activity activity, String title, String message, int positiveText, int negativeText, OnClickListener positive, OnClickListener negative) {
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(positiveText, positive)
                .setNegativeButton(negativeText, negative)
                .create();
        dialog.show();
        return dialog;
    }

    public static AlertDialog showDoubleDialog(Activity activity, int title, int message, int positiveText, int negativeText, OnClickListener positive, OnClickListener negative) {
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(positiveText, positive)
                .setNegativeButton(negativeText, negative)
                .create();
        dialog.show();
        return dialog;
    }

    public static AlertDialog showMultiDialog(Activity activity, int title, int message, int positiveText, int negativeText, OnClickListener positive, OnClickListener negative, OnClickListener neutral) {
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(positiveText, positive)
                .setNegativeButton(negativeText, negative)
                .setNeutralButton("取消", neutral)
                .create();
        dialog.show();
        return dialog;
    }

    public static AlertDialog showDoubleDialog(Activity activity, int title, int message, OnClickListener positive, OnClickListener negative) {
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.confirm, positive)
                .setNegativeButton(R.string.cancel, negative)
                .create();
        dialog.show();
        return dialog;
    }
}
