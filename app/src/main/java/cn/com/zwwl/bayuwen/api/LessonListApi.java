package cn.com.zwwl.bayuwen.api;

import android.app.Activity;
import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.BaseResponse;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.LessonModel;
import cn.com.zwwl.bayuwen.util.GsonUtil;

/**
 * 课程详情页面获取子课列表
 */
public class LessonListApi extends BaseApi {
    private FetchEntryListListener listListener;
    private ResponseCallBack<BaseResponse> callBack;
    private Activity activity;
    private String url;

    /**
     * 按照kid 获取子课列表
     */
    public LessonListApi(Context context, String cid, int page, FetchEntryListListener
            listListener) {
        super(context);
        mContext = context;
        this.listListener = listListener;
        this.url = UrlUtil.getLecturesUrl(cid, String.valueOf(page));
        get();
    }

    public LessonListApi(Activity context, String cid, int page, ResponseCallBack<BaseResponse>
            callBack) {
        super(context);
        this.activity = context;
        this.callBack = callBack;
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
    protected void handler(final JSONObject json, JSONArray jsonArray, final ErrorMsg errorMsg) {
        if (callBack != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    BaseResponse response = null;
                    if (json != null)
                        response = GsonUtil.parseJson(BaseResponse.class, json.toString());
                    callBack.result(response, errorMsg);
                }
            });
        } else {
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

}
