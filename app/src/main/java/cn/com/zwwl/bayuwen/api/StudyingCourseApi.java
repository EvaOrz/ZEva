package cn.com.zwwl.bayuwen.api;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.StudyingModel;
import cn.com.zwwl.bayuwen.util.GsonUtil;

public class StudyingCourseApi extends BaseApi {
    private String kid;
    ResponseCallBack listener;
    private Activity activity;

    public StudyingCourseApi(Activity context, String kid, ResponseCallBack listener) {
        super(context);
        this.activity = context;
        this.listener = listener;
        this.kid = kid;
        get();
    }

    @Override
    protected String getUrl() {
        return UrlUtil.getStudyingCourse()+"?kid="+kid;
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
                listener.result(GsonUtil.parseJson(StudyingModel.class, json.toString()),errorMsg);
            }
        });

    }
}
