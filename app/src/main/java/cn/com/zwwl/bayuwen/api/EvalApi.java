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
 * 评价
 * Created by zhumangmang at 2018/5/31 18:24
 */
public class EvalApi extends BaseApi {
    private Activity activity;
    private ResponseCallBack<CommonModel> callBack;
    private HashMap<String, String> para;

    public EvalApi(Activity context, HashMap<String, String> para, ResponseCallBack<CommonModel> callBack) {
        super(context);
        this.activity = context;
        this.para = para;
        this.callBack = callBack;
        post();
    }

    @Override
    protected String getUrl() {
        return UrlUtil.addEval();
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
                CommonModel model = null;
                if (json != null) model = GsonUtil.parseJson(CommonModel.class, json.toString());
                callBack.result(model, errorMsg);
            }
        });
    }
}
