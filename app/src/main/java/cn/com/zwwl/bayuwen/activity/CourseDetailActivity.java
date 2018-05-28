package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.CoursePageAdapter;
import cn.com.zwwl.bayuwen.api.CourseApi;
import cn.com.zwwl.bayuwen.api.fm.PinglunApi;
import cn.com.zwwl.bayuwen.fragment.CDetailTabFrag1;
import cn.com.zwwl.bayuwen.fragment.CDetailTabFrag2;
import cn.com.zwwl.bayuwen.fragment.CDetailTabFrag3;
import cn.com.zwwl.bayuwen.glide.GlideApp;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ChildModel;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.TeacherModel;
import cn.com.zwwl.bayuwen.model.fm.PinglunModel;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.widget.CircleImageView;

/**
 * 课程详情页面
 * Created by lousx on 2018/5/16.
 */
public class CourseDetailActivity extends BaseActivity {
    private TextView course_tv;
    private TextView classno_tv;
    private TextView price_tv;
    private ImageView course_iv;
    private TextView place_tv;
    private TextView teacher_tv;
    private TextView time_tv;
    private TextView date_tv;
    private TextView priceTv1;
    private TextView priceTv2;
    private RadioButton button1, button2, button3;
    private View line1, line2, line3;

    private LinearLayout teacherLayout;

    private List<Fragment> fragments = new ArrayList<>();
    private ViewPager mViewPager;
    private CoursePageAdapter myViewPagerAdapter;

    private KeModel keModel;
    private String cid;
    private CDetailTabFrag1 cDetailTabFrag1;
    private CDetailTabFrag2 cDetailTabFrag2;
    private CDetailTabFrag3 cDetailTabFrag3;
    private List<PinglunModel> pinglunModels = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        cid = getIntent().getStringExtra("CourseDetailActivity_id");
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void initData() {
        new CourseApi(mContext, cid, new FetchEntryListener() {

            @Override
            public void setData(Entry entry) {
                if (entry != null && entry instanceof KeModel) {
                    keModel = (KeModel) entry;
                    handler.sendEmptyMessage(0);
                }

            }

            @Override
            public void setError(ErrorMsg error) {
                if (error != null)
                    showToast(error.getDesc());
            }
        });
        new PinglunApi(mContext, cid, null, new FetchEntryListListener() {
            @Override
            public void setData(List list) {
                if (Tools.listNotNull(list)) {
//                    keModel = (KeModel) entry;
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });
    }

    private void initView() {
        course_tv = findViewById(R.id.course_tv);
        classno_tv = findViewById(R.id.classno_tv);
        price_tv = findViewById(R.id.price_tv);
        course_iv = findViewById(R.id.course_iv);
        place_tv = findViewById(R.id.place_tv);
        teacher_tv = findViewById(R.id.teacher_tv);
        time_tv = findViewById(R.id.time_tv);
        date_tv = findViewById(R.id.date_tv);
        priceTv1 = findViewById(R.id.group_purchase_price1);
        priceTv2 = findViewById(R.id.group_purchase_price2);
        teacherLayout = findViewById(R.id.teacher_layout);
        mViewPager = findViewById(R.id.videoList_vp);

        cDetailTabFrag1 = CDetailTabFrag1.newInstance();
        cDetailTabFrag2 = CDetailTabFrag2.newInstance();
        cDetailTabFrag3 = CDetailTabFrag3.newInstance("ss");
        fragments.add(cDetailTabFrag1);
        fragments.add(cDetailTabFrag2);
        fragments.add(cDetailTabFrag3);
        myViewPagerAdapter = new CoursePageAdapter(getSupportFragmentManager(),
                fragments, null);
        mViewPager.setAdapter(myViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(fragments.size());
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
        button1 = findViewById(R.id.course_d_bt1);
        button2 = findViewById(R.id.course_d_bt2);
        button3 = findViewById(R.id.course_d_bt3);
        line1 = findViewById(R.id.course_d_line1);
        line2 = findViewById(R.id.course_d_line2);
        line3 = findViewById(R.id.course_d_line3);

        button1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    changeRadio(0);

            }
        });
        button2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    changeRadio(1);
            }
        });
        button3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    changeRadio(2);
            }
        });

        findViewById(R.id.ke_back).setOnClickListener(this);
        findViewById(R.id.group_purchase_bt1).setOnClickListener(this);
        findViewById(R.id.group_purchase_bt2).setOnClickListener(this);

    }

    /**
     * 切换fragment
     *
     * @param position
     */
    private void changeRadio(int position) {
        mViewPager.setCurrentItem(position);
        if (position == 0) {
            line1.setBackgroundColor(getResources().getColor(R.color.gold));
        } else {
            line1.setBackgroundColor(getResources().getColor(R.color.transparent));
        }
        if (position == 1) {
            line2.setBackgroundColor(getResources().getColor(R.color.gold));
        } else {
            line2.setBackgroundColor(getResources().getColor(R.color.transparent));
        }
        if (position == 2) {
            line3.setBackgroundColor(getResources().getColor(R.color.gold));
        } else {
            line3.setBackgroundColor(getResources().getColor(R.color.transparent));
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.ke_back:
                finish();
                break;
            case R.id.group_purchase_bt1: //团购报名
                Intent i = new Intent(mContext, TuanIndexActivity.class);
                i.putExtra("TuanIndexActivity_data", keModel);
                startActivity(i);
                break;
            case R.id.group_purchase_bt2: //单独报名
                Intent j = new Intent(mContext, TuanPayActivity.class);
                j.putExtra("TuanPayActivity_data", keModel);
                startActivity(j);
                break;
        }
    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    course_tv.setText(keModel.getTitle());
                    classno_tv.setText("班级编码：" + keModel.getModel());
                    price_tv.setText("￥ " + keModel.getBuyPrice());
                    GlideApp.with(mContext)
                            .load(keModel.getPic())
                            .placeholder(R.drawable.avatar_placeholder)
                            .error(R.drawable.avatar_placeholder)
                            .into(course_iv);
                    place_tv.setText(keModel.getSchool());
                    teacher_tv.setText(keModel.getTname());
                    date_tv.setText(CalendarTools.format(Long.valueOf(keModel.getStartPtime()),
                            "yyyy-MM-dd") + " 至 " + CalendarTools.format(Long.valueOf(keModel
                                    .getEndPtime()),
                            "yyyy-MM-dd"));
                    time_tv.setText(keModel.getClass_start_at() + " - " + keModel.getClass_end_at
                            ());
                    teacherLayout.removeAllViews();
                    for (TeacherModel t : keModel.getTeacherModels())
                        teacherLayout.addView(getTeacherView(t));
                    priceTv1.setText("￥" + keModel.getGroupbuy().getDiscount_pintrice());
                    priceTv2.setText("￥" + keModel.getGroupbuy().getDiscount_pintrice());

                    cDetailTabFrag1.setData(keModel.getLessonModels());
                    cDetailTabFrag2.setData(keModel);
                    break;

            }
        }
    };

    private View getTeacherView(TeacherModel teacherModel) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_cdetail_teacher, null);
        CircleImageView avatar = view.findViewById(R.id.cdetail_t_avatar);
        TextView name = view.findViewById(R.id.cdetail_t_name);
        GlideApp.with(mContext)
                .load(teacherModel.getPic())
                .placeholder(R.drawable.avatar_placeholder)
                .error(R.drawable.avatar_placeholder)
                .into(avatar);
        name.setText(teacherModel.getName());
        return view;
    }
}
