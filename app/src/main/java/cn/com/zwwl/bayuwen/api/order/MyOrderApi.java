package cn.com.zwwl.bayuwen.api.order;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

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
 * 我的订单api
 */
public class MyOrderApi extends BaseApi {
    private FetchEntryListListener listListener;
    private String url;
    private int type = 0;

    /**
     * @param context
     * @param type         1:购课单 2:待付款 3:已付款 4:退款
     * @param listListener
     */
    public MyOrderApi(Context context, int type, FetchEntryListListener listListener) {
        super(context);
        mContext = context;
        this.listListener = listListener;
        this.type = type;
        this.url = UrlUtil.getMyOrder() + "?type=" + type;
        get();
    }

    @Override
    protected String getUrl() {
        return url;
    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }

    @Override
    protected void handler(JSONObject json, JSONArray jsonArray, ErrorMsg errorMsg) {
        if (errorMsg != null) {
            listListener.setError(errorMsg);
        }
        if (type == 1) {
            listListener.setData(parseGoukeDan(jsonArray));
        }

    }

    private List<KeModel> parseGoukeDan(JSONArray array) {
        List<KeModel> keModelList = new ArrayList<>();
        if (!isNull(array)) {
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.optJSONObject(i).optJSONObject("course");
                if (!isNull(jsonObject)) {
                    Gson gson = new Gson();
                    KeModel keModel = gson.fromJson(jsonObject.toString(), KeModel.class);
                    keModelList.add(keModel);
                }
            }
        }
        return keModelList;
    }


}
