package cn.com.zwwl.bayuwen.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;

import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.webridge.JavascriptInterface;
import cn.com.zwwl.bayuwen.webridge.WBUri;
import cn.com.zwwl.bayuwen.webridge.WBWebridge;
import cn.com.zwwl.bayuwen.webridge.WBWebridgeImplement;
import cn.com.zwwl.bayuwen.webridge.WebUriImplement;

/**
 * 通用webview
 */
public class CommonWebView extends WebView {
    private Context context;

    private static final String JS_NAME = "androidWebridge";

    protected WBUri mWbUri;
    protected WBWebridge mWebridge;

    public CommonWebView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CommonWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        init();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @JavascriptInterface
    private void init() {
        mWbUri = new WBUri(context, new WebUriImplement(context));
        WebSettings s = getSettings();
        // android 5.0以上默认不支持Mixed Content
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            s.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        s.setDomStorageEnabled(true);
        s.setSupportZoom(false);
        s.setBuiltInZoomControls(false);
        s.setAppCacheEnabled(true);
        s.setUseWideViewPort(true);
        s.setJavaScriptEnabled(true);
        s.setLoadWithOverviewMode(true);
        s.setRenderPriority(WebSettings.RenderPriority.HIGH);// 提高渲染优先级
        s.setBlockNetworkImage(false);// 图片加载放在最后来加载渲染(onPageFinished)
        s.setAllowFileAccess(true);
        s.setCacheMode(WebSettings.LOAD_DEFAULT);

        // 解决bug
        s.setJavaScriptCanOpenWindowsAutomatically(true);
        s.setPluginState(WebSettings.PluginState.ON);
        s.setDefaultTextEncodingName("UTF-8");
        s.setUserAgentString(s.getUserAgentString() + "Slate/1.0");
        s.setSaveFormData(false);
        /** bug fix **/
        setSaveEnabled(false);

        /* JS_NAME是自己定义的，供javascript访问的接口 */
        mWebridge = new WBWebridge(this, new WBWebridgeImplement(context));
        if (mWebridge == null) {
            Log.e("初始化webridge出错", "mWebridge == null");
        }
        addJavascriptInterface(mWebridge, JS_NAME);


        setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition,
                                        String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });
    }

    @JavascriptInterface
    public WBWebridge getWebridge() {
        return mWebridge;
    }

    /**
     * 设置上拉页面变白
     */
    @Override
    public void setVerticalScrollbarOverlay(boolean overlay) {
        if (overlay) {
            this.setOverScrollMode(OVER_SCROLL_NEVER);
        }
    }


    @Override
    public void loadUrl(String url) {
        super.loadUrl(url, Tools.getRequastHeaderMap(context));
    }
}
