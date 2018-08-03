package cn.com.zwwl.bayuwen.api.order;

import android.content.Context;

import com.google.gson.Gson;

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
import cn.com.zwwl.bayuwen.model.TuifeeModel;

/**
 * 退费相关Api
 */
public class TuifeeApi extends BaseApi {

    private FetchEntryListener listener;
    private Map<String, String> pamas = new HashMap<>();
    private int type = -1;//0:原路 1:账户
    private String url = "";// 获取详情get方法

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
     * @param listener
     */
    public TuifeeApi(Context context, String id, String oid, String kid,
                     FetchEntryListener listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        url = UrlUtil.getTuifeeDetail() + "?id=" + id + "&oid=" + oid + "&kid=" + kid;
        get();
    }


    @Override
    protected String getUrl() {
        if (type == 1) {
            return UrlUtil.accountTuifee();
        } else if (type == 0) {
            return UrlUtil.yuanluTuifee();
        } else {
            return url;
        }
    }

    @Override
    protected Map<String, String> getPostParams() {
        return pamas;
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        if (errorMsg != null) {
            listener.setError(errorMsg);
        } else {
            listener.setError(null);
        }

        // 退费详情解析、退费操作不解析
        if (!isNull(json)) {
            Gson gson = new Gson();
            TuifeeModel tuifeeModel = gson.fromJson(json.toString(), TuifeeModel.class);
            if (!isNull(json.optJSONObject("course"))) {
                KeModel k = gson.fromJson(json.optJSONObject("course").toString(), KeModel.class);
                if (k != null) {
                    tuifeeModel.setKeModel(k);
                }
            }
            listener.setData(tuifeeModel);
        }

    }

}


