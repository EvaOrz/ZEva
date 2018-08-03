package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.model.CalendarJigouModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 获取第三方机构列表
 */
public class CalendarJigouListApi extends BaseApi {
    private FetchEntryListListener listListener;

    public CalendarJigouListApi(Context context, FetchEntryListListener listListener) {
        super(context);
        mContext = context;
        this.listListener = listListener;
        get();
    }

    @Override
    protected String getUrl() {
        return UrlUtil.getJiGouList();
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        if (errorMsg != null) {
            listListener.setError(errorMsg);
        }
        if (!isNull(array)) {
            Gson gson = new Gson();
            List<CalendarJigouModel> list = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                CalendarJigouModel c = gson.fromJson(array.optJSONObject(i).toString(),
                        CalendarJigouModel.class);
                list.add(c);
            }
            listListener.setData(list);
        } else listListener.setData(null);
    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }
}
