package cn.com.zwwl.bayuwen.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.com.zwwl.bayuwen.R;

/**
 * 退费的详情页面
 */
public class OrderTuifeeDetailActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuifee_detail);
        initView();

    }

    private void initView() {
        findViewById(R.id.tuifee_l_back).setOnClickListener(this);

    }


    @Override
    protected void initData() {

    }

}
