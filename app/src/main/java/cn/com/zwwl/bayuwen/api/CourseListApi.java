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

/**
 * 获取课程列表接口
 */
public class CourseListApi extends BaseApi {
    private Map<String, String> pamas = new HashMap<>();
    private FetchEntryListListener listListener;
    private String url;

    /**
     * 按分类获取列表
     *
     * @param context
     * @param cid
     * @param page
     * @param listListener
     */
    public CourseListApi(Context context, String cid, int page, FetchEntryListListener
            listListener) {
        super(context);
        mContext = context;
        isNeedJsonArray = true;
        this.listListener = listListener;
        this.url = UrlUtil.getLecturesUrl(cid, String.valueOf(page));
        get();
    }

    /**
     * 按关键字获取列表
     *
     * @param context
     * @param listListener
     */
    public CourseListApi(Context context, FetchEntryListListener listListener) {
        super(context);
        mContext = context;
        this.listListener = listListener;
        this.url = UrlUtil.getCDetailUrl(null) + "/search";
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
            listListener.setError(errorMsg);
        }
        if (!isNull(json)) {
            JSONArray array = json.optJSONArray("data");
            if (!isNull(array)) {
                List<KeModel> ks = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject j = array.optJSONObject(i);
                    Gson gson = new Gson();
                    KeModel keModel = gson.fromJson(j.toString(), KeModel.class);
                    ks.add(keModel);
                }
                listListener.setData(ks);
            } else {
                listListener.setData(null);
            }


        }
    }

}
