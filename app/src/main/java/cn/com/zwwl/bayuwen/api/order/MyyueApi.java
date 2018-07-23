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
import cn.com.zwwl.bayuwen.model.YueModel;

/**
 * 我的余额api
 */
public class MyyueApi extends BaseApi {
    private FetchEntryListListener listener;
    private int type;

    /**
     * @param context
     * @param type     0:退款 1:支出
     * @param listener
     */
    public MyyueApi(Context context, int type, FetchEntryListListener listener) {
        super(context);
        this.listener = listener;
        this.type = type;
        get();
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        listener.setError(errorMsg);
        if (!isNull(array)) {
            Gson gson = new Gson();
            List<YueModel> datas = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                YueModel y = gson.fromJson(array.optJSONObject(i).toString(), YueModel.class);
                datas.add(y);
            }
            listener.setData(datas);
        }
    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        if (type == 0)
            return UrlUtil.getMyyue() + "?flowType=REFUND";
        else return UrlUtil.getMyyue() + "?flowType=PAYMENT";
    }


}

