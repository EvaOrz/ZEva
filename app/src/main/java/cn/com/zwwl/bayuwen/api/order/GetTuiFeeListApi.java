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
import cn.com.zwwl.bayuwen.model.OrderForMyListModel;

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
        listener.setError(errorMsg);

        if (!isNull(array)) {
            List<OrderForMyListModel> listModels = new ArrayList<>();
            Gson gson = new Gson();
            for (int i = 0; i < array.length(); i++) {
                JSONObject j = array.optJSONObject(i);
                OrderForMyListModel o = new OrderForMyListModel();
                o.setId(j.optString("id"));

                JSONObject order = j.optJSONObject("order");
                if (!isNull(order)){
                    o.setOid(order.optString("oid"));
                    o.setReal_fee(order.optString("real_fee"));
                }
                JSONObject course = j.optJSONObject("course");
                if (!isNull(course)){
                    KeModel k = gson.fromJson(course.toString(), KeModel.class);
                    List<KeModel> ks = new ArrayList<>();
                    ks.add(k);
                    o.setKeModels(ks);
                }

                listModels.add(o);
            }
            listener.setData(listModels);
        }

    }
}
