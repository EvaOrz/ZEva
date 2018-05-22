package cn.com.zwwl.bayuwen.api.fm;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.model.fm.AlbumModel;
import cn.com.zwwl.bayuwen.model.fm.PinglunModel;
import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 获取课程评论接口
 */
public class PinglunApi extends BaseApi {
    private FetchPingListListener listListener;
    private String kid, cid;
    private List<PinglunModel> pinglunModels = new ArrayList<>();

    public PinglunApi(Context context, String kid, String cid, FetchPingListListener listListener) {
        super(context);
        mContext = context;
        isNeedJsonArray = true;
        this.listListener = listListener;
        this.kid = kid;
        this.cid = cid;
        get();
    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return UrlUtil.getPingListurl(kid);
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
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


    public interface FetchPingListListener {
        /**
         * 给View传递数据
         *
         * @param list
         */
        public void setData(List<PinglunModel> list);


        public void setError(ErrorMsg error);
    }
}
