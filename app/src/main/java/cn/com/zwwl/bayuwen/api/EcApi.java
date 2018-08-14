package cn.com.zwwl.bayuwen.api;

import android.app.Activity;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.EcModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.util.GsonUtil;

/**
 * 新版选课
 */
public class EcApi extends BaseApi {
    private Activity activity;
    private ResponseCallBack<EcModel> listener;

    public EcApi(Activity context, ResponseCallBack<EcModel> listener) {
        super(context);
        this.activity = context;
        this.listener = listener;
        get();
    }

    @Override
    protected String getUrl() {
        return UrlUtil.getElectiveCourseUrl();
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
                EcModel model = null;
                if (json != null) {
                    model = GsonUtil.parseJson(EcModel.class, json.toString());

                }
                listener.result(model, errorMsg);

            }
        });
    }
}
