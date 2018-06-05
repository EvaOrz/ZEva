package cn.com.zwwl.bayuwen.api.order;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 退费列表
 */
public class GetTuiFeeListApi extends BaseApi {

    private FetchEntryListListener listener;

    public GetTuiFeeListApi(Context context, FetchEntryListListener listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        get();
    }


    @Override
    protected String getUrl() {
        return UrlUtil.getTuifeeList();
    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {

    }
}
