package cn.com.zwwl.bayuwen.api;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;


public class AddCommentApi extends BaseApi {
    private Activity activity;
  private ResponseCallBack<String> callBack;
    private HashMap<String, String> para;

    public AddCommentApi(Activity context, HashMap<String, String> para , ResponseCallBack<String> callBack) {
        super(context);
        this.activity = context;
        this.para = para;
        this.callBack = callBack;
        post();
    }

    @Override
    protected String getUrl() {
        return UrlUtil.getTopicComment();
    }

    @Override
    protected Map<String, String> getPostParams() {
        return para;
    }

    @Override
    protected void handler(final JSONObject json, final JSONArray array, final ErrorMsg errorMsg) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (errorMsg == null||errorMsg.equals("")) {
                    callBack.result("ss",errorMsg);
                }

            }
        });
    }
}
