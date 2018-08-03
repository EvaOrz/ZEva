package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.CalendarEventModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 获取第三方日历事件详情
 */
public class CalendarDetailApi extends BaseApi {
    private Map<String, String> pamas = new HashMap<>();
    private FetchEntryListener listener;
    private String id;

    public CalendarDetailApi(Context context, String id, FetchEntryListener listener) {
        super(context);
        mContext = context;
        this.id = id;
        this.listener = listener;
        get();
    }


    @Override
    protected String getUrl() {
        return UrlUtil.addCalendarEvent() + "/" + id;
    }

    @Override
    protected Map<String, String> getPostParams() {
        return pamas;
    }

    @Override
    protected void handler(JSONObject json, JSONArray jsonArray, ErrorMsg errorMsg) {
        listener.setError(errorMsg);
        if (!isNull(json)) {

            CalendarEventModel c = new CalendarEventModel();
            c.parseCalendarEventModel(json, c);
            listener.setData(c);
        } else listener.setData(null);
    }

}
