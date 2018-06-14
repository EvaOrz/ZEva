package cn.com.zwwl.bayuwen.api;

import android.app.Activity;
import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.OrderModel;
import cn.com.zwwl.bayuwen.model.SearchModel;
import cn.com.zwwl.bayuwen.util.GsonUtil;

/**
 * 获取课程列表接口
 */
public class CourseListApi extends BaseApi {
    private FetchEntryListListener listListener;
    private String url;
    private Activity activity;
    private ResponseCallBack<SearchModel> callBack;

    public CourseListApi(Activity context, String url, ResponseCallBack<SearchModel> callBack) {
        super(context);
        this.activity = context;
        this.callBack = callBack;
        this.url = url;
        get();
    }

    /**
     * 选课中心获取数据列表
     *
     * @param context
     * @param url
     * @param listListener
     */
    public CourseListApi(Context context, String url, FetchEntryListListener listListener) {
        super(context);
        mContext = context;
        this.listListener = listListener;
        this.url = url;
        get();
    }

    /**
     * 获取可开发票的课程列表
     *
     * @param context
     * @param listListener
     */
    public CourseListApi(Context context, FetchEntryListListener listListener) {
        super(context);
        mContext = context;
        this.listListener = listListener;
        this.url = UrlUtil.getPiaoKeListUrl();
        get();

    }

    @Override
    protected String getUrl() {
        return url;
    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }

    @Override
    protected void handler(final JSONObject json, JSONArray jsonArray, final ErrorMsg errorMsg) {
        if (callBack != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SearchModel model = null;
                    if (json != null)
                        model = GsonUtil.parseJson(SearchModel.class, json.toString());
                    callBack.result(model, errorMsg);
                }
            });
        } else {
            if (errorMsg != null) {
                listListener.setError(errorMsg);
            } else listListener.setError(null);

            if (!isNull(json)) {
                // 解析选课中心课列表
                listListener.setData(parseKemodel(json.optJSONArray("data")));
            }

            // 开票历史
            if (!isNull(jsonArray)) {
                listListener.setData(parseKemodel(jsonArray));
            }
        }
    }

    private List<KeModel> parseKemodel(JSONArray array) {
        List<KeModel> ks = new ArrayList<>();
        if (!isNull(array)) {
            Gson gson = new Gson();
            for (int i = 0; i < array.length(); i++) {
                JSONObject j = array.optJSONObject(i);
                KeModel keModel = gson.fromJson(j.toString(), KeModel.class);
                JSONObject jj = j.optJSONObject("order_detail");
                if (!isNull(jj)) {
                    OrderModel.OrderDetailModel detailModel = gson.fromJson(jj.toString(),
                            OrderModel
                                    .OrderDetailModel.class);
                    keModel.setOrderDetailModel(detailModel);
                }
                ks.add(keModel);
            }
        }
        return ks;
    }

}
