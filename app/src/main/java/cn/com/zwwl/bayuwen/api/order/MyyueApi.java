package cn.com.zwwl.bayuwen.api.order;

import android.app.Activity;
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
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.SearchModel;
import cn.com.zwwl.bayuwen.model.YueModel;
import cn.com.zwwl.bayuwen.util.GsonUtil;

/**
 * 我的余额api
 */
public class MyyueApi extends BaseApi {
    private ResponseCallBack<List<YueModel>> callBack;
    private int type, page;
    private Activity activity;

    /**
     * @param context
     * @param type     0:退款 1:支出
     * @param callBack
     */
    public MyyueApi(Activity context, int type, int page, ResponseCallBack<List<YueModel>>
            callBack) {
        super(context);
        this.activity = context;
        this.callBack = callBack;
        this.type = type;
        this.page = page;
        get();
    }

    @Override
    protected void handler(JSONObject json, final JSONArray array, final ErrorMsg errorMsg) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<YueModel> datas = new ArrayList<>();
                if (!isNull(array)) {
                    Gson gson = new Gson();

                    for (int i = 0; i < array.length(); i++) {
                        YueModel y = gson.fromJson(array.optJSONObject(i).toString(), YueModel
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
        if (type == 0)
            return UrlUtil.getMyyue() + "?flowType=REFUND&page=" + page;
        else return UrlUtil.getMyyue() + "?flowType=PAYMENT&page=" + page;
    }


}

