package cn.com.zwwl.bayuwen.activity;

import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Switch;

import butterknife.BindView;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;

/**
 * 课程评价
 * Create by zhumangmang at 2018/5/26 16:22
 */
public class CourseEvalActivity extends BasicActivityWithTitle {
    @BindView(R.id.content)
    AppCompatEditText content;
    @BindView(R.id.hide_name)
    Switch hideName;

    @Override
    protected int setContentView() {
        return R.layout.activity_course_eval;
    }

    @Override
    protected void initView() {
        setCustomTitle("课程评价");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void close() {
        finish();
    }
}
