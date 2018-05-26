package cn.com.zwwl.bayuwen.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.api.CDeatailApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.TeacherModel;
import cn.com.zwwl.bayuwen.view.PagerSlidingTabStrip;
import cn.com.zwwl.bayuwen.widget.CustomViewPager;

/**
 * 课程详情页面
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
    private TextView title_tv;
    private PagerSlidingTabStrip psts;
    private CustomViewPager mViewPager;

    private List<TeacherModel> teacherList = new ArrayList<>();

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
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
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

        title_tv.setText("课程详情");

        findViewById(R.id.back_btn).setOnClickListener(this);
        sign_up_tv.setOnClickListener(this);
        group_purchase_tv.setOnClickListener(this);

        getTeacherDetailList(cid);
    }

    private void setData() {

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
                handler.sendEmptyMessage(0);
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
