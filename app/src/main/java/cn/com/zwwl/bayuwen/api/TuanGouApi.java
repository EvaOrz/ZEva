package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.GroupBuyModel;
import cn.com.zwwl.bayuwen.model.KeModel;

/**
 * 团购
 */
public class TuanGouApi extends BaseApi {
    private FetchEntryListener listener;
    private String kid;
    private Map<String, String> pamas = new HashMap<>();

    /**
     * 获取团购信息，可从课程详情中解析
     *
     * @param context
     * @param kid
     * @param listener
     */
    public TuanGouApi(Context context, String kid, FetchEntryListener listener) {
        super(context);
        mContext = context;
        this.kid = kid;
        this.listener = listener;
        get();
    }

    @Override
    protected String getUrl() {
        return UrlUtil.getTuanInfo() + "/" + kid;
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        if (errorMsg != null)
            listener.setError(errorMsg);
        if (!isNull(json)) {
           GroupBuyModel groupBuy = new GroupBuyModel();
            groupBuy.parseGroupBuyModel(json, groupBuy);
            listener.setData(groupBuy);
        } else {
            listener.setData(null);
        }

    }

    @Override
    protected Map<String, String> getPostParams() {
        return pamas;
    }


}

