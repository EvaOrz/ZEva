package cn.com.zwwl.bayuwen.model;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合课model
 */
public class PromotionModel extends Entry {

    private String id;
    private String kids;
    private String title;
    private String lesson_titles;
    private double original_price;
    private double discount_price;
    private List<KeModel> keModels;

    public List<KeModel> getKeModels() {
        return keModels;
    }

    public void setKeModels(List<KeModel> keModels) {
        this.keModels = keModels;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKids() {
        return kids;
    }

    public void setKids(String kids) {
        this.kids = kids;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLesson_titles() {
        return lesson_titles;
    }

    public void setLesson_titles(String lesson_titles) {
        this.lesson_titles = lesson_titles;
    }

    public double getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(double original_price) {
        this.original_price = original_price;
    }

    public double getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(double discount_price) {
        this.discount_price = discount_price;
    }

    public PromotionModel parsePromotionModel(JSONObject jsonObject, PromotionModel
            promotionModel) {
        promotionModel.setId(jsonObject.optString("id"));
        promotionModel.setKids(jsonObject.optString("kids"));
        promotionModel.setDiscount_price(jsonObject.optDouble("discount_price"));
        promotionModel.setLesson_titles(jsonObject.optString("lesson_titles"));
        promotionModel.setOriginal_price(jsonObject.optDouble("original_price"));
        promotionModel.setTitle(jsonObject.optString("title"));
        Gson gson = new Gson();
        JSONArray jsonArray = jsonObject.optJSONArray("item");
        if (!isNull(jsonArray)) {
            List<KeModel> ks = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                KeModel k = gson.fromJson(jsonArray.optJSONObject(i).toString(), KeModel.class);
                ks.add(k);
            }
            promotionModel.setKeModels(ks);
        }
        return promotionModel;
    }
}
