package cn.com.zwwl.bayuwen.activity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Arrays;

import butterknife.BindView;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.InClassStatusAdapter;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;

/**
 * 浏览照片
 * Create by zhumangmang at 2018/5/26 11:13
 */
public class InClassStatusActivity extends BasicActivityWithTitle {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected int setContentView() {
        return R.layout.activity_in_class_status;
    }

    @Override
    protected void initView() {
        setCustomTitle("查看照片");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initData() {
        String[] urls = getIntent().getStringArrayExtra("urls");
        InClassStatusAdapter adapter = new InClassStatusAdapter(Arrays.asList(urls));
        recyclerView.setAdapter(adapter);
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
