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
    private int id;

    public RecommentApi(Context context, int id, FetchRecommentListListener listener) {
        super(context);
        mContext = context;
        this.id = id;
        this.listener = listener;
        get();
    }

    @Override
    protected String getUrl() {
        return UrlUtil.getMainurl(id);
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        listener.setError(errorMsg);
        if (!isNull(array)) {
            for (int i = 0; i < array.length(); i++) {
                JSONObject o = array.optJSONObject(i);
                RecommentModel f = new RecommentModel();
                f.parseRecommentModel(o, f);
                recommentModels.add(f);
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
