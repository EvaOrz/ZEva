package cn.com.zwwl.bayuwen.push;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * 推送管理类
 * Created by Eva. on 16/7/14.
 */
public class NewPushManager {
    private static Context mContext;

    private static NewPushManager instance;

    public static String token; // token的缓存


    public NewPushManager(Context context) {
        this.mContext = context;
    }

    public static synchronized NewPushManager getInstance(Context context) {
        // 建立图集表
        if (null == instance) {
            instance = new NewPushManager(context);
        }
        return instance;
    }

    /**
     * 注册推送
     */
    public void register() {

        Log.e("注册极光推送", "注册极光推送");
        registerJPush();
//        DataHelper.setPushServiceEnable(mContext, true);
    }

    /**
     * setting 页面关闭推送
     */
    public void closePush(Context context) {
        Log.e("反注册极光推送", "反注册极光推送");
        JPushInterface.stopPush(context.getApplicationContext());
    }

    /**
     * mainactivity的onresume 生命周期需要添加第三方的统计数据
     */
    public void onresume(Context context) {
        Log.e("极光恢复", "极光恢复推送");
        JPushInterface.onResume(context);
    }

    /**
     * mainactivity的onpause 生命周期需要添加第三方的统计数据
     *
     * @param context
     */
    public void onpause(Activity context) {
        JPushInterface.onPause(context);
    }

    /**
     * 注册极光的推送服务
     */
    private void registerJPush() {
        // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
        JPushInterface.setDebugMode(false);//设置调试模式
        JPushInterface.init(mContext.getApplicationContext());

    }

    /**
     * 目前一个设备仅仅有一个token，所以用了静态的string做缓存；
     * 做如果以后有多个token，那么把{@link NewPushManager}变成单例，存一个token的map即可。
     */
    public static void sendDeviceToken(String token) {
        if (token == null) {
            return;
        }
        NewPushManager.token = token;

        /**
         * 没注册过，向服务器上传注册id
         */
//        OperateController.getInstance(mContext).pushDeviceInfo(mContext, token, type, new
// FetchEntryListener() {
//            @Override
//            public void setData(Entry entry) {
//                /**
//                 * 记录已注册成功的状态
//                 */
////                DataHelper.setPushServiceEnable(mContext, true);
//            }
//
//            @Override
//            public void setError(ErrorMsg error) {
//
//            }
//        });

    }


    public String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return line;
    }

    /**
     * 判断当前activity是否在main进程中
     * <p/>
     * 注意：因为推送服务等设置为运行在另外一个进程，这导致本Application会被实例化两次。
     * 而有些操作我们需要让应用的主进程时才进行，所以用到了这个方法
     */
    public boolean isInMainProcess() {
        ActivityManager am = ((ActivityManager) mContext.getSystemService(Context
                .ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processes = am.getRunningAppProcesses();
        String mainProcessName = mContext.getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processes) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取推送文章url
     *
     * @param jsonString
     * @return
     */
    private String newParseArticleUrl(String jsonString) {
        String url = "";

        Log.e("push ariticle url", jsonString);
        return url;
    }

}