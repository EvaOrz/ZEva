package cn.com.zwwl.bayuwen.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 专辑model
 */
public class AlbumModel extends Entry {
    private String kid = "";
    private String title = "";
    private String pic = "";
    private String content = "";
    private String desc = "";
    private String tid = "";
    private String tname = "";
    private int likeNum;
    private boolean likeState = false;
    private String buyPrice = "";
    private String type = "";
    private String created_at = "";
    private String update_time = "";
    private List<FmModel> fmModels = new ArrayList<>();
    private List<Teacher> teachers = new ArrayList<>();
    private String conllectId = "";

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public boolean isLikeState() {
        return likeState;
    }

    public void setLikeState(boolean likeState) {
        this.likeState = likeState;
    }

    public String getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(String buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public List<FmModel> getFmModels() {
        return fmModels;
    }

    public void setFmModels(List<FmModel> fmModels) {
        this.fmModels = fmModels;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getConllectId() {
        return conllectId;
    }

    public void setConllectId(String conllectId) {
        this.conllectId = conllectId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public AlbumModel() {
    }

    public class Teacher extends Entry {
        private String name;
        private String pic;
        private String t_desc;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getT_desc() {
            return t_desc;
        }

        public void setT_desc(String t_desc) {
            this.t_desc = t_desc;
        }

        public Teacher() {
        }

        public Teacher parseTeacher(JSONObject jsonObject, Teacher teacher) {
            teacher.setName(jsonObject.optString("name"));
            teacher.setPic(jsonObject.optString("pic"));
            teacher.setT_desc(jsonObject.optString("t_desc"));
            return teacher;
        }
    }

    public AlbumModel parseKinfo(JSONObject keinfo, AlbumModel albumModel) {
        albumModel.setKid(keinfo.optString("kid"));
        albumModel.setTitle(keinfo.optString("title"));
        albumModel.setPic(keinfo.optString("pic"));
        albumModel.setContent(keinfo.optString("content"));
        albumModel.setTid(keinfo.optString("tid"));
        albumModel.setDesc(keinfo.optString("desc"));
        albumModel.setTname(keinfo.optString("tname"));
        albumModel.setBuyPrice(keinfo.optString("buyPrice"));
        albumModel.setCreated_at(keinfo.optString("created_at"));
        albumModel.setLikeNum(keinfo.optInt("likeNum"));
        albumModel.setLikeState(keinfo.optBoolean("likeState", false));
        albumModel.setUpdate_time(keinfo.optString("update_time"));
        JSONObject collect = keinfo.optJSONObject("collection");
        if (!isNull(collect)) {
            albumModel.setConllectId(collect.optString("id"));
        }
        return albumModel;
    }

    /**
     * 专辑详情解析
     *
     * @param jsonObject
     * @param albumModel
     * @return
     */
    public AlbumModel parseAlbumModel(JSONObject jsonObject, AlbumModel albumModel) {

        JSONObject keinfo = jsonObject.optJSONObject("keinfo");
        if (!isNull(keinfo)) {
            albumModel.parseKinfo(keinfo, albumModel);
        }
        JSONArray array = jsonObject.optJSONArray("lectures");
        List<FmModel> fmModels = new ArrayList<>();
        if (!isNull(array)) {
            for (int i = 0; i < array.length(); i++) {
                JSONObject o = array.optJSONObject(i);
                FmModel f = new FmModel();
                f.parseFmModel(o, f);
                fmModels.add(f);
            }
        }
        albumModel.setFmModels(fmModels);
        JSONArray tArray = jsonObject.optJSONArray("teachers");
        List<Teacher> teachers = new ArrayList<>();
        if (!isNull(tArray)) {
            for (int i = 0; i < tArray.length(); i++) {
                JSONObject o = tArray.optJSONObject(i);
                Teacher t = new Teacher();
                t.parseTeacher(o, t);
                teachers.add(t);
            }
        }
        albumModel.setTeachers(teachers);

        return albumModel;
    }


}
