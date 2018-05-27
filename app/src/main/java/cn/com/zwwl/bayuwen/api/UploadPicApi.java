package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.db.UserDataHelper;
import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.AddressModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.UserModel;
import cn.com.zwwl.bayuwen.model.fm.AlbumModel;

/**
 * 上传图片api
 */
public class UploadPicApi extends BaseApi {
    private FetchEntryListener listener;

    public UploadPicApi(Context context, File file, FetchEntryListener listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        postFile(file);
    }

    @Override
    protected String getUrl() {
        return UrlUtil.uploadUrl();
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        if (errorMsg != null)
            listener.setError(errorMsg);
        if (!isNull(json)) {
            UserModel m = new UserModel();
            m.setPic(json.optString("url"));
            listener.setData(m);
        } else {
            listener.setData(null);
        }

    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }


}

