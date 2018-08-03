package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.PintuModel;

/**
 * 获取格子详情
 */
public class PintuIntroApi extends BaseApi {

    private FetchEntryListener fetchEntryListener;
    private int selectionId;

    public PintuIntroApi(Context context, int selectionId, FetchEntryListener fetchEntryListener) {
        super(context);
        this.fetchEntryListener = fetchEntryListener;
        this.selectionId = selectionId;
        get();
    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return UrlUtil.getPintuIntro() + selectionId;
    }

    /**
     * {
     * "id":893,
     * "title":"《左传》与先秦史传散文",
     * "content":null,
     * "term":2,
     * "course_id":31
     * }
     *
     * @param json
     * @param array
     * @param errorMsg
     */
    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        fetchEntryListener.setError(errorMsg);

        if (!isNull(json)) {
            PintuModel.LectureinfoBean.SectionListBean lectureinfoBean = new PintuModel
                    .LectureinfoBean.SectionListBean();
            lectureinfoBean.setSectionId(json.optInt("id"));
            lectureinfoBean.setTitle(json.optString("title"));
            lectureinfoBean.setContent(json.optString("content"));
            fetchEntryListener.setData(lectureinfoBean);
        }

    }
}
