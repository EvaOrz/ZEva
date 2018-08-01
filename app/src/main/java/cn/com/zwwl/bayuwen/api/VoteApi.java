package cn.com.zwwl.bayuwen.api;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.CommonModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.util.GsonUtil;

/**
 *
 */
public class VoteApi extends BaseApi {
    private Activity activity;
    private ResponseCallBack<ErrorMsg> callBack;
    private HashMap<String, String> para;

    public VoteApi(Activity context, HashMap<String, String> map, ResponseCallBack<ErrorMsg>
            callBack) {
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
    protected void handler(final JSONObject json, JSONArray array, final ErrorMsg errorMsg) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                callBack.result(null, errorMsg);
            }
        });
    }
}
