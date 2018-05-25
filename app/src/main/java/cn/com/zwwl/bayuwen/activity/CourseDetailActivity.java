package cn.com.zwwl.bayuwen.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CoursePageAdapter;
import cn.com.zwwl.bayuwen.adapter.TeacherListAdapter;
import cn.com.zwwl.bayuwen.api.CDeatailApi;
import cn.com.zwwl.bayuwen.api.TDeatailApi;
import cn.com.zwwl.bayuwen.fragment.FgCourseList;
import cn.com.zwwl.bayuwen.fragment.FgCourseSyllabus;
import cn.com.zwwl.bayuwen.fragment.FgPevaluationList;
import cn.com.zwwl.bayuwen.glide.BorderCircleTransform;
import cn.com.zwwl.bayuwen.glide.GlideApp;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.CourseDetailModel;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.TeacherDetailModel;
import cn.com.zwwl.bayuwen.model.TeacherModel;
import cn.com.zwwl.bayuwen.view.PagerSlidingTabStrip;
import cn.com.zwwl.bayuwen.widget.CustomViewPager;
import cn.com.zwwl.bayuwen.widget.decoration.HSpacesItemDecoration;

/**
 * Created by lousx on 2018/5/16.
 */

public class CourseDetailActivity extends BaseActivity {
    private TextView course_tv;
    private TextView classno_tv;
    private TextView price_tv;
    private ImageView play_iv;
    private ImageView course_iv;
    private TextView place_tv;
    private TextView time_tv;
    private TextView date_tv;
    private TextView adviserTv;
    private TextView explainTv;
    private TextView sign_up_tv;
    private TextView group_purchase_tv;

    private List<Fragment> fragments;
    private List<String> list;
    private CoursePageAdapter mAdapter;
    private RecyclerView teacher_recycler;
    private TextView title_tv;
    private PagerSlidingTabStrip psts;
    private CustomViewPager mViewPager;

    private TeacherListAdapter teacherListAdapter;
    private List<CourseDetailModel.TeacherEntity> teacherList = new ArrayList<>();
    private CourseDetailModel courseDetailModel;

    private String cid;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_course_detail);
        cid = getIntent().getStringExtra("cid");
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
        FgCourseList vrl = FgCourseList.newInstance(cid);
        vrl.setCustomViewPager(mViewPager);
        fragments.add(vrl);

        list.add("课程大纲");
        FgCourseSyllabus cs = FgCourseSyllabus.newInstance();
        cs.setCustomViewPager(mViewPager);
        fragments.add(cs);

        list.add("家长评价");
        FgPevaluationList vcf = FgPevaluationList.newInstance("");
        vcf.setCustomViewPager(mViewPager);
        fragments.add(vcf);


        mAdapter = new CoursePageAdapter(getSupportFragmentManager(), fragments, list);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(fragments.size());
        psts.setViewPager(mViewPager);
    }

    private void initView() {
        course_tv = findViewById(R.id.course_tv);
        classno_tv = findViewById(R.id.classno_tv);
        price_tv = findViewById(R.id.price_tv);
        play_iv = findViewById(R.id.play_iv);
        course_iv = findViewById(R.id.course_iv);
        place_tv = findViewById(R.id.place_tv);
        time_tv = findViewById(R.id.time_tv);
        date_tv = findViewById(R.id.date_tv);
        adviserTv = findViewById(R.id.adviserTv);
        explainTv = findViewById(R.id.explainTv);
        sign_up_tv = findViewById(R.id.sign_up_tv);
        group_purchase_tv = findViewById(R.id.group_purchase_tv);


        psts = findViewById(R.id.vp_title);
        title_tv = findViewById(R.id.title_tv);
        mViewPager = findViewById(R.id.videoList_vp);
        mViewPager.resetHeight(0);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
//                mViewPager.resetHeight(position);
//                findViewById(R.id.scroll).scrollTo(0, 0);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        teacher_recycler = findViewById(R.id.teacher_recycler);
        teacher_recycler.setHasFixedSize(true);
        teacher_recycler.setLayoutManager(new LinearLayoutManager(mContext, OrientationHelper.HORIZONTAL, false));
        teacher_recycler.addItemDecoration(new HSpacesItemDecoration(getResources().getDimensionPixelSize(R.dimen.dp_10)));

        teacherListAdapter = new TeacherListAdapter(mContext, teacherList);
        teacher_recycler.setAdapter(teacherListAdapter);

        title_tv.setText("课程详情");

        findViewById(R.id.back_btn).setOnClickListener(this);
        sign_up_tv.setOnClickListener(this);
        group_purchase_tv.setOnClickListener(this);

        getTeacherDetailList(cid);
    }

    private void setData() {
        CourseDetailModel.CourseEntity entity = courseDetailModel.getCourse();
        course_tv.setText(entity.getTitle());
        classno_tv.setText("班级编码：" + entity.getModel());
        price_tv.setText("¥ " + entity.getBuyPrice());
        place_tv.setText(entity.getSchool());
        date_tv.setText(entity.getStart_at() + "至" + entity.getEnd_at());
        time_tv.setText(entity.getClass_start_at() + "-" + entity.getClass_end_at());
        sign_up_tv.setText("¥" + entity.getBuyPrice() + "\n单独报名");
        if (entity.getIs_groupbuy().equals("1")) {
            group_purchase_tv.setVisibility(View.VISIBLE);
            group_purchase_tv.setText("¥" + entity.getGroupBuy().getDiscount_price() + "\n团购报名");
        } else
            group_purchase_tv.setVisibility(View.INVISIBLE);
        GlideApp.with(mContext).load(courseDetailModel.getCourse().getPic())
                .into(course_iv);
        teacherListAdapter.appendData(courseDetailModel.getTeacher());
        FgCourseSyllabus courseSyllabus = (FgCourseSyllabus) mAdapter.getItem(1);
        courseSyllabus.setEntity(courseDetailModel.getCourse_program());
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.back_btn:
                finish();
                break;
            case R.id.sign_up_tv:
                //单独报名
                break;
            case R.id.group_purchase_tv:
                //团购报名
                break;
        }
    }

    private void getTeacherDetailList(String cid) {
        new CDeatailApi(mContext, cid, new FetchEntryListener() {

            @Override
            public void setData(Entry entry) {
                if (entry != null && entry instanceof CourseDetailModel) {
                    courseDetailModel = (CourseDetailModel) entry;
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void setError(ErrorMsg error) {
                if (error != null)
                    showToast(error.getDesc());
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    setData();
                    break;
            }
        }
    };
}
