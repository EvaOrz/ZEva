package cn.com.zwwl.bayuwen.api;

import android.content.Context;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.AddressModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 添加、修改、删除、查看收货地址api
 */
public class AddressApi extends BaseApi {
    private String url;
    private FetchEntryListener listener;
    private FetchAddressListListener listListener;
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
        pamas.put("province_id", model.getProvince_id());
        pamas.put("city_id", model.getCity_id());
        pamas.put("district_id", model.getDistrict_id());
        pamas.put("address", model.getAddress());
        pamas.put("address_alias", model.getAddress_alias());
        this.url = UrlUtil.addressUrl();
        this.listener = listener;
        post();
    }

    /**
     * 修改
     *
     * @param context
     * @param aId
     * @param pama1    要修改的属性
     * @param listener
     */
    public AddressApi(Context context, String aId, String pama1, FetchEntryListener listener) {
        super(context);
        mContext = context;
        pamas.put("to_user", pama1);

        this.url = UrlUtil.addressUrl() + "/" + aId;
        this.listener = listener;
        patch();
    }

    /**
     * get列表
     *
     * @param context
     * @param listener
     */
    public AddressApi(Context context, FetchAddressListListener listener) {
        super(context);
        mContext = context;
        isNeedJsonArray = true;
        this.url = UrlUtil.addressUrl();
        this.listListener = listener;
        get();
    }

    /**
     * 删除、设为默认地址
     *
     * @param context
     * @param aId
     * @param listener
     * @param type     0:删除 1:设为默认
     */
    public AddressApi(Context context, String aId, int type, FetchAddressListListener listener) {
        super(context);
        mContext = context;
        if (type == 0) {
            this.url = UrlUtil.addressUrl() + "/" + aId;
            delete();
        } else {
            this.url = UrlUtil.addressUrl() + "/" + aId + "/default";
            patch();
        }

        this.listListener = listener;


    }


    @Override
    protected Map<String, String> getPostParams() {
        return pamas;
    }

    @Override
    public String getUrl() {
        return url;
    }


    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        try {
            if (errorMsg != null) {
                if (listener != null)
                    listener.setError(errorMsg);
                if (listListener != null)
                    listListener.setError(errorMsg);
            }
            if (isNeedJsonArray) {// 获取列表
                List<AddressModel> addressModels = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    AddressModel a = new AddressModel();
                    a.parseAddressModel(array.getJSONObject(i), a);
                }
                listListener.setData(addressModels);

            } else {// 增删改
                listListener.setData(null);
            }
        } catch (JSONException e) {
        }
    }

    public interface FetchAddressListListener {
        /**
         * 给View传递数据
         *
         * @param list
         */
        public void setData(List<AddressModel> list);


        public void setError(ErrorMsg error);
    }
}
