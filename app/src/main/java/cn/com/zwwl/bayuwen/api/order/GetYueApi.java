package cn.com.zwwl.bayuwen.api.order;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.db.UserDataHelper;
import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 获取账户余额api
 */
public class GetYueApi extends BaseApi {
    private FetchEntryListener listener;


    public GetYueApi(Context context, FetchEntryListener listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        get();
    }

    @Override
    protected String getUrl() {
        return UrlUtil.getMyYue() + "?uid=" + UserDataHelper.getUid(mContext);
    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }

    //{"success":true,"statusCode":200,"data":{"uid":"260921","amount":null},"err_msg":""}
    @Override
    protected void handler(JSONObject json, JSONArray jsonArray, ErrorMsg errorMsg) {
        if (errorMsg != null) {
            listener.setError(errorMsg);
        } else listener.setError(null);
        if (!isNull(json)) {
            ErrorMsg e = new ErrorMsg();
            e.setDesc(json.optDouble("amount", 0.00) + "");
            listener.setData(e);
        }
    }
}
