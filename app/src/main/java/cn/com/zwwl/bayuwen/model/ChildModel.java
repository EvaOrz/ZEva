package cn.com.zwwl.bayuwen.model;

import org.json.JSONObject;

import cn.com.zwwl.bayuwen.R;

/**
 * 学员model
 */
public class ChildModel extends Entry {
    private String id = "";
    private String uid = "";
    private String no = "";
    private String name = "";
    private int gender ;//学员性别0女1男2未知
    private String birthday = "";
    private String province = "";
    private String province_id = "";
    private String city = "";
    private String city_id = "";
    private String district = "";
    private String district_id = "";
    private String tel = "";
    private String parent = "";
    private String parent_phone = "";
    private String urgent_phone = "";
    private String urgent_contact = "";
    private String reg_school = "";
    private String school = "";
    private String grade = "";
    private String address = "";
    private String isdefault = "";
    private String admission_time= "";
    private String pic = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getParent_phone() {
        return parent_phone;
    }

    public void setParent_phone(String parent_phone) {
        this.parent_phone = parent_phone;
    }

    public String getUrgent_phone() {
        return urgent_phone;
    }

    public void setUrgent_phone(String urgent_phone) {
        this.urgent_phone = urgent_phone;
    }

    public String getUrgent_contact() {
        return urgent_contact;
    }

    public void setUrgent_contact(String urgent_contact) {
        this.urgent_contact = urgent_contact;
    }

    public String getReg_school() {
        return reg_school;
    }

    public void setReg_school(String reg_school) {
        this.reg_school = reg_school;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(String isdefault) {
        this.isdefault = isdefault;
    }

    public String getAdmission_time() {
        return admission_time;
    }

    public void setAdmission_time(String admission_time) {
        this.admission_time = admission_time;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getSexTxt(int i) {
        if (i == 1) return R.string.male;
        else if (i == 0) return R.string.female;
        return R.string.nomale;
    }

    public ChildModel parseChildModel(JSONObject jsonObject, ChildModel childModel) {
        childModel.setId(jsonObject.optString("id"));
        childModel.setUid(jsonObject.optString("uid"));
        childModel.setNo(jsonObject.optString("no"));
        childModel.setGender(jsonObject.optInt("gender"));
        childModel.setName(jsonObject.optString("name"));
        childModel.setBirthday(jsonObject.optString("birthday"));
        childModel.setProvince(jsonObject.optString("province"));
        childModel.setProvince_id(jsonObject.optString("province_id"));
        childModel.setCity(jsonObject.optString("city"));
        childModel.setCity_id(jsonObject.optString("city_id"));
        childModel.setDistrict(jsonObject.optString("district"));
        childModel.setDistrict_id(jsonObject.optString("district_id"));
        childModel.setTel(jsonObject.optString("tel"));
        childModel.setParent(jsonObject.optString("parent"));
        childModel.setParent_phone(jsonObject.optString("parent_phone"));
        childModel.setUrgent_contact(jsonObject.optString("urgent_contact"));
        childModel.setUrgent_phone(jsonObject.optString("urgent_phone"));
        childModel.setReg_school(jsonObject.optString("reg_school"));
        childModel.setSchool(jsonObject.optString("school"));
        childModel.setGrade(jsonObject.optString("grade"));
        childModel.setIsdefault(jsonObject.optString("isdefault"));
        childModel.setAdmission_time(jsonObject.optString("admission_time"));
        childModel.setPic(jsonObject.optString("pic"));
        return childModel;
    }
}
