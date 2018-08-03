package cn.com.zwwl.bayuwen.model;

public class AddTopicLabelModel extends Entry{

    /**
     * id : 1
     * name : 一年级大语文
     * url :
     * type : 2
     * create_at : 2018-06-06 11:11:28
     * update_at : 2018-06-06 11:11:28
     */

    private String id;
    private String name;
    private String url;
    private String type;
    private String create_at;
    private String update_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }
}
