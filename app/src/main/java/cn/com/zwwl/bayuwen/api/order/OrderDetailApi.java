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
import cn.com.zwwl.bayuwen.model.GroupBuyModel;
import cn.com.zwwl.bayuwen.model.OrderForMyListModel;

/**
 * 订单详情api
 */
public class OrderDetailApi extends BaseApi {
    private FetchEntryListener listener;
    private Map<String, String> pamas = new HashMap<>();

    /**
     * 获取团购信息，可从课程详情中解析
     *
     * @param context
     * @param listener
     */
    public OrderDetailApi(Context context, String oid, FetchEntryListener listener) {
        super(context);
        mContext = context;
        pamas.put("oid", oid);
        this.listener = listener;
        post();
    }

    @Override
    protected String getUrl() {
        return UrlUtil.getOrderDetail();
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        if (errorMsg != null)
            listener.setError(errorMsg);
        if (!isNull(json)) {
            OrderForMyListModel o = new OrderForMyListModel();
            o.parseOrderForMyListModel(json, o);
            listener.setData(o);
        } else {
            listener.setData(null);
        }

    }

    @Override
    protected Map<String, String> getPostParams() {
        return pamas;
    }


}

