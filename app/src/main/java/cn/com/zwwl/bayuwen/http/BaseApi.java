package cn.com.zwwl.bayuwen.http;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Map;

import cn.com.zwwl.bayuwen.listener.FetchDataListener;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

public abstract class BaseApi {
    protected Context mContext;
    protected HttpUtil httpUtil = null;
    protected boolean isNeedJsonArray = false;// 返回的data数据是否是array格式

    public BaseApi(Context context) {
        httpUtil = HttpUtil.getInstance(context);
    }


    /**
     * 接口地址
     */
    protected abstract String getUrl();

    /**
     * pama参数设置
     */
    protected abstract Map<String, String> getPostParams();


    protected void post() {
        httpUtil.postDataAsynToNet(getUrl(), getPostParams(), new FetchDataListener() {
            @Override
            public void fetchData(boolean isSuccess, String data, boolean fromHttp) {
                handlerData(isSuccess, data, fromHttp);
            }
        });
    }

    protected void put() {
        httpUtil.putDataAsynToNet(getUrl(), getPostParams(), new FetchDataListener() {
            @Override
            public void fetchData(boolean isSuccess, String data, boolean fromHttp) {
                handlerData(isSuccess, data, fromHttp);
            }
        });
    }

    protected void postFile(File file) {
        httpUtil.postFile(getUrl(), file, new FetchDataListener() {
            @Override
            public void fetchData(boolean isSuccess, String data, boolean fromHttp) {
                handlerData(isSuccess, data, fromHttp);
            }
        });
    }

    protected void postMultiFile(List<File> file) {
        httpUtil.postMultiFile(getUrl(), file, new FetchDataListener() {
            @Override
            public void fetchData(boolean isSuccess, String data, boolean fromHttp) {
                handlerData(isSuccess, data, fromHttp);
            }
        });
    }

    protected void get() {
        httpUtil.getDataAsynFromNet(getUrl(), new FetchDataListener() {
            @Override
            public void fetchData(boolean isSuccess, String data, boolean fromHttp) {
                handlerData(isSuccess, data, fromHttp);
            }
        });
    }

    protected void delete() {
        httpUtil.deleteDataAsynToNet(getUrl(), getPostParams(), new FetchDataListener() {
            @Override
            public void fetchData(boolean isSuccess, String data, boolean fromHttp) {
                handlerData(isSuccess, data, fromHttp);
            }
        });

    }

    protected void patch() {
        httpUtil.patchDataAsynToNet(getUrl(), getPostParams(), new FetchDataListener() {
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
     * @param json
     * @param array
     */
    protected abstract void handler(JSONObject json, JSONArray array, ErrorMsg errorMsg);


    /**
     * 解析数据
     * <p>
     * {"success":false,"statusCode":500,"data":[],"err_msg":"该账号不存在"}
     *
     * @param isSuccess      true:解析data ; false:解析err_msg
     * @param responseString
     * @param fromHttp       后期做接口缓存用，目前不需要
     */
    private void handlerData(boolean isSuccess, String responseString, boolean fromHttp) {
        if (isSuccess) {//200
            try {
                JSONObject object = new JSONObject(responseString);
                if (isNull(object)) {
                    handler(null, null, getServerError());
                } else {
                    if (object.optBoolean("success")) {// 解析data
                        JsonParser parser = new JsonParser();
                        JsonElement element = parser.parse(responseString);
                        JsonObject root = element.getAsJsonObject();
                        if (root.get("data").isJsonArray()) {
                            handler(null, object.optJSONArray("data"), null);
                        } else
                            handler(object.optJSONObject("data"), null, null);
                    } else {
                        String err_msg = object.optString("err_msg");
                        ErrorMsg errorMsg = new ErrorMsg();
                        errorMsg.setDesc(err_msg);
                        handler(null, null, errorMsg);
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
                handler(null, null, getServerError());
            }
        } else {
            handler(null, null, getServerError());
        }
    }

    /**
     * 报服务器错
     *
     * @return
     */
    private ErrorMsg getServerError() {
        ErrorMsg msg = new ErrorMsg();
        msg.setDesc("Server error");
        return msg;
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
