package cn.com.zwwl.bayuwen.api;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.umeng.debug.log.E;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.EleCourseModel;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.fm.AlbumModel;

/**
 * Created by lousx on 2018/5/23.
 */

public class EleCourseListApi extends BaseApi {
    private List<EleCourseModel> eleCourseModels = new ArrayList<>();
    private Map<String, String> pamas = new HashMap<>();
    private FetchEntryListListener<EleCourseModel> listener;
    private String url;

    public EleCourseListApi(Context context, FetchEntryListListener<EleCourseModel> listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        this.url = UrlUtil.getEleCourseListUrl();
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
        if (!isNull(jsonArray)) {
            Gson gson = new Gson();
            eleCourseModels =  gson.fromJson(String.valueOf(jsonArray),new TypeToken<List<EleCourseModel>>(){}.getType());
            Log.d("EleCourseListApi",eleCourseModels.size()+"========");
            listener.setData(eleCourseModels);
        }
    }

}
