package cn.com.zwwl.bayuwen.api.order;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.FaPiaoModel;

/**
 * 计算发票价格api
 */
public class FaPiaoCountPriceApi extends BaseApi {
    private FetchEntryListener listener;
    private String url = "";

    public FaPiaoCountPriceApi(Context context, String id, FetchEntryListener listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        url += UrlUtil.countPiaoPrice() + "?item_oid=" + id;
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
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        listener.setError(errorMsg);

        if (!isNull(json)) {
            FaPiaoModel.PiaoType piaoType = new FaPiaoModel.PiaoType();
            piaoType.parsePiaoType(json, piaoType);
            listener.setData(piaoType);
        } else listener.setData(null);


    }
}
