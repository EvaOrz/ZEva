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
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import cn.com.zwwl.bayuwen.db.TempDataHelper;
import cn.com.zwwl.bayuwen.db.UserDataHelper;
import okhttp3.Headers;

/**
 * 工具类
 * Created by Eva. on 16/9/16.
 */
public class Tools {
    public static final int STROKE = 2;// 边框宽度

    public static final int REQUEST_ZOOM = 111;


    public static void transforCircleBitmap(Context context, int resId, ImageView imageView) {
        transforCircleBitmap(BitmapFactory.decodeResource(context.getResources(), resId),
                imageView);
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
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context
                .ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo
                        .IMPORTANCE_BACKGROUND) {
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
            final PackageInfo info = packageManager.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
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
    public static HashMap<String, String> getRequastHeaderMap(Context mContext) {
        HashMap<String, String> headerMap = new HashMap<String, String>();
        try {
            String authorization = UserDataHelper.getUserToken(mContext);
            if (!TextUtils.isEmpty(authorization))
                headerMap.put("Authorization", "Bearer " + authorization);
            else
                headerMap.put("Authorization", "");
            String city = "";
            if (TextUtils.isEmpty(TempDataHelper.getCurrentCity(mContext)))
                city = URLEncoder.encode("北京市", "UTF-8");
            else city = URLEncoder.encode(TempDataHelper.getCurrentCity(mContext), "UTF-8");
            headerMap.put("City", city);
            headerMap.put("Device", "android");
            int grade = TempDataHelper.getCurrentChildGrade(mContext);
            if (grade != 0)
                headerMap.put("Grade", grade + "");
            String no = TempDataHelper.getCurrentChildNo(mContext);
            if (!TextUtils.isEmpty(no)) headerMap.put("StudentNo", no + "");
            String accessToken = TempDataHelper.getAccessToken(mContext);
            if (!TextUtils.isEmpty(accessToken))
                headerMap.put("Access-Token", accessToken);
            headerMap.put("app_version", Tools.getAppVersion(mContext));
        } catch (UnsupportedEncodingException e) {

        }

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
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE);
        /* 网络连接状态 */
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * 获取版本号
     */
    public static String getAppVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo info = packageManager.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 将数据保留两位小数
     */
    public static String getTwoDecimal(double num) {
        BigDecimal b = new BigDecimal(num);
        if (num == 0) return "0.00";
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    public static String getText(TextView t) {
        return t.getText().toString().trim();
    }

    public static int getCourseType(int online, int source, String time) {
        if (online == 0)
            return 1;//面授
        if (source == 2) return 2;//录播
        if (source == 1 && TimeUtil.convertToMillis(time) >= System.currentTimeMillis())
            return 3;//直播
        return 4;//回放
    }

    public static Pair<Integer, Integer> getUiPixels(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return new Pair<>(view.getMeasuredHeight(), view.getMeasuredWidth());
    }
}
