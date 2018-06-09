package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.LessonModel;

/**
 * 获取课程列表接口
 */
public class CourseListApi extends BaseApi {
    private FetchEntryListListener listListener;
    private String url;


    /**
     * 选课中心获取数据列表
     *
     * @param context
     * @param url
     * @param listListener
     */
    public CourseListApi(Context context, String url, FetchEntryListListener listListener) {
        super(context);
        mContext = context;
        this.listListener = listListener;
        this.url = url;
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
    protected void handler(JSONObject json, JSONArray jsonArray, ErrorMsg errorMsg) {
        if (errorMsg != null) {
            listListener.setError(errorMsg);
        } else listListener.setError(null);

        if (!isNull(json)) {
            Gson gson = new Gson();
            // 解析选课中心课列表
            JSONArray array = json.optJSONArray("data");
            if (!isNull(array)) {
                List<KeModel> ks = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject j = array.optJSONObject(i);
                    KeModel keModel = gson.fromJson(j.toString(), KeModel.class);
                    ks.add(keModel);
                }
                listListener.setData(ks);
            }
        }
    }

}
