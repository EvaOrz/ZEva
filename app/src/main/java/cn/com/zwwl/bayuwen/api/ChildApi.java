package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.model.AddressModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 添加、修改、删除、查看学员接口
 *
 */
public class ChildApi extends BaseApi {
    private String url;
    private Map<String, String> pamas = new HashMap<>();


    public ChildApi(Context context) {
        super(context);
        mContext = context;
        isNeedJsonArray = true;

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
    public ChildApi(Context context, String aId, String pama1, AddressApi.FetchAddressListListener listener) {
        super(context);
        mContext = context;
        isNeedJsonArray = true;
        pamas.put("to_user", pama1);
        this.url = UrlUtil.addressUrl() + "/" + aId;
        patch();
    }

    /**
     * get列表
     *
     * @param context
     * @param listener
     */
    public ChildApi(Context context, AddressApi.FetchAddressListListener listener) {
        super(context);
        mContext = context;
        isNeedJsonArray = true;
        this.url = UrlUtil.addressUrl();
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
    public ChildApi(Context context, String aId, int type, AddressApi.FetchAddressListListener listener) {
        super(context);
        mContext = context;
        isNeedJsonArray = true;
        if (type == 0) {
            this.url = UrlUtil.addressUrl() + "/" + aId;
            delete();
        } else {
            this.url = UrlUtil.addressUrl() + "/" + aId + "/default";
            patch();
        }


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
//        try {
//            if (errorMsg != null) {
//                if (listListener != null)
//                    listListener.setError(errorMsg);
//            }
//            if (!isNull(array)) {// 获取列表
//                List<AddressModel> addressModels = new ArrayList<>();
//                for (int i = 0; i < array.length(); i++) {
//                    AddressModel a = new AddressModel();
//                    a.parseAddressModel(array.getJSONObject(i), a);
//                    addressModels.add(a);
//                }
//                listListener.setData(addressModels);
//
//            } else {// 增删改
//                listListener.setData(null);
//            }
//        } catch (JSONException e) {
//        }
    }


}