package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.model.ChildModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 添加、修改、删除、查看学员接口
 */
public class ChildApi extends BaseApi {
    private String url;
    private Map<String, String> pamas = new HashMap<>();
    private FetchEntryListListener listListener;

    /**
     * 添加、修改
     *
     * @param context
     * @param isModify     是否是修改
     * @param listListener
     */
    public ChildApi(Context context, ChildModel childModel, boolean isModify,
                    FetchEntryListListener listListener) {
        super(context);
        mContext = context;
        pamas.put("name", childModel.getName());
        pamas.put("tel", childModel.getTel());
        pamas.put("grade", childModel.getGrade());
        pamas.put("gender", childModel.getGender() + "");
        pamas.put("birthday", childModel.getBirthday());
        pamas.put("isdefault", childModel.getIsdefault());
        pamas.put("admission_time", childModel.getAdmission_time());
        pamas.put("pic", childModel.getPic());
        pamas.put("school", childModel.getSchool());
        this.listListener = listListener;
        if (isModify) {
            this.url = UrlUtil.childUrl() + "/" + childModel.getId();
            put();
        } else {
            this.url = UrlUtil.childUrl() + "/";
            post();
        }

    }

    /**
     * get列表
     *
     * @param context
     * @param listener
     */
    public ChildApi(Context context, FetchEntryListListener listener) {
        super(context);
        mContext = context;
        isNeedJsonArray = true;
        this.listListener = listener;
        this.url = UrlUtil.childUrl();
        get();
    }

    /**
     * 删除
     *
     * @param context
     * @param aId
     * @param listener
     */
    public ChildApi(Context context, String aId, FetchEntryListListener listener
    ) {
        super(context);
        mContext = context;
        this.url = UrlUtil.childUrl() + "/" + aId;
        this.listListener = listener;
        delete();

    }


    @Override
    protected Map<String, String> getPostParams() {
        return pamas;
    }

    @Override
    public String getUrl() {
        return url;
    }


    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
//        try {
        if (errorMsg != null) {
            if (listListener != null)
                listListener.setError(errorMsg);
        }
        if (!isNull(array)) {// 获取列表
            List<ChildModel> childModels = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                ChildModel c = new ChildModel();
                c.parseChildModel(array.optJSONObject(i), c);
                childModels.add(c);
            }
            listListener.setData(childModels);

        } else {// 增删改
            listListener.setData(null);
        }
//        } catch (JSONException e) {
//        }
    }


}