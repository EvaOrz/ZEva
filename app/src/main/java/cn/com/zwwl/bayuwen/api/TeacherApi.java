package cn.com.zwwl.bayuwen.api;

import android.content.Context;
import android.text.TextUtils;

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
 * 获取全部教师、按筛选条件获取教师列表、教师详情Api
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
     * @param type         1:教师 2:班主任
     * @param listListener
     */
    public TeacherApi(Context context, int type, FetchEntryListListener listListener) {
        super(context);
        mContext = context;
        this.listListener = listListener;
        this.url = UrlUtil.getTeacherUrl(null) + "?flag=" + type;
        get();
    }

    /**
     * 按筛选条件获取教师列表
     *
     * @param context
     * @param type         项目id串
     * @param users        年级串
     * @param keyword      搜索关键字
     * @param flag         1:教师 2:班主任
     * @param listListener
     */
    public TeacherApi(Context context, String type, String users, String keyword, int flag,
                      FetchEntryListListener listListener) {
        super(context);
        mContext = context;
        this.listListener = listListener;
        this.url = UrlUtil.getTeacherUrl(null) + "/search?flag=" + flag;

        if (!TextUtils.isEmpty(type)) {
            String and = url.endsWith("?") ? "" : "&";
            url += and + "type=" + type;
        }
        if (!TextUtils.isEmpty(users)) {
            String and = url.endsWith("?") ? "" : "&";
            url += and + "users=" + users;
        }
        if (!TextUtils.isEmpty(keyword)) {
            String and = url.endsWith("?") ? "" : "&";
            url += and + "keyword=" + keyword;
        }
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
        if (listListener != null)
            listListener.setError(errorMsg);
        if (listener != null)
            listener.setError(errorMsg);

        // 解析教师筛选接口数据
        if (!isNull(array)) {
            List<TeacherModel> ts = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                TeacherModel t = new TeacherModel();
                t.parseTeacherModel(array.optJSONObject(i), t);
                ts.add(t);
            }
            listListener.setData(ts);
        }
        if (!isNull(json)) {
            // 解析全部教师接口数据
            if (listListener != null) {
                JSONArray adata = json.optJSONArray("teachers");
                List<TeacherModel> ts = new ArrayList<>();
                if (!isNull(adata)) {
                    for (int i = 0; i < adata.length(); i++) {
                        TeacherModel t = new TeacherModel();
                        t.parseTeacherModel(adata.optJSONObject(i), t);
                        ts.add(t);
                    }

                }
                listListener.setData(ts);
            }
            // 解析教师详情接口数据
            if (listener != null) {
                JSONObject tjson = json.optJSONObject("teacher");
                JSONArray carray = json.optJSONArray("courses");
                TeacherModel t = new TeacherModel();
                t.parseTeacherModel(tjson, t);
                List<KeModel> keModelList = new ArrayList<>();
                if (!isNull(carray)) {
                    for (int i = 0; i < carray.length(); i++) {
                        Gson gs = new Gson();
                        KeModel k = gs.fromJson(carray.optString(i), KeModel.class);
                        keModelList.add(k);
                    }
                    t.setKeModels(keModelList);
                }
                listener.setData(t);
            }

        }
    }
}