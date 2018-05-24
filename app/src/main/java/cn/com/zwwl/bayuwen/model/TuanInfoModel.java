package cn.com.zwwl.bayuwen.model;

import org.json.JSONObject;

/**
 * 团购model
 */
public class TuanInfoModel extends Entry {

    private String id = "";
    private String title = "";
    private String item_id = "";
    private int discount_pintrice;
    private String limit_num = "";
    private int material_price;
    private int total_price;

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

    /**
     * @param jsonObject
     * @param tuanInfoModel
     * @return
     */
    public TuanInfoModel parseTuanInfoModel(JSONObject jsonObject, TuanInfoModel tuanInfoModel) {
        tuanInfoModel.setId(jsonObject.optString("id"));
        tuanInfoModel.setItem_id(jsonObject.optString("item_id"));
        tuanInfoModel.setTitle(jsonObject.optString("title"));
        tuanInfoModel.setDiscount_pintrice(Integer.valueOf(jsonObject.optString("discount_price")));
        tuanInfoModel.setMaterial_price(Integer.valueOf(jsonObject.optString("material_price")));
        tuanInfoModel.setLimit_num(jsonObject.optString("limit_num"));
        tuanInfoModel.setTotal_price(jsonObject.optInt("total_price"));
        return tuanInfoModel;
    }

}
