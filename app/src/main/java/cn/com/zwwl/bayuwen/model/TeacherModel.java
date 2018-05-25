package cn.com.zwwl.bayuwen.model;

import org.json.JSONObject;

/**
 * 教师model
 */
public class TeacherModel extends Entry {
    private String to_uid = "";
    private String vote_num = "";
    private String theme = "";
    private String to_name = "";
    private String tid;
    private String pic;
    private String name;
    private String t_desc;
    private String t_style;
    private Object t_harvest;
    private String t_words;
    private String t_shortdesc = "";
    private String t_idea = "";
    private String ke_main = "";

    public String getTo_uid() {
        return to_uid;
    }

    public void setTo_uid(String to_uid) {
        this.to_uid = to_uid;
    }

    public String getVote_num() {
        return vote_num;
    }

    public void setVote_num(String vote_num) {
        this.vote_num = vote_num;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getTo_name() {
        return to_name;
    }

    public void setTo_name(String to_name) {
        this.to_name = to_name;
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

    /**
     * @param jsonObject
     * @param teacherModel
     * @return
     */
    public TeacherModel parseTeacherModel(JSONObject jsonObject, TeacherModel teacherModel) {
        teacherModel.setTo_uid(jsonObject.optString("to_uid"));
        teacherModel.setVote_num(jsonObject.optString("vote_num"));
        teacherModel.setTheme(jsonObject.optString("theme"));
        teacherModel.setTo_name(jsonObject.optString("to_name"));
        teacherModel.setPic(jsonObject.optString("pic"));
        teacherModel.setT_desc(jsonObject.optString("t_desc"));
        return teacherModel;
    }

}
