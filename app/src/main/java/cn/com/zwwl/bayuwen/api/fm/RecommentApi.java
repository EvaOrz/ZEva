package cn.com.zwwl.bayuwen.api.fm;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.db.DataHelper;
import cn.com.zwwl.bayuwen.model.RecommentModel;
import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * FM首页推荐接口
 */
public class RecommentApi extends BaseApi {

    private FetchRecommentListListener listener;
    private List<RecommentModel> recommentModels = new ArrayList<>();

    public RecommentApi(Context context, FetchRecommentListListener listener) {
        mContext = context;
        this.listener = listener;
        get();
    }

    @Override
    protected String getUrl() {
        return UrlUtil.getMainurl();
    }


    /**
     * 19        APP-FM导航栏推荐（Banner）
     * 20        APP-FM热门推荐
     * 21        APP-FM新课推荐
     * 22        APP-FM直播推荐
     * 23        APP-FM回放录播
     * 24        APP-FM名师推荐
     *
     * @param jsonObject
     * @param errorMsg
     */
    @Override
    protected void handler(JSONObject jsonObject, ErrorMsg errorMsg) {
        if (errorMsg == null) {
            JSONObject data = jsonObject.optJSONObject("data");
            if (isNull(data)) listener.setError(new ErrorMsg());
            else {
                JSONArray a19 = data.optJSONArray("19");
                if (!isNull(a19)) {
                    for (int i = 0; i < a19.length(); i++) {
                        JSONObject o = a19.optJSONObject(i);
                        RecommentModel f = new RecommentModel();
                        f.parseRecommentModel(o, f);
                        recommentModels.add(f);
                    }
                }
                JSONArray a20 = data.optJSONArray("20");
                if (!isNull(a20)) {
                    for (int i = 0; i < a20.length(); i++) {
                        JSONObject o = a20.optJSONObject(i);
                        RecommentModel f = new RecommentModel();
                        f.parseRecommentModel(o, f);
                        recommentModels.add(f);
                    }
                }

                listener.setData(recommentModels);
            }
        } else {
            listener.setError(errorMsg);
        }
    }

    @Override
    protected String getHeadValue() {
        return DataHelper.getUserToken(mContext);
    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }

    public interface FetchRecommentListListener {
        /**
         * 给View传递数据
         *
         * @param list
         */
        public void setData(List<RecommentModel> list);


        public void setError(ErrorMsg error);
    }
}
