package cn.com.zwwl.bayuwen.model;

import org.json.JSONObject;

/**
 * 首页推荐model
 */
public class RecommentModel extends Entry {
    private String parent;
    private String title;
    private String pic;
    private String link;
    private String type;// 1：课程 2：老师 3：活动页面
    private String sort;
    private String teacher;
    private AlbumModel albumModel;

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }


    public AlbumModel getAlbumModel() {
        return albumModel;
    }

    public void setAlbumModel(AlbumModel albumModel) {
        this.albumModel = albumModel;
    }

    public RecommentModel() {
    }

    public RecommentModel parseRecommentModel(JSONObject jsonObject, RecommentModel recommentModel) {
        recommentModel.setParent(jsonObject.optString("parent"));
        recommentModel.setTitle(jsonObject.optString("title"));
        recommentModel.setPic(jsonObject.optString("pic"));
        recommentModel.setLink(jsonObject.optString("link"));
        recommentModel.setType(jsonObject.optString("type"));
        recommentModel.setSort(jsonObject.optString("sort"));
        recommentModel.setTeacher(jsonObject.optString("teacher"));

        JSONObject keinfo = jsonObject.optJSONObject("keinfo");
        AlbumModel albumModel = new AlbumModel();
        if (!isNull(keinfo)) {
            albumModel.parseKinfo(keinfo, albumModel);
        }
        recommentModel.setAlbumModel(albumModel);
        return recommentModel;
    }

//    /**
//     * 转换成AlbumModel
//     *
//     * @param recommentModel
//     * @return
//     */
//    public AlbumModel transToAlbumModel(RecommentModel recommentModel) {
//        AlbumModel albumModel = new AlbumModel();
//        albumModel.setTitle(recommentModel.getTitle());
//        albumModel.setKid(recommentModel.getkId());
//        albumModel.setPic(recommentModel.getkPic());
//        return albumModel;
//    }
}
