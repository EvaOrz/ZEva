package cn.com.zwwl.bayuwen.api;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.LessonModel;
import cn.com.zwwl.bayuwen.util.GsonUtil;

/**
 * 获取子课列表
 * Created by zhumangmang at 2018/6/4 9:35
 */
public class TransferLectureApi extends BaseApi {
    private ResponseCallBack<List<LessonModel>> callBack;
    private Activity activity;
    private String url;

    public TransferLectureApi(Activity context, String kid, ResponseCallBack<List<LessonModel>> callBack) {
        this(context, kid, 0, callBack);
    }

    public TransferLectureApi(Activity context, String kid, int type, ResponseCallBack<List<LessonModel>> callBack) {
        super(context);
        this.activity = context;
        this.callBack = callBack;
        url = UrlUtil.getLecturesList(type) + "?kid=" + kid;
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
    protected void handler(JSONObject json, final JSONArray array, final ErrorMsg errorMsg) {

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayList<LessonModel> lessonModels = null;
                if (array != null) {
                    lessonModels = GsonUtil.parseJsonArray(LessonModel.class, array.toString());
                }
                callBack.result(lessonModels, errorMsg);
            }
        });

    }
}
