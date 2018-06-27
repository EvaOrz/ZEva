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
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.fm.AlbumModel;

/**
 * 添加、删除收藏接口
 */
public class CollectionApi extends BaseApi {
    private String url;
    private FetchEntryListener listener;
    private Map<String, String> pamas = new HashMap<>();
    private int type;

    /**
     * 添加收藏
     *
     * @param context
     * @param content
     * @param type     1-课程  2-FM
     * @param listener
     */
    public CollectionApi(Context context, String content, int type, FetchEntryListener listener) {
        super(context);
        mContext = context;
        this.type = type;
        pamas.put("kid", content);
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
    public CollectionApi(Context context, int cid, FetchEntryListener listener) {
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
        listener.setError(errorMsg);

        if (!isNull(json)) {
            if (type == 1) {
                KeModel keModel = new KeModel();
                // "state":1(关注) 0：（未关注）
                keModel.setCollectionId(json.optInt("id"));
                keModel.setCollection_state(json.optInt("state"));
                listener.setData(keModel);
            } else if (type == 2) {
//                AlbumModel albumModel = new AlbumModel();
//                albumModel.setConllectId(json.optInt("id"));
            }

        }
    }

    @Override
    protected String getUrl() {
        return url;

    }

}
