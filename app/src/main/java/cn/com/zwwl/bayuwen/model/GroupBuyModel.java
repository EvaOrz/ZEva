package cn.com.zwwl.bayuwen.model;

import org.json.JSONObject;

/**
 * 团购信息
 */
public class GroupBuyModel extends Entry {

    private String id = "";
    private String title = "";
    private String item_id = "";
    private float discount_pintrice;
    private String limit_num = "";
    private String start_time = "";
    private String end_time = "";
    private float material_price;
    private float total_price;

    public GroupBuyModel() {
    }

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

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public float getDiscount_pintrice() {
        return discount_pintrice;
    }

    public void setDiscount_pintrice(float discount_pintrice) {
        this.discount_pintrice = discount_pintrice;
    }

    public String getLimit_num() {
        return limit_num;
    }

    public void setLimit_num(String limit_num) {
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

    public float getMaterial_price() {
        return material_price;
    }

    public void setMaterial_price(float material_price) {
        this.material_price = material_price;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }


    public GroupBuyModel parseGroupBuyModel(JSONObject jsonObject, GroupBuyModel groupBuyModel) {
        groupBuyModel.setId(jsonObject.optString("id"));
        groupBuyModel.setItem_id(jsonObject.optString("item_id"));
        groupBuyModel.setTitle(jsonObject.optString("title"));
        groupBuyModel.setDiscount_pintrice(Float.valueOf(jsonObject.optString("discount_price")));
        groupBuyModel.setStart_time(jsonObject.optString("start_time"));
        groupBuyModel.setEnd_time(jsonObject.optString("end_time"));
        groupBuyModel.setLimit_num(jsonObject.optString("limit_num"));
        groupBuyModel.setMaterial_price(Float.valueOf(jsonObject.optString("material_price")));
        groupBuyModel.setTotal_price(Float.valueOf(jsonObject.optString("total_price")));
        return groupBuyModel;
    }

}
