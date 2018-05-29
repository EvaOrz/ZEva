package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeTypeModel;

/**
 * 获取课程筛选条件接口
 */
public class KeSelectTypeApi extends BaseApi {
    private FetchEntryListener listener;

    /**
     * @param context
     * @param listener
     */
    public KeSelectTypeApi(Context context, FetchEntryListener
            listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        get();
    }


    @Override
    protected String getUrl() {
        return UrlUtil.getCDetailUrl(null) + "/find_content";
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        if (errorMsg != null)
            listener.setError(errorMsg);
        else listener.setError(null);

        if (!isNull(json)) {
            Gson gson = new Gson();
            KeTypeModel keTypeModel = gson.fromJson(json.toString(), KeTypeModel.class);
            listener.setData(keTypeModel);

        }

    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }

}
