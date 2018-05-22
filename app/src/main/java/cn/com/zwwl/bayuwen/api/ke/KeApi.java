package cn.com.zwwl.bayuwen.api.ke;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 *
 */
public class KeApi extends BaseApi {
    public KeApi(Context context) {
        super(context);
        mContext = context;

    }

    @Override
    protected String getUrl() {
        return null;
    }


    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {

    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }
}
