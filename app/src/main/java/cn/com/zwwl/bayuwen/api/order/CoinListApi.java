package cn.com.zwwl.bayuwen.api.order;

import android.app.Activity;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.CoinModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 积分列表api
 */
public class CoinListApi extends BaseApi {
    private ResponseCallBack<List<CoinModel>> callBack;
    private int page;
    private Activity activity;

    /**
     * @param context
     * @param callBack
     */
    public CoinListApi(Activity context, int page, ResponseCallBack<List<CoinModel>>
            callBack) {
        super(context);
        this.activity = context;
        this.callBack = callBack;
        this.page = page;
        get();
    }

    @Override
    protected void handler(JSONObject json, final JSONArray array, final ErrorMsg errorMsg) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<CoinModel> datas = new ArrayList<>();
                if (!isNull(array)) {
                    Gson gson = new Gson();

                    for (int i = 0; i < array.length(); i++) {
                        CoinModel y = gson.fromJson(array.optJSONObject(i).toString(), CoinModel
                                .class);
                        datas.add(y);
                    }
                }
                callBack.result(datas, errorMsg);

            }
        });

    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return UrlUtil.getCoinList() + "?page=" + page;
    }


}

