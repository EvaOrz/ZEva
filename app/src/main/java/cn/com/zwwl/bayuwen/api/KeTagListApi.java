package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 获取选课首页tag标签列表
 */
public class KeTagListApi extends BaseApi {
    private List<TagCourseModel> eleCourseModels = new ArrayList<>();
    private FetchEntryListListener listener;
    private String url;

    public KeTagListApi(Context context, FetchEntryListListener listener) {
        super(context);
        mContext = context;
        isNeedJsonArray = true;
        this.listener = listener;
        this.url = UrlUtil.getEleCourseListUrl();
        get();
    }

    @Override
    protected String getUrl() {
        return url;
    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }

    @Override
    protected void handler(JSONObject json, JSONArray jsonArray, ErrorMsg errorMsg) {
        if (errorMsg != null) {
            listener.setError(errorMsg);
        }
        if (!isNull(jsonArray)) {
            for (int i = 0; i < jsonArray.length(); i++) {
                TagCourseModel a = new TagCourseModel();
                a.parseTagCourseModel(jsonArray.optJSONObject(i), a);
                eleCourseModels.add(a);
            }
            listener.setData(eleCourseModels);
        }
    }

    /**
     * 选课首页类别model
     */
    public class TagCourseModel extends Entry {

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

        public TagCourseModel parseTagCourseModel(JSONObject jsonObject, TagCourseModel
                tagCourseModel) {
            tagCourseModel.setId(jsonObject.optInt("id"));
            tagCourseModel.setImg(jsonObject.optString("img"));
            tagCourseModel.setName(jsonObject.optString("name"));
            return tagCourseModel;
        }

    }


}
