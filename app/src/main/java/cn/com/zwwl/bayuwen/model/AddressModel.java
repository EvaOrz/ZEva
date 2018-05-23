package cn.com.zwwl.bayuwen.model;

import org.json.JSONObject;

/**
 * 收货地址 Model
 */
public class AddressModel extends Entry {
    private String id = "";
    private String is_default = "";// 1：default 0：非default
    private String to_user = "";
    private String phone = "";
    private String province = "";
    private String city = "";
    private String district = "";
    private String province_id = "";
    private String city_id = "";
    private String district_id = "";
    private String address = "";
    private String address_alias = "";

    public String getTo_user() {
        return to_user;
    }

    public void setTo_user(String to_user) {
        this.to_user = to_user;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress_alias() {
        return address_alias;
    }

    public void setAddress_alias(String address_alias) {
        this.address_alias = address_alias;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIs_default() {
        return is_default;
    }

    public void setIs_default(String is_default) {
        this.is_default = is_default;
    }

    /**
     * @param jsonObject
     * @param addressModel
     * @return
     */
    public AddressModel parseAddressModel(JSONObject jsonObject, AddressModel addressModel) {
        addressModel.setId(jsonObject.optString("id"));
        addressModel.setTo_user(jsonObject.optString("to_user"));
        addressModel.setPhone(jsonObject.optString("phone"));
        addressModel.setProvince(jsonObject.optString("province"));
        addressModel.setCity(jsonObject.optString("city"));
        addressModel.setDistrict(jsonObject.optString("district"));
        addressModel.setProvince_id(jsonObject.optString("province_id"));
        addressModel.setCity_id(jsonObject.optString("city_id"));
        addressModel.setDistrict_id(jsonObject.optString("district_id"));
        addressModel.setAddress(jsonObject.optString("address"));
        addressModel.setAddress_alias(jsonObject.optString("address_alias"));
        addressModel.setIs_default(jsonObject.optString("is_default"));
        return addressModel;
    }

}
