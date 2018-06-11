package cn.com.zwwl.bayuwen.model;

import org.json.JSONObject;

/**
 * 优惠券model
 */
public class CouponModel extends Entry {

    private String id = "";
    private String desc = "";
    private String start_use_time = "";
    private String end_use_time = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStart_use_time() {
        return start_use_time;
    }

    public void setStart_use_time(String start_use_time) {
        this.start_use_time = start_use_time;
    }

    public String getEnd_use_time() {
        return end_use_time;
    }

    public void setEnd_use_time(String end_use_time) {
        this.end_use_time = end_use_time;
    }

    public static CouponModel parseCouponModel(JSONObject jsonObject, CouponModel couponModel) {
        couponModel.setId(jsonObject.optString("id"));
        couponModel.setDesc(jsonObject.optString("name"));
        couponModel.setStart_use_time(jsonObject.optString("start_use_time"));
        couponModel.setEnd_use_time(jsonObject.optString("end_use_time"));
        return couponModel;
    }
}
