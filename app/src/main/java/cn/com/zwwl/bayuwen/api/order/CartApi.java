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

/**
 * 加入购课单、获取购课单列表
 */
public class CartApi extends BaseApi {
    private Map<String, String> pamas = new HashMap<>();
    private FetchEntryListener listener;
    private FetchEntryListListener listListener;

    private String url = UrlUtil.getCarturl();

    /**
     * 加入购课单
     *
     * @param context
     * @param kid
     * @param listener
     */
    public CartApi(Context context, String kid, FetchEntryListener listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        pamas.put("kid", kid);
        post();
    }

    /**
     * 删除购课单
     *
     * @param context
     * @param deleteIds
     * @param flag
     */
    public CartApi(Context context, String deleteIds, int flag, FetchEntryListener listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        url += "/" + deleteIds;
        delete();
    }


    /**
     * 获取购课单列表
     *
     * @param context
     * @param listListener
     */
    public CartApi(Context context, FetchEntryListListener listListener) {
        super(context);
        mContext = context;
        this.listListener = listListener;
        get();
    }

    @Override
    protected String getUrl() {
        return url;
    }

    @Override
    protected Map<String, String> getPostParams() {
        return pamas;
    }

    @Override
    protected void handler(JSONObject json, JSONArray jsonArray, ErrorMsg errorMsg) {
        if (errorMsg != null) {
            if (listListener != null) {
                listListener.setError(errorMsg);
            }
            if (listener != null)
                listener.setError(errorMsg);
        } else {
            if (listener != null)
                listener.setError(null);
        }

        if (!isNull(jsonArray)) {
            Gson gson = new Gson();
            List<KeModel> keModelList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject j = jsonArray.optJSONObject(i);
                JSONObject ke = j.optJSONObject("course");
                if (!isNull(ke)) {
                    KeModel keModel = gson.fromJson(ke.toString(), KeModel.class);
                    keModel.setCartId(j.optString("id"));
                    keModelList.add(keModel);
                }
            }
            listListener.setData(keModelList);

        }
    }

}