package cn.com.zwwl.bayuwen.api;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.QuestionModel;
import cn.com.zwwl.bayuwen.util.GsonUtil;

public class QuestionListApi extends BaseApi {
    private Activity activity;
    private String url;
    private ResponseCallBack<QuestionModel> callBack;

    public QuestionListApi(Activity context, String sectionId, ResponseCallBack<QuestionModel> callBack) {
        super(context);
        this.activity = context;
        this.callBack = callBack;
        this.url = UrlUtil.getQuestionList() + sectionId;
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
                QuestionModel model = null;
                if (json != null)
                    model = GsonUtil.parseJson(QuestionModel.class, json.toString());
                callBack.result(model, errorMsg);
            }
        });
    }
}
