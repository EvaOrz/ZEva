package cn.com.zwwl.bayuwen.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.dialog.FinalEvalDialog;
import cn.com.zwwl.bayuwen.util.ShareTools;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.widget.CommonWebView;

/**
 * 课程报告的web页面
 */
public class WebReportActivity extends BaseActivity {
    private CommonWebView commonWebView;
    private LinearLayout web_main;
    private TextView title;
    private FinalEvalDialog evalDialog;
    private String fileName = "";

    private int type = 0;// 0：子课报告 1：期中报告 2：期末报告 3：欢迎致辞
    private String webUrl;
    private String kid, lessonid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_web);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            android.webkit.WebView.enableSlowWholeDocumentDraw();
        }
        type = getIntent().getIntExtra("WebActivity_type", -1);
        webUrl = getIntent().getStringExtra("WebActivity_url");
        kid = getIntent().getStringExtra("WebActivity_kid");
        lessonid = getIntent().getStringExtra("WebActivity_lessonid");
        initView();
        initErrorLayout();
        initData();
    }


    protected void initData() {
        if (!Tools.checkNetWork(this)) {
            showError(R.mipmap.blank_no_wifi, R.string.no_wifi);
        }
    }

    private void initView() {
        title = findViewById(R.id.web_title);
        findViewById(R.id.web_back).setOnClickListener(this);
        findViewById(R.id.web_share).setOnClickListener(this);
        web_main = findViewById(R.id.web_main);
        commonWebView = findViewById(R.id.web_webview);

        if (!TextUtils.isEmpty(webUrl))
            commonWebView.loadUrl(webUrl);

        commonWebView.addJavascriptInterface(new ZwwlJSKit(mContext), "android");
        commonWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(final WebView view, String url) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        title.setText(view.getTitle());
                    }
                });
//                commonWebView.loadUrl("javascript:evaluate()");
            }

        });
    }

    private void showEvalDialog() {
        evalDialog = new FinalEvalDialog(this);
        if (type == 0) {
            evalDialog.setData(1, kid, lessonid);
        } else if (type == 1) {
            evalDialog.setData(2, kid, null);
        } else if (type == 2) {
            evalDialog.setData(3, kid, null);
        }

        evalDialog.setSubmitListener(new FinalEvalDialog.SubmitListener() {
            @Override
            public void show() {
                evalDialog.showAtLocation(web_main, Gravity.BOTTOM, 0, 0);
            }

            @Override
            public void ok() {
                showToast("提交成功，感谢您的评价！");
            }

            @Override
            public void error(int code) {
                evalDialog.dismiss();
            }
        });
    }

    public class ZwwlJSKit {
        private Context context;

        public ZwwlJSKit(Context context) {
            super();
            this.context = context;
        }

        @JavascriptInterface
        public void evaluate() {
            showEvalDialog();
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.web_back:
                finish();
                break;
            case R.id.web_share:
                Bitmap bitmap = captureScreen();
                if (bitmap != null) {
                    showToast("截屏成功");
                    ShareTools.doSharePic(WebReportActivity.this, bitmap);
                }
                break;
        }
    }

    /**
     * 截屏
     *
     * @return
     */
    private Bitmap captureScreen() {
        // WebView 生成长图，也就是超过一屏的图片，代码中的 longImage 就是最后生成的长图
        commonWebView.measure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.UNSPECIFIED, View
                        .MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        commonWebView.layout(0, 0, commonWebView.getMeasuredWidth(), commonWebView
                .getMeasuredHeight());
        commonWebView.setDrawingCacheEnabled(true);
        commonWebView.buildDrawingCache();
        Bitmap longImage = Bitmap.createBitmap(commonWebView.getMeasuredWidth(),
                commonWebView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(longImage);  // 画布的宽高和 WebView 的网页保持一致
        Paint paint = new Paint();
        canvas.drawBitmap(longImage, 0, commonWebView.getMeasuredHeight(), paint);
        commonWebView.draw(canvas);

        return longImage;
    }


}
