package cn.com.zwwl.bayuwen.api.fm;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.db.UserDataHelper;
import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.fm.AlbumModel;

/**
 * 添加、删除收藏接口
 */
public class CollectionApi extends BaseApi {
    private String url;
    private FetchEntryListener listener;
    private Map<String, String> pamas = new HashMap<>();

    /**
     * 添加收藏
     *
     * @param context
     * @param content
     * @param type     1-课程  2-文本  3-图片  4-链接
     * @param listener
     */
    public CollectionApi(Context context, String content, int type, FetchEntryListener listener) {
        super(context);
        mContext = context;
        pamas.put("content", content);
        pamas.put("type", type + "");
        this.url = UrlUtil.getCollecturl();
        this.listener = listener;
        post();
    }

    /**
     * 删除收藏
     *
     * @param context
     * @param cid      收藏id
     * @param listener
     */
    public CollectionApi(Context context, String cid, FetchEntryListener listener) {
        super(context);
        mContext = context;
        this.url = UrlUtil.getCollecturl() + "/" + cid;
        this.listener = listener;
        delete();
    }


    @Override
    protected Map<String, String> getPostParams() {
        return pamas;
    }


    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        if (errorMsg != null)
            listener.setError(errorMsg);

        if (!isNull(json)) {
//            ErrorMsg err = new ErrorMsg();
//            err.setNo(json.optInt("id"));
//            listener.setData(err);
        }
    }

    @Override
    protected String getUrl() {
        return url;

    }

}
