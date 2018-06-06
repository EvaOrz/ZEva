package cn.com.zwwl.bayuwen.api.order;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;

/**
 * 退费列表
 */
public class GetTuiFeeListApi extends BaseApi {

    private FetchEntryListListener listener;

    public GetTuiFeeListApi(Context context, FetchEntryListListener listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        get();
    }


    @Override
    protected String getUrl() {
        return UrlUtil.getTuifeeList();
    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        if (errorMsg != null) listener.setError(errorMsg);

        if (!isNull(array)) {
            List<KeModel> keModelList = new ArrayList<>();
            Gson gson = new Gson();
            for (int i = 0; i < array.length(); i++) {
                KeModel k = gson.fromJson(array.optJSONObject(i).toString(),KeModel.class);
                k.se
            }
        }

    }
}
