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
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.CouponModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 获取用户可使用的优惠券
 */
public class CouponApi extends BaseApi {
    private FetchEntryListListener listListener;
    private FetchEntryListener listener;
    private String url = "";

    /**
     * 获取优惠券列表
     *
     * @param context
     * @param type         获取可用的优惠券  all = 1 可领取的优惠券， 0已领取的优惠券
     * @param listListener
     */
    public CouponApi(Context context, int type, String kids, FetchEntryListListener listListener) {
        super(context);
        this.listListener = listListener;
        url += UrlUtil.cuponListUrl() + "?kids=" + kids + "&all=" + type;
        get();
    }

    /**
     * 领取优惠券
     *
     * @param context
     * @param coupon_id
     * @param listener
     */
    public CouponApi(Context context, String coupon_id, FetchEntryListener listener) {
        super(context);
        this.listener = listener;
        url += UrlUtil.getCouponUrl() + "?coupon_id=" + coupon_id;
        get();
    }

    @Override
    protected String getUrl() {
        return url;
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        if (listListener != null)
            listListener.setError(errorMsg);
        if (listener != null) listener.setError(errorMsg);

        if (!isNull(array)) {
            List<CouponModel> couponModels = new ArrayList<>();
            Gson gson = new Gson();
            for (int i = 0; i < array.length(); i++) {
                CouponModel c = gson.fromJson(array.optJSONObject(i).toString(), CouponModel.class);
                couponModels.add(c);
            }
            listListener.setData(couponModels);
        }

    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }


}
