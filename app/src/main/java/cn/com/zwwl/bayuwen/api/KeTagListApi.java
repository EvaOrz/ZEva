package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.Index2Model;
import cn.com.zwwl.bayuwen.model.Index2Model.*;

/**
 * 获取选课首页tag标签列表
 */
public class KeTagListApi extends BaseApi {
    private Index2Model index2Model = new Index2Model();
    private FetchEntryListener listener;

    public KeTagListApi(Context context, FetchEntryListener listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        get();
    }

    @Override
    protected String getUrl() {
        return UrlUtil.getKeTagListUrl();
    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }

    @Override
    protected void handler(JSONObject json, JSONArray jsonArray, ErrorMsg errorMsg) {
        listener.setError(errorMsg);
        if (!isNull(json)) {
            Gson gson = new Gson();
            JSONObject j1 = json.optJSONObject("partOne");
            if (!isNull(j1)) {
                TagCourseModel model = gson.fromJson(j1.toString(), TagCourseModel.class);
                index2Model.setPartOne(model);
            }
            JSONObject j2 = json.optJSONObject("partTwo");
            if (!isNull(j2)) {
                TagCourseModel model = gson.fromJson(j2.toString(), TagCourseModel.class);
                index2Model.setPartTwo(model);
            }
            JSONObject j3 = json.optJSONObject("partThree");
            if (!isNull(j3)) {
                TagCourseModel model = gson.fromJson(j3.toString(), TagCourseModel.class);
                index2Model.setPartThree(model);
            }
            listener.setData(index2Model);
        }
    }


}
