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
import cn.com.zwwl.bayuwen.model.FaPiaoModel;

/**
 * 开票历史api
 */
public class FaPiaoHistoryApi extends BaseApi {
    private FetchEntryListListener listListener;

    /**
     * 获取开发票历史列表
     *
     * @param context
     * @param listListener
     */
    public FaPiaoHistoryApi(Context context, FetchEntryListListener listListener) {
        super(context);
        mContext = context;
        this.listListener = listListener;
        get();
    }

    @Override
    protected String getUrl() {
        return UrlUtil.getPiaoHistoryUrl();
    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        listListener.setError(errorMsg);
        if (!isNull(json)) {
            JSONArray jsonArray = json.optJSONArray("invoices");
            if (!isNull(jsonArray)) {
                List<FaPiaoModel> models = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < jsonArray.length(); i++) {
                    FaPiaoModel mm = gson.fromJson(jsonArray.optJSONObject(i).toString(),
                            FaPiaoModel.class);
                    models.add(mm);
                }
                listListener.setData(models);
            }


        }

    }
}
