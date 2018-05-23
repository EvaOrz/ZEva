package cn.com.zwwl.bayuwen.model;

import org.json.JSONObject;

import cn.com.zwwl.bayuwen.R;

/**
 */
public class UserModel extends Entry {

    private String uid = "";
    private String token = "";
    private String name = "";
    private String relName = "";
    private String tel = "";
    private String pic = "";
    private int sex = 0;// 1男2女0保密
    private int year = 0;
    private int month = 0;
    private int day = 0;
    private int province = 0;
    private int city = 0;
    private int area = 0;
    private String created_at = "";
    private String updated_at = "";
    private int level = 0;
    private String userAccount = "";

    public UserModel() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelName() {
        return relName;
    }

    public void setRelName(String relName) {
        this.relName = relName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }


    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getSexTxt(int i) {
        if (i == 1) return R.string.male;
        else if (i == 2) return R.string.female;
        return R.string.nomale;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    /**
     *
     * @param jsonObject
     * @param userModel
     * @return
     */
    public UserModel parseUserModel(JSONObject jsonObject, UserModel userModel) {
        userModel.setUid(jsonObject.optString("uid"));
        userModel.setToken(jsonObject.optString("token"));
        userModel.setName(jsonObject.optString("name"));
        userModel.setRelName(jsonObject.optString("relName"));
        userModel.setTel(jsonObject.optString("tel"));
        userModel.setPic(jsonObject.optString("pic"));
        userModel.setSex(jsonObject.optInt("sex"));
        userModel.setYear(jsonObject.optInt("year"));
        userModel.setMonth(jsonObject.optInt("month"));
        userModel.setDay(jsonObject.optInt("day"));
        userModel.setProvince(jsonObject.optInt("province"));
        userModel.setCity(jsonObject.optInt("city"));
        userModel.setArea(jsonObject.optInt("area"));
        userModel.setCreated_at(jsonObject.optString("created_at"));
        userModel.setUpdated_at(jsonObject.optString("updated_at"));
        userModel.setLevel(jsonObject.optInt("level"));
        userModel.setUserAccount(jsonObject.optString("userAccount"));
        return userModel;
    }

}
