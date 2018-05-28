package cn.com.zwwl.bayuwen.model;

import org.json.JSONObject;

/**
 * 团购信息
 */
public class GroupBuyModel extends Entry {

    private String id = "";
    private String title = "";
    private String item_id = "";
    private int discount_pintrice ;
    private String limit_num = "";
    private String start_time = "";
    private String end_time = "";
    private int material_price ;
    private int total_price;

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

    public int getDiscount_pintrice() {
        return discount_pintrice;
    }

    public void setDiscount_pintrice(int discount_pintrice) {
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

    public int getMaterial_price() {
        return material_price;
    }

    public void setMaterial_price(int material_price) {
        this.material_price = material_price;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }


    public GroupBuyModel parseGroupBuyModel(JSONObject jsonObject, GroupBuyModel groupBuyModel) {
        groupBuyModel.setId(jsonObject.optString("id"));
        groupBuyModel.setItem_id(jsonObject.optString("item_id"));
        groupBuyModel.setTitle(jsonObject.optString("title"));
        groupBuyModel.setDiscount_pintrice(Integer.valueOf(jsonObject.optString("discount_price")));
        groupBuyModel.setStart_time(jsonObject.optString("start_time"));
        groupBuyModel.setEnd_time(jsonObject.optString("end_time"));
        groupBuyModel.setLimit_num(jsonObject.optString("limit_num"));
        groupBuyModel.setMaterial_price(Integer.valueOf(jsonObject.optString("material_price")));
        groupBuyModel.setTotal_price(jsonObject.optInt("total_price"));
        return groupBuyModel;
    }

}
