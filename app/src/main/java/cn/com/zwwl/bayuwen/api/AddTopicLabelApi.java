package cn.com.zwwl.bayuwen.api;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.AddTopicLabelModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.util.GsonUtil;

public class AddTopicLabelApi extends BaseApi {

    private String url;
    private Activity activity;
    ResponseCallBack<List<AddTopicLabelModel>> listener;

    public AddTopicLabelApi(Activity context, String url, ResponseCallBack<List<AddTopicLabelModel>> listener) {
        super(context);
        this.activity = context;
        this.url = url;
        this.listener =listener;
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
                ArrayList<AddTopicLabelModel> model = null;
                if (array != null) {
                    model = GsonUtil.parseJsonArray(AddTopicLabelModel.class, array.toString());

                }
                listener.result(model, errorMsg);

            }
        });

    }
}
