package cn.com.zwwl.bayuwen.http;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;


/**
 *  网络访问日志
 *  Created by zhumangmang at 2018/6/4 14:21
 */
public class LoggerInterceptor implements Interceptor {
    private static final String TAG = "NetWork";

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        logForRequest(request);
        Response response = chain.proceed(request);
        return logForResponse(response);
    }

    private Response logForResponse(Response response) {
        try {
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            Log.e(TAG, "Response[\nUrl: " + clone.request().url() + ", \nCode: " + clone.code() + ",\nProtocol:" + clone.protocol() + ", \nMessage: " + clone.message() + " ]");
            ResponseBody body = clone.body();
            if (body != null) {
                MediaType mediaType = body.contentType();
                if (mediaType != null) {
                    if (isText(mediaType)) {
                        String resp = body.string();
                        Log.e(TAG, "Response Content[\nType: " + mediaType.toString() + ", \nContent: " + resp + " ]");
                        body = ResponseBody.create(mediaType, resp);
                        return response.newBuilder().body(body).build();
                    } else {
                        Log.e(TAG, "Response Content[\nType: " + mediaType.toString() + ",\nContent: maybe [file part] , too large too print , ignored! ]");
                    }
                }
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }

        return response;
    }

    private void logForRequest(Request request) {
        try {
            String url = request.url().toString();
            Headers headers = request.headers();

            if (headers != null && headers.size() > 0) {
                Log.e(TAG, "Request[\nUrl: " + url + "   ,\nMethod: " + request.method() + "\nHeader: " + headers.toString() + "]");
            } else {
                Log.e(TAG, "Request[\nUrl: " + url + "   ,\nMethod: " + request.method() + "]");
            }
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    if (isText(mediaType)) {
                        Log.e(TAG, "Request Content [\nType:" + mediaType.toString() + " ,\nContent: " + bodyToString(request) + "]");
                    } else {
                        Log.e(TAG, "Request Content [\nType:" + mediaType.toString() + " ,\nContent:  maybe [file part] , too large too print , ignored!]");
                    }
                }
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    private boolean isText(MediaType mediaType) {
        return mediaType.type() != null && mediaType.type().equals("text") || mediaType.subtype() != null && (mediaType.subtype().equals("json") || mediaType.subtype().equals("xml") || mediaType.subtype().equals("html") || mediaType.subtype().equals("x-www-form-urlencoded"));
    }

    private String bodyToString(final Request request) {
        try {
            Request copy = request.newBuilder().build();
            Buffer buffer = new Buffer();
            Objects.requireNonNull(copy.body()).writeTo(buffer);
            return buffer.readUtf8();
        } catch (IOException e) {
            return "something error when show requestBody.";
        }
    }
}
