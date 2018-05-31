package cn.com.zwwl.bayuwen.http;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.db.TempDataHelper;
import cn.com.zwwl.bayuwen.db.UserDataHelper;
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
    private Context mContext;
    private OkHttpClient mOkHttpClient;

    private HttpUtil(Context context) {
        this.mContext = context;
        if (MyApplication.DEBUG == 1) {
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
            clientBuilder.readTimeout(20, TimeUnit.SECONDS);//读取超时
            clientBuilder.connectTimeout(6, TimeUnit.SECONDS);//连接超时
            clientBuilder.writeTimeout(60, TimeUnit.SECONDS);//写入超时

            //支持HTTPS请求，跳过证书验证
            clientBuilder.sslSocketFactory(createSSLSocketFactory());
            clientBuilder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            mOkHttpClient = clientBuilder.build();
        } else {
            mOkHttpClient = new OkHttpClient.Builder()
                    //设置连接超时时间
                    .connectTimeout(20, TimeUnit.SECONDS)
                    //设置读取超时时间
                    .readTimeout(6, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();
        }

    }

    /**
     * 单例模式获取NetUtils
     *
     * @return
     */
    public static HttpUtil getInstance(Context context) {
        if (mInstance == null) {
            synchronized (LOCKER) {
                if (mInstance == null) {
                    mInstance = new HttpUtil(context);
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
    public Response getDataSynFromNet(String url) {
        //1 构造Request
        Request.Builder builder = new Request.Builder();
        Request request = setRequestHeader(builder.get().url(url));
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
    public Response postDataSynToNet(String url, Map<String, String> bodyParams) {
        //1构造RequestBody
        RequestBody body = setRequestBody(bodyParams);
        //2 构造Request
        Request.Builder requestBuilder = new Request.Builder();
        Request request = setRequestHeader(requestBuilder.post(body).url(url));
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
    public void getDataAsynFromNet(String url, final FetchDataListener listener) {
        //1 构造Request
        Request.Builder builder = new Request.Builder();
        Request request = setRequestHeader(builder.get().url(url));
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
                    listener.fetchData(false, response.body().string(), true);
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
    public void postDataAsynToNet(String url, Map<String, String> bodyParams,
                                  final FetchDataListener listener) {
        RequestBody body = setRequestBody(bodyParams);
        Request.Builder requestBuilder = new Request.Builder();
        Request request = setRequestHeader(requestBuilder.post(body).url(url));
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
                    listener.fetchData(false, response.body().string(), true);
                }
            }
        });
    }

    /**
     * put 异步
     *
     * @param url
     * @param bodyParams
     * @param listener
     */
    public void putDataAsynToNet(String url, Map<String, String> bodyParams,
                                 final FetchDataListener listener) {
        RequestBody body = setRequestBody(bodyParams);
        Request.Builder requestBuilder = new Request.Builder();
        Request request = setRequestHeader(requestBuilder.put(body).url(url));
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
                    listener.fetchData(false, response.body().string(), true);
                }
            }
        });
    }

    /**
     * 上传文件
     *
     * @param url
     * @param file
     * @param listener
     */
    public void postFile(String url, File file, final FetchDataListener
            listener) {
        // form 表单形式上传
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (file != null) {
            RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
            // 参数分别为， 请求key ，文件名称 ， RequestBody
            requestBody.addFormDataPart("file", file.getName(), body);

        }
        Request.Builder requestBuilder = new Request.Builder();
        Request request = setRequestHeader(requestBuilder.post(requestBody.build()).url(url));
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
                    listener.fetchData(false, response.body().string(), true);
                }
            }
        });
    }

    /**
     * 上传文件
     *
     * @param url
     * @param file
     * @param listener
     */
    public void postMultiFile(String url, List<File> file, final FetchDataListener
            listener) {
        // form 表单形式上传
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (file != null) {
            for (File f : file) {
                if (f.exists())
                    requestBody.addFormDataPart("file", f.getName(), RequestBody.create(MediaType.parse("image/*"), f));
            }
        }
        Request.Builder requestBuilder = new Request.Builder();
        Request request = setRequestHeader(requestBuilder.post(requestBody.build()).url(url));
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
                    listener.fetchData(false, response.body().string(), true);
                }
            }
        });
    }


    /**
     * 异步patch方法
     *
     * @param url
     * @param bodyParams
     * @param listener
     */
    public void patchDataAsynToNet(String url, Map<String, String> bodyParams,
                                   final FetchDataListener listener) {
        RequestBody body = setRequestBody(bodyParams);
        Request.Builder requestBuilder = new Request.Builder();
        Request request = setRequestHeader(requestBuilder.patch(body).url(url));
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
                    listener.fetchData(false, response.body().string(), true);
                }
            }
        });

    }

//    /**
//     * 解析
//     */
//    private void parseSuccessData(String response){
//
//    }


    /**
     * 异步delete方法
     *
     * @param url
     * @param bodyParams
     * @param listener
     */
    public void deleteDataAsynToNet(String url, Map<String, String> bodyParams,
                                    final FetchDataListener listener) {
        RequestBody body = setRequestBody(bodyParams);
        Request.Builder requestBuilder = new Request.Builder();
        Request request = setRequestHeader(requestBuilder.delete(body).url(url));
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
                    listener.fetchData(false, response.body().string(), true);
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

    private Request setRequestHeader(Request.Builder requestBuilder) {
        Request request = null;
        try {
            String authorization = UserDataHelper.getUserToken(mContext);
            if (!TextUtils.isEmpty(authorization))
                requestBuilder.addHeader("Authorization", "Bearer " + authorization);
            else
                requestBuilder.addHeader("Authorization", "");
            String city = "";
            if (TextUtils.isEmpty(TempDataHelper.getCurrentCity(mContext)))
                city = URLEncoder.encode("北京", "UTF-8");
            else city = URLEncoder.encode(TempDataHelper.getCurrentCity(mContext), "UTF-8");
            requestBuilder.addHeader("City", city).addHeader
                    ("Device", "android");
            int grade = TempDataHelper.getCurrentChildGrade(mContext);
            if (grade != 0)
                requestBuilder.addHeader("Grade", grade + "");
            String no = TempDataHelper.getCurrentChildNo(mContext);
            if (!TextUtils.isEmpty(no)) requestBuilder.addHeader("StudentNo", no + "");
            String accessToken = TempDataHelper.getAccessToken(mContext);
            if (!TextUtils.isEmpty(accessToken))
                requestBuilder.addHeader("Access-Token", accessToken);
            request = requestBuilder.build();
        } catch (UnsupportedEncodingException e) {

        }
        return request;
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
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws
                CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws
                CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
}
