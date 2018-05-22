package cn.com.zwwl.bayuwen.api.fm;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.model.fm.AlbumModel;
import cn.com.zwwl.bayuwen.model.fm.RecommentModel;
import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * FM首页推荐接口
 */
public class RecommentApi extends BaseApi {

    private FetchRecommentListListener listener;
    private List<RecommentModel> recommentModels = new ArrayList<>();

    public RecommentApi(Context context, FetchRecommentListListener listener) {
        super(context);
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
     * @param json
     * @param errorMsg
     */
    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        if (errorMsg != null)
            listener.setError(errorMsg);

        if (!isNull(json)) {
            JSONArray a19 = json.optJSONArray("19");
            if (!isNull(a19)) {
                for (int i = 0; i < a19.length(); i++) {
                    JSONObject o = a19.optJSONObject(i);
                    RecommentModel f = new RecommentModel();
                    f.parseRecommentModel(o, f);
                    recommentModels.add(f);
                }
            }
            JSONArray a20 = json.optJSONArray("20");
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
