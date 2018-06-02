package cn.com.zwwl.bayuwen.api.order;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 实时计算订单金额api
 */
public class CountPriceApi extends BaseApi {
    private FetchEntryListener listener;
    private String baseUrl = UrlUtil.countPrice();

    /**
     * @param context
     * @param item
     * @param coupon_code 优惠券码
     * @param promotion   组合优惠id
     * @param assets      余额
     * @param groupbuy    团购码
     * @param listener
     */
    public CountPriceApi(Context context, String item, String coupon_code, String promotion,
                         String assets, String groupbuy, FetchEntryListener
                                 listener) {
        super(context);
        mContext = context;
        this.listener = listener;


        baseUrl += "?item=" + item;
        if (!TextUtils.isEmpty(coupon_code)) {
            baseUrl += "&coupon_code=" + coupon_code;
        }
        if (!TextUtils.isEmpty(promotion)) {
            String and = baseUrl.endsWith("?") ? "" : "&";
            baseUrl += and + "promotion=" + promotion;
        }
        if (!TextUtils.isEmpty(assets)) {
            String and = baseUrl.endsWith("?") ? "" : "&";
            baseUrl += and + "assets=" + assets;
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
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {

    }
}
