package cn.com.zwwl.bayuwen.api;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.TopicDetailModel;
import cn.com.zwwl.bayuwen.util.GsonUtil;

public class TopicDetailApi extends BaseApi {

    private String url;
    private Activity activity;
    private String topicId;
    ResponseCallBack<TopicDetailModel> listener;

    public TopicDetailApi(Activity context,  String url,String topicId, ResponseCallBack<TopicDetailModel> listener) {
        super(context);
        this.activity = context;
        this.listener = listener;
        this.topicId = topicId;

        this.url = url + "/" + topicId ;
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
    protected void handler(final JSONObject json, JSONArray array, final ErrorMsg errorMsg) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TopicDetailModel model = null;
                if (json != null) {
                    model = GsonUtil.parseJson(TopicDetailModel.class, json.toString());
                }
                listener.result(model, errorMsg);

            }
        });
    }
}
