package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.TuanInfoModel;

/**
 * 团购
 */
public class TuanGouApi extends BaseApi {
    private FetchEntryListener listener;
    private String kid;
    private Map<String, String> pamas = new HashMap<>();

    /**
     * 获取团购信息，可从课程详情中解析
     *
     * @param context
     * @param kid
     * @param listener
     */
    public TuanGouApi(Context context, String kid, FetchEntryListener listener) {
        super(context);
        mContext = context;
        this.kid = kid;
        this.listener = listener;
        get();
    }

    @Override
    protected String getUrl() {
        return UrlUtil.getTuan() + "/" + kid;
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        if (errorMsg != null)
            listener.setError(errorMsg);
        if (!isNull(json)) {
            TuanInfoModel tuanInfoModel = new TuanInfoModel();
            tuanInfoModel.parseTuanInfoModel(json, tuanInfoModel);
            listener.setData(tuanInfoModel);
        } else {
            listener.setData(null);
        }

    }

    @Override
    protected Map<String, String> getPostParams() {
        return pamas;
    }


}

