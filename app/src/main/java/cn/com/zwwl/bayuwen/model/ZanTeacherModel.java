package cn.com.zwwl.bayuwen.model;

import org.json.JSONObject;

/**
 * 助教、顾问model
 */
public class ZanTeacherModel extends Entry {
    private String to_uid;
    private String vote_num;
    private String theme;
    private String to_name;
    private String pic;
    private String t_desc;

    public void setTo_uid(String to_uid) {
        this.to_uid = to_uid;
    }

    public void setVote_num(String vote_num) {
        this.vote_num = vote_num;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setTo_name(String to_name) {
        this.to_name = to_name;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setT_desc(String t_desc) {
        this.t_desc = t_desc;
    }

    public String getTo_uid() {
        return to_uid;
    }

    public String getVote_num() {
        return vote_num;
    }

    public String getTheme() {
        return theme;
    }

    public String getTo_name() {
        return to_name;
    }

    public String getPic() {
        if (pic == null)
            return "";
        return pic;
    }

    public String getT_desc() {
        return t_desc;
    }

    /**
     * @param jsonObject
     * @param zanTeacherModel
     * @return
     */
    public ZanTeacherModel parseGuwenModel(JSONObject jsonObject, ZanTeacherModel zanTeacherModel) {
        zanTeacherModel.setTo_name(jsonObject.optString("to_name"));
        zanTeacherModel.setTo_uid(jsonObject.optString("to_uid"));
        zanTeacherModel.setVote_num(jsonObject.optString("vote_num"));
        zanTeacherModel.setTheme(jsonObject.optString("theme"));
        zanTeacherModel.setT_desc(jsonObject.optString("t_desc"));
        zanTeacherModel.setPic(jsonObject.optString("pic"));
        return zanTeacherModel;
    }
}
