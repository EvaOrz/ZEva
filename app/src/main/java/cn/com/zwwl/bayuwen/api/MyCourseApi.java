package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.StudyCourseModel;
import cn.com.zwwl.bayuwen.util.GsonUtil;

public class MyCourseApi extends BaseApi {
    FetchEntryListener listener;

    public MyCourseApi(Context context, FetchEntryListener listener) {
        super(context);
        this.listener=listener;
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
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        listener.setError(errorMsg);
        listener.setData(GsonUtil.parseJson(StudyCourseModel.class, json.toString()));
    }
}
