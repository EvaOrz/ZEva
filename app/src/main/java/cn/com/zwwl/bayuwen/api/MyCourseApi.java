package cn.com.zwwl.bayuwen.api;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.MyCourseModel;
import cn.com.zwwl.bayuwen.util.GsonUtil;

public class MyCourseApi extends BaseApi {
    ResponseCallBack<MyCourseModel> listener;
    Activity context;

    public MyCourseApi(Activity context, ResponseCallBack<MyCourseModel> listener) {
        super(context);
        this.context = context;
        this.listener = listener;
        get();
    }

    @Override
    protected String getUrl() {
        return UrlUtil.getMyCourse();
    }

    @Override
    protected Map<String, String> getPostParams() {
        return new HashMap<>();
    }

    @Override
    protected void handler(final JSONObject json, JSONArray array, final ErrorMsg errorMsg) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MyCourseModel model = null;
                if (json != null) {
                    model = GsonUtil.parseJson(MyCourseModel.class, json.toString());
                }
                listener.result(model, errorMsg);
            }
        });

    }
}
