package cn.com.zwwl.bayuwen.activity;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.List;

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
    private List<String> imgUrls = new ArrayList<>();

    @Override
    protected int setContentView() {
        return R.layout.activity_look_ppt;
    }

    @Override
    protected void initView() {
        setCustomTitle("第X课 PPT");
    }

    @Override
    protected void initData() {
        imgUrls.add("https://m.360buyimg.com/n0/jfs/t8830/106/1760940277/195595/5cf9412f/59bf2ef5N5ab7dc16.jpg!q70.jpg");
        imgUrls.add("https://m.360buyimg.com/n0/jfs/t5428/70/1520969931/274676/b644dd0d/591128e7Nd2f70da0.jpg!q70.jpg");
        imgUrls.add("https://m.360buyimg.com/n0/jfs/t5566/365/1519564203/36911/620c750c/591128eaN54ac3363.jpg!q70.jpg");
        PPTAdapter adapter = new PPTAdapter(getSupportFragmentManager(), imgUrls);
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
