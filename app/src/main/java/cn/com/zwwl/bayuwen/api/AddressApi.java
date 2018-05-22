package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.AddressModel;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 添加、修改、删除、查看收货地址api
 */
public class AddressApi extends BaseApi {
    private String url;
    private FetchEntryListener listener;
    private Map<String, String> pamas = new HashMap<>();

    /**
     * 添加
     *
     * @param context
     * @param model
     * @param listener
     */
    public AddressApi(Context context, AddressModel model, FetchEntryListener listener) {
        super(context);
        mContext = context;
        pamas.put("to_user", model.getTo_user());
        pamas.put("phone", model.getPhone());
        pamas.put("province", model.getProvince());
        pamas.put("city", model.getCity());
        pamas.put("district", model.getDistrict());
        pamas.put("phone", model.getPhone());
        pamas.put("phone", model.getPhone());
        pamas.put("phone", model.getPhone());
        pamas.put("phone", model.getPhone());


        this.url = UrlUtil.addressUrl();
        this.listener = listener;
        post();
    }


    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    protected void handler(JSONObject data, ErrorMsg errorMsg) {

    }
}
