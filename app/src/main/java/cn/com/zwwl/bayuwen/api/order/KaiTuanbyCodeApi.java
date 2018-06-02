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
 * 根据课程码开通课程
 */
public class KaiTuanbyCodeApi extends BaseApi {
    private FetchEntryListener listener;
    private Map<String, String> pamas = new HashMap<>();

    /**
     * @param context
     * @param pama     kid|code
     * @param listener
     */
    public KaiTuanbyCodeApi(Context context, String pama, FetchEntryListener
            listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        pamas.put("code", pama);
        post();
    }


    @Override
    protected String getUrl() {
        return UrlUtil.useTuanCode();
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