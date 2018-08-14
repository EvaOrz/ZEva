package cn.com.zwwl.bayuwen.model;

import java.util.List;

/**
 * 选课中间介绍
 */
public class MidIntroModel {
    private String id;
    private String name;
    private String desc;
    private String img;
    private String video;
    private List<ParentCommentModel> comments;
    private List<Materialmodel> material;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public List<ParentCommentModel> getComments() {
        return comments;
    }

    public void setComments(List<ParentCommentModel> comments) {
        this.comments = comments;
    }

    public List<Materialmodel> getMaterial() {
        return material;
    }

    public void setMaterial(List<Materialmodel> material) {
        this.material = material;
    }


}
