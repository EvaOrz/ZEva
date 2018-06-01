package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.model.CalendarEventModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 获取日历页面事件列表api
 */
public class CalendarEventListApi extends BaseApi {
    private Map<String, List<CalendarEventModel>> eventMaps = new HashMap<>();

    private FetchCalendarEventMapListener listener;
    private String url = "";

    public CalendarEventListApi(Context context, String startDate, String endDate,
                                FetchCalendarEventMapListener listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        url = UrlUtil.getCalendarEvents() + "?startDate=" + startDate + "&endDate=" + endDate;
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
            listener.setError(errorMsg);
        }
        if (!isNull(json)) {
            Iterator keys = json.keys();
            while (keys.hasNext()) {
                String key = String.valueOf(keys.next());
                List<CalendarEventModel> eventModels = new ArrayList<>();
                Gson gson = new Gson();
                JSONArray array = json.optJSONArray(key);
                if (!isNull(array)) {
                    for (int i = 0; i < array.length(); i++) {
                        String ss = array.optJSONObject(i).toString();
                        CalendarEventModel c = gson.fromJson(ss, CalendarEventModel.class);
                        eventModels.add(c);
                    }
                }
                eventMaps.put(key, eventModels);
            }
            listener.setData(eventMaps);
        } else listener.setData(null);
    }

    public interface FetchCalendarEventMapListener {
        public void setData(Map<String, List<CalendarEventModel>> maps);

        public void setError(ErrorMsg error);
    }

}
