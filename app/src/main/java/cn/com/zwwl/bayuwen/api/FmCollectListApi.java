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
import cn.com.zwwl.bayuwen.model.FmListCollectModel;
import cn.com.zwwl.bayuwen.model.FmMyCourceListModel;
import cn.com.zwwl.bayuwen.util.GsonUtil;

public class FmCollectListApi extends BaseApi {

    private String url;
    private Activity activity;
    private String type;
    ResponseCallBack<FmListCollectModel> listener;
    private String titlename;

    public FmCollectListApi(Activity context , String url, ResponseCallBack<FmListCollectModel> listener) {
        super(context);
        this.activity = context;
        this.type="2";
        this.url =url+"?type="+this.type;
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
                FmListCollectModel model = null;
                if (json != null) {
                    model = GsonUtil.parseJson(FmListCollectModel.class, json.toString());

                }
                listener.result(model, errorMsg);

            }
        });

    }
}
