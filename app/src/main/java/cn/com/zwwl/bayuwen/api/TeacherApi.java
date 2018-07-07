package cn.com.zwwl.bayuwen.api;

import android.app.Activity;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.SearchTModel;
import cn.com.zwwl.bayuwen.model.TeacherModel;

/**
 * 获取全部教师、按筛选条件获取教师列表、教师详情Api
 */
public class TeacherApi extends BaseApi {
    private Map<String, String> pamas = new HashMap<>();
    private FetchEntryListener listener;
    private String url;
    private Activity activity;
    private ResponseCallBack<SearchTModel> callBack;


    public TeacherApi(Activity context, String url, ResponseCallBack<SearchTModel> callBack) {
        super(context);
        this.activity = context;
        this.callBack = callBack;
        this.url = url;
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
    protected void handler(final JSONObject json, final JSONArray array, final ErrorMsg errorMsg) {

        if (callBack != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // 解析教师筛选接口数据
                    if (!isNull(json)) {
                        SearchTModel searchTModel = new SearchTModel();
                        List<TeacherModel> ts = new ArrayList<>();
                        JSONArray array1 = json.optJSONArray("teachers");
                        if (!isNull(array1)) {
                            for (int i = 0; i < array1.length(); i++) {
                                TeacherModel t = new TeacherModel();
                                t.parseTeacherModel(array1.optJSONObject(i), t);
                                ts.add(t);
                            }
                            searchTModel.setData(ts);
                        }
                        searchTModel.setPage(json.optInt("page"));
                        searchTModel.setPagesize(json.optInt("pagesize"));
                        searchTModel.setTotal(Integer.valueOf(json.optString("total")));
                        callBack.result(searchTModel, errorMsg);
                    }
                }
            });
        } else {
            if (listener != null)
                listener.setError(errorMsg);

            if (!isNull(json)) {
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
}