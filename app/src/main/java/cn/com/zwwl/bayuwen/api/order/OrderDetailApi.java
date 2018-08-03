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
    private String url = "";

    /**
     * 获取团购信息，可从课程详情中解析
     *
     * @param context
     * @param listener
     * @param isDelete 是否是取消订单操作
     */
    public OrderDetailApi(Context context, String oid, boolean isDelete, FetchEntryListener
            listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        if (isDelete) {
            url = UrlUtil.cancleOrder() + oid;
            delete();
        } else {
            url = UrlUtil.getOrderDetail();
            pamas.put("oid", oid);
            post();
        }

    }

    @Override
    protected String getUrl() {
        return url;
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        if (errorMsg != null)
            listener.setError(errorMsg);
        else
            listener.setError(null);
        
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

