package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.PintuModel;

/**
 * 获取所有的拼图课程
 */
public class PintuListApi extends BaseApi {

    private FetchEntryListListener fetchEntryListener;

    public PintuListApi(Context context, FetchEntryListListener fetchEntryListener) {
        super(context);
        this.fetchEntryListener = fetchEntryListener;
        get();
    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return UrlUtil.getPintu();
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        fetchEntryListener.setError(errorMsg);

        if (!isNull(array)) {
            List<PintuModel> pintuModels = new ArrayList<>();
            Gson gson = new Gson();
            for (int i = 0; i < array.length(); i++) {
                PintuModel p = gson.fromJson(array.optJSONObject(i).toString(), PintuModel.class);
                pintuModels.add(p);
            }
            fetchEntryListener.setData(pintuModels);
        }
    }
}
