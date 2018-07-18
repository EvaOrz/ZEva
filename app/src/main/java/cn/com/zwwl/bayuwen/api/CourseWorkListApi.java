package cn.com.zwwl.bayuwen.api;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.com.zwwl.bayuwen.http.BaseApi;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.MyCourseModel;
import cn.com.zwwl.bayuwen.model.WorkListModel;
import cn.com.zwwl.bayuwen.util.GsonUtil;

public class CourseWorkListApi extends BaseApi {
    ResponseCallBack<WorkListModel> listener;
    Activity context;
    private HashMap<String,String> map=new HashMap<>();

    public CourseWorkListApi(Activity context, HashMap<String,String> paras,ResponseCallBack<WorkListModel> listener) {
        super(context);
        this.context = context;
        this.listener = listener;
        this.map=paras;
        post();
    }

    @Override
    protected String getUrl() {
        return UrlUtil.courseList();
    }

    @Override
    protected Map<String, String> getPostParams() {
        return map;
    }

    @Override
    protected void handler(final JSONObject json, JSONArray array, final ErrorMsg errorMsg) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                WorkListModel model=null;
                if (json!=null){
                    model=GsonUtil.parseJson(WorkListModel.class,json.toString());
                }
                listener.result(model,errorMsg);
            }
        });

    }
}
