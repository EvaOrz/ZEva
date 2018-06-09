package cn.com.zwwl.bayuwen.api.order;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.OrderModel;

/**
 * 生成订单
 */
public class MakeOrderApi extends BaseApi {
    private String baseUrl = UrlUtil.setOrder();
    private FetchEntryListener listener;

    /**
     * @param context
     * @param channel     1支付宝 2微信 11手动支付
     * @param coupon_code 优惠券码
     * @param aid         地区id
     * @param saleno      推荐码
     * @param assets      账户余额
     * @param item
     * @param groupbuy
     * @param listener
     * @param promotion   组合课id
     */
    public MakeOrderApi(Context context, String channel, String coupon_code, String aid, String
            saleno, String assets, String item, String groupbuy, String promotion,
                        FetchEntryListener listener) {
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
        if (!TextUtils.isEmpty(promotion)) {
            String and = baseUrl.endsWith("?") ? "" : "&";
            baseUrl += and + "promotion=" + promotion;
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

    /**
     * {"trade_channel":"1","trade_fee":1,
     * "bill_no":"10180604140210664305","bill_title":"四年级大语文","trade_type":"1",
     * "return_url":"http%3A%2F%2Fm.dev.zhugexuetang
     * .com%2F%2Forderdetail%2F10180604140210664305"}
     *
     * @param json
     * @param jsonArray
     * @param errorMsg
     */
    @Override
    protected void handler(JSONObject json, JSONArray jsonArray, ErrorMsg errorMsg) {
        if (errorMsg != null) {
            listener.setError(errorMsg);
        } else listener.setError(null);

        if (!isNull(json)) {
            Gson gson = new Gson();
            OrderModel orderModel = gson.fromJson(json.toString(), OrderModel.class);
            listener.setData(orderModel);
        }

    }

}