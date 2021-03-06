package cn.com.zwwl.bayuwen.api.order;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.OrderModel;

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
        baseUrl += "?item=" + item + "&assets=" + assets ;

        if (!TextUtils.isEmpty(coupon_code)) baseUrl += "&coupon_code=" + coupon_code;
        if (!TextUtils.isEmpty(promotion)) baseUrl += "&promotion=" + promotion;
        if (!TextUtils.isEmpty(groupbuy)) baseUrl += "&groupbuy=" + groupbuy;
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
        if (errorMsg != null) {
            listener.setError(errorMsg);
        }
        if (!isNull(json)) {
            Gson gson = new Gson();
            OrderModel.OrderDetailModel detailModel = gson.fromJson(json.toString(), OrderModel
                    .OrderDetailModel.class);
            listener.setData(detailModel);

        }
    }
}
