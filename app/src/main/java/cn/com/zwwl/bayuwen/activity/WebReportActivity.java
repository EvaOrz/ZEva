package cn.com.zwwl.bayuwen.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.dialog.FinalEvalDialog;
import cn.com.zwwl.bayuwen.model.LessonModel;
import cn.com.zwwl.bayuwen.model.StudyingModel;
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


    private int type = 0;// 0：子课报告 1：期中报告 2：期末报告 3：欢迎致辞
    private StudyingModel studyingModel;
    private LessonModel lessonModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        type = getIntent().getIntExtra("WebActivity_type", -1);
        if (type == 0) {
            lessonModel = (LessonModel) getIntent().getSerializableExtra("WebActivity_data");
        } else {
            studyingModel = (StudyingModel) getIntent().getSerializableExtra("WebActivity_data");
        }
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
        web_main = findViewById(R.id.web_main);
        commonWebView = findViewById(R.id.web_webview);

        commonWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(final WebView view, String url) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        title.setText(view.getTitle());
                    }
                });
            }
        });
        evalDialog = new FinalEvalDialog(this);
        String url = "";
        if (type == 0) {
            url = lessonModel.getReport_url();
            evalDialog.setData(1, lessonModel.getKid(), lessonModel.getId());
        } else if (type == 1) {
            url = studyingModel.getMidterm_report();
            evalDialog.setData(2, studyingModel.getCourse().getKid(), null);
        } else if (type == 2) {
            url = studyingModel.getEnd_term_report();
            evalDialog.setData(3, studyingModel.getCourse().getKid(), null);
        } else if (type == 3) {
            url = studyingModel.getWelcome_speech();
        }
        if (!TextUtils.isEmpty(url))
            commonWebView.loadUrl(url);

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

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.web_back:
                finish();
                break;

        }
    }
}
