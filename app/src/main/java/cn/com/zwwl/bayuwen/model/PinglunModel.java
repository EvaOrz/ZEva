package cn.com.zwwl.bayuwen.model;

import org.json.JSONObject;

/**
 * 评论model
 */
public class PinglunModel extends Entry {
    private String id;
    private String pid;
    private String kid;
    private String uid;
    private String fuid;
    private String content;
    private String ctime;
    private String ip;
    private String state;
    private String type;
    private String cid;
    private UserModel userModel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFuid() {
        return fuid;
    }

    public void setFuid(String fuid) {
        this.fuid = fuid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
    
    public PinglunModel parsePinglunModel(JSONObject jsonObject, PinglunModel pinglunModel) {
        pinglunModel.setId(jsonObject.optString("id"));
        pinglunModel.setPid(jsonObject.optString("pid"));
        pinglunModel.setKid(jsonObject.optString("kid"));
        pinglunModel.setUid(jsonObject.optString("uid"));
        pinglunModel.setFuid(jsonObject.optString("fuid"));
        pinglunModel.setContent(jsonObject.optString("content"));
        pinglunModel.setCtime(jsonObject.optString("ctime"));
        pinglunModel.setIp(jsonObject.optString("ip"));
        pinglunModel.setState(jsonObject.optString("state"));
        pinglunModel.setType(jsonObject.optString("type"));
        pinglunModel.setCid(jsonObject.optString("cid"));

        JSONObject userinfo = jsonObject.optJSONObject("userinfo");
        UserModel userModel = new UserModel();
        if (!isNull(userinfo)) {
            userModel.setUid(userinfo.optString("uid"));
            userModel.setName(userinfo.optString("name"));
            userModel.setRelName(userinfo.optString("relName"));
            userModel.setPic(userinfo.optString("pic"));
        }
        pinglunModel.setUserModel(userModel);
        return pinglunModel;
    }
}
