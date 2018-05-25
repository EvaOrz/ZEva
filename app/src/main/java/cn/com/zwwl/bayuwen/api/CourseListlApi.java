package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 获取课程列表页面
 */
public class CourseListlApi extends BaseApi {
    private Map<String, String> pamas = new HashMap<>();
    private FetchEntryListener listener;
    private String url;

    public CourseListlApi(Context context, String cid, int page, FetchEntryListener listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        this.url = UrlUtil.getLecturesUrl(cid, String.valueOf(page));
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

        }
    }

}
