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
 * 提交课节报告
 */
public class LectureEvalCommitApi extends BaseApi {
    private Map<String, String> pamas = new HashMap<>();
    private FetchEntryListener listener;

    public LectureEvalCommitApi(Context context, String kid, String lecture_id, String tid, String tsa, String hid, String hsa, String sid, String ssa, FetchEntryListener listener) {
        super(context);
        pamas.put("kid", kid);
        pamas.put("lecture_id", lecture_id);
        pamas.put("teacher_id", tid);
        pamas.put("teacher_satisfaction", tsa);
        pamas.put("headTeacher_id", hid);
        pamas.put("headTeacher_satisfaction", hsa);
        pamas.put("school_id", sid);
        pamas.put("school_satisfaction", ssa);
        this.listener = listener;
        post();
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        listener.setError(errorMsg);
    }

    @Override
    protected String getUrl() {
        return UrlUtil.commitLectureEval();
    }

    @Override
    protected Map<String, String> getPostParams() {
        return pamas;
    }

}

