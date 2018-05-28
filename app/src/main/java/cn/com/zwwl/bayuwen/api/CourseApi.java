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
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.GroupBuyModel;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.LessonModel;
import cn.com.zwwl.bayuwen.model.TeacherModel;

/**
 * 课程相关接口
 */

public class CourseApi extends BaseApi {
    private Map<String, String> pamas = new HashMap<>();
    private FetchEntryListener listener;
    private String url;

    /**
     * 获取课程详情
     *
     * @param context
     * @param id
     * @param listener
     */
    public CourseApi(Context context, String id, FetchEntryListener listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        this.url = UrlUtil.getCDetailUrl(id);
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
            JSONObject course = json.optJSONObject("course");
            Gson gson = new Gson();
            KeModel keModel = gson.fromJson(course.toString(), KeModel.class);
            if (!isNull(course)) {
                JSONObject gjson = course.optJSONObject("groupbuy");
                if (!isNull(gjson)) {
                    GroupBuyModel groupBuyModel = new GroupBuyModel();
                    groupBuyModel.parseGroupBuyModel(gjson, groupBuyModel);
                    keModel.setGroupbuy(groupBuyModel);
                }
            }
            JSONArray larray = json.optJSONArray("lessons");
            if (!isNull(larray)) {
                List<LessonModel> ls = new ArrayList<>();
                for (int i = 0; i < larray.length(); i++) {
                    LessonModel l = gson.fromJson(larray.optJSONObject(i).toString(), LessonModel
                            .class);
                    ls.add(l);
                }
                keModel.setLessonModels(ls);
            }

            JSONArray tarray = json.optJSONArray("teacher");
            if (!isNull(tarray)) {
                List<TeacherModel> ts = new ArrayList<>();
                for (int i = 0; i < tarray.length(); i++) {
                    TeacherModel t = new TeacherModel();
                    t.parseTeacherModel(tarray.optJSONObject(i), t);
                    ts.add(t);
                }
                keModel.setTeacherModels(ts);
            }
            listener.setData(keModel);
        }
    }

}
