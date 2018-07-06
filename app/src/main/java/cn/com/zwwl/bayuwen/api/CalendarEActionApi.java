package cn.com.zwwl.bayuwen.api;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

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
public class CalendarEActionApi extends BaseApi {
    private FetchEntryListener listener;
    private Map<String, String> pamas = new HashMap<>();
    private String url = "";


    /**
     * 添加课程
     *
     * @param context
     * @param calendarEventModel
     * @param courseDate
     * @param listener
     */
    public CalendarEActionApi(Context context, CalendarEventModel calendarEventModel, String
            courseDate, FetchEntryListener listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        pamas.put("userId", UserDataHelper.getUid(mContext));
        pamas.put("name", calendarEventModel.getName());// 课程名称
        pamas.put("orgName", calendarEventModel.getOrgName());
        pamas.put("startTime", calendarEventModel.getStartTime() + ":00");
        pamas.put("endTime", calendarEventModel.getEndTime() + ":00");
        pamas.put("totalWeeks", calendarEventModel.getTotalWeeks() + "");
        if (!TextUtils.isEmpty(calendarEventModel.getTeacherName()))
            pamas.put("teacher", calendarEventModel.getTeacherName());
        if (!TextUtils.isEmpty(calendarEventModel.getAddress()))
            pamas.put("address", calendarEventModel.getAddress());
        pamas.put("orgId", calendarEventModel.getOutOrgId());
        pamas.put("totalNumber", calendarEventModel.getTotalNumber() + "");
        pamas.put("courseDates", courseDate);
        pamas.put("jPushAlias", UserDataHelper.getPushAlias(mContext));
        url += UrlUtil.addCalendarEvent();
        post();
    }

    /**
     * 根据id删除第三方日历指定日期
     *
     * @param context
     * @param recordId     日历id
     * @param courseDateId 日期id
     * @param listener
     */
    public CalendarEActionApi(Context context, String recordId, String courseDateId,
                              FetchEntryListener
                                      listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        pamas.put("recordId", recordId);
        pamas.put("courseDateId", courseDateId);
        url += UrlUtil.addCalendarEvent() + "/del";
        post();
    }

    /**
     * 修改(添加)第三方课程日历--日期信息
     *
     * @param context
     * @param recordId
     * @param courseDate
     * @param flag
     * @param listener
     */
    public CalendarEActionApi(Context context, String recordId, String courseDate, int flag,
                              FetchEntryListener
                                      listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        pamas.put("courseDate", courseDate);
        url += UrlUtil.addCalendarEvent() + "/" + recordId;
        patch();
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
        listener.setError(errorMsg);

        if (!isNull(json)) {
            if (json.has("recordId")) {
                ErrorMsg errorMsg1 = new ErrorMsg();
                errorMsg1.setNo(json.optInt("recordId"));
                listener.setData(errorMsg1);
            }

        } else listener.setData(null);
    }

}
