package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.StudyingModel;

/**
 * 判断是否可以转班、调课，获取期中期末报告是否已经评价状态
 */
public class CourseStateApi extends BaseApi {
    private String kid;
    private ResponseCallBack<CourseStateModel> listener;

    public CourseStateApi(Context context, String kid, ResponseCallBack<CourseStateModel>
            listener) {
        super(context);
        this.listener = listener;
        this.kid = kid;
        get();
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        CourseStateModel courseStateModel = null;
        if (!isNull(json)) {
            Gson gson = new Gson();
            courseStateModel = gson.fromJson(json.toString(), CourseStateModel.class);
        }
        listener.result(courseStateModel, errorMsg);
    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return UrlUtil.getCourseState() + "?kid=" + kid;
    }

    /**
     */
    public class CourseStateModel extends Entry {

        private boolean midterm_report;// 是否评价过期中报告
        private boolean end_term_report;// 是否评价过期末报告
        private boolean transfer_class;// 是否可以转班
        private boolean transfer_course;// 是否可以调课

        public boolean isMidterm_report() {
            return midterm_report;
        }

        public void setMidterm_report(boolean midterm_report) {
            this.midterm_report = midterm_report;
        }

        public boolean isEnd_term_report() {
            return end_term_report;
        }

        public void setEnd_term_report(boolean end_term_report) {
            this.end_term_report = end_term_report;
        }

        public boolean isTransfer_class() {
            return transfer_class;
        }

        public void setTransfer_class(boolean transfer_class) {
            this.transfer_class = transfer_class;
        }

        public boolean isTransfer_course() {
            return transfer_course;
        }

        public void setTransfer_course(boolean transfer_course) {
            this.transfer_course = transfer_course;
        }
    }
}
