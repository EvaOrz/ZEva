package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import org.json.JSONObject;

import java.io.File;
import java.util.Map;

import cn.com.zwwl.bayuwen.db.UserDataHelper;
import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

public class UploadApi extends BaseApi {
    private FetchEntryListener listener;

    public UploadApi(Context context, File file, FetchEntryListener listener) {
        mContext = context;
        this.listener = listener;
        postFile(file);
    }

    @Override
    protected String getUrl() {
        return UrlUtil.uploadUrl();
    }

    @Override
    protected void handler(JSONObject jsonObject, ErrorMsg errorMsg) {
        if (errorMsg == null) {
            ErrorMsg err = new ErrorMsg();
            boolean success = jsonObject.optBoolean("success", false);
            if (success) {
                String data = jsonObject.optString("data");
                err.setDesc(data);
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
    protected Map<String, String> getPostParams() {
        return null;
    }


}

