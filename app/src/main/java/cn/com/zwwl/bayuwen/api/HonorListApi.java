package cn.com.zwwl.bayuwen.api;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.GiftAndJiangModel;
import cn.com.zwwl.bayuwen.util.GsonUtil;

/**
 * 获取奖状、礼物列表
 */
public class HonorListApi extends BaseApi {

    private int type = 1;
    private FetchEntryListListener listListener;
    private String student_no;

    /**
     * @param context
     * @param type         // 1-奖状 2-礼物
     * @param listListener
     */
    public HonorListApi(Context context, int type, String student_no, FetchEntryListListener
            listListener) {
        super(context);
        this.type = type;
        this.student_no = student_no;
        this.listListener = listListener;
        get();
    }

    @Override
    protected Map<String, String> getPostParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return UrlUtil.getHonorurl() + "?type=" + type + "&student_no=" + student_no;
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        if (errorMsg != null) listListener.setError(errorMsg);

        if (!isNull(array)) {
            List<GiftAndJiangModel> datas = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                GiftAndJiangModel g = GsonUtil.parseJson(GiftAndJiangModel.class, array
                        .optJSONObject(i).toString());
                datas.add(g);
            }
            listListener.setData(datas);
        }
    }
}
