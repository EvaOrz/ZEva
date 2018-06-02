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
 * 发起拼团（获取我自己的拼团码）
 * <p>
 * 我要垫付（获取我自己的拼团码）
 */
public class GetTuanCodeApi extends BaseApi {
    private FetchEntryListener listener;
    private Map<String, String> pamas = new HashMap<>();

    /**
     * @param context
     * @param pama     kid|code
     * @param listener
     */
    public GetTuanCodeApi(Context context, String pama, int type, FetchEntryListener
            listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        pamas.put("kid", pama);
        pamas.put("type", type + "");
        post();
    }


    @Override
    protected String getUrl() {
        return UrlUtil.faqiTuan();
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        if (errorMsg != null)
            listener.setError(errorMsg);
        else listener.setError(null);

        if (!isNull(json)) {
            String purchase_code = json.optString("purchase_code");
            ErrorMsg error = new ErrorMsg();
            error.setDesc(purchase_code);
            listener.setData(error);
        } else {
            listener.setData(null);
        }

    }

    @Override
    protected Map<String, String> getPostParams() {
        return pamas;
    }


}