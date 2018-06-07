package cn.com.zwwl.bayuwen.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import cn.com.zwwl.bayuwen.R;

/**
 * 全部教师页面
 */
public class AllTeacherActivity extends BaseActivity {


    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_teacher);
        initView();
    }

    private void initView() {
        findViewById(R.id.all_t_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.all_t_back:
                finish();
                break;
        }
    }
}
