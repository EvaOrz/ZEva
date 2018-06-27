package cn.com.zwwl.bayuwen.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.FragmentAdapter;
import cn.com.zwwl.bayuwen.fragment.FmHistoryFragment;
import cn.com.zwwl.bayuwen.fragment.FmMyCollectFragment;
import cn.com.zwwl.bayuwen.fragment.FmMyCourseFragment;

public class OurFmActivity extends AppCompatActivity implements View.OnClickListener{


  private   ImageView myTuanBack;
    @BindView(R.id.tab_title)
    TabLayout tabTitle;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    List<Fragment> fragments;
    FragmentManager manager;
    FragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_fm);
        ButterKnife.bind(this);
        myTuanBack=findViewById(R.id.my_tuan_back);
        myTuanBack.setOnClickListener(this);
        init();
    }

    /* 初始化*/
    private void init() {

//        new 一个Fragment的集合
        fragments = new ArrayList<>();
//        给Fragment集合中添加子Fragment;
        fragments.add(new FmHistoryFragment());
        fragments.add(new FmMyCourseFragment());
        fragments.add(new FmMyCollectFragment());
//        获取Fragment管理者
        manager = getSupportFragmentManager();
//        new 出Adapter
        adapter = new FragmentAdapter(manager, (ArrayList<Fragment>) fragments);
//        给Viewpager设置adapter
        viewpager.setAdapter(adapter);
//        实现联动
        tabTitle.setupWithViewPager(viewpager);
//        设置tab标签上的文字
        tabTitle.getTabAt(0).setText("收听历史");
        tabTitle.getTabAt(1).setText("我的课程");
        tabTitle.getTabAt(2).setText("我的收藏");



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case   R.id.my_tuan_back:
                finish();
                break;
        }
    }
}
