package cn.com.zwwl.bayuwen.api;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.LessonModel;
import cn.com.zwwl.bayuwen.util.GsonUtil;

public class TraceSearchApi extends cn.com.zwwl.bayuwen.http.BaseApi {
    private ResponseCallBack<List<LessonModel> > callBack;
    private Activity activity;
    private String url;

    public TraceSearchApi(Activity context, String content, ResponseCallBack<List<LessonModel> >  callBack) {
        super(context);
        this.callBack = callBack;
        url = UrlUtil.traceSearch() + "?keyword=" + content;
        this.activity = context;
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
    protected void handler(final JSONObject json, final JSONArray array, final ErrorMsg errorMsg) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<LessonModel> model = null;
                if (array != null)
                    model = GsonUtil.parseJsonArray(LessonModel.class, array.toString());
                callBack.result(model, errorMsg);
            }
        });
    }
}
