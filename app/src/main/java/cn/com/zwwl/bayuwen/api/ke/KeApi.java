package cn.com.zwwl.bayuwen.api.ke;

import org.json.JSONObject;

import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 *
 */
public class KeApi extends BaseApi {
    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected String getHeadValue() {
        return null;
    }

    @Override
    protected void handler(JSONObject data, ErrorMsg errorMsg) {

    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }
}
