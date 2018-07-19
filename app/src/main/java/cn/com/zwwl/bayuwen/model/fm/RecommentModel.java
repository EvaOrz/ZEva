package cn.com.zwwl.bayuwen.model.fm;

import com.google.gson.Gson;

import org.json.JSONObject;

import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.KeModel;

/**
 * 首页推荐model
 */
public class RecommentModel extends Entry {
    private String id;
    private String title;
    private String pic;
    private String link;
    private String sort;
    private KeModel albumModel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public KeModel getAlbumModel() {
        return albumModel;
    }

    public void setAlbumModel(KeModel albumModel) {
        this.albumModel = albumModel;
    }

    public RecommentModel() {
    }

    public RecommentModel parseRecommentModel(JSONObject jsonObject, RecommentModel
            recommentModel) {
        recommentModel.setId(jsonObject.optString("parent"));
        recommentModel.setTitle(jsonObject.optString("title"));
        recommentModel.setPic(jsonObject.optString("pic"));
        recommentModel.setLink(jsonObject.optString("link"));
        recommentModel.setSort(jsonObject.optString("sort"));

        JSONObject keinfo = jsonObject.optJSONObject("course");
        if (!isNull(keinfo)) {
            KeModel keModel = new KeModel();
            keModel.setKid(keinfo.optString("kid"));
            keModel.setPic(keinfo.optString("pic"));
            keModel.setTitle(keinfo.optString("title"));
            keModel.setPlayNum(keinfo.optInt("playNum") + "");
            recommentModel.setAlbumModel(keModel);
        }
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
