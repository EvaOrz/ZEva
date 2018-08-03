package cn.com.zwwl.bayuwen.api;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import cn.com.zwwl.bayuwen.db.UserDataHelper;
import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.MessageModel;
import cn.com.zwwl.bayuwen.util.GsonUtil;

public class NotifyMessageApi extends BaseApi {
    private int page;
    private int pageSize;
    private String url;
    private Activity activity;
    private String UserId;
    ResponseCallBack<MessageModel> listener;

    public NotifyMessageApi(Activity context, int page,int pageSize, String url, ResponseCallBack<MessageModel> listener) {
        super(context);
        this.activity = context;
        this.listener = listener;
        this.page = page;
        this.pageSize=pageSize;
        this.UserId = UserDataHelper.getUid(this.activity);

        this.url = url + "?receiveUserId=" + UserId + "&page=" + page+"&pageSize="+pageSize;
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
                MessageModel model = null;
                if (json != null) {
                    model = GsonUtil.parseJson(MessageModel.class, json.toString());
                }
                listener.result(model, errorMsg);

            }
        });
    }
}
