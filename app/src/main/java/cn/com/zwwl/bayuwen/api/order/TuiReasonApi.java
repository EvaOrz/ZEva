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
    private int type;// 0：offline 1：online

    public TuiReasonApi(Context context, int type, FetchEntryListListener listener) {
        super(context);
        mContext = context;
        this.type = type;
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
            if (type == 1) {
                JSONArray offline = json.optJSONArray("offline");
                if (!isNull(offline)) {
                    listener.setData(parseErrorMsg(offline));
                }
            } else {
                JSONArray online = json.optJSONArray("online");
                if (!isNull(online)) {
                    listener.setData(parseErrorMsg(online));
                }
            }
        }
    }

    private List<ErrorMsg> parseErrorMsg(JSONArray array) {
        List<ErrorMsg> offDatas = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            ErrorMsg e = new ErrorMsg();
            JSONObject j = array.optJSONObject(i);
            e.setDesc(j.optString("name"));
            e.setNo(Integer.valueOf(j.optString("value")));
            offDatas.add(e);
        }
        return offDatas;
    }
}

