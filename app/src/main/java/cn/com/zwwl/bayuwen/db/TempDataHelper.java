package cn.com.zwwl.bayuwen.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.util.List;

import cn.com.zwwl.bayuwen.model.UserModel;
import cn.com.zwwl.bayuwen.util.AppValue;
import cn.com.zwwl.bayuwen.util.UmengLogUtil;

/**
 * 存储用户信息
 */
public class TempDataHelper {

    /**
     * 第一次进入应用
     */
    private static final String FIRST_USE = "firststartapp_";

    /**
     * 游客token
     */
    private static final String ACCESS_TOKEN = "access_token";
    /**
     *
     */
    public static final String CITY = "city";

    /**
     * 当前学员信息
     */
    private static final String CURRENT_CHILD_GRADE = "current_child_grade";
    private static final String CURRENT_CHILD_NO = "current_child_no";
    /**
     * 推送开启状态
     */
    private static final String PUSH_STATUS = "push_status";

    private static SharedPreferences mPref;

    private static SharedPreferences getPref(Context context) {
        if (mPref == null) {
            mPref = PreferenceManager.getDefaultSharedPreferences(context);
        }
        return mPref;
    }

    public static int getCurrentChildGrade(Context context) {
        String grade = getPref(context).getString(CURRENT_CHILD_GRADE, "");
        for (int i = 0; i < AppValue.getGradeStrings().size(); i++) {
            if (grade.equals(AppValue.getGradeStrings().get(i))) {
                return i + 1;
            }
        }
        return 0;
    }

    public static void setCurrentChildGrade(Context context, String grade) {
        Editor editor = getPref(context).edit();
        editor.putString(CURRENT_CHILD_GRADE, grade);
        editor.commit();
    }

    /**
     * 存储当前定位城市
     *
     * @param context
     * @param city
     */
    public static void setCurrentCity(Context context, String city) {
        UmengLogUtil.logChangeCity(context);
        Editor editor = getPref(context).edit();
        editor.putString(CITY, city);
        editor.commit();
    }


    /**
     * 获取用户城市
     *
     * @param context
     * @return
     */
    public static String getCurrentCity(Context context) {
        return getPref(context).getString(CITY, null);
    }

    public static String getCurrentChildNo(Context context) {
        return getPref(context).getString(CURRENT_CHILD_NO, "");
    }

    public static void setCurrentChildNo(Context context, String no) {
        Editor editor = getPref(context).edit();
        editor.putString(CURRENT_CHILD_NO, no);
        editor.commit();
    }

    public static String getAccessToken(Context context) {
        return getPref(context).getString(ACCESS_TOKEN, "");
    }

    public static void setAccessToken(Context context, String token) {
        Editor editor = getPref(context).edit();
        editor.putString(ACCESS_TOKEN, token);
        editor.commit();
    }


    /**
     * 是否是第一次进应用
     *
     * @param context
     * @return
     */
    public static boolean getFirstStartApp(Context context) {
        return getPref(context).getBoolean(FIRST_USE, true);
    }

    /**
     * 设置不是第一次进应用
     *
     * @param context
     */
    public static void setFirstStartApp(Context context) {
        Editor editor = getPref(context).edit();
        editor.putBoolean(FIRST_USE, false);
        editor.commit();
    }


    /**
     * 清除登录或者注册后得到的数据
     *
     * @param context
     */
    public static void clearLoginInfo(Context context) {
        Editor editor = getPref(context).edit();
        editor.putString(CURRENT_CHILD_GRADE, "");
        editor.putString(CURRENT_CHILD_NO, "");
        editor.commit();
    }

    /**
     * 设置是否推送
     *
     * @param context
     * @param isNeedPush
     */
    public static void setPushStatus(Context context, boolean isNeedPush) {
        Editor editor = getPref(context).edit();
        editor.putBoolean(PUSH_STATUS, isNeedPush);
        editor.commit();
    }

    /**
     * 获取推送状态
     *
     * @param context
     * @return
     */
    public static boolean getPushStatus(Context context) {
        return getPref(context).getBoolean(PUSH_STATUS, true);
    }

}

