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
 * 提交闯关答题
 * Created by zhumangmang at 2018/6/15 17:57
 */
public class SubmitAnswerApi extends BaseApi {
    private Activity activity;
    private ResponseCallBack<CommonModel> callBack;
    HashMap<String, String> map = new HashMap<>();

    public SubmitAnswerApi(Activity context, String data, ResponseCallBack<CommonModel> callBack) {
        super(context);
        this.activity = context;
        this.callBack=callBack;
        map.put("answer", data);
        post();
//        post(data);
    }

    @Override
    protected String getUrl() {
        return UrlUtil.submitAnswer();
    }

    @Override
    protected Map<String, String> getPostParams() {
        return map;
    }

    @Override
    protected void handler(final JSONObject json, JSONArray array, final ErrorMsg errorMsg) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CommonModel model = null;
                if (json != null) {
                    model = GsonUtil.parseJson(CommonModel.class, json.toString());
                }
                callBack.result(model, errorMsg);
            }
        });
    }
}
