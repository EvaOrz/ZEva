package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.com.zwwl.bayuwen.db.UserDataHelper;
import cn.com.zwwl.bayuwen.model.UserModel;
import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 获取用户信息接口
 */
public class UserInfoApi extends BaseApi {
    private Map<String, String> pamas = new HashMap<>();
    private FetchEntryListener listener;
    private String url;

    /**
     * 修改用户信息
     *
     * @param context
     * @param userModel
     * @param listener
     */
    public UserInfoApi(Context context, UserModel userModel, FetchEntryListener listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        this.url = UrlUtil.changeInfoUrl(UserDataHelper.getUid(context));
        pamas.put("name", userModel.getName());
        pamas.put("sex", userModel.getSex() + "");
        pamas.put("pic", userModel.getPic());
        patch();

    }


    /**
     * 获取用户信息构造
     *
     * @param context
     * @param listener
     */
    public UserInfoApi(Context context, FetchEntryListener listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        this.url = UrlUtil.userInfoUrl();
        get();
    }

    @Override
    protected Map<String, String> getPostParams() {
        return pamas;
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        listener.setError(errorMsg);

        if (!isNull(json)) {
            UserModel userModel = new UserModel();
            userModel = userModel.parseUserModel(json, userModel);
            UserDataHelper.saveUserLoginInfo(mContext, userModel);
            listener.setData(userModel);
        }else listener.setData(null);
    }


    @Override
    protected String getUrl() {
        return url;
    }
}
