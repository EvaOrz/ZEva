package cn.com.zwwl.bayuwen.api;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 初始化域名api
 */
public class InitInterfaceApi extends BaseApi {

    public InitInterfaceApi(Context mContext) {
        super(mContext);
        get();
    }

    @Override
    protected String getUrl() {
        return UrlUtil.getInterface();
    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        if (!isNull(json) && !TextUtils.isEmpty(json.optString("api_url"))) {
            UrlUtil.HOST = json.optString("api_url");
        }
    }
}
