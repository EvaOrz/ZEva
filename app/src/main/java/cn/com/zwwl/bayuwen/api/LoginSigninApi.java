package cn.com.zwwl.bayuwen.api;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.com.zwwl.bayuwen.db.UserDataHelper;
import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 登录注册接口
 * 只返回token
 */
public class LoginSigninApi extends BaseApi {

    private GetUserType userType;
    private Map<String, String> pamas = new HashMap<>();
    private FetchEntryListener listener;

    public enum GetUserType {
        //普通登录
        LOGIN,
        //快捷登录
        FAST_LOGIN,
        //注册
        REGISTER,
    }

    /**
     * 注册构造
     *
     * @param context
     * @param registerPam1
     * @param registerPam2
     * @param registerPam3
     * @param listener
     */
    public LoginSigninApi(Context context, String registerPam1, String registerPam2, String registerPam3, FetchEntryListener listener) {
        mContext = context;
        this.listener = listener;
        this.userType = GetUserType.REGISTER;
        pamas.put("username", registerPam1);
        pamas.put("password", registerPam2);
        pamas.put("code", registerPam3);

        post();

    }

    /**
     * 登录构造
     *
     * @param context
     * @param userType
     * @param loginPam1
     * @param loginPam2
     * @param listener
     */
    public LoginSigninApi(Context context, GetUserType userType, String loginPam1, String loginPam2, FetchEntryListener listener) {
        mContext = context;
        this.listener = listener;
        this.userType = userType;
        if (userType.equals(GetUserType.LOGIN)) {
            pamas.put("username", loginPam1);
            pamas.put("password", loginPam2);
        } else if (userType.equals(GetUserType.FAST_LOGIN)) {
            pamas.put("phone", loginPam1);
            pamas.put("code", loginPam2);
        }
        post();
    }

    @Override
    protected Map<String, String> getPostParams() {
        return pamas;
    }

    @Override
    protected void handler(JSONObject jsonObject, ErrorMsg errorMsg) {
        if (errorMsg == null) {
            ErrorMsg e = new ErrorMsg();

            if (jsonObject.optBoolean("success", false)) {
                JSONObject data = jsonObject.optJSONObject("data");
                if (!isNull(data)) {
                    String token = data.optString("token");
                    if (!TextUtils.isEmpty(token)) {
                        e.setNo(0);
                        UserDataHelper.saveToken(mContext, token);
                    }
                }
            } else {
                e.setDesc(jsonObject.optString("data"));
            }
            listener.setData(e);

        } else {
            listener.setData(errorMsg);
        }

    }

    @Override
    protected String getHeadValue() {
        return UserDataHelper.getUserToken(mContext);
    }

    @Override
    protected String getUrl() {
        switch (userType) {
            case LOGIN:
                return UrlUtil.LoginUrl();
            case FAST_LOGIN:
                return UrlUtil.smsloginUrl();
            case REGISTER:
                return UrlUtil.registerUrl();
            default:
                return "";
        }

    }
}
