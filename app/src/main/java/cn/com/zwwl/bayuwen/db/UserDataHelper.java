package cn.com.zwwl.bayuwen.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import cn.com.zwwl.bayuwen.model.UserModel;

/**
 * 存储用户信息
 */
public class UserDataHelper {
    /**
     * 用户字段
     */
    public static final String UID = "uid";
    public static final String TOKEN = "token";

    public static final String PIC = "pic";
    public static final String PHONE = "phone";
    public static final String SEX = "sex";
    public static final String NAME = "name";
    public static final String LEVEL = "level";
    public static final String SIGNCODE = "sign_code";

    private static SharedPreferences mPref;

    private static SharedPreferences getPref(Context context) {
        if (mPref == null) {
            mPref = PreferenceManager.getDefaultSharedPreferences(context);
        }
        return mPref;
    }

    /**
     * 获取用户token
     *
     * @param context
     * @return
     */
    public static String getUserToken(Context context) {
        return getPref(context).getString(TOKEN, "");
    }

    /**
     * 获取用户id
     *
     * @param context
     * @return
     */
    public static String getUid(Context context) {
        return getPref(context).getString(UID, "");
    }


    /**
     * 存储token
     *
     * @param context
     * @param token
     */
    public static void saveToken(Context context, String token) {
        Editor editor = getPref(context).edit();
        editor.putString(TOKEN, token);
        editor.commit();
    }

    /**
     * 存储登录或者注册后得到的uid、token以及用户名
     *
     * @param context
     * @param user
     */
    public static void saveUserLoginInfo(Context context, UserModel user) {
        Editor editor = getPref(context).edit();
        editor.putString(UID, user.getUid());
//        editor.putString(TOKEN, user.getToken());
        editor.putString(PHONE, user.getTel());
        editor.putString(PIC, user.getPic());
        editor.putInt(LEVEL,user.getLevel());
        editor.putString(NAME, user.getName());
        editor.putInt(SEX, user.getSex());
        editor.putString(SIGNCODE, user.getSignCode());
        editor.commit();
    }

    /**
     * 取得登录或者注册后得到的uid、token以及用户名
     *
     * @param context
     * @return
     */
    public static UserModel getUserLoginInfo(Context context) {
        UserModel user = new UserModel();
        user.setUid(getPref(context).getString(UID, ""));
        user.setToken(getPref(context).getString(TOKEN, ""));
        user.setTel(getPref(context).getString(PHONE, ""));
        user.setPic(getPref(context).getString(PIC, ""));
        user.setSex(getPref(context).getInt(SEX, 0));
        user.setLevel(getPref(context).getInt(LEVEL,0));
        user.setName(getPref(context).getString(NAME, ""));
        user.setSignCode(getPref(context).getString(SIGNCODE, ""));
        if (TextUtils.isEmpty(getPref(context).getString(TOKEN, ""))) return null;
        return user;
    }

    /**
     * 清除登录或者注册后得到的数据
     *
     * @param context
     */
    public static void clearLoginInfo(Context context) {
        Editor editor = getPref(context).edit();
        editor.putString(UID, "");
        editor.putString(TOKEN, "");
        editor.putString(PHONE, "");
        editor.putString(PIC, "");
        editor.putInt(SEX, 0);
        editor.putString(NAME, "");
        editor.putInt(LEVEL,0);
        editor.putString(SIGNCODE, "");
        editor.commit();
    }

    /**
     * 获取推送别名
     *
     * @param context
     * @return
     */
    public static String getPushAlias(Context context) {
        String uid = getPref(context).getString(UID, "");
        if (!TextUtils.isEmpty(uid)) return "zgxt_" + uid;
        return null;
    }
}

