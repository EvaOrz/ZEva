package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.util.AppValue;

/**
 *
 */
public class WebUrlApi extends BaseApi {

    public WebUrlApi(Context context) {
        super(context);
        get();
    }


    @Override
    protected String getUrl() {
        return UrlUtil.getWebUrl();
    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }

    /**
     * {
     * "enroll":"http://www.zhugexuetang.com/explain/enroll.html",
     * "refund":"http://www.zhugexuetang.com/explain/refund.html",
     * "invite":"http://www.zhugexuetang.com/explain/invite.html",
     * "about":"http://www.zhugexuetang.com/explain/about.html",
     * "help":"http://www.zhugexuetang.com/explain/help.html",
     * "version":"http://www.zhugexuetang.com/explain/version.html"
     * }
     */
    @Override
    protected void handler(final JSONObject json, final JSONArray array, final ErrorMsg errorMsg) {
        if (!isNull(json)) {
            AppValue.enrollUrl = json.optString("enroll");
            AppValue.refundUrl = json.optString("refund");
            AppValue.inviteUrl = json.optString("invite");
            AppValue.aboutUrl = json.optString("about");
            AppValue.helpUrl = json.optString("help");
        }
    }
}
