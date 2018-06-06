package cn.com.zwwl.bayuwen.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的订单页面
 * 待支付、已支付、退款售后model
 * <p>
 * 订单详情页面
 * 订单model
 */
public class OrderForMyListModel extends Entry {

    private String id;
    private String oid;
    private String uid;
    private String title;
    private String total_fee;
    private String real_fee;
    private String state;
    private String pay_channel;
    private String pay_at;
    private String coupon_code;
    private String assets;
    private String create_at;
    private String expire_at;
    private List<KeModel> keModels = new ArrayList<>();
    private AddressModel addressModel;

    public List<KeModel> getKeModels() {
        return keModels;
    }

    public void setKeModels(List<KeModel> keModels) {
        this.keModels = keModels;
    }

    public AddressModel getAddressModel() {
        return addressModel;
    }

    public void setAddressModel(AddressModel addressModel) {
        this.addressModel = addressModel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getReal_fee() {
        return real_fee;
    }

    public void setReal_fee(String real_fee) {
        this.real_fee = real_fee;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPay_channel() {
        return pay_channel;
    }

    public void setPay_channel(String pay_channel) {
        this.pay_channel = pay_channel;
    }

    public String getPay_at() {
        return pay_at;
    }

    public void setPay_at(String pay_at) {
        this.pay_at = pay_at;
    }

    public String getCoupon_code() {
        return coupon_code;
    }

    public void setCoupon_code(String coupon_code) {
        this.coupon_code = coupon_code;
    }

    public String getAssets() {
        return assets;
    }

    public void setAssets(String assets) {
        this.assets = assets;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getExpire_at() {
        return expire_at;
    }

    public void setExpire_at(String expire_at) {
        this.expire_at = expire_at;
    }

    /**
     * @param jsonObject
     * @param orderForMyListModel
     * @return
     */
    public OrderForMyListModel parseOrderForMyListModel(JSONObject jsonObject,
                                                        OrderForMyListModel orderForMyListModel) {
        orderForMyListModel.setId(jsonObject.optString("id"));
        orderForMyListModel.setOid(jsonObject.optString("oid"));
        orderForMyListModel.setUid(jsonObject.optString("uid"));
        orderForMyListModel.setTitle(jsonObject.optString("title"));
        orderForMyListModel.setTotal_fee(jsonObject.optString("total_fee"));
        orderForMyListModel.setReal_fee(jsonObject.optString("real_fee"));
        orderForMyListModel.setState(jsonObject.optString("state"));
        orderForMyListModel.setPay_channel(jsonObject.optString("pay_channel"));
        orderForMyListModel.setPay_at(jsonObject.optString("pay_at"));
        orderForMyListModel.setCoupon_code(jsonObject.optString("coupon_code"));
        orderForMyListModel.setAssets(jsonObject.optString("assets"));
        orderForMyListModel.setCreate_at(jsonObject.optString("create_at"));
        orderForMyListModel.setExpire_at(jsonObject.optString("expire_at"));

        Gson gson = new Gson();
        // 订单列表页面解析课程列表
        JSONArray courses = jsonObject.optJSONArray("courses");
        if (!isNull(courses)) {
            List<KeModel> keModelList = new ArrayList<>();
            for (int i = 0; i < courses.length(); i++) {
                KeModel keModel = gson.fromJson(courses.optJSONObject(i).toString(), KeModel.class);
                keModelList.add(keModel);
            }
            orderForMyListModel.setKeModels(keModelList);
        }
        // 订单详情页面解析课程列表
        JSONArray details = jsonObject.optJSONArray("details");
        if (!isNull(details)) {
            List<KeModel> keModelList = new ArrayList<>();
            for (int i = 0; i < details.length(); i++) {
                JSONObject j = details.optJSONObject(i);
                KeModel keModel = gson.fromJson(j.optJSONObject("course")
                        .toString(), KeModel.class);
                keModel.setDetailId(j.optString("id"));
                keModel.setRefund(j.optString("refund"));
                keModelList.add(keModel);
            }
            orderForMyListModel.setKeModels(keModelList);
        }


        JSONObject a = jsonObject.optJSONObject("address");
        AddressModel addressModel = new AddressModel();
        if (!isNull(a)) {
            addressModel.parseAddressModel(a, addressModel);
        }
        orderForMyListModel.setAddressModel(addressModel);
        return orderForMyListModel;
    }

}
