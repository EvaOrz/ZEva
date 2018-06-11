package cn.com.zwwl.bayuwen.api;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.ReportModel;
import cn.com.zwwl.bayuwen.util.GsonUtil;

/**
 * 获取最新报告
 * Created by zhumangmang at 2018/6/11 17:34
 */
public class ReportPushApi extends BaseApi {
    private Activity activity;
    private ResponseCallBack<ReportModel> callBack;

    public ReportPushApi(Activity context, ResponseCallBack<ReportModel> callBack) {
        super(context);
        this.activity = context;
        this.callBack = callBack;
        get();
    }

    @Override
    protected String getUrl() {
        return UrlUtil.pushReport() + "?flag=login";
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
                ReportModel model = null;
                if (json != null) model = GsonUtil.parseJson(ReportModel.class, json.toString());
                callBack.result(model, errorMsg);
            }
        });
    }
}
