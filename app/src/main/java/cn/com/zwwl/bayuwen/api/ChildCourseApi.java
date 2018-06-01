package cn.com.zwwl.bayuwen.api;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.BaseResponse;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.util.GsonUtil;

/**
 * 子课程列表
 * Created by zhumangmang at 2018/6/1 14:27
 */
public class ChildCourseApi extends BaseApi {
    private Activity activity;
    private ResponseCallBack callBack;
    private String url;

    public ChildCourseApi(Activity context, String id, String page, ResponseCallBack callBack) {
        super(context);
        this.activity = context;
        this.callBack = callBack;
        url = UrlUtil.getLecturesUrl(id, page);
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
                if (json != null)
                    callBack.result(GsonUtil.parseJson(BaseResponse.class, json.toString()), errorMsg);
            }
        });
    }
}
