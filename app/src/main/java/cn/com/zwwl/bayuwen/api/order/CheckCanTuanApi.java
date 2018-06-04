package cn.com.zwwl.bayuwen.api.order;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 检查是否可以参团
 */
public class CheckCanTuanApi extends BaseApi {
    private FetchEntryListener listener;
    private Map<String, String> pamas = new HashMap<>();

    /**
     * @param context
     * @param code
     * @param listener
     */
    public CheckCanTuanApi(Context context, String code, FetchEntryListener
            listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        pamas.put("code", code);
        post();
    }


    @Override
    protected String getUrl() {
        return UrlUtil.checkCanTuan();
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


}
