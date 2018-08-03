package cn.com.zwwl.bayuwen.api.order;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.OrderForMyListModel;
import cn.com.zwwl.bayuwen.model.TuanDianModel;

/**
 * 获取团购垫付之后的码
 */
public class GetTuanDiancodesApi extends BaseApi {

    private FetchEntryListListener listener;
    private Map<String, String> pamas = new HashMap<>();
    private String kid, code;

    public GetTuanDiancodesApi(Context context, String kid, String code, FetchEntryListListener
            listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        this.kid = kid;
        this.code = code;
        get();
    }


    @Override
    protected String getUrl() {
        return UrlUtil.getTuanCodes() + "?kid=" + kid + "&code=" + code;
    }

    @Override
    protected Map<String, String> getPostParams() {
        return pamas;
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        listener.setError(errorMsg);

        if (!isNull(array)) {
            List<TuanDianModel> listModels = new ArrayList<>();
            Gson gson = new Gson();
            for (int i = 0; i < array.length(); i++) {
                TuanDianModel t = gson.fromJson(array.optJSONObject(i).toString(), TuanDianModel
                        .class);
                listModels.add(t);
            }
            listener.setData(listModels);
        }

    }
}
