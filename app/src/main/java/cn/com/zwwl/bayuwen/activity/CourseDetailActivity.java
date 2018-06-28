package cn.com.zwwl.bayuwen.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.MyViewPagerAdapter;
import cn.com.zwwl.bayuwen.api.CourseApi;
import cn.com.zwwl.bayuwen.api.fm.CollectionApi;
import cn.com.zwwl.bayuwen.api.fm.PinglunApi;
import cn.com.zwwl.bayuwen.api.order.CartApi;
import cn.com.zwwl.bayuwen.api.order.CouponApi;
import cn.com.zwwl.bayuwen.api.order.KaiTuanbyCodeApi;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.CouponModel;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.PromotionModel;
import cn.com.zwwl.bayuwen.model.TeacherModel;
import cn.com.zwwl.bayuwen.model.fm.PinglunModel;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.view.KeDetailView1;
import cn.com.zwwl.bayuwen.view.KeDetailView2;
import cn.com.zwwl.bayuwen.view.KeDetailView3;
import cn.com.zwwl.bayuwen.view.YouHuiJuanPopWindow;
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
    private TextView cantuan_tv;
    private TextView baoman_tv;
    private TextView youhuiBt;
    private RadioGroup radioGroup;
    private RadioButton button1, button2, button3;
    private View line1, line2, line3;
    private ImageView follow_status;
    private TextView title_tv, add_tv, sure_tv, duihuan_tv;
    private LinearLayout nomalFooter;
    private LinearLayout promotionLayout, promotionContain;

    private LinearLayout teacherLayout, groupLayout, buyLayout;

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
    // 优惠券数据
    private List<CouponModel> couponModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
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
        initView();
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

    }

    private void initView() {
        course_tv = findViewById(R.id.course_tv);
        classno_tv = findViewById(R.id.classno_tv);
        youhuiBt = findViewById(R.id.youhui_layout);
        price_tv = findViewById(R.id.price_tv);
        course_iv = findViewById(R.id.course_iv);
        place_tv = findViewById(R.id.place_tv);
        teacher_tv = findViewById(R.id.teacher_tv);
        time_tv = findViewById(R.id.time_tv);
        date_tv = findViewById(R.id.date_tv);
        priceTv1 = findViewById(R.id.group_purchase_price1);
        priceTv2 = findViewById(R.id.group_purchase_price2);
        cantuan_tv = findViewById(R.id.group_purchase_price4);
        baoman_tv = findViewById(R.id.group_purchase_price3);
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
        cDetailTabFrag1 = new KeDetailView1(this, cid);
        cDetailTabFrag2 = new KeDetailView2(mContext);
        cDetailTabFrag3 = new KeDetailView3(this, cid);
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
                if (position == 0)
                    radioGroup.check(R.id.course_d_bt1);
                else if (position == 1) {
                    radioGroup.check(R.id.course_d_bt2);
                } else if (position == 2) {
                    radioGroup.check(R.id.course_d_bt3);
                }
                mViewPager.resetHeight(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        radioGroup = findViewById(R.id.course_d_group);
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
        buyLayout = findViewById(R.id.group_purchase_bt2);
        buyLayout.setOnClickListener(this);
        add_tv.setOnClickListener(this);
        sure_tv.setOnClickListener(this);
        youhuiBt.setOnClickListener(this);
        findViewById(R.id.explainTv).setOnClickListener(this);
        findViewById(R.id.adviserTv).setOnClickListener(this);
        findViewById(R.id.followtv).setOnClickListener(this);
        findViewById(R.id.course_d_play).setOnClickListener(this);
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

    /**
     * @param oid
     * @param type 1：未完成、2：已完成
     */
    private void goOrderDetail(String oid, int type) {
        Intent intent = new Intent(mContext, OrderDetailActivity.class);
        intent.putExtra("OrderDetailActivity_data", oid);
        intent.putExtra("OrderDetailActivity_type", type);
        startActivity(intent);
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
                    if (keModel.getGroupbuy().getState() == 1) {
                        goOrderDetail(keModel.getGroupbuy().getOid() + "", 1);
                    } else if (keModel.getGroupbuy().getState() == 2) {
                        goOrderDetail(keModel.getGroupbuy().getOid() + "", 2);
                    } else {
                        Intent i = new Intent(mContext, TuanIndexActivity.class);
                        i.putExtra("TuanIndexActivity_data", keModel);
                        startActivity(i);
                    }
                }
                break;
            case R.id.group_purchase_bt2: //单独报名
                int leftNo = Integer.valueOf(keModel.getNum());
                if (leftNo == 0) {
                    showToast("该班已报满");
                } else {
                    Intent j = new Intent(mContext, PayActivity.class);
                    j.putExtra("TuanPayActivity_type", 2);
                    j.putExtra("TuanPayActivity_data", keModel);
                    j.putExtra("TuanPayActivity_code", "");
                    startActivity(j);
                }
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
            case R.id.adviserTv:// 拨打顾问电话
                String number = "10086";
                //用intent启动拨打电话
                try {
                    if (askPermission(new String[]{Manifest.permission.CALL_PHONE}, 101)) {
                        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number)));
                    }
                } catch (SecurityException e) {
                }


                break;
            case R.id.explainTv:// 说明
                startActivity(new Intent(mContext, WebActivity.class));
                break;
            case R.id.duihuan_footer:// 兑换
                doKaitongBycode(code);
                break;
            case R.id.youhui_layout:// 领取优惠券
                new YouHuiJuanPopWindow(mContext, couponModels);
                break;
            case R.id.course_d_play:// 去播放
                Intent i = new Intent(mContext, VideoPlayActivity.class);
                i.putExtra("VideoPlayActivity_url", keModel.getVideo());
                i.putExtra("VideoPlayActivity_pic", keModel.getPic());
                startActivity(i);
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
        if (keModel.getCollection_state() == 1) {
            new CollectionApi(mContext, keModel.getCollectionId(), new FetchEntryListener() {
                @Override
                public void setData(Entry entry) {

                }

                @Override
                public void setError(ErrorMsg error) {
                    showLoadingDialog(false);
                    if (error != null)
                        showToast(error.getDesc());
                    else {
                        keModel.setCollection_state(0);
                        handler.sendEmptyMessage(3);
                    }

                }
            });
        } else {
            new CollectionApi(mContext, keModel.getKid(), 1, new FetchEntryListener() {
                @Override
                public void setData(Entry entry) {
                    showLoadingDialog(false);
                    if (entry != null && entry instanceof ErrorMsg) {
                        ErrorMsg errorMsg = (ErrorMsg) entry;
                        keModel.setCollection_state(errorMsg.getNo());
                        handler.sendEmptyMessage(3);
                    }
                }

                @Override
                public void setError(ErrorMsg error) {
                    showLoadingDialog(false);
                    if (error != null)
                        showToast(error.getDesc());
                }
            });
        }

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
                case 3:// 关注状态
                    setFollow_status();
                    break;
                case 5:// 显示领取优惠券
                    youhuiBt.setVisibility(View.VISIBLE);
                    break;

            }
        }
    };

    private void setFollow_status() {
        if (keModel.getCollection_state() == 1) {
            follow_status.setImageResource(R.mipmap.icon_star_yellow);
        } else {
            follow_status.setImageResource(R.mipmap.icon_star_default);
        }

    }

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

        String startTime = keModel.getClass_start_at();
        String endtime = keModel.getClass_end_at();
        time_tv.setText(startTime.substring(0, startTime.length() - 3) + " - " + endtime
                .substring(0, endtime.length() - 3));

        priceTv2.setText("￥" + keModel.getBuyPrice());

        teacherLayout.removeAllViews();
        for (TeacherModel t : keModel.getTeacherModels())
            teacherLayout.addView(getTeacherView(t));

        // 已报满的班显示灰色
        int leftNo = Integer.valueOf(keModel.getNum());
        if (leftNo == 0) {
            add_tv.setVisibility(View.GONE);
            groupLayout.setVisibility(View.INVISIBLE);
            buyLayout.setBackgroundResource(R.drawable.gray_circle);
            priceTv2.setVisibility(View.GONE);
            baoman_tv.setText("已报满");
        }

        // 已参团的情况
        if (keModel.getGroupbuy() != null) {
            groupLayout.setVisibility(View.VISIBLE);
            if (keModel.getGroupbuy().getState() == 1 || keModel.getGroupbuy().getState() == 2) {
                priceTv1.setVisibility(View.GONE);
                cantuan_tv.setText("已参团");
            } else {
                priceTv1.setVisibility(View.VISIBLE);
                cantuan_tv.setText("团购报名");
                priceTv1.setText("￥" + keModel.getGroupbuy().getDiscount().getDiscount_price());
            }
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
        // 获取到课程信息之后  开始获取优惠券信息
        checkYouhui();
        setFollow_status();
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
                Intent i = new Intent(mContext, PayActivity.class);
                i.putExtra("TuanPayActivity_promo", keModel.getPromotionModels().get(position));
                i.putExtra("TuanPayActivity_type", 4);
                startActivity(i);
            }
        });
        return view;
    }

    /**
     * 检查是否有优惠券
     */
    private void checkYouhui() {
        new CouponApi(mContext, 1, keModel.getKid(), new FetchEntryListListener() {
            @Override
            public void setData(List list) {
                if (Tools.listNotNull(list)) {
                    couponModels.clear();
                    couponModels.addAll(list);
                    handler.sendEmptyMessage(5);
                }
            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 101:
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Log.e("ppppppppphas", permissions[i]);
                    } else {
                        Log.e("pppppppppno", permissions[i]);
                    }
                }
                break;
        }

    }
}
