package cn.com.zwwl.bayuwen;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import com.avos.avoscloud.AVOSCloud;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UmengTool;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.AlbumLoader;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import cn.beecloud.BeeCloud;
import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.LessonModel;
import cn.com.zwwl.bayuwen.service.NewMusicService;
import cn.com.zwwl.bayuwen.util.AddressTools;

/**
 * Created by Eva. on 2018/3/23.
 */

public class MyApplication extends Application {

    private String UMENG_APPKEY = "5ad40381f29d987f490000be";// 友盟appkey

    public static int DEBUG = 1;

    /**
     * 文件夹名
     */
    public static String CACHE_FILE_NAME = "bayuwen";

    // weixin id / secret
    public static String WEIXIN_APP_ID = "";
    public static String WEIXIN_SECRET = "";
    public static String WEIXIN_PARTNER_ID = "";
    // sina id / secret
    public static String SINA_APP_ID;
    public static String SINA_SECRET = "";
    // qq id / secret
    public static String QQ_APP_ID = "";
    public static String QQ_SECRET = "";

    public static int width;
    public static int height;
    public static Context mContext;

    public static Class<?> drawCls;// 图片资源class
    public static Class<?> stringCls;// string资源class
    public static Class<?> idsCls;// id资源class
    public static Map<String, Activity> activityMap = new HashMap<String, Activity>();
    public static AddressTools addressTools;

    /**
     * 数据刷新状态标识符
     * <p>
     * 0：没变化
     * 1：登录状态变化
     * 2：所在城市变化
     * 3：切换学员
     * 4：当前用户信息变化
     * 5：学员资料变化
     */
    public static boolean loginStatusChange = false;
    public static boolean userStatusChange = false;
    public static boolean cityStatusChange = false;

    /**
     * 渠道
     */
    public static String CHANNEL = "";
    /**
     * 课程跟踪
     */
    public int operate_type;
    public LessonModel oldLesson, newLesson;
    public KeModel oldKe, newKe;
    private static MyApplication instance;

    @Override
    public void onCreate() {
        WEIXIN_APP_ID = "wx22ea2fa3b35cb13f";
        WEIXIN_SECRET = "e117d862971d54e940247349a8778276";
        WEIXIN_PARTNER_ID = "";

        SINA_APP_ID = "3041420061";
        SINA_SECRET = "be08435126fd7c6908b6398985c71d83";
        QQ_APP_ID = "1105723648";
        QQ_SECRET = "SClqzj0HjnAx2ATM";

        mContext = this;
        instance = this;
        super.onCreate();

        //对分享的各个平台的key进行配置
        PlatformConfig.setWeixin("wx22ea2fa3b35cb13f", "e117d862971d54e940247349a8778276");
        PlatformConfig.setQQZone("1105723648", "SClqzj0HjnAx2ATM");
        PlatformConfig.setSinaWeibo("3041420061", "be08435126fd7c6908b6398985c71d83", "http://open.weibo.com/apps/3041420061/privilege/oauth");

        initScreenInfo();
        initChannel();
        initLeanCloud();
        initUmeng();
        UrlUtil.setHost();
        initAlbum();
        initBeeCloud();
        initAdressJson();
    }

    /**
     * 初始化地区json
     */
    private void initAdressJson() {
        addressTools = new AddressTools(mContext);
    }

    /**
     * 初始化beecloud 支付模块
     */
    private void initBeeCloud() {
        BeeCloud.setAppIdAndSecret("721109e7-16df-4697-adca-3d5ba26b85ff",
                "bdd7ea95-977e-4771-bafa-dcc2e0ae57d7");
    }

    private void initAlbum() {
        Album.initialize(AlbumConfig.newBuilder(this)
                .setAlbumLoader(new AlbumLoader() {
                    @Override
                    public void load(ImageView imageView, AlbumFile albumFile) {
                        load(imageView, albumFile.getPath());
                    }

                    @Override
                    public void load(ImageView imageView, String url) {
                        ImageLoader.display(getApplicationContext(), imageView, url);
                    }
                })
                .setLocale(Locale.getDefault())
                .build()
        );
    }

    /**
     * 初始化leancloud 短信服务模块
     */
    private void initLeanCloud() {
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this, "8c05u6ekuwc5dh82jfahkxgdqr6wdxetvwosh6jstq71psfp",
                "r1f7mfxc8d2d49in3gbpqw88q78xc8om5egdbv0l9a7vykm9");
    }

    private void initUmeng() {
        UMConfigure.init(getApplicationContext(), UMENG_APPKEY
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
    }


    /**
     * 初始化渠道号
     */
    public void initChannel() {

    }

    @Nullable
    private static String getApkPath(@NonNull final Context context) {
        String apkPath = null;
        try {
            final ApplicationInfo applicationInfo = context.getApplicationInfo();
            if (applicationInfo == null) {
                return null;
            }
            apkPath = applicationInfo.sourceDir;
        } catch (Throwable e) {
        }
        return apkPath;
    }

    /**
     * 初始化页面信息
     */
    public void initScreenInfo() {
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;

    }


    public static Context getInstance() {
        return instance;
    }
}
