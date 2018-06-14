package cn.com.zwwl.bayuwen.api;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.BaseResponse;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.util.GsonUtil;

public class TDetailListApi extends BaseApi {
    private Activity activity;
    private ResponseCallBack<BaseResponse> callBack;
    private String url;

    public TDetailListApi(Activity context, String tId, int page, ResponseCallBack<BaseResponse> callBack) {
        super(context);
        this.activity = context;
        this.callBack = callBack;
        url = UrlUtil.TDetailList() + "?tid=" + tId + "&page=" + page;
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
    protected void handler(final JSONObject json, JSONArray array, final ErrorMsg errorMsg) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseResponse response = null;
                if (json != null)
                    response = GsonUtil.parseJson(BaseResponse.class, json.toString());
                callBack.result(response, errorMsg);
            }
        });
    }
}
