package cn.com.zwwl.bayuwen.api.fm;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.model.fm.AlbumModel;
import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 获取课程的专辑列表接口
 * <p>
 * 搜索接口
 * <p>
 * 播放历史接口
 */
public class AlbumListApi extends BaseApi {
    private String url;
    private List<AlbumModel> albumModels = new ArrayList<>();
    private FetchAlbumListListener listener;
    private boolean isCollect = false;

    /**
     * 通过kid获取课程的专辑列表
     *
     * @param context
     * @param kid
     * @param page
     * @param listener
     */
    public AlbumListApi(Context context, String kid, int page, FetchAlbumListListener listener) {
        super(context);
        mContext = context;
        this.url = UrlUtil.getAlbumListUrl(kid, page);
        this.listener = listener;
        get();
    }

    /**
     * 搜索构造
     *
     * @param context
     * @param search
     * @param listener
     */
    public AlbumListApi(Context context, String search, FetchAlbumListListener listener) {
        super(context);
        mContext = context;
        this.url = UrlUtil.getSearchUrl(search);
        this.listener = listener;
        get();
    }

    /**
     * 获取播放历史
     *
     * @param context
     * @param listener
     */
    public AlbumListApi(Context context, FetchAlbumListListener listener) {
        super(context);
        mContext = context;
        this.url = UrlUtil.getHistoryurl();
        this.listener = listener;
        get();
    }

    /**
     * 获取收藏列表
     *
     * @param context
     * @param type
     * @param listener
     */
    public AlbumListApi(Context context, int type, FetchAlbumListListener listener) {
        super(context);
        mContext = context;
        isCollect = true;
        this.url = UrlUtil.getCollecturl() + "?type=" + type;
        this.listener = listener;
        get();
    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        if (errorMsg != null)
            listener.setError(errorMsg);

        if (!isNull(json)) {
            JSONArray jsonArray = json.optJSONArray("data");
            if (!isNull(jsonArray)) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject o = jsonArray.optJSONObject(i);
                    AlbumModel f = new AlbumModel();
                    if (isCollect) {
                        f.parseAlbumModel(o, f);
                    } else
                        f.parseKinfo(o, f);
                    albumModels.add(f);
                }
                listener.setData(albumModels);
            }

        }
    }

    @Override
    protected String getUrl() {
        return url;

    }

    public interface FetchAlbumListListener {
        /**
         * 给View传递数据
         *
         * @param list
         */
        public void setData(List<AlbumModel> list);


        public void setError(ErrorMsg error);
    }
}
