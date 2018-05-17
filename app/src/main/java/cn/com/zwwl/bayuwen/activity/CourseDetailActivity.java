package cn.com.zwwl.bayuwen.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CoursePageAdapter;
import cn.com.zwwl.bayuwen.adapter.TeacherListAdapter;
import cn.com.zwwl.bayuwen.fragment.FgCourseList;
import cn.com.zwwl.bayuwen.fragment.FgCourseSyllabus;
import cn.com.zwwl.bayuwen.fragment.FgPevaluationList;
import cn.com.zwwl.bayuwen.model.AlbumModel;
import cn.com.zwwl.bayuwen.model.TeacherModel;
import cn.com.zwwl.bayuwen.view.PagerSlidingTabStrip;
import cn.com.zwwl.bayuwen.widget.HSpacesItemDecoration;

/**
 * Created by lousx on 2018/5/16.
 */

public class CourseDetailActivity extends BaseActivity {
    private List<Fragment> fragments;
    private List<String> list;
    private CoursePageAdapter mAdapter;
    private RecyclerView teacher_recycler;
    private PagerSlidingTabStrip psts;
    private ViewPager mViewPager;

    private TeacherListAdapter teacherListAdapter;
    private List<TeacherModel> teacherList = new ArrayList<>();;

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
        fragments.add(vrl);

        list.add("课程大纲");
        FgCourseSyllabus cs = FgCourseSyllabus.newInstance("");
        fragments.add(cs);

        list.add("家长评价");
        FgPevaluationList vcf = FgPevaluationList.newInstance("");
        fragments.add(vcf);


        mAdapter = new CoursePageAdapter(getSupportFragmentManager(), fragments, list);
        mViewPager.setAdapter(mAdapter);
        psts.setViewPager(mViewPager);
    }

    private void initView(){
        psts = findViewById(R.id.vp_title);
        mViewPager = findViewById(R.id.videoList_vp);

        teacher_recycler = findViewById(R.id.teacher_recycler);
        teacher_recycler.setHasFixedSize(true);
        teacher_recycler.setLayoutManager(new LinearLayoutManager(mContext, OrientationHelper.HORIZONTAL, false));
        teacher_recycler.addItemDecoration(new HSpacesItemDecoration(getResources().getDimensionPixelSize(R.dimen.dp_10)));

        for (int i = 0; i<3;i++){
            TeacherModel teacherModel = new TeacherModel();
            teacherModel.setId(String.valueOf(i+1));
            teacherModel.setName("邵鑫");
            teacherModel.setPic("");
        }
        teacherListAdapter = new TeacherListAdapter(mContext, teacherList);
        teacher_recycler.setAdapter(teacherListAdapter);
    }
}
