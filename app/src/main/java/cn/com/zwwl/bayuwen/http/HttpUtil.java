package cn.com.zwwl.bayuwen.http;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cn.com.zwwl.bayuwen.listener.FetchDataListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Eva. on 2018/3/23.
 */

public class HttpUtil {
    private static final byte[] LOCKER = new byte[0];
    private static HttpUtil mInstance;
    private OkHttpClient mOkHttpClient;

    private HttpUtil() {
        okhttp3.OkHttpClient.Builder ClientBuilder = new okhttp3.OkHttpClient.Builder();
        ClientBuilder.readTimeout(20, TimeUnit.SECONDS);//读取超时
        ClientBuilder.connectTimeout(6, TimeUnit.SECONDS);//连接超时
        ClientBuilder.writeTimeout(60, TimeUnit.SECONDS);//写入超时
        //支持HTTPS请求，跳过证书验证
        ClientBuilder.sslSocketFactory(createSSLSocketFactory());
        ClientBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        mOkHttpClient = ClientBuilder.build();
    }

    /**
     * 单例模式获取NetUtils
     *
     * @return
     */
    public static HttpUtil getInstance() {
        if (mInstance == null) {
            synchronized (LOCKER) {
                if (mInstance == null) {
                    mInstance = new HttpUtil();
                }
            }
        }
        return mInstance;
    }

    /**
     * get请求，同步方式，获取网络数据，是在主线程中执行的，需要新起线程，将其放到子线程中执行
     *
     * @param url
     * @return
     */
    public Response getDataSynFromNet(String url, String headValue) {
        //1 构造Request
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(url).addHeader("Authorization", "Bearer " + headValue).addHeader("Device", "android").build();
        //2 将Request封装为Call
        Call call = mOkHttpClient.newCall(request);
        //3 执行Call，得到response
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * post请求，同步方式，提交数据，是在主线程中执行的，需要新起线程，将其放到子线程中执行
     *
     * @param url
     * @param bodyParams
     * @return
     */
    public Response postDataSynToNet(String url, Map<String, String> bodyParams, String headValue) {
        //1构造RequestBody
        RequestBody body = setRequestBody(bodyParams);
        //2 构造Request
        Request.Builder requestBuilder = new Request.Builder();
        Request request = requestBuilder.post(body).url(url).addHeader("Authorization", "Bearer " + headValue).addHeader("Device", "android").build();
        //3 将Request封装为Call
        Call call = mOkHttpClient.newCall(request);
        //4 执行Call，得到response
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    /**
     * get请求，异步方式，获取网络数据，是在子线程中执行的，需要切换到主线程才能更新UI
     *
     * @param url
     * @param listener
     * @return
     */
    public void getDataAsynFromNet(String url, String headValue, final FetchDataListener listener) {
        //1 构造Request
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(url).addHeader("Authorization", "Bearer " + headValue).addHeader("Device", "android").build();
        //2 将Request封装为Call
        Call call = mOkHttpClient.newCall(request);
        //3 执行Call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.fetchData(false, null, false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200)
                    listener.fetchData(true, response.body().string(), true);
                else {
                    listener.fetchData(false, response.body().string(), false);
                }

            }
        });
    }

    /**
     * post请求，异步方式，提交数据，是在子线程中执行的，需要切换到主线程才能更新UI
     *
     * @param url
     * @param bodyParams
     * @param listener
     */
    public void postDataAsynToNet(String url, Map<String, String> bodyParams, String headValue, final FetchDataListener listener) {
        //1构造RequestBody
        RequestBody body = setRequestBody(bodyParams);
        //2 构造Request
        Request.Builder requestBuilder = new Request.Builder();
        Request request = requestBuilder.post(body).url(url).addHeader("Authorization", "Bearer " + headValue).addHeader("Device", "android").build();
        //3 将Request封装为Call
        Call call = mOkHttpClient.newCall(request);
        //4 执行Call
        call.enqueue(new Callback() {
            // 访问接口失败，已数据来源非http处理
            @Override
            public void onFailure(Call call, IOException e) {
                listener.fetchData(false, null, false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200)
                    listener.fetchData(true, response.body().string(), true);
                else {
                    listener.fetchData(true, response.body().string(), false);
                }
            }
        });
    }

    /**
     * 上传文件
     *
     * @param url
     * @param headValue
     * @param file
     * @param listener
     */
    public void postFile(String url, String headValue, File file, final FetchDataListener listener) {
        // form 表单形式上传
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if(file != null){
            RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
            // 参数分别为， 请求key ，文件名称 ， RequestBody
            requestBody.addFormDataPart("file", file.getName(), body);
        }

        Request.Builder requestBuilder = new Request.Builder();
        Request request = requestBuilder.post(requestBody.build()).url(url).addHeader("Authorization", "Bearer " + headValue).addHeader("Device", "android").build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.fetchData(false, null, false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200)
                    listener.fetchData(true, response.body().string(), true);
                else {
                    listener.fetchData(true, response.body().string(), false);
                }
            }
        });
    }


    /**
     * 异步patch方法
     *
     * @param url
     * @param bodyParams
     * @param headValue
     * @param listener
     */
    public void patchDataAsynToNet(String url, Map<String, String> bodyParams, String headValue, final FetchDataListener listener) {
        RequestBody body = setRequestBody(bodyParams);
        Request.Builder requestBuilder = new Request.Builder();
        Request request = requestBuilder.patch(body).url(url).addHeader("Authorization", "Bearer " + headValue).addHeader("Device", "android").build();
        //3 将Request封装为Call
        Call call = mOkHttpClient.newCall(request);
        //4 执行Call
        call.enqueue(new Callback() {
            // 访问接口失败，已数据来源非http处理
            @Override
            public void onFailure(Call call, IOException e) {
                listener.fetchData(false, null, false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200)
                    listener.fetchData(true, response.body().string(), true);
                else {
                    listener.fetchData(true, response.body().string(), false);
                }
            }
        });

    }


    /**
     * 异步delete方法
     *
     * @param url
     * @param bodyParams
     * @param headValue
     * @param listener
     */
    public void deleteDataAsynToNet(String url, Map<String, String> bodyParams, String headValue, final FetchDataListener listener) {
        RequestBody body = setRequestBody(bodyParams);
        Request.Builder requestBuilder = new Request.Builder();
        Request request = requestBuilder.delete(body).url(url).addHeader("Authorization", "Bearer " + headValue).addHeader("Device", "android").build();
        //3 将Request封装为Call
        Call call = mOkHttpClient.newCall(request);
        //4 执行Call
        call.enqueue(new Callback() {
            // 访问接口失败，已数据来源非http处理
            @Override
            public void onFailure(Call call, IOException e) {
                listener.fetchData(false, null, false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200)
                    listener.fetchData(true, response.body().string(), true);
                else {
                    listener.fetchData(true, response.body().string(), false);
                }
            }
        });

    }

    /**
     * post的请求参数，构造RequestBody
     *
     * @param BodyParams
     * @return
     */
    private RequestBody setRequestBody(Map<String, String> BodyParams) {
        RequestBody body = null;
        okhttp3.FormBody.Builder formEncodingBuilder = new okhttp3.FormBody.Builder();
        if (BodyParams != null) {
            Iterator<String> iterator = BodyParams.keySet().iterator();
            String key = "";
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                formEncodingBuilder.add(key, BodyParams.get(key));
                Log.d("post http", "post_Params===" + key + "====" + BodyParams.get(key));
            }
        }
        body = formEncodingBuilder.build();
        return body;

    }

    /**
     * 生成安全套接字工厂，用于https请求的证书跳过
     *
     * @return
     */
    public SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }
        return ssfFactory;
    }

    /**
     * 用于信任所有证书
     */
    class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
}
