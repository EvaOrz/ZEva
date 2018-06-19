package cn.com.zwwl.bayuwen.api;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.CommonModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
/**
 *  提交闯关答题
 *  Created by zhumangmang at 2018/6/15 17:57
 */
public class SubmitAnswerApi extends BaseApi {
    private Activity activity;
    private ResponseCallBack<CommonModel> callBack;

    public SubmitAnswerApi(Activity context, String data, ResponseCallBack<CommonModel> callBack) {
        super(context);
        this.activity = context;
        post(data);
    }

    @Override
    protected String getUrl() {
        return UrlUtil.submitAnswer();
    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {

    }
}
