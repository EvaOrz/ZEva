package cn.com.zwwl.bayuwen.api;

import android.app.Activity;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.EvalContentModel;
import cn.com.zwwl.bayuwen.util.GsonUtil;

/**
 * 获取月度、课程评价标签内容
 * Created by zhumangmang at 2018/6/9 13:37
 */
public class EvalLabelApi extends BaseApi {
    private Activity activity;
    private StringBuilder url = new StringBuilder(UrlUtil.evalContent() + "?");
    private ResponseCallBack<EvalContentModel> callBack;

    public EvalLabelApi(Activity context, HashMap<String, String> map, ResponseCallBack<EvalContentModel> callBack) {
        super(context);
        this.activity = context;
        this.callBack = callBack;
        for (String key : map.keySet()) {
            if (!TextUtils.isEmpty(map.get(key))) {
                url.append(url.toString().endsWith("?") ? "" : "&").append(key).append("=").append(map.get(key));
            }
        }
        get();
    }

    @Override
    protected String getUrl() {
        return url.toString();
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
                EvalContentModel model = null;
                if (json != null) {
                    model = GsonUtil.parseJson(EvalContentModel.class, json.toString());
                }
                callBack.result(model, errorMsg);
            }
        });
    }
}
