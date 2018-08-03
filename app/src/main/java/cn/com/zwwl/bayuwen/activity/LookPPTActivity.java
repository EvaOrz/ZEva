package cn.com.zwwl.bayuwen.activity;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.rd.PageIndicatorView;

import butterknife.BindView;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.PPTAdapter;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;

/**
 * PPT 浏览
 * Create by zhumangmang at 2018/5/25 17:42
 */
public class LookPPTActivity extends BasicActivityWithTitle {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.pager_indicator)
    PageIndicatorView pagerIndicator;

    @Override
    protected int setContentView() {
        return R.layout.activity_look_ppt;
    }

    @Override
    protected void initView() {
        setCustomTitle("课程PPT");
    }

    @Override
    protected void initData() {
        PPTAdapter adapter = new PPTAdapter(getSupportFragmentManager(), getIntent().getStringArrayExtra("urls"));
        viewPager.setAdapter(adapter);
        pagerIndicator.setViewPager(viewPager);
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
