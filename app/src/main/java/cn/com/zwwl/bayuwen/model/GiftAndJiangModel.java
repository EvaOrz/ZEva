package cn.com.zwwl.bayuwen.model;

/**
 * 礼物、奖状
 */
public class GiftAndJiangModel extends Entry {

    private int id;
    private String title;
    private String desc;
    private String pic;
    private int type;
    private String date;
    private boolean isDeleteStatus = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDeleteStatus() {
        return isDeleteStatus;
    }

    public void setDeleteStatus(boolean deleteStatus) {
        isDeleteStatus = deleteStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
