package cn.com.zwwl.bayuwen.api;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.MyCourseModel;
import cn.com.zwwl.bayuwen.util.GsonUtil;

/**
 * 已完成课程课次列表
 * Created by zhumangmang at 2018/6/1 14:27
 */
public class FCourseListApi extends BaseApi {
    private Activity activity;
    private ResponseCallBack callBack;
    private String url;

    public FCourseListApi(Activity context, String type, ResponseCallBack callBack) {
        super(context);
        this.activity = context;
        this.callBack = callBack;
        url = UrlUtil.getFCourseList() + "?type=" + type;
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
                if (array != null)
                    callBack.result(GsonUtil.parseJsonArray(MyCourseModel.UnfinishedBean.class, array.toString()), errorMsg);
            }
        });
    }
}
