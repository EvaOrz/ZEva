package cn.com.zwwl.bayuwen.api;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.AnswerModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.util.GsonUtil;

/**
 * 错题本
 * Created by zhumangmang at 2018/6/21 14:23
 */
public class ErrorListApi extends BaseApi {
    private Activity activity;
    private ResponseCallBack<List<AnswerModel>> callBack;
    private String url;

    public ErrorListApi(Activity context, String pId, ResponseCallBack<List<AnswerModel>> callBack) {
        super(context);
        this.activity = context;
        this.callBack = callBack;
        url = UrlUtil.errorList() + "?puzzleId=246&studentId=1201597";
//        url = UrlUtil.getQuestionList() + "?puzzleId=" + pId + "&studentId=" + TempDataHelper.getCurrentChildNo(activity);
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
                List<AnswerModel> models = null;
                if (array != null)
                    models = GsonUtil.parseJsonArray(AnswerModel.class, array.toString());
                callBack.result(models, errorMsg);
            }
        });
    }
}
