package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;

/**
 * 关注课程\获取关注列表接口
 */
public class FollowApi extends BaseApi {
    private String url;
    private FetchEntryListener listener;
    private FetchEntryListListener listListener;
    private Map<String, String> pamas = new HashMap<>();

    /**
     * 添加\取消关注
     *
     * @param context
     * @param kid
     * @param type     1-课程  2-fm
     * @param listener
     */
    public FollowApi(Context context, String kid, int type, FetchEntryListener listener) {
        super(context);
        mContext = context;
        pamas.put("kid", kid);
        pamas.put("type", type + "");
        this.url = UrlUtil.getCollecturl();
        this.listener = listener;
        post();
    }

    /**
     * 取消关注
     *
     * @param context
     * @param type     1-课程  2-fm
     * @param listener
     */
    public FollowApi(Context context, int type, FetchEntryListListener listener) {
        super(context);
        mContext = context;
        this.url = UrlUtil.getCollecturl() + "/my?type=" + type;
        this.listListener = listener;
        get();
    }


    @Override
    protected Map<String, String> getPostParams() {
        return pamas;
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        if (errorMsg != null) {
            if (listener != null)
                listener.setError(errorMsg);
            if (listListener != null)
                listListener.setError(errorMsg);
        }

        if (!isNull(json)) {
            if (listener != null) {
                ErrorMsg err = new ErrorMsg();
                // "state":1(关注) 0：（未关注）
                err.setNo(json.optInt("state"));
                listener.setData(err);
            }
            if (listListener != null) {
                JSONArray aa = json.optJSONArray("data");
                if (!isNull(aa)) {
                    List<KeModel> keModelList = new ArrayList<>();
                    Gson gson = new Gson();
                    for (int i = 0; i < aa.length(); i++) {
                        JSONObject j = aa.optJSONObject(i);
                        if (!isNull(j)) {
                            JSONObject k = j.optJSONObject("keinfo");
                            if (!isNull(k)) {
                                KeModel keModel = gson.fromJson(k.toString(), KeModel.class);
                                keModelList.add(keModel);
                            }
                        }
                    }
                    listListener.setData(keModelList);
                }
            }

        }
    }

    @Override
    protected String getUrl() {
        return url;

    }
}
