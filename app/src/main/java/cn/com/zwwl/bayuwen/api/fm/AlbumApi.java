package cn.com.zwwl.bayuwen.api.fm;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.model.fm.AlbumModel;
import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 获取fm详情接口
 */
public class AlbumApi extends BaseApi {
    private String url;
    private AlbumModel albumModel;
    private FetchEntryListener listener;

    public AlbumApi(Context context, String fmId, FetchEntryListener listener) {
        super(context);
        this.mContext = context;
        this.url = UrlUtil.getAlbumUrl(fmId);
        this.listener = listener;
        albumModel = new AlbumModel();
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
            albumModel = new AlbumModel();
            albumModel.parseAlbumModel(json, albumModel);
            listener.setData(albumModel);
        }
    }


    @Override
    protected String getUrl() {
        return url;

    }
}
