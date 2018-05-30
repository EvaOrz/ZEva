package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.view.View;

import butterknife.OnClick;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;

/**
 * 调课（换班）结果
 * Created by zhumangmang at 2018/5/28 17:54
 */
public class ChangeResultActivity extends BasicActivityWithTitle {
    @Override
    protected int setContentView() {
        return R.layout.activity_change_result;
    }

    @Override
    protected void initView() {
setDisplayShowTitleEnabled(false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @OnClick(R.id.submit)
    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, StudyingCourseActivity.class));
    }

    @Override
    public void close() {
        finish();
    }
}
