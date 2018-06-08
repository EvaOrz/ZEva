package cn.com.zwwl.bayuwen.api;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.CommentModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

public class VoteApi extends BaseApi {
    private Activity activity;
    private ResponseCallBack<CommentModel> callBack;
    private HashMap<String, String> para;

    public VoteApi(Activity context, HashMap<String, String> map, ResponseCallBack<CommentModel> callBack) {
        super(context);
        this.activity = context;
        this.para = map;
        this.callBack = callBack;
        post();
    }

    @Override
    protected String getUrl() {
        return UrlUtil.addVote();
    }

    @Override
    protected Map<String, String> getPostParams() {
        return para;
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, final ErrorMsg errorMsg) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                callBack.result(null, errorMsg);
            }
        });
    }
}
