package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.PraiseModel;
import cn.com.zwwl.bayuwen.model.TeacherDetailModel;

/**
 * Created by lousx on 2018/5/23.
 */

public class TDeatailApi extends BaseApi {
    private Map<String, String> pamas = new HashMap<>();
    private TeacherDetailModel teacherDetailModel;
    private FetchEntryListener listener;
    private String url;

    public TDeatailApi(Context context, String id, FetchEntryListener listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        this.url = UrlUtil.getTDetailUrl(id);
        get();
    }

    @Override
    protected String getUrl() {
        return url;
    }

    @Override
    protected Map<String, String> getPostParams() {
        return pamas;
    }

    @Override
    protected void handler(JSONObject json, JSONArray jsonArray, ErrorMsg errorMsg) {
        if (errorMsg != null) {
            listener.setError(errorMsg);
        }
        if (!isNull(json)) {
            Gson gson = new Gson();
            teacherDetailModel = gson.fromJson(String.valueOf(json), TeacherDetailModel.class);
            listener.setData(teacherDetailModel);
        }
    }

}
