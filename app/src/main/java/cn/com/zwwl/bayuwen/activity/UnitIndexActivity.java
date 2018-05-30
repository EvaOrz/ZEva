package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;

/**
 * 具体课次首页
 * Created by zhumangmang at 2018/5/30 11:11
 */
public class UnitIndexActivity extends BasicActivityWithTitle {
    @BindView(R.id.ppt_list)
    RecyclerView pptList;
    @BindView(R.id.homework)
    RecyclerView homework;

    @Override
    protected int setContentView() {
        return R.layout.activity_unit_index;
    }

    @Override
    protected void initView() {
        setCustomTitle("第XX课");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.submit_work})
    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, UploadPicActivity.class));
    }

    @Override
    public boolean setParentScrollable() {
        return true;
    }

    @Override
    public void close() {
        finish();
    }
}
