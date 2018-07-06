package cn.com.zwwl.bayuwen.api.order;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 获取未付款订单数量
 */
public class OrderCancleNumApi extends BaseApi {
    private FetchEntryListener listener;

    public OrderCancleNumApi(Activity context, FetchEntryListener listener) {
        super(context);
        this.listener = listener;
        get();
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        listener.setError(errorMsg);
        if (!isNull(json)) {
            ErrorMsg errorMsg1 = new ErrorMsg();
            errorMsg1.setNo(json.optInt("num"));
            listener.setData(errorMsg1);
        }


    }

    @Override
    protected String getUrl() {
        return UrlUtil.getOrderNum();
    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }
}
