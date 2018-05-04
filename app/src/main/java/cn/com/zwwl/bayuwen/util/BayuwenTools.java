package cn.com.zwwl.bayuwen.util;

import android.content.Context;

import android.text.TextUtils;

import java.util.regex.Pattern;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.BaseActivity;

/**
 * 工具类
 * Created by Eva. on 16/9/16.
 */
public class BayuwenTools extends Tools {
    public static final int STROKE = 2;// 边框宽度

    public static final int REQUEST_ZOOM = 111;


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

        if (data.length() == 11 && !data.contains("@") && !(Pattern.compile("[a-zA-z]").matcher(data).find()))
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
