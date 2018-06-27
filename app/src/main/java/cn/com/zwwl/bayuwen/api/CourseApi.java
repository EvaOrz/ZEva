package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

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
import cn.com.zwwl.bayuwen.model.PromotionModel;
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

    /**
     * 根据兑换的课程码获取课程详情
     *
     * @param context
     * @param code
     * @param flag
     * @param listener
     */
    public CourseApi(Context context, String code, int flag, FetchEntryListener listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        this.url = UrlUtil.getKemodelByCode()+"?course_code="+code;
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
                    GroupBuyModel groupBuyModel = gson.fromJson(gjson.toString(), GroupBuyModel.class);
                    keModel.setGroupbuy(groupBuyModel);
                }

                JSONArray promotion = course.optJSONArray("promotion");
                if (!isNull(promotion)) {
                    List<PromotionModel> ps = new ArrayList<>();
                    for (int i = 0; i < promotion.length(); i++) {
                        PromotionModel p = new PromotionModel();
                        p.parsePromotionModel(promotion.optJSONObject(i), p);
                        ps.add(p);
                    }
                    keModel.setPromotionModels(ps);
                }
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
