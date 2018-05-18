package cn.com.zwwl.bayuwen.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import cn.com.zwwl.bayuwen.R;

/**
 * 注册添加学员页面
 */
public class RegisterAddChildActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_register_addchild);
        initView();
    }

    private void initView() {
        findViewById(R.id.register_a_back).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.register_a_back:
                finish();
                break;
        }

    }

}
