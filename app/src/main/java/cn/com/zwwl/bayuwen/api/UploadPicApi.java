package cn.com.zwwl.bayuwen.api;

import android.app.Activity;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.CommonModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.UserModel;
import cn.com.zwwl.bayuwen.util.GsonUtil;

/**
 * 上传图片api
 */
public class UploadPicApi extends BaseApi {
    private FetchEntryListener listener;
    ResponseCallBack callBack;
    Activity activity;

    public UploadPicApi(Context context, File file, FetchEntryListener listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        postFile(file);
    }

    public UploadPicApi(Activity context, List<File> file, ResponseCallBack callBack) {
        super(context);
        activity = context;
        this.callBack = callBack;
        postMultiFile(file);
    }

    @Override
    protected String getUrl() {
        return UrlUtil.uploadUrl();
    }

    @Override
    protected void handler(JSONObject json, final JSONArray array, final ErrorMsg errorMsg) {
        if (listener != null) {
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
        if (callBack != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ArrayList<CommonModel> commonModel = null;
                    if (array != null) {
                        commonModel = GsonUtil.parseJsonArray(CommonModel.class, array.toString());
                    }
                    callBack.result(commonModel, errorMsg);
                }
            });
        }
    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }


}

