package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.db.UserDataHelper;
import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.CalendarEventModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 添加课程api
 */
public class CalendarEventAddApi extends BaseApi {
    private FetchEntryListener listener;
    private Map<String, String> pamas = new HashMap<>();

    public CalendarEventAddApi(Context context, CalendarEventModel calendarEventModel, int
            totalWeeks, String totalNumber, String[] courseDate, String teacher,
                               FetchEntryListener listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        pamas.put("userId", UserDataHelper.getUid(mContext));
        pamas.put("name", calendarEventModel.getName());// 课程名称
        pamas.put("orgName", calendarEventModel.getOrgName());
        pamas.put("startTime", calendarEventModel.getStartTime());
        pamas.put("endTime", calendarEventModel.getEndTime());
        pamas.put("totalWeeks", totalWeeks + "");
        pamas.put("teacher", teacher);
        pamas.put("totalNumber", totalNumber);
        pamas.put("courseDate", courseDate.toString());
        pamas.put("jPushAlias", "jPushAlias");
        post();
    }

    @Override
    protected String getUrl() {
        return "http://t.lanxum.com/api/course-record/courseRecords";
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

        } else listener.setData(null);
    }

}
