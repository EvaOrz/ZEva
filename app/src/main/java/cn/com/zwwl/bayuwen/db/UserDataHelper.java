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
    public static final String PROVINCE = "province";
    public static final String CITY = "city";
    public static final String AREA = "area";
    public static final String PIC = "pic";
    public static final String PHONE = "phone";
    public static final String SEX = "sex";
    public static final String NAME = "name";

    /**
     * 第一次进入应用
     */
    private static final String FIRST_USE = "firststartapp_";

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
     * 获取用户城市
     *
     * @param context
     * @return
     */
    public static String getCity(Context context) {
        return getPref(context).getString(CITY, "");
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
        editor.putInt(PROVINCE, user.getProvince());
        editor.putInt(CITY, user.getCity());
        editor.putInt(AREA, user.getArea());
        editor.putString(PIC, user.getPic());
        editor.putString(NAME, user.getName());
        editor.putInt(SEX, user.getSex());
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
        user.setProvince(getPref(context).getInt(PROVINCE, 0));
        user.setCity(getPref(context).getInt(CITY, 0));
        user.setArea(getPref(context).getInt(AREA, 0));
        user.setTel(getPref(context).getString(PHONE, ""));
        user.setPic(getPref(context).getString(PIC, ""));
        user.setSex(getPref(context).getInt(SEX, 0));
        user.setName(getPref(context).getString(NAME, ""));
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
        editor.putInt(PROVINCE, 0);
        editor.putInt(CITY, 0);
        editor.putInt(AREA, 0);
        editor.putString(PIC, "");
        editor.putInt(SEX, 0);
        editor.putString(NAME, "");
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
}

