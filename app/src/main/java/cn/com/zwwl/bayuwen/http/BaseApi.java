package cn.com.zwwl.bayuwen.http;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Map;

import cn.com.zwwl.bayuwen.listener.FetchDataListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

public abstract class BaseApi {
    protected Context mContext;
    protected HttpUtil httpUtil = HttpUtil.getInstance();

    /**
     * 接口地址
     */
    protected abstract String getUrl();

    /**
     * pama参数设置
     */
    protected abstract Map<String, String> getPostParams();

    /**
     * header参数设置
     */
    protected abstract String getHeadValue();


    protected void post() {
        httpUtil.postDataAsynToNet(getUrl(), getPostParams(), getHeadValue(), new FetchDataListener() {
            @Override
            public void fetchData(boolean isSuccess, String data, boolean fromHttp) {
                Log.e(getUrl(), data);
                handlerData(isSuccess, data, fromHttp);
            }
        });
    }

    protected void postFile(File file) {
        httpUtil.postFile(getUrl(), getHeadValue(), file, new FetchDataListener() {
            @Override
            public void fetchData(boolean isSuccess, String data, boolean fromHttp) {
                Log.e(getUrl(), data);
                handlerData(isSuccess, data, fromHttp);
            }
        });
    }

    protected void get() {
        httpUtil.getDataAsynFromNet(getUrl(), getHeadValue(), new FetchDataListener() {
            @Override
            public void fetchData(boolean isSuccess, String data, boolean fromHttp) {
                Log.e(getUrl(), data);
                handlerData(isSuccess, data, fromHttp);
            }
        });
    }

    protected void delete() {
        httpUtil.deleteDataAsynToNet(getUrl(), getPostParams(), getHeadValue(), new FetchDataListener() {
            @Override
            public void fetchData(boolean isSuccess, String data, boolean fromHttp) {
                Log.e(getUrl(), data);
                handlerData(isSuccess, data, fromHttp);
            }
        });

    }

    protected void patch() {
        httpUtil.patchDataAsynToNet(getUrl(), getPostParams(), getHeadValue(), new FetchDataListener() {
            @Override
            public void fetchData(boolean isSuccess, String data, boolean fromHttp) {
                Log.e(getUrl(), data);
                handlerData(isSuccess, data, fromHttp);
            }
        });
    }

    /**
     * 由子类去解析json数据
     *
     * @param data
     */
    protected abstract void handler(JSONObject data, ErrorMsg errorMsg);

    /**
     * 解析数据
     *
     * @param isSuccess
     * @param data
     * @param fromHttp
     */
    private void handlerData(boolean isSuccess, String data, boolean fromHttp) {
        if (isSuccess) {
            if (TextUtils.isEmpty(data)) {
                handler(null, new ErrorMsg());
                // showToast(R.string.net_error);
            } else {
                try {
                    if (data.equals("[]")) {
                        data = "{}";
                    }
                    JSONObject obj = new JSONObject(data);
                    if (isNull(obj)) {
                        handler(null, new ErrorMsg());
                    } else {
//                        JSONObject dataJson = obj.optJSONObject("data");
//                        if (!isNull(dataJson))
                        handler(obj, null);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            handler(null, new ErrorMsg());
        }
    }

    /**
     * JSONObject是否为空
     *
     * @param object
     * @return
     */
    protected boolean isNull(JSONObject object) {
        return JSONObject.NULL.equals(object) || object == null;
    }

    /**
     * JSONArray是否为空
     *
     * @param array
     * @return
     */
    protected boolean isNull(JSONArray array) {
        return JSONObject.NULL.equals(array) || array == null || array.length() == 0;
    }
}
