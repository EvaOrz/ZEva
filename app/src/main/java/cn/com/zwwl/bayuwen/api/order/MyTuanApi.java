package cn.com.zwwl.bayuwen.api.order;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 我的团购页面：
 * <p>
 * 我参加的团购
 * 我发起的团购
 */
public class MyTuanApi extends BaseApi {
    private FetchEntryListener listener;

    public MyTuanApi(Context context, FetchEntryListener listener) {
        super(context);
        this.listener = listener;
        get();
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        listener.setError(errorMsg);
    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return UrlUtil.getMyTuan();
    }
}
