package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.db.TempDataHelper;
import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ChildModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 添加、修改、删除、查看学员接口
 */
public class ChildInfoApi extends BaseApi {
    private String url;
    private Map<String, String> pamas = new HashMap<>();
    private FetchEntryListListener listListener;
    private FetchEntryListener listener;

    /**
     * 添加、修改
     *
     * @param context
     * @param isModify 是否是修改
     * @param listener
     */
    public ChildInfoApi(Context context, ChildModel childModel, boolean isModify,
                        FetchEntryListener listener) {
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
        this.listener = listener;
        if (isModify) {
            this.url = UrlUtil.childUrl() + "/" + childModel.getId();
            put();
        } else {
            this.url = UrlUtil.childUrl();
            post();
        }

    }

    /**
     * get列表
     *
     * @param context
     * @param listener
     */
    public ChildInfoApi(Context context, FetchEntryListListener listener) {
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
    public ChildInfoApi(Context context, String aId, FetchEntryListListener listener
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

    /**
     * 添加、修改、设置默认之后都会再去获取一次列表信息，所以只需要在获取列表信息的时候存储默认学员信息，用来作为所有请求的header info
     *
     * @param json
     * @param array
     * @param errorMsg
     */
    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        if (errorMsg != null) {
            if (listListener != null)
                listListener.setError(errorMsg);
            if (listener != null)
                listener.setError(errorMsg);
        } else {// 添加、删除、设置默认成功
            if (listener != null)
                listener.setData(new ChildModel());
        }

        if (isNeedJsonArray) {// 获取列表
            if (!isNull(array)) {
                List<ChildModel> childModels = new ArrayList<>();
                boolean hasDefault = false;
                for (int i = 0; i < array.length(); i++) {
                    if (i < 3) {
                        ChildModel c = new ChildModel();
                        c.parseChildModel(array.optJSONObject(i), c);
                        if (c.getIsdefault().equals("1")) {
                            hasDefault = true;
                            saveStudent(c);
                        }
                        childModels.add(c);
                    }
                }
                // 如果服务端丢失默认学员信息，设置第一个为默认
                if (!hasDefault && childModels.size() > 0) {
                    childModels.get(0).setIsdefault("1");
                    saveStudent(childModels.get(0));
                }
                listListener.setData(childModels);

            } else {// 增删改
                listListener.setData(null);
            }
        }
    }

    /**
     * 存储默认学员信息
     *
     * @param c
     */
    private void saveStudent(ChildModel c) {
        TempDataHelper.setCurrentChildNo(mContext, c.getNo());
        // todo?? 年级的类型待定
        TempDataHelper.setCurrentChildGrade(mContext, 0);
    }
}