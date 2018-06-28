package cn.com.zwwl.bayuwen.api.order;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.GroupBuyModel;

/**
 * 我的团购页面：
 * <p>
 * 我参加的团购
 * 我发起的团购
 */
public class MyTuanApi extends BaseApi {
    private FetchMyTuanListListener listener;

    public MyTuanApi(Context context, FetchMyTuanListListener listener) {
        super(context);
        this.listener = listener;
        get();
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        listener.setError(errorMsg);
        if (!isNull(json)) {
            Gson gson = new Gson();
            JSONArray sponsor = json.optJSONArray("sponsor");
            List<GroupBuyModel> sList = new ArrayList<>();
            if (!isNull(sponsor)) {
                for (int i = 0; i < sponsor.length(); i++) {
                    GroupBuyModel g = gson.fromJson(sponsor.optJSONObject(i).toString(),
                            GroupBuyModel.class);
                    sList.add(g);
                }

            }
            List<GroupBuyModel> jList = new ArrayList<>();
            JSONArray join = json.optJSONArray("join");
            if (!isNull(join)) {
                for (int i = 0; i < join.length(); i++) {
                    GroupBuyModel g = gson.fromJson(join.optJSONObject(i).toString(),
                            GroupBuyModel.class);
                    jList.add(g);
                }
            }
            listener.setData(sList, jList);
        } else listener.setData(null, null);
    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return UrlUtil.getMyTuan();
    }


    public interface FetchMyTuanListListener {
        public void setData(List sponsor, List join);

        public void setError(ErrorMsg error);
    }
}
