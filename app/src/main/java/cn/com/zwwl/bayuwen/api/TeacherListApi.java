package cn.com.zwwl.bayuwen.api;

import android.app.Activity;
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
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.SearchTModel;
import cn.com.zwwl.bayuwen.model.TeacherModel;

/**
 * 获取教师列表api
 */
public class TeacherListApi extends BaseApi {
    private Map<String, String> pamas = new HashMap<>();
    private String url;
    private Activity activity;
    private ResponseCallBack<SearchTModel> callBack;

    /**
     * 筛选
     *
     * @param context
     * @param url
     * @param callBack
     */
    public TeacherListApi(Activity context, String url, ResponseCallBack<SearchTModel> callBack) {
        super(context);
        this.activity = context;
        this.callBack = callBack;
        this.url = url;
        get();
    }

    /**
     * 全部
     *
     * @param context
     * @param type     1:老师 2:班主任
     * @param callBack
     */
    public TeacherListApi(Context context, int type, ResponseCallBack<SearchTModel> callBack) {
        super(context);
        mContext = context;
        this.callBack = callBack;
        this.url = UrlUtil.getTeacherUrl(null) + "?flag=" + type;
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
                        searchTModel.setTotal(Integer.valueOf(json.optString("total_count")));
                        callBack.result(searchTModel, errorMsg);
                    }
                }
            });
        }
    }
}