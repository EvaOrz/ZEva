package cn.com.zwwl.bayuwen.api;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.FinalModel;
import cn.com.zwwl.bayuwen.util.GsonUtil;

public class FinalApi extends BaseApi {
    private String url;
    private Activity activity;
    private ResponseCallBack<FinalModel> callBack;

    public FinalApi(Activity context, String kid, ResponseCallBack<FinalModel> callBack) {
        super(context);
        this.activity = context;
        this.callBack = callBack;
        url = UrlUtil.getFinal() + "?kid=" + kid;
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
                FinalModel model = null;
                if (json != null) {
                    model = GsonUtil.parseJson(FinalModel.class, json.toString());
                }
                callBack.result(model, errorMsg);
            }
        });
    }
}
