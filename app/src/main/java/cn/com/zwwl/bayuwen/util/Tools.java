package cn.com.zwwl.bayuwen.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import cn.com.zwwl.bayuwen.R;
import okhttp3.Headers;

/**
 * 工具类
 * Created by Eva. on 16/9/16.
 */
public class Tools {
    public static final int STROKE = 2;// 边框宽度

    public static final int REQUEST_ZOOM = 111;


    public static void transforCircleBitmap(Context context, int resId, ImageView imageView) {
        transforCircleBitmap(BitmapFactory.decodeResource(context.getResources(), resId), imageView);
    }

    /**
     * 把图片转换成圆形
     *
     * @param bitmap
     * @return
     */
    public static void transforCircleBitmap(Bitmap bitmap, ImageView imageView) {
        if (bitmap == null) return;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 取小的
        int radius = width > height ? height / 2 : width / 2;
        Bitmap output = Bitmap.createBitmap(radius * 2, radius * 2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        Paint paint = new Paint();
        int left = 0, top = 0;
        int right = width, bottom = height;
        if (width > height) {
            left = width / 2 - radius;
            right = width / 2 + radius;
        } else if (width < height) {
            top = height / 2 - radius;
            bottom = height / 2 + radius;
        }
        Rect src = new Rect(left, top, right, bottom);// 截取原始图片的地方
        Rect dst = new Rect(0, 0, 2 * radius, 2 * radius);

        paint.setAntiAlias(true);
        canvas.drawCircle(radius, radius, radius - STROKE, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(bitmap, src, dst, paint);

        setCircle(imageView, output, canvas, radius);
    }


    /**
     * 画边框
     *
     * @param imageView
     * @param output
     * @param canvas
     * @param radius
     */
    private static void setCircle(ImageView imageView, Bitmap output, Canvas canvas, int radius) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(STROKE);
        paint.setColor(Color.parseColor("#FFCCCCCC"));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        canvas.drawCircle(radius, radius, radius - STROKE, paint);
        imageView.setImageBitmap(output);
    }

    public static String getStringToDate(String timeStr) {
        if (TextUtils.isEmpty(timeStr)) return "";
        long t = Long.parseLong(timeStr) * 1000;
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
        String start = format.format(new Date(t));
        return start;
    }


    /**
     * 判断用户名、密码是否为空
     *
     * @param str
     * @param view
     * @return
     */
    public static boolean checkString(String str, View view, Animation animation) {
        if (TextUtils.isEmpty(str)) {
            Tools.doAnim(view, animation);
            return false;
        }
        return true;
    }

    /**
     * 判断密码 ，不能少于6位
     *
     * @param str
     * @param view
     * @param animation
     * @return
     */

    public static boolean checkPwd(Context c, String str, View view, Animation animation) {
        if (TextUtils.isEmpty(str) || str.length() < 6) {
            Tools.doAnim(view, animation);
//            showToast(c, R.string.password_hint);
            return false;
        }
        return true;
    }


    /**
     * 执行动画
     *
     * @param view
     * @param animation
     */
    protected static void doAnim(View view, Animation animation) {
        view.startAnimation(animation);
    }


    public static <T> boolean listNotNull(List<T> list) {
        return list != null && !list.isEmpty();
    }


    public static boolean isAppBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    Log.e("后台运行", appProcess.processName);
                    return true;
                } else {
                    Log.e("前台运行", appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }


    /**
     * 判断当前手机是否有ROOT权限
     *
     * @return
     */
    public static boolean isRooted() {
        boolean bool = false;

        try {
            if ((!new File("/system/bin/su").exists()) && (!new File("/system/xbin/su").exists())) {
                bool = false;
            } else bool = true;
        } catch (Exception e) {
        }
        return bool;
    }

    /**
     * 获取应用版本信息
     *
     * @param context
     * @return
     */
    public static String getAppVersion(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            final PackageInfo info = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return null;
    }


    /**
     * 获取要添加的头部信息
     *
     * @return
     */
    public static Headers getRequastHeader(Context context) {

        Headers headers = Headers.of(getRequastHeaderMap(context));
        return headers;
    }

    /**
     * 获取要添加的头部信息
     *
     * @return
     */
    public static HashMap<String, String> getRequastHeaderMap(Context context) {
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("X-SLATE-DEVICEID", getDeviceId(context));
        headerMap.put("X-SLATE-UUID", getMyUUID(context));
        headerMap.put("X-SLATE-ANDROIDTOKEN", Tools.getDeviceToken(context));
        headerMap.put("X-SLATE-CLIENTVERSION", Tools.getAppVersionName(context));
        headerMap.put("X-SLATE-DEVICETYPE", android.os.Build.MODEL);//设备信息
        // 未root:10  已root:11
        headerMap.put("X-SLATE-JAILBROKEN", isRooted() ? "11" : "10");//是否root（如果可以获取就获取）

        return headerMap;
    }

    public static String parseString(Context context, int resId, Object... args) {
        return String.format(context.getString(resId), args);
    }

    /**
     * 检测网络状态
     *
     * @param context
     * @return
     */
    public static boolean checkNetWork(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        /* 网络连接状态 */
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * 获取设备ID
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }


    /**
     * 获取设备网卡mac地址
     *
     * @param context
     * @return
     */
    public static String getNetMacAdress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        WifiInfo info = wifi.getConnectionInfo();

        return info.getMacAddress();
    }


    /**
     * 返回手机唯一标识
     *
     * @return
     */
    public static String getMyUUID(Context mContext) {
        final TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        String imie = "" + tm.getDeviceId();
        String tmSerial = "" + tm.getSimSerialNumber();


        UUID deviceUuid = new UUID(getDeviceToken(mContext).hashCode(), ((long) imie.hashCode() << 32) | tmSerial.hashCode());
        return MD5.MD5Encode(deviceUuid.toString());
    }

    /**
     * The Android ID
     * 通常被认为不可信，因为它有时为null。开发文档中说明了：这个ID会改变如果进行了出厂设置。并且，如果某个Andorid手机被Root过的话，这个ID也可以被任意改变
     *
     * @return
     */
    public static String getDeviceToken(Context mContext) {
        return "" + android.provider.Settings.Secure.getString(mContext.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取版本号
     */
    public static String getAppVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo info = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取版本号
     */
    public static int getAppIntVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo info = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (TextUtils.isEmpty(info.versionName)) {
                return 0;
            } else return Integer.valueOf(info.versionName.replace(".", ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 将小数转换成百分数
     */
    public static String parseDecimal(double data) {
        DecimalFormat nt = new DecimalFormat("0%");
        nt.setRoundingMode(RoundingMode.DOWN);
        return nt.format(data);
    }

    public static String getText(TextView t) {
        return t.getText().toString().trim();
    }

    public static int getCourseType(int online, int source, String time) {
        if (online == 0) {
            return 0;//面授
        }
        if (source == 2) return 1;//录播
        if (source == 1 && TimeUtil.convertToMillis(time) >= System.currentTimeMillis())
            return 3;//直播
        return 4;//回放
    }
}
