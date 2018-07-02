package cn.com.zwwl.bayuwen.api.order;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.FaPiaoModel;

/**
 * 发票详情、申请开票
 */
public class FaPiaoApi extends BaseApi {
    private FetchEntryListener listener;
    private String url = "";
    private Map<String, String> pamas = new HashMap<>();

    public FaPiaoApi(Context context, String id, FetchEntryListener listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        url += UrlUtil.getPiaoDetail() + "?id=" + id;
        get();
    }

    public FaPiaoApi(Context context, FaPiaoModel faPiaoModel, String oids,String invo_remark, FetchEntryListener
            listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        pamas.put("item_oid", oids);
        pamas.put("utype", faPiaoModel.getUtype());
        pamas.put("invo_type", faPiaoModel.getPiaoType().getValue()+"");
        pamas.put("invo_amount", faPiaoModel.getPiaoType().getMoney());
        pamas.put("invo_title", faPiaoModel.getInvo_title());
        pamas.put("invo_tax_no", faPiaoModel.getInvo_tax_no());
        pamas.put("rece_name", faPiaoModel.getRece_name());
        pamas.put("rece_phone", faPiaoModel.getRece_phone());
        pamas.put("rece_address", faPiaoModel.getRece_address());
        pamas.put("invo_remark", invo_remark);

        url += UrlUtil.kaiPiao();
        post();
    }

    @Override
    protected String getUrl() {
        return url;
    }

    @Override
    protected Map<String, String> getPostParams() {
        return pamas;
    }

    @Override
    protected void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg) {
        listener.setError(errorMsg);

        if (!isNull(json)) {
            Gson gson = new Gson();
            FaPiaoModel mm = gson.fromJson(json.toString(),
                    FaPiaoModel.class);
            listener.setData(mm);
        }


    }
}
