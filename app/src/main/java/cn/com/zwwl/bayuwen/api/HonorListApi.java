package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 获取奖状、礼物列表
 */
public class HonorListApi extends BaseApi {

    private int type = 1;
    private FetchEntryListListener listListener;

    public HonorListApi(Context context, int type, FetchEntryListListener listListener) {
        super(context);
        this.type = type;
        this.listListener = listListener;
        get();
    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return UrlUtil.getHonorurl() + "?type=" + type;
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        if (errorMsg != null) listListener.setError(errorMsg);
    }
}
