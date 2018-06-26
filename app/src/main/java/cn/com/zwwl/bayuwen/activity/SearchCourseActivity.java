package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.util.Tools;

/**
 * 搜索课程页面
 */
public class SearchCourseActivity extends BaseActivity {
    private EditText search_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_course);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        search_view.setFocusable(true);
        search_view.setFocusableInTouchMode(true);
        search_view.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        hideJianpan();
    }

    private void initView() {
        findViewById(R.id.back_btn).setOnClickListener(this);
        search_view = findViewById(R.id.search_view);

        search_view.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Intent i = new Intent(mContext, CourseCenterActivity.class);
                    i.putExtra("SearchCourseActivity_key", Tools.getText(v));
                    startActivity(i);
                    finish();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.back_btn:
                finish();
                break;
        }
    }

    @Override
    protected void initData() {

    }
}
