package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.EvalContentModel;

/**
 * 课节报告数据
 */
public class LectureEvalApi extends BaseApi {
    private FetchEntryListener listener;
    private String kid, lecture_id;

    public LectureEvalApi(Context context, String kid, String lecture_id, FetchEntryListener listener) {
        super(context);
        this.kid = kid;
        this.lecture_id = lecture_id;
        this.listener = listener;
        get();
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        listener.setError(errorMsg);
        if (!isNull(json)) {
            LectureEvalModel model = new LectureEvalModel();
            Gson gson = new Gson();
            JSONObject tj = json.optJSONObject("teacher");
            if (!isNull(tj)) {
                EvalContentModel.DataBean td = gson.fromJson(tj.toString(), EvalContentModel.DataBean.class);
                model.setTeacher(td);
            }

            JSONObject hj = json.optJSONObject("head_teacher");
            if (!isNull(hj)) {
                EvalContentModel.DataBean hd = gson.fromJson(hj.toString(), EvalContentModel.DataBean.class);
                model.setHeadteacher(hd);
            }

            JSONObject sj = json.optJSONObject("school");
            if (!isNull(sj)) {
                EvalContentModel.DataBean sd = new EvalContentModel.DataBean();
                sd.setTid(sj.optString("id"));
                sd.setName(sj.optString("name"));
                model.setSchool(sd);
            }
            listener.setData(model);
        }

    }

    @Override
    protected String getUrl() {
        return UrlUtil.getLectureEval() + "?kid=" + kid + "&lecture_id=" + lecture_id;
    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }

    public class LectureEvalModel extends Entry {
        private EvalContentModel.DataBean teacher;
        private EvalContentModel.DataBean headteacher;
        private EvalContentModel.DataBean school;

        public EvalContentModel.DataBean getTeacher() {
            return teacher;
        }

        public void setTeacher(EvalContentModel.DataBean teacher) {
            this.teacher = teacher;
        }

        public EvalContentModel.DataBean getHeadteacher() {
            return headteacher;
        }

        public void setHeadteacher(EvalContentModel.DataBean headteacher) {
            this.headteacher = headteacher;
        }

        public EvalContentModel.DataBean getSchool() {
            return school;
        }

        public void setSchool(EvalContentModel.DataBean school) {
            this.school = school;
        }
    }
}

