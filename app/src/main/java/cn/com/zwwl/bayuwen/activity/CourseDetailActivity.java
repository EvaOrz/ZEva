package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.MyViewPagerAdapter;
import cn.com.zwwl.bayuwen.api.CourseApi;
import cn.com.zwwl.bayuwen.api.CourseListApi;
import cn.com.zwwl.bayuwen.api.FollowApi;
import cn.com.zwwl.bayuwen.api.LeasonListApi;
import cn.com.zwwl.bayuwen.api.fm.PinglunApi;
import cn.com.zwwl.bayuwen.api.order.CartApi;
import cn.com.zwwl.bayuwen.api.order.KaiTuanbyCodeApi;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.LessonModel;
import cn.com.zwwl.bayuwen.model.PromotionModel;
import cn.com.zwwl.bayuwen.model.TeacherModel;
import cn.com.zwwl.bayuwen.model.fm.PinglunModel;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.view.KeDetailView1;
import cn.com.zwwl.bayuwen.view.KeDetailView2;
import cn.com.zwwl.bayuwen.view.KeDetailView3;
import cn.com.zwwl.bayuwen.widget.CircleImageView;
import cn.com.zwwl.bayuwen.widget.CustomViewPager;

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
    private ImageView follow_status;
    private TextView title_tv, add_tv, sure_tv, duihuan_tv;
    private LinearLayout nomalFooter;
    private LinearLayout promotionLayout, promotionContain;

    private LinearLayout teacherLayout, groupLayout;

    private List<View> keDetailViews = new ArrayList<>();
    private CustomViewPager mViewPager;
    private MyViewPagerAdapter myViewPagerAdapter;

    private KeModel keModel;
    private String cid;
    private String code; // 兑换的课程码
    private KeDetailView1 cDetailTabFrag1;
    private KeDetailView2 cDetailTabFrag2;
    private KeDetailView3 cDetailTabFrag3;
    private List<PinglunModel> pinglunModels = new ArrayList<>();
    private List<LessonModel> lesonModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        initView();
        // 兑换课程详情
        if (getIntent().getSerializableExtra("CourseDetailActivity_data") != null && getIntent()
                .getSerializableExtra("CourseDetailActivity_data") instanceof KeModel) {
            keModel = (KeModel) getIntent().getSerializableExtra("CourseDetailActivity_data");
            cid = keModel.getKid();
            code = getIntent().getStringExtra("CourseDetailActivity_id");
            setUi();
            setkeData();
        } else {// nomal情况课程详情
            cid = getIntent().getStringExtra("CourseDetailActivity_id");
            initData();
        }
        getPinglunData();
        getLeasonsData();
    }

    private void setUi() {
        title_tv.setText("确认课程详情");
        duihuan_tv.setVisibility(View.VISIBLE);
        add_tv.setVisibility(View.GONE);
        sure_tv.setVisibility(View.VISIBLE);
        nomalFooter.setVisibility(View.GONE);
    }

    /**
     * 获取评论列表
     */
    private void getPinglunData() {
        new PinglunApi(mContext, cid, null, new FetchEntryListListener() {
            @Override
            public void setData(List list) {
                if (Tools.listNotNull(list)) {
                    pinglunModels.clear();
                    pinglunModels.addAll(list);
                    handler.sendEmptyMessage(1);
                }
            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });
    }

    /**
     * 获取子课列表
     */
    private void getLeasonsData() {
        new LeasonListApi(mContext, cid, 1, new FetchEntryListListener() {
            @Override
            public void setData(List list) {
                if (Tools.listNotNull(list)) {
                    lesonModels.clear();
                    lesonModels.addAll(list);
                    handler.sendEmptyMessage(4);
                }
            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });

    }

    @Override
    protected void initData() {
        cid = "209";
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
        follow_status = findViewById(R.id.follow_status);
        duihuan_tv = findViewById(R.id.duihuan_hint);
        title_tv = findViewById(R.id.ke_title);
        add_tv = findViewById(R.id.ke_add);
        sure_tv = findViewById(R.id.duihuan_footer);
        nomalFooter = findViewById(R.id.nomal_footer);
        promotionLayout = findViewById(R.id.promotion_layout);
        promotionContain = findViewById(R.id.promotion_container);
        cDetailTabFrag1 = new KeDetailView1(mContext);
        cDetailTabFrag2 = new KeDetailView2(mContext);
        cDetailTabFrag3 = new KeDetailView3(mContext);
        keDetailViews.add(cDetailTabFrag1);
        keDetailViews.add(cDetailTabFrag2);
        keDetailViews.add(cDetailTabFrag3);
        myViewPagerAdapter = new MyViewPagerAdapter(keDetailViews);
        mViewPager.setAdapter(myViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(keDetailViews.size());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                changeRadio(position);
                mViewPager.resetHeight(position);
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

        groupLayout = findViewById(R.id.group_purchase_bt1);
        findViewById(R.id.ke_back).setOnClickListener(this);
        groupLayout.setOnClickListener(this);
        findViewById(R.id.group_purchase_bt2).setOnClickListener(this);
        add_tv.setOnClickListener(this);
        sure_tv.setOnClickListener(this);
        findViewById(R.id.followtv).setOnClickListener(this);
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
                if (keModel.getGroupbuy() != null) {
                    Intent i = new Intent(mContext, TuanIndexActivity.class);
                    i.putExtra("TuanIndexActivity_data", keModel);
                    startActivity(i);
                }
                break;
            case R.id.group_purchase_bt2: //单独报名
                Intent j = new Intent(mContext, TuanPayActivity.class);
                j.putExtra("TuanPayActivity_type", 2);
                j.putExtra("TuanPayActivity_data", keModel);
                j.putExtra("TuanPayActivity_code", "");
                startActivity(j);
                break;
            case R.id.ke_add:// 加入购物车
                showLoadingDialog(true);
                new CartApi(mContext, keModel.getKid(), new FetchEntryListener() {
                    @Override
                    public void setData(Entry entry) {

                    }

                    @Override
                    public void setError(ErrorMsg error) {
                        showLoadingDialog(false);
                        if (error != null) showToast(error.getDesc());
                        else showToast("加入购课单成功");
                    }
                });
                break;
            case R.id.followtv:// 关注
                doFollow();
                break;
            case R.id.duihuan_footer:// 兑换
                doKaitongBycode(code);
                break;
        }
    }

    /**
     * 根据开团码开通课程
     *
     * @param code
     */
    private void doKaitongBycode(String code) {
        showLoadingDialog(true);
        new KaiTuanbyCodeApi(mContext, code, new FetchEntryListener() {
            @Override
            public void setError(ErrorMsg error) {
                showLoadingDialog(false);
                if (error == null) {// 没有错误信息，则操作成功
                    startActivity(new Intent(mContext, TuanPayResultActivity.class));
                    finish();
                } else {
                    showToast(error.getDesc());
                }

            }

            @Override
            public void setData(Entry entry) {

            }
        });
    }

    /**
     * 添加、取消关注
     */
    private void doFollow() {
        showLoadingDialog(true);
        new FollowApi(mContext, keModel.getKid(), 1, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                showLoadingDialog(false);
                if (entry != null && entry instanceof ErrorMsg) {
                    ErrorMsg errorMsg = (ErrorMsg) entry;
                    if (errorMsg.getNo() == 1)
                        handler.sendEmptyMessage(2);
                    else handler.sendEmptyMessage(3);
                }
            }

            @Override
            public void setError(ErrorMsg error) {
                showLoadingDialog(false);
            }
        });
    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    setkeData();
                    break;
                case 1:
                    cDetailTabFrag3.setData(pinglunModels);
                    break;
                case 4:
                    cDetailTabFrag1.setData(lesonModels);
                    break;
                case 2:// 关注状态
                    follow_status.setBackgroundColor(getResources().getColor(R.color.gold));
                    break;
                case 3:// 未关注状态
                    follow_status.setBackgroundColor(getResources().getColor(R.color.gray_dark));
                    break;

            }
        }
    };

    /**
     * 设置课程信息
     */
    private void setkeData() {
        course_tv.setText(keModel.getTitle());
        classno_tv.setText("班级编码：" + keModel.getModel());
        price_tv.setText("￥ " + keModel.getBuyPrice());

        ImageLoader.display(mContext, course_iv, keModel.getPic(), R
                .drawable.avatar_placeholder, R.drawable.avatar_placeholder);
        place_tv.setText(keModel.getSchool());
        teacher_tv.setText(keModel.getTname());
        date_tv.setText(CalendarTools.format(Long.valueOf(keModel.getStartPtime()),
                "yyyy-MM-dd") + " 至 " + CalendarTools.format(Long.valueOf(keModel
                        .getEndPtime()),
                "yyyy-MM-dd"));
        time_tv.setText(keModel.getClass_start_at() + " - " + keModel.getClass_end_at
                ());
        priceTv2.setText("￥" + keModel.getBuyPrice());

        teacherLayout.removeAllViews();
        for (TeacherModel t : keModel.getTeacherModels())
            teacherLayout.addView(getTeacherView(t));

        if (keModel.getGroupbuy() != null) {
            groupLayout.setVisibility(View.VISIBLE);
            priceTv1.setText("￥" + keModel.getGroupbuy().getDiscount_pintrice());
        } else {
            groupLayout.setVisibility(View.INVISIBLE);
        }
        if (Tools.listNotNull(keModel.getPromotionModels())) {
            promotionLayout.setVisibility(View.VISIBLE);
            promotionContain.removeAllViews();
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(MyApplication.width
                    / 2, LinearLayout.LayoutParams.WRAP_CONTENT);
            for (int i = 0; i < keModel.getPromotionModels().size(); i++) {
                promotionContain.addView(getPromotionView(i, keModel.getPromotionModels().get(i))
                        , params);
            }

        } else {
            promotionLayout.setVisibility(View.GONE);
        }
        cDetailTabFrag2.setData(keModel);
    }

    private View getTeacherView(final TeacherModel teacherModel) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_cdetail_teacher, null);
        CircleImageView avatar = view.findViewById(R.id.cdetail_t_avatar);
        TextView name = view.findViewById(R.id.cdetail_t_name);
        ImageLoader.display(mContext, avatar, teacherModel.getPic(), R
                .drawable.avatar_placeholder, R.drawable.avatar_placeholder);
        name.setText(teacherModel.getName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, TeacherDetailActivity.class);
                i.putExtra("tid", teacherModel.getTid());
                startActivity(i);
            }
        });
        return view;
    }

    private View getPromotionView(final int position, final PromotionModel promotionModel) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_cdetail_promotion, null);
        ImageView img = view.findViewById(R.id.cdetail_t_pic);
        TextView name = view.findViewById(R.id.cdetail_t_name);
        TextView price = view.findViewById(R.id.cdetail_t_price);
        TextView youhui = view.findViewById(R.id.cdetail_t_youhui);

        name.setText("套餐" + (position + 1) + "：");
        price.setText("￥" + promotionModel.getOriginal_price());
        youhui.setText("已优惠：￥" + promotionModel.getDiscount_price());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, PromotionActivity.class);
                i.putExtra("PromotionActivity_data", keModel);
                i.putExtra("PromotionActivity_position", position);
                startActivity(i);
            }
        });
        view.findViewById(R.id.cdetail_t_buy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, TeacherDetailActivity.class);
//                i.putExtra("tid", teacherModel.getTid());
                startActivity(i);
            }
        });
        return view;
    }
}
