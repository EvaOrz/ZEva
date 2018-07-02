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
import cn.com.zwwl.bayuwen.model.AchievementModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 成就列表api
 */
public class AchievementApi extends BaseApi {
    private FetchEntryListListener listener;

    public AchievementApi(Context context, FetchEntryListListener listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        get();
    }


    @Override
    protected String getUrl() {
        return UrlUtil.getAchievement() + "/all";
    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }

    @Override
    protected void handler(JSONObject json, JSONArray jsonArray, ErrorMsg errorMsg) {
        listener.setError(errorMsg);

        if (!isNull(jsonArray)) {
            Gson gson = new Gson();
            List<AchievementModel> achievementModels = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                AchievementModel a = gson.fromJson(jsonArray.optJSONObject(i).toString(),
                        AchievementModel.class);
                achievementModels.add(a);
            }
            listener.setData(achievementModels);
        } else listener.setData(null);
    }

}
