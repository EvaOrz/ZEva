package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.GuwenModel;
import cn.com.zwwl.bayuwen.model.TeacherModel;

/**
 * 选课首页教师排行接口
 */
public class PraiseListApi extends BaseApi {
    private FetchEntryListener listener;
    private String url;

    public PraiseListApi(Context context, FetchEntryListener listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        this.url = UrlUtil.getTopListUrl();
        get();
    }

    @Override
    protected String getUrl() {
        return url;
    }

    @Override
    protected Map<String, String> getPostParams() {
        return new HashMap<>();
    }

    @Override
    protected void handler(JSONObject json, JSONArray jsonArray, ErrorMsg errorMsg) {
        if (errorMsg != null) {
            listener.setError(errorMsg);
        }
        PraiseModel praiseModel = null;
        if (!isNull(json)) {
            JSONArray tarray = json.optJSONArray("teachers");
            JSONArray garray = json.optJSONArray("stu_advisors");
            JSONArray zarray = json.optJSONArray("tutors");
            List<TeacherModel> ts = new ArrayList<>();
            List<GuwenModel> gs = new ArrayList<>();
            List<GuwenModel> zs = new ArrayList<>();
            if (!isNull(tarray)) {
                for (int i = 0; i < tarray.length(); i++) {
                    TeacherModel t = new TeacherModel();
                    ts.add(t.parseTeacherModel(tarray.optJSONObject(i), t));
                }
                praiseModel.setTeacherModels(ts);

            }
            if (!isNull(garray)) {
                for (int i = 0; i < garray.length(); i++) {
                    GuwenModel g = new GuwenModel();
                    gs.add(g.parseGuwenModel(garray.optJSONObject(i), g));
                }
                praiseModel.setGuwenModels(gs);
            }
            if (!isNull(zarray)) {
                for (int i = 0; i < zarray.length(); i++) {
                    GuwenModel z = new GuwenModel();
                    zs.add(z.parseGuwenModel(zarray.optJSONObject(i), z));
                }
                praiseModel.setZhujiaoModels(zs);
            }
            listener.setData(praiseModel);
        } else listener.setData(null);
    }

    /**
     * 教师排行临时model
     */
    public class PraiseModel extends Entry {
        private List<TeacherModel> teacherModels = new ArrayList<>();
        private List<GuwenModel> guwenModels = new ArrayList<>();
        private List<GuwenModel> zhujiaoModels = new ArrayList<>();

        public PraiseModel() {

        }

        public List<TeacherModel> getTeacherModels() {
            return teacherModels;
        }

        public void setTeacherModels(List<TeacherModel> teacherModels) {
            this.teacherModels = teacherModels;
        }

        public List<GuwenModel> getGuwenModels() {
            return guwenModels;
        }

        public void setGuwenModels(List<GuwenModel> guwenModels) {
            this.guwenModels = guwenModels;
        }

        public List<GuwenModel> getZhujiaoModels() {
            return zhujiaoModels;
        }

        public void setZhujiaoModels(List<GuwenModel> zhujiaoModels) {
            this.zhujiaoModels = zhujiaoModels;
        }
    }

}
