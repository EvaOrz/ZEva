package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.com.zwwl.bayuwen.db.TempDataHelper;
import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.GiftAndJiangModel;

/**
 * 增删改 奖状、礼物列表
 */
public class HonorActionApi extends BaseApi {

    private FetchEntryListener listener;
    private Map<String, String> pamas = new HashMap<>();
    private String url = "";

    /**
     * 添加
     *
     * @param context
     * @param type
     * @param model
     * @param listener
     */
    public HonorActionApi(Context context, int type, GiftAndJiangModel model, FetchEntryListener
            listener) {
        super(context);
        this.listener = listener;
        pamas.put("title", model.getTitle());
        pamas.put("desc", model.getTitle());
        pamas.put("type", type + "");
        pamas.put("pic", model.getPic());
        pamas.put("date", model.getDate());
        pamas.put("student_no", model.getStudent_no());
        url = UrlUtil.getHonorurl();
        post();
    }

    /**
     * 删除
     *
     * @param context
     * @param ids
     * @param listener
     */
    public HonorActionApi(Context context, String ids, FetchEntryListener
            listener) {
        super(context);
        this.listener = listener;
        url += UrlUtil.getHonorurl() + "/" + ids;
        delete();
    }

    @Override
    protected Map<String, String> getPostParams() {
        return pamas;
    }

    @Override
    protected String getUrl() {
        return url;
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        listener.setError(errorMsg);
    }
}
