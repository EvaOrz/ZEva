package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 反馈
 */
public class FeedbackApi extends BaseApi {
    private FetchEntryListener listener;
    private Map<String, String> pamas = new HashMap<>();

    /**
     * @param context
     * @param content
     * @param satisf_degree 0-不满意 1-一般 2-满意 3-随意
     * @param listener
     */
    public FeedbackApi(Context context, String content, int satisf_degree, FetchEntryListener
            listener) {
        super(context);
        this.listener = listener;
        pamas.put("content", content);
        pamas.put("satisf_degree", satisf_degree + "");
        post();
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        if (errorMsg != null)
            listener.setError(errorMsg);
        else listener.setError(null);

    }

    @Override
    protected Map<String, String> getPostParams() {
        return pamas;
    }

    @Override
    protected String getUrl() {
        return UrlUtil.feedback();
    }
}
