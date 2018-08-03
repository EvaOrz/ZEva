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
import cn.com.zwwl.bayuwen.model.ZanTeacherModel;
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
        PraiseModel praiseModel = new PraiseModel();
        if (!isNull(json)) {
            JSONArray tarray = json.optJSONArray("teachers");
            JSONArray garray = json.optJSONArray("stu_advisors");
            JSONArray zarray = json.optJSONArray("tutors");
            List<ZanTeacherModel> ts = new ArrayList<>();
            List<ZanTeacherModel> gs = new ArrayList<>();
            List<ZanTeacherModel> zs = new ArrayList<>();
            if (!isNull(tarray)) {
                for (int i = 0; i < tarray.length(); i++) {
                    ZanTeacherModel t = new ZanTeacherModel();
                    ts.add(t.parseGuwenModel(tarray.optJSONObject(i), t));
                }
                praiseModel.setTeacherModels(ts);

            }
            if (!isNull(garray)) {
                for (int i = 0; i < garray.length(); i++) {
                    ZanTeacherModel g = new ZanTeacherModel();
                    gs.add(g.parseGuwenModel(garray.optJSONObject(i), g));
                }
                praiseModel.setGuwenModels(gs);
            }
            if (!isNull(zarray)) {
                for (int i = 0; i < zarray.length(); i++) {
                    ZanTeacherModel z = new ZanTeacherModel();
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
        private List<ZanTeacherModel> teacherModels = new ArrayList<>();
        private List<ZanTeacherModel> guwenModels = new ArrayList<>();
        private List<ZanTeacherModel> zhujiaoModels = new ArrayList<>();

        public PraiseModel() {

        }

        public List<ZanTeacherModel> getTeacherModels() {
            return teacherModels;
        }

        public void setTeacherModels(List<ZanTeacherModel> teacherModels) {
            this.teacherModels = teacherModels;
        }

        public List<ZanTeacherModel> getGuwenModels() {
            return guwenModels;
        }

        public void setGuwenModels(List<ZanTeacherModel> guwenModels) {
            this.guwenModels = guwenModels;
        }

        public List<ZanTeacherModel> getZhujiaoModels() {
            return zhujiaoModels;
        }

        public void setZhujiaoModels(List<ZanTeacherModel> zhujiaoModels) {
            this.zhujiaoModels = zhujiaoModels;
        }
    }

}
