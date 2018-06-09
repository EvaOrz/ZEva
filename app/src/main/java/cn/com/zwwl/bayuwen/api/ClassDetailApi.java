package cn.com.zwwl.bayuwen.api;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ClassModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.util.GsonUtil;
/**
 *  班级详情
 *  Created by zhumangmang at 2018/6/7 19:57
 */
public class ClassDetailApi extends BaseApi {
    private Activity activity;
    private ResponseCallBack<ClassModel> callBack;
    private String url;

    public ClassDetailApi(Activity context, String kid, ResponseCallBack<ClassModel> callBack) {
        super(context);
        this.activity = context;
        url = UrlUtil.classDetail() + "?kid=" + kid;
        this.callBack = callBack;
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
                ClassModel classModel = null;
                if (json != null) {
                    classModel = GsonUtil.parseJson(ClassModel.class, json.toString());
                }
                callBack.result(classModel, errorMsg);
            }
        });
    }
}
