package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.com.zwwl.bayuwen.db.UserDataHelper;
import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 收藏接口
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
        mContext = context;
        this.url = UrlUtil.getCollecturl() + "/" + cid;
        this.listener = listener;
        delete();
    }


    @Override
    protected Map<String, String> getPostParams() {
        return pamas;
    }

    /**
     * success = true表示操作成功
     *
     * @param jsonObject
     * @param errorMsg
     */
    @Override
    protected void handler(JSONObject jsonObject, ErrorMsg errorMsg) {
        if (errorMsg == null) {
            ErrorMsg err = new ErrorMsg();
            boolean success = jsonObject.optBoolean("success", false);
            if (success) {
                JSONObject data = jsonObject.optJSONObject("data");
                if (!isNull(data)) {
                    err.setNo(data.optInt("id"));
                } else {
                    err.setNo(0);
                }
            }
            listener.setData(err);
        } else {
            listener.setData(errorMsg);
        }

    }


    @Override
    protected String getHeadValue() {
        return UserDataHelper.getUserToken(mContext);
    }

    @Override
    protected String getUrl() {
        return url;

    }

}
