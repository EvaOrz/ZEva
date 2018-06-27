package cn.com.zwwl.bayuwen.api;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.db.TempDataHelper;
import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.OptionModel;
import cn.com.zwwl.bayuwen.util.GsonUtil;

public class AnswerDetailApi extends BaseApi {
    private Activity activity;
    private ResponseCallBack<List<OptionModel>> callBack;
    private String url;

    public AnswerDetailApi(Activity context, String pId, ResponseCallBack<List<OptionModel>> callBack) {
        super(context);
        this.activity = context;
        this.callBack = callBack;
//        url = UrlUtil.errorList() + "?puzzleId=246&studentId=1201597";
        url = UrlUtil.answerDeatil() + "?sectionId=" + pId + "&studentId=" + TempDataHelper.getCurrentChildNo(activity);
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
    protected void handler(JSONObject json, final JSONArray array, final ErrorMsg errorMsg) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<OptionModel> models = null;
                if (array != null)
                    models = GsonUtil.parseJsonArray(OptionModel.class, array.toString());
                callBack.result(models, errorMsg);
            }
        });
    }
}
