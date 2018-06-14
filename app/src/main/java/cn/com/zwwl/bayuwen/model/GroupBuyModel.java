package cn.com.zwwl.bayuwen.model;

import org.json.JSONObject;

/**
 * 团购信息
 */
public class GroupBuyModel extends Entry {

    private String id = "";
    private String code = "";// 拼团码
    private String title = "";
    private String kid = "";
    private double discount_pintrice;
    private int limit_num;
    private String start_time = "";
    private String end_time = "";
    private double material_price; // 教材价格
    private double total_price;
    private double sponsor_price;
    private double join_price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public double getDiscount_pintrice() {
        return discount_pintrice;
    }

    public void setDiscount_pintrice(double discount_pintrice) {
        this.discount_pintrice = discount_pintrice;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getLimit_num() {
        return limit_num;
    }

    public void setLimit_num(int limit_num) {
        this.limit_num = limit_num;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public double getMaterial_price() {
        return material_price;
    }

    public void setMaterial_price(double material_price) {
        this.material_price = material_price;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public double getSponsor_price() {
        return sponsor_price;
    }

    public void setSponsor_price(double sponsor_price) {
        this.sponsor_price = sponsor_price;
    }

    public double getJoin_price() {
        return join_price;
    }

    public void setJoin_price(double join_price) {
        this.join_price = join_price;
    }

    public GroupBuyModel parseGroupBuyModel(JSONObject jsonObject, GroupBuyModel groupBuyModel) {
        groupBuyModel.setId(jsonObject.optString("id"));
        groupBuyModel.setKid(jsonObject.optString("kid"));
        groupBuyModel.setTitle(jsonObject.optString("title"));
        groupBuyModel.setDiscount_pintrice(jsonObject.optDouble("discount_price"));
        groupBuyModel.setStart_time(jsonObject.optString("start_time"));
        groupBuyModel.setEnd_time(jsonObject.optString("end_time"));
        groupBuyModel.setLimit_num(jsonObject.optInt("limit_num"));
        groupBuyModel.setMaterial_price(jsonObject.optDouble("material_price"));
        groupBuyModel.setTotal_price(jsonObject.optDouble("total_price"));
        groupBuyModel.setJoin_price(jsonObject.optDouble("join_price"));
        groupBuyModel.setSponsor_price(jsonObject.optDouble("sponsor_price"));
        return groupBuyModel;
    }

}
