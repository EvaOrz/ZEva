package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.TuanInfoModel;

/**
 * 发起拼团（获取拼团码）
 * <p>
 * 根据课程码开通课程
 */
public class TuanKaiApi extends BaseApi {
    private FetchEntryListener listener;
    private Map<String, String> pamas = new HashMap<>();
    private boolean isGetCode = true;// 区分获取开团码和根据团购码开通课程

    /**
     * @param context
     * @param pama     kid|code
     * @param listener
     */
    public TuanKaiApi(Context context, String pama, boolean isGetCode, FetchEntryListener
            listener) {
        super(context);
        mContext = context;
        this.isGetCode = isGetCode;
        this.listener = listener;
        if (isGetCode) {
            pamas.put("kid", pama);
        } else {
            pamas.put("code", pama);
        }
        post();
    }


    @Override
    protected String getUrl() {
        if (isGetCode)
            return UrlUtil.faqiTuan();
        else return UrlUtil.faqiTuan() + "/open";
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