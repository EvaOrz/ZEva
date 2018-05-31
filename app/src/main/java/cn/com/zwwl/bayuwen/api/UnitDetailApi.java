package cn.com.zwwl.bayuwen.api;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.UnitDetailModel;
import cn.com.zwwl.bayuwen.util.GsonUtil;

/**
 *  课程单元详情
 *  Created by zhumangmang at 2018/5/31 15:11
 */
public class UnitDetailApi extends BaseApi{
    private ResponseCallBack callBack;
    private Activity activity;
    private String url;
    public UnitDetailApi(Activity context, String kId,String cId, ResponseCallBack callBack) {
        super(context);
        this.callBack=callBack;
      url=UrlUtil.getUnitDetail()+"?kid="+kId+"?lecture_id="+cId;
        this.activity=context;
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
    protected void handler(final JSONObject json, JSONArray array, final ErrorMsg errorMsg) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                callBack.error(errorMsg);
                callBack.success(GsonUtil.parseJson(UnitDetailModel.class, json.toString()));
            }
        });
    }
}
