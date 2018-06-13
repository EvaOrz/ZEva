package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.Index1Model;
import cn.com.zwwl.bayuwen.util.GsonUtil;

/**
 * 首页fragment1 api
 */
public class Index1Api extends BaseApi {

    private FetchEntryListener fetchEntryListener;

    public Index1Api(Context context, FetchEntryListener fetchEntryListener) {
        super(context);
        this.fetchEntryListener = fetchEntryListener;

        get();
    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return UrlUtil.getIndex();
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        fetchEntryListener.setError(errorMsg);
        if (!isNull(json)) {
            Index1Model index1Model = GsonUtil.parseJson(Index1Model.class, json.toString());
            fetchEntryListener.setData(index1Model);
        }
    }
}
