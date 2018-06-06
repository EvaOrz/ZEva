package cn.com.zwwl.bayuwen.api.order;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;

/**
 * 退费相关Api
 */
public class TuifeeApi extends BaseApi {

    private FetchEntryListener listener;
    private FetchEntryListListener listListener;
    private Map<String, String> pamas = new HashMap<>();
    private int type = -1;//0:原路 1:账户

    /**
     * 原路退费
     * 账户退费
     *
     * @param context
     * @param type
     * @param detail_id
     * @param reason
     * @param listener
     */
    public TuifeeApi(Context context, int type, String detail_id, String reason,
                     FetchEntryListener listener) {
        super(context);
        mContext = context;
        this.type = type;
        this.listener = listener;
        pamas.put("detail_id", detail_id);
        pamas.put("reason", reason);
        post();
    }

    /**
     * 获取退费详情
     *
     * @param context
     * @param id
     * @param oid
     * @param kid
     * @param listListener
     */
    public TuifeeApi(Context context, String id, String oid, String kid,
                     FetchEntryListListener listListener) {
        super(context);
        mContext = context;
        this.listListener = listListener;
        pamas.put("id", id);
        pamas.put("oid", oid);
        pamas.put("kid", kid);
        post();
    }


    @Override
    protected String getUrl() {
        if (type == 1) {
            return UrlUtil.accountTuifee();
        } else if (type == 0) {
            return UrlUtil.yuanluTuifee();
        } else {
            return UrlUtil.getTuifeeDetail();
        }
    }

    @Override
    protected Map<String, String> getPostParams() {
        return pamas;
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        if (errorMsg != null) {
            if (listener != null)
                listener.setError(errorMsg);

            if (listListener != null)
                listListener.setError(errorMsg);
        } else {
            if (listener != null)
                listener.setError(null);
        }


    }

}


