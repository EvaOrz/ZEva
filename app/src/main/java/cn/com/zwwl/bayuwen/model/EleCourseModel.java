package cn.com.zwwl.bayuwen.model;

/**
 * Created by lousx on 2018/5/11.
 */

public class EleCourseModel extends Entry{

    /**
     * id : 101
     * name : 王者班
     * img : http://img.zhugexuetang.com/FtZ2RJD6MqND2yLYoQSmM2OYSce4
     * sort : 101
     */

    private int id;
    private String name;
    private String img;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        if (name == null)
            return "";
        return name;
    }

    public String getImg() {
        if (img == null)
            return "";
        return img;
    }

}
