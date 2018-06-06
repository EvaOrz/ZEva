package cn.com.zwwl.bayuwen.api.order;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 退费理由api
 */
public class TuiReasonApi extends BaseApi {

    private FetchEntryListListener listener;

    public TuiReasonApi(Context context, FetchEntryListListener listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        get();
    }


    @Override
    protected String getUrl() {
        return UrlUtil.getTuifeeReasonList();
    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        if (errorMsg != null) listener.setError(errorMsg);

        if (!isNull(json)) {
            List<ErrorMsg> errorMsgs = new ArrayList<>();
            JSONArray offline = json.optJSONArray("offline");
            if (!isNull(offline)) {
                errorMsgs.addAll(parseErrorMsg(0, offline));
            }
            JSONArray online = json.optJSONArray("online");
            if (!isNull(online)) {
                errorMsgs.addAll(parseErrorMsg(1, online));
            }
            listener.setData(errorMsgs);
        }
    }

    private List<ErrorMsg> parseErrorMsg(int isOffline, JSONArray array) {
        List<ErrorMsg> offDatas = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            ErrorMsg e = new ErrorMsg();
            JSONObject j = array.optJSONObject(i);
            e.setDesc(j.optString("name"));
            e.setNo(isOffline);
            offDatas.add(e);
        }
        return offDatas;
    }
}

