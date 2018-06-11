package cn.com.zwwl.bayuwen.util;

import android.content.Context;

import android.text.TextUtils;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.BaseActivity;

/**
 * 工具类
 * Created by Eva. on 16/9/16.
 */
public class AppValue extends Tools {

    /**
     * 判断密码 ，不能少于6位
     *
     * @param str
     * @return
     */

    public static boolean checkPwd(Context c, String str) {
        if (TextUtils.isEmpty(str) || str.length() < 6) {
            showToast(c, R.string.password_length_error);
            return false;
        }
        return true;
    }

    public static List<String> getGradeStrings() {
        List<String> grades = new ArrayList<>();
        grades.add("一年级");
        grades.add("二年级");
        grades.add("三年级");
        grades.add("四年级");
        grades.add("五年级");
        grades.add("六年级");
        grades.add("初一");
        grades.add("初二");
        grades.add("初三");
        grades.add("高一");
        grades.add("高二");
        grades.add("高三");
        return grades;
    }

    /**
     * 判断手机验证码 ，不能少于6位
     *
     * @param str
     * @return
     */

    public static boolean checkCode(Context c, String str) {
        if (TextUtils.isEmpty(str) || str.length() < 6) {
            showToast(c, R.string.veriy_length_error);
            return false;
        }
        return true;
    }


    /**
     * 检查输入的数据是否合法,当前检查数据是否是手机号码
     *
     * @param data 账号
     * @return true:账号格式有效; false:给出相应的错误提示
     */
    public static boolean checkIsPhone(Context context, String data) {

        if (data.length() == 11 && !data.contains("@") && !(Pattern.compile("[a-zA-z]").matcher
                (data).find()))
            return true;
        else {
            showToast(context, R.string.get_account_error);// 手机号码格式错误
            return false;
        }

    }

    /**
     * 显示toast
     *
     * @param context
     * @param resId
     */
    public static void showToast(Context context, int resId) {
        if (context instanceof BaseActivity) {
            ((BaseActivity) context).showToast(resId);
        }
    }

    /**
     * 显示toast
     *
     * @param context
     * @param resStr
     */
    public static void showToast(Context context, String resStr) {
        if (context instanceof BaseActivity) {
            ((BaseActivity) context).showToast(resStr);
        }
    }

}
