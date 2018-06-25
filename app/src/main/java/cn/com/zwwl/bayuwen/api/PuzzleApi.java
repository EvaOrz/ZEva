package cn.com.zwwl.bayuwen.api;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.PuzzleModel;
import cn.com.zwwl.bayuwen.util.GsonUtil;

public class PuzzleApi extends BaseApi {
    private Activity activity;
    private String url;
    private ResponseCallBack<ArrayList<PuzzleModel>> callBack;

    public PuzzleApi(Activity context, String cId, ResponseCallBack<ArrayList<PuzzleModel>> callBack) {
        super(context);
        this.activity = context;
        this.callBack = callBack;
        url = UrlUtil.puzzle() + "?courseId=" + cId;
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
                ArrayList<PuzzleModel> models = null;
                if (array != null)
                    models = GsonUtil.parseJsonArray(PuzzleModel.class, array.toString());
                callBack.result(models, errorMsg);
            }
        });
    }
}
