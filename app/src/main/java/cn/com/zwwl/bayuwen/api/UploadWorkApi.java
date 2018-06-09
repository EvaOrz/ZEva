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

public class UploadWorkApi extends BaseApi {
    private Activity activity;
    private HashMap<String, String> para;
    private ResponseCallBack<CommonModel> callBack;

    public UploadWorkApi(Activity context, HashMap<String, String> map, ResponseCallBack<CommonModel> callBack) {
        super(context);
        this.activity = context;
        this.para = map;
        this.callBack = callBack;
        post();
    }

    @Override
    protected String getUrl() {
        return UrlUtil.addWork();
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
                CommonModel commonModel = null;
                if (json != null)
                    commonModel = GsonUtil.parseJson(CommonModel.class, json.toString());
                callBack.result(commonModel, errorMsg);
            }
        });
    }
}
