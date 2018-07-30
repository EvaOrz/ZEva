package cn.com.zwwl.bayuwen.model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 教师model
 */
public class TeacherModel extends Entry {
    private String tid;
    private String pic;
    private String name;
    private String tel;
    private String t_desc;
    private String t_style;
    private Object t_harvest;
    private String t_words;
    private String shareUrl;
    private String t_shortdesc = "";
    private String t_idea = "";
    private String ke_main = "";

    private List<KeModel> keModels = new ArrayList<>();

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getT_desc() {
        return t_desc;
    }

    public void setT_desc(String t_desc) {
        this.t_desc = t_desc;
    }

    public String getT_style() {
        return t_style;
    }

    public void setT_style(String t_style) {
        this.t_style = t_style;
    }

    public Object getT_harvest() {
        return t_harvest;
    }

    public void setT_harvest(Object t_harvest) {
        this.t_harvest = t_harvest;
    }

    public String getT_words() {
        return t_words;
    }

    public void setT_words(String t_words) {
        this.t_words = t_words;
    }

    public String getT_shortdesc() {
        return t_shortdesc;
    }

    public void setT_shortdesc(String t_shortdesc) {
        this.t_shortdesc = t_shortdesc;
    }

    public String getT_idea() {
        return t_idea;
    }

    public void setT_idea(String t_idea) {
        this.t_idea = t_idea;
    }

    public String getKe_main() {
        return ke_main;
    }

    public void setKe_main(String ke_main) {
        this.ke_main = ke_main;
    }

    public List<KeModel> getKeModels() {
        return keModels;
    }

    public void setKeModels(List<KeModel> keModels) {
        this.keModels = keModels;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    /**
     * @param jsonObject
     * @param teacherModel
     * @return
     */
    public TeacherModel parseTeacherModel(JSONObject jsonObject, TeacherModel teacherModel) {
        teacherModel.setTid(jsonObject.optString("tid"));
        teacherModel.setName(jsonObject.optString("name"));
        teacherModel.setTel(jsonObject.optString("tel"));
        teacherModel.setT_shortdesc(jsonObject.optString("t_shortdesc"));
        teacherModel.setPic(jsonObject.optString("pic"));
        teacherModel.setT_desc(jsonObject.optString("t_desc"));
        teacherModel.setShareUrl(jsonObject.optString("shareUrl"));
        teacherModel.setT_style(jsonObject.optString("t_style"));
        teacherModel.setT_idea(jsonObject.optString("t_idea"));
        teacherModel.setKe_main(jsonObject.optString("ke_main"));
        return teacherModel;
    }

}
