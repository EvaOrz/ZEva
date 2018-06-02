package cn.com.zwwl.bayuwen.api.order;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 生成订单
 */
public class MakeOrderApi extends BaseApi {
    private String baseUrl = UrlUtil.setOrder();
    private FetchEntryListener listener;

    /**
     * @param context
     * @param channel     1支付宝 2微信 11手动支付
     * @param coupon_code
     * @param aid
     * @param saleno      推荐码
     * @param assets      账户余额
     * @param item
     * @param groupbuy
     * @param listener
     */
    public MakeOrderApi(Context context, String channel, String coupon_code, String aid, String
            saleno, String assets, String item, String groupbuy, FetchEntryListener listener) {
        super(context);
        mContext = context;
        this.listener = listener;

        baseUrl += "?";
        if (!TextUtils.isEmpty(channel)) {
            String and = baseUrl.endsWith("?") ? "" : "&";
            baseUrl += and + "channel=" + channel;
        }
        if (!TextUtils.isEmpty(coupon_code)) {
            String and = baseUrl.endsWith("?") ? "" : "&";
            baseUrl += and + "coupon_code=" + coupon_code;
        }
        if (!TextUtils.isEmpty(aid)) {
            String and = baseUrl.endsWith("?") ? "" : "&";
            baseUrl += and + "aid=" + aid;
        }
        if (!TextUtils.isEmpty(saleno)) {
            String and = baseUrl.endsWith("?") ? "" : "&";
            baseUrl += and + "saleno=" + saleno;
        }
        if (!TextUtils.isEmpty(assets)) {
            String and = baseUrl.endsWith("?") ? "" : "&";
            baseUrl += and + "assets=" + assets;
        }
        if (!TextUtils.isEmpty(item)) {
            String and = baseUrl.endsWith("?") ? "" : "&";
            baseUrl += and + "item=" + item;
        }
        if (!TextUtils.isEmpty(groupbuy)) {
            String and = baseUrl.endsWith("?") ? "" : "&";
            baseUrl += and + "groupbuy=" + groupbuy;
        }
        get();
    }

    @Override
    protected String getUrl() {
        return baseUrl;
    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }

    @Override
    protected void handler(JSONObject json, JSONArray jsonArray, ErrorMsg errorMsg) {
        if (errorMsg != null) {
            listener.setError(errorMsg);
        } else listener.setError(null);

    }

}