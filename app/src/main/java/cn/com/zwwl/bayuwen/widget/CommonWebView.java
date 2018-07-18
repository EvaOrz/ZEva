package cn.com.zwwl.bayuwen.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import cn.com.zwwl.bayuwen.util.Tools;

/**
 * 通用webview
 */
public class CommonWebView extends WebView {
    private Context context;

    public CommonWebView(Context context) {
        super(context);
        this.context = context;
    }

    public CommonWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
    }

    @Override
    public void loadUrl(String url) {
        super.loadUrl(url, Tools.getRequastHeaderMap(context));
    }
}
