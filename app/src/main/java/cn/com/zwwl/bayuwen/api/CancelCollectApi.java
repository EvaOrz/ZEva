package cn.com.zwwl.bayuwen.api;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;


public class CancelCollectApi extends BaseApi {
    private Activity activity;
  private ResponseCallBack<String> callBack;
    private String ID;
    private String url;
    public CancelCollectApi(Activity context,String id , ResponseCallBack<String> callBack) {
        super(context);
        this.activity = context;
        this.ID = id;
        this.callBack = callBack;
        this.url=UrlUtil.getCollecturl()+"/"+ID;
        delete();
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
    protected void handler(final JSONObject json, final JSONArray array, final ErrorMsg errorMsg) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (errorMsg == null) {
                    callBack.result("ss",errorMsg);
                }

            }
        });
    }
}
