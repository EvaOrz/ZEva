package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.LessonModel;

/**
 * 课程详情页面获取子课列表
 */
public class LeasonListApi extends BaseApi {
    private FetchEntryListListener listListener;
    private String url;

    /**
     * 按照kid 获取子课列表
     *
     * @param context
     * @param cid
     * @param page
     * @param listListener
     */
    public LeasonListApi(Context context, String cid, int page, FetchEntryListListener
            listListener) {
        super(context);
        mContext = context;
        isNeedJsonArray = true;
        this.listListener = listListener;
        this.url = UrlUtil.getLecturesUrl(cid, String.valueOf(page));
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
            // 解析课程详情子课
            JSONArray larray = json.optJSONArray("lectures");
            if (!isNull(larray)) {
                List<LessonModel> ls = new ArrayList<>();
                for (int i = 0; i < larray.length(); i++) {
                    LessonModel l = gson.fromJson(larray.optJSONObject(i).toString(), LessonModel
                            .class);
                    ls.add(l);
                }
                listListener.setData(ls);
            }
        }
    }

}
