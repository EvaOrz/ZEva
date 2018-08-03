package cn.com.zwwl.bayuwen.api;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.BaseResponse;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.TeacherDetailStuentevaluateModel;
import cn.com.zwwl.bayuwen.util.GsonUtil;

public class TDetailEvaluateListApi extends BaseApi {
    private Activity activity;
    private ResponseCallBack<TeacherDetailStuentevaluateModel> callBack;
    private String url;


    public TDetailEvaluateListApi(Activity context, String tId, int page, int type, ResponseCallBack<TeacherDetailStuentevaluateModel> callBack) {
        super(context);
        this.activity = context;
        this.callBack = callBack;
        url = UrlUtil.TDetailList() + "?tid=" + tId + "&page=" + page+"&type="+type;
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
                TeacherDetailStuentevaluateModel response = null;
                if (json != null)
                    response = GsonUtil.parseJson(TeacherDetailStuentevaluateModel.class, json.toString());
                callBack.result(response, errorMsg);
            }
        });
    }
}
