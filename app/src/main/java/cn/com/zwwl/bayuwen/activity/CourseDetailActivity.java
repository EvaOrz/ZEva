package cn.com.zwwl.bayuwen.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CoursePageAdapter;
import cn.com.zwwl.bayuwen.adapter.TeacherListAdapter;
import cn.com.zwwl.bayuwen.fragment.FgCourseList;
import cn.com.zwwl.bayuwen.fragment.FgCourseSyllabus;
import cn.com.zwwl.bayuwen.fragment.FgPevaluationList;
import cn.com.zwwl.bayuwen.model.TeacherModel;
import cn.com.zwwl.bayuwen.view.PagerSlidingTabStrip;
import cn.com.zwwl.bayuwen.widget.CustomViewPager;
import cn.com.zwwl.bayuwen.widget.decoration.HSpacesItemDecoration;

/**
 * Created by lousx on 2018/5/16.
 */

public class CourseDetailActivity extends BaseActivity {
    private List<Fragment> fragments;
    private List<String> list;
    private CoursePageAdapter mAdapter;
    private RecyclerView teacher_recycler;
    private TextView title_tv;
    private PagerSlidingTabStrip psts;
    private CustomViewPager mViewPager;

    private TeacherListAdapter teacherListAdapter;
    private List<TeacherModel> teacherList = new ArrayList<>();
    ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_course_detail);
        initView();
        init();
    }

    @Override
    protected void initData() {

    }

    private void init() {
        fragments = new ArrayList<>();
        list = new ArrayList<>();

        list.add("课程表");
        FgCourseList vrl = FgCourseList.newInstance("");
        vrl.setCustomViewPager(mViewPager);
        fragments.add(vrl);

        list.add("课程大纲");
        FgCourseSyllabus cs = FgCourseSyllabus.newInstance("");
        cs.setCustomViewPager(mViewPager);
        fragments.add(cs);

        list.add("家长评价");
        FgPevaluationList vcf = FgPevaluationList.newInstance("");
        vcf.setCustomViewPager(mViewPager);
        fragments.add(vcf);


        mAdapter = new CoursePageAdapter(getSupportFragmentManager(), fragments, list);
        mViewPager.setAdapter(mAdapter);
        psts.setViewPager(mViewPager);
    }

    private void initView() {
        psts = findViewById(R.id.vp_title);
        title_tv = findViewById(R.id.title_tv);
        mViewPager = findViewById(R.id.videoList_vp);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mViewPager.resetHeight(position);
//                findViewById(R.id.scroll).scrollTo(0, 0);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.resetHeight(0);

        teacher_recycler = findViewById(R.id.teacher_recycler);
        teacher_recycler.setHasFixedSize(true);
        teacher_recycler.setLayoutManager(new LinearLayoutManager(mContext, OrientationHelper.HORIZONTAL, false));
        teacher_recycler.addItemDecoration(new HSpacesItemDecoration(getResources().getDimensionPixelSize(R.dimen.dp_10)));

        for (int i = 0; i < 3; i++) {
            TeacherModel teacherModel = new TeacherModel();
            teacherModel.setId(String.valueOf(i + 1));
            teacherModel.setName("邵鑫");
            teacherModel.setPic("");
            teacherList.add(teacherModel);
        }
        teacherListAdapter = new TeacherListAdapter(mContext, teacherList);
        teacher_recycler.setAdapter(teacherListAdapter);

        title_tv.setText("课程详情");

        findViewById(R.id.back_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.back_btn:
                finish();
                break;
        }
    }
}
