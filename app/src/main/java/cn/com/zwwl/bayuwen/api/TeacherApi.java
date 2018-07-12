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
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.TeacherModel;

/**
 * 获取全部教师、按筛选条件获取教师列表、教师详情Api
 */
public class TeacherApi extends BaseApi {
    private Map<String, String> pamas = new HashMap<>();
    private FetchEntryListener listener;
    private String url;

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
        listener.setError(errorMsg);

        if (!isNull(json)) {
            // 解析教师详情接口数据
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