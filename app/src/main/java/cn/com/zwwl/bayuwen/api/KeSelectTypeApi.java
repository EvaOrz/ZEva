package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeTypeModel;
import cn.com.zwwl.bayuwen.model.KeTypeModel.*;
import cn.com.zwwl.bayuwen.model.TeacherTypeModel;
import cn.com.zwwl.bayuwen.model.TeacherTypeModel.*;

/**
 * 获取课程筛选条件Api
 * 获取教师筛选条件Api
 */
public class KeSelectTypeApi extends BaseApi {
    private FetchEntryListener listener;
    private int search_type = 1;

    /**
     * @param context
     * @param search_type 1:课程 2:教师
     * @param listener
     */
    public KeSelectTypeApi(Context context, int search_type, FetchEntryListener
            listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        this.search_type = search_type;
        get();
    }


    @Override
    protected String getUrl() {
        return UrlUtil.getCDetailUrl(null) + "/find_content?search_type=" + search_type;
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        if (errorMsg != null)
            listener.setError(errorMsg);
        else listener.setError(null);

        if (!isNull(json)) {
            Gson gson = new Gson();
            if (search_type == 1) {

                KeTypeModel keTypeModel = gson.fromJson(json.toString(), KeTypeModel.class);
                listener.setData(keTypeModel);

            } else if (search_type == 2) {

                TeacherTypeModel teacherTypeModel = new TeacherTypeModel();
                JSONArray courseType = json.optJSONArray("courseType");
                List<CourseTypeBean> cs = new ArrayList<>();
                for (int i = 0; i < courseType.length(); i++) {
                    CourseTypeBean c = gson.fromJson(courseType.optJSONObject(i).toString(),
                            CourseTypeBean.class);
                    cs.add(c);
                }
                teacherTypeModel.setCourseType(cs);

                JSONArray grades = json.optJSONArray("grades");
                List<GradesModel> gs = new ArrayList<>();
                for (int i = 0; i < grades.length(); i++) {
                    GradesModel g = new GradesModel();
                    JSONObject js = grades.optJSONObject(i);
                    g.setName(js.optString("name"));
                    g.setType(js.optString("type"));
                    g.setGrade(parseGradesBean(gson, js.optJSONArray("grade")));
                    gs.add(g);
                }
                teacherTypeModel.setGrades(gs);
                listener.setData(teacherTypeModel);
            }

        }

    }

    private List<GradesBean> parseGradesBean(Gson gson, JSONArray array) {
        List<GradesBean> cs = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            GradesBean c = gson.fromJson(array.optJSONObject(i).toString(),
                    GradesBean.class);
            cs.add(c);
        }
        return cs;
    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }

}
