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
 * 关注列表
 * <p>
 * 1-课程  2-fm
 */

public class CollectionListApi extends BaseApi {
    private String url;
    private FetchEntryListListener listListener;
    private Map<String, String> pamas = new HashMap<>();


    public CollectionListApi(Context context, int type, FetchEntryListListener listener) {
        super(context);
        mContext = context;
        this.url = UrlUtil.getCollecturl() + "?type=" + type;
        this.listListener = listener;
        get();
    }


    @Override
    protected Map<String, String> getPostParams() {
        return pamas;
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        listListener.setError(errorMsg);

        if (!isNull(json)) {
            JSONArray aa = json.optJSONArray("data");
            if (!isNull(aa)) {
                List<KeModel> keModelList = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < aa.length(); i++) {
                    JSONObject j = aa.optJSONObject(i);
                    if (!isNull(j)) {
                        JSONObject k = j.optJSONObject("course");
                        if (!isNull(k)) {
                            KeModel keModel = gson.fromJson(k.toString(), KeModel.class);
                            keModel.setCollectionId(j.optInt("id"));// 解析收藏id
                            keModelList.add(keModel);
                        }
                    }
                }
                listListener.setData(keModelList);
            }

        }
    }

    @Override
    protected String getUrl() {
        return url;

    }
}
