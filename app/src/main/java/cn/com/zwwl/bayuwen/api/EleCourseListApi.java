package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
 * Created by lousx on 2018/5/23.
 */

public class EleCourseListApi extends BaseApi {
    private List<EleCourseModel> eleCourseModels = new ArrayList<>();
    private FetchEntryListListener<EleCourseModel> listener;
    private String url;

    public EleCourseListApi(Context context, FetchEntryListListener<EleCourseModel> listener) {
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
            Gson gson = new Gson();
            eleCourseModels = gson.fromJson(String.valueOf(jsonArray), new
                    TypeToken<List<EleCourseModel>>() {
                    }.getType());
            listener.setData(eleCourseModels);
        }
    }

    /**
     * 选课首页类别model
     */
    public class EleCourseModel extends Entry {

        /**
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


}
