package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.TeacherModel;

/**
 * 获取全部教师、教师详情Api
 */
public class TeacherApi extends BaseApi {
    private Map<String, String> pamas = new HashMap<>();
    private FetchEntryListListener listListener;
    private FetchEntryListener listener;
    private String url;

    /**
     * 教师列表
     *
     * @param context
     * @param listListener
     */
    public TeacherApi(Context context, FetchEntryListListener listListener) {
        super(context);
        mContext = context;
        isNeedJsonArray = true;
        this.listListener = listListener;
        this.url = UrlUtil.getTeacherUrl(null);
        get();
    }

    /**
     * 获取教师详情
     *
     * @param context
     * @param id
     * @param listener
     */
    public TeacherApi(Context context, String id, FetchEntryListener listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        this.url = UrlUtil.getTeacherUrl(id);
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
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        if (errorMsg != null) {
            if (listListener != null)
                listListener.setError(errorMsg);
            if (listener != null)
                listener.setError(errorMsg);
        }
        if (isNeedJsonArray) {// 获取列表
            if (!isNull(array)) {
                List<TeacherModel> ts = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    TeacherModel t = new TeacherModel();
                    t.parseTeacherModel(array.optJSONObject(i), t);
                    ts.add(t);
                }
                listListener.setData(ts);

            } else {// 增删改
                listListener.setData(null);
            }
        }
        if (!isNull(json)) {
            JSONObject tjson = json.optJSONObject("teacher");
            JSONArray carray = json.optJSONArray("courses");
            TeacherModel t = new TeacherModel();
            t.parseTeacherModel(tjson, t);

            List<KeModel> keModelList = new ArrayList<>();
            if (!isNull(carray)) {
                for (int i = 0; i < carray.length(); i++) {
                    KeModel k = new KeModel();
                    Gson gs = new Gson();
                    k = gs.fromJson(carray.optString(i), KeModel.class);
                    keModelList.add(k);
                }
                t.setKeModels(keModelList);
            }

            listener.setData(t);
        } else listener.setData(null);
    }
}