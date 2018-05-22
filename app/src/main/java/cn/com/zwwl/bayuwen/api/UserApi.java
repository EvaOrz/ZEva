package cn.com.zwwl.bayuwen.api;

import android.content.Context;

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
public class UserApi extends BaseApi {
    private Map<String, String> pamas = new HashMap<>();
    private UserModel userModel;
    private FetchEntryListener listener;
    private String url;

    /**
     * 修改用户信息
     *
     * @param context
     * @param name
     * @param phone
     * @param gendar
     * @param province
     * @param city
     * @param pic
     * @param listener
     */
    public UserApi(Context context, String name, String phone, int gendar, int province, int
            city, String pic, FetchEntryListener listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        this.url = UrlUtil.changeInfoUrl(UserDataHelper.getUid(context));
        pamas.put("name", name);
        pamas.put("tel", phone);
        pamas.put("sex", gendar + "");
        pamas.put("province", province + "");
        pamas.put("city", city + "");
        pamas.put("pic", pic);
        userModel = new UserModel();
        patch();

    }


    /**
     * 获取用户信息构造
     *
     * @param context
     * @param listener
     */
    public UserApi(Context context, FetchEntryListener listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        this.url = UrlUtil.userInfoUrl();
        userModel = new UserModel();
        get();
    }

    @Override
    protected Map<String, String> getPostParams() {
        return pamas;
    }

    @Override
    protected void handler(JSONObject jsonObject, ErrorMsg errorMsg) {
        if (errorMsg == null) {
            ErrorMsg e = new ErrorMsg();
            JSONObject data = jsonObject.optJSONObject("data");
            if (jsonObject.optBoolean("success", false)) {
                if (isNull(data)) {
                    listener.setData(e);
                } else {
                    userModel.parseUserModel(data, userModel);
                    UserDataHelper.saveUserLoginInfo(mContext, userModel);
                    listener.setData(userModel);
                }
            } else {
                e.setDesc(data.optString("message"));
                listener.setData(e);
            }


        } else {
            listener.setData(errorMsg);
        }

    }

    @Override
    protected String getUrl() {
        return url;
    }
}
