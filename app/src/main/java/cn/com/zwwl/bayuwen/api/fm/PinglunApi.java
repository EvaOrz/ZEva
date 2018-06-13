package cn.com.zwwl.bayuwen.api.fm;

import android.app.Activity;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.EvalListModel;
import cn.com.zwwl.bayuwen.model.fm.PinglunModel;
import cn.com.zwwl.bayuwen.util.GsonUtil;

/**
 * 获取课程评论接口
 */
public class PinglunApi extends BaseApi {
    private FetchEntryListListener listListener;
    private String kid, cid;
    private List<PinglunModel> pinglunModels = new ArrayList<>();
    private String url;
    private Activity activity;
    private ResponseCallBack<EvalListModel> callBack;

    public PinglunApi(Context context, String kid, String cid, FetchEntryListListener listListener) {
        super(context);
        mContext = context;
        isNeedJsonArray = true;
        this.listListener = listListener;
        this.kid = kid;
        this.cid = cid;
        url = UrlUtil.getPingListurl(kid);
        get();
    }

    public PinglunApi(Activity context, String kid, String page, ResponseCallBack<EvalListModel> callBack) {
        super(context);
        this.activity = context;
        this.callBack = callBack;
        url = UrlUtil.getEvalList() + "?kid=" + kid + "&page=" + page;
        get();
    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return url;
    }

    @Override
    protected void handler(final JSONObject json, JSONArray array, final ErrorMsg errorMsg) {
        if (callBack != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    EvalListModel model = null;
                    if (json != null) {
                        model = GsonUtil.parseJson(EvalListModel.class, json.toString());
                    }
                    callBack.result(model, errorMsg);
                }
            });
        } else {
            if (errorMsg != null)
                listListener.setError(errorMsg);
            if (!isNull(array)) {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject o = array.optJSONObject(i);
                    PinglunModel pinglunModel = new PinglunModel();
                    pinglunModel.parsePinglunModel(o, pinglunModel);
                    pinglunModels.add(pinglunModel);
                }
                listListener.setData(pinglunModels);
            }
        }
    }
}
