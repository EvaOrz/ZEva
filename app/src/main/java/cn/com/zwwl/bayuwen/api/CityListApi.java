package cn.com.zwwl.bayuwen.api;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.CitySortModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.util.GsonUtil;

public class CityListApi extends BaseApi {

    private String url;
    private Activity activity;
    ResponseCallBack<CitySortModel> listener;
    private String searchText;

    public CityListApi(Activity context, String url, ResponseCallBack<CitySortModel> listener) {
        super(context);
        this.activity = context;
        this.url = url;
        this.listener =listener;
        get();
    }
    public CityListApi(Activity context, String url,String searchText, ResponseCallBack<CitySortModel> listener) {
        super(context);
        this.activity = context;
        this.url = url;
        this.listener =listener;
        this.searchText=searchText;
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
                CitySortModel model = null;
                if (json != null) {
                    model = GsonUtil.parseJson(CitySortModel.class, json.toString());

                }
                listener.result(model, errorMsg);

            }
        });

    }
}
